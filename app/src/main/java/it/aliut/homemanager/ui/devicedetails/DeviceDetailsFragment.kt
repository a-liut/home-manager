package it.aliut.homemanager.ui.devicedetails

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.net.RequestState
import kotlinx.android.synthetic.main.fragment_devicedetails.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeviceDetailsFragment : Fragment(), DeviceDataAdapter.OnItemClickListener {

    companion object {
        fun newInstance(deviceId: String) = DeviceDetailsFragment().apply {
            id = deviceId
        }
    }

    private lateinit var id: String

    private val viewModel: DeviceDetailsViewModel by viewModel { parametersOf(id) }

    private lateinit var adapter: DeviceDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_devicedetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        viewModel.devicesData.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
        })

        viewModel.lastUpdate.observe(this, Observer { time ->
            textview_devicedetails_lastupdate.text = DateUtils.formatDateTime(
                activity,
                time,
                DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_ALL
            )
        })

        viewModel.state.observe(this, Observer { state ->
            if (state == RequestState.ERROR) {
                Toast.makeText(context, "Cannot get device's data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun prepareRecyclerView() {
        recyclerview_devicesdata_datalist.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = DeviceDataAdapter(this)

        recyclerview_devicesdata_datalist.adapter = adapter
    }

    override fun onDeviceDataClicked(data: Data) {
        // Do nothing, by now
    }

}
