package it.aliut.homemanager.ui.devicedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.ui.devicedetails.adapter.DeviceDataAdapter
import kotlinx.android.synthetic.main.fragment_devicedatalist.*
import kotlinx.android.synthetic.main.fragment_devicedatalist.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DeviceDataListFragment : Fragment(), DeviceDataAdapter.OnItemClickListener {

    companion object {
        fun newInstance(deviceId: String) = DeviceDataListFragment().apply {
            this.deviceId = deviceId
        }
    }

    private lateinit var deviceId: String

    private val deviceDetailsViewModel: DeviceDetailsViewModel by sharedViewModel(from = { parentFragment!! })

    private lateinit var adapter: DeviceDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_devicedatalist, container, false)

        prepareRecyclerView(view)

        return view
    }

    private fun prepareRecyclerView(view: View) {
        view.recyclerview_devicedatalist_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = DeviceDataAdapter(this)

        view.recyclerview_devicedatalist_list.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceDetailsViewModel.devicesData.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)

            pagedList.addWeakLoadStateListener { _, state, _ ->
                if (state == PagedList.LoadState.DONE) {
                    textview_devicedatalist_header.text = resources.getQuantityString(
                        R.plurals.devicedetails_data_header,
                        pagedList.loadedCount,
                        pagedList.loadedCount
                    )
                }
            }
        })
    }


    override fun onDeviceDataClicked(data: Data) {
        // Do nothing, by now
    }
}