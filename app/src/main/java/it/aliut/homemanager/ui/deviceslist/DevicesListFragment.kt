package it.aliut.homemanager.ui.deviceslist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.net.RequestState
import kotlinx.android.synthetic.main.fragment_deviceslist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DevicesListFragment : Fragment(), DeviceAdapter.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

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
        prepareSwipeRefresh()

        viewModel.devices.observe(this, Observer { pagedList ->
            swiperefreshlayout_deviceslist_swiperefresh.isRefreshing = false

            adapter.submitList(pagedList)
        })

        viewModel.state.observe(this, Observer { state ->
            if (state == RequestState.ERROR) {
                Toast.makeText(context, getString(R.string.get_devices_error), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_listfragment, menu)
    }

    private fun prepareRecyclerView() {
        recyclerview_deviceslist_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = DeviceAdapter(this)

        recyclerview_deviceslist_list.adapter = adapter
    }

    private fun prepareSwipeRefresh() {
        swiperefreshlayout_deviceslist_swiperefresh.setOnRefreshListener(this)
    }

    override fun onDeviceClicked(device: Device) {
        // Do nothing by now
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

}
