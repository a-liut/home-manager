package it.aliut.homemanager.ui.deviceslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.net.RequestState
import kotlinx.android.synthetic.main.fragment_deviceslist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DevicesListFragment : Fragment(), DeviceAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = DevicesListFragment()
    }

    private val viewModel: DevicesListViewModel by viewModel()

    private lateinit var adapter: DeviceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_deviceslist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        viewModel.devices.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
        })

        viewModel.state.observe(this, Observer { state ->
            if (state == RequestState.ERROR) {
                Toast.makeText(context, getString(R.string.get_devices_error), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun prepareRecyclerView() {
        recyclerview_deviceslist_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = DeviceAdapter(this)

        recyclerview_deviceslist_list.adapter = adapter
    }

    override fun onDeviceClicked(device: Device) {
        // Do nothing by now
    }

}
