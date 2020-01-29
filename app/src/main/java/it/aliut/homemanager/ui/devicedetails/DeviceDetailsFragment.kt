package it.aliut.homemanager.ui.devicedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.net.RequestState
import kotlinx.android.synthetic.main.fragment_devicedetails.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeviceDetailsFragment : Fragment(), DeviceDataAdapter.OnItemClickListener {

    private lateinit var id: String

    private val viewModel: DeviceDetailsViewModel by viewModel { parametersOf(id) }

    private lateinit var adapter: DeviceDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_devicedetails, container, false)

        id = DeviceDetailsFragmentArgs.fromBundle(arguments!!).deviceId

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(toolbar, findNavController())

        prepareRecyclerView()

        viewModel.devicesData.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
        })

        viewModel.device.observe(this, Observer { device ->
            collapsing_toolbar.title = device.name
            collapsing_toolbar.subtitle = device.address
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
