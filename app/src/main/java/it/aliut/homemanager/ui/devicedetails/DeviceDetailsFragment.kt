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
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import it.aliut.homemanager.R
import it.aliut.homemanager.net.RequestState
import kotlinx.android.synthetic.main.fragment_devicedetails.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DeviceDetailsFragment : Fragment() {

    private lateinit var id: String

    private val viewModel: DeviceDetailsViewModel by viewModel { parametersOf(id) }

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

        viewModel.device.observe(this, Observer { device ->
            device.pictureUrl?.let { pictureUrl ->
                Picasso.get()
                    .load(pictureUrl)
                    .into(backdrop)
            }
            collapsing_toolbar.title = device.name
            collapsing_toolbar.subtitle = device.address
        })

        viewModel.state.observe(this, Observer { state ->
            if (state == RequestState.ERROR) {
                Toast.makeText(context, "Cannot get device's data", Toast.LENGTH_LONG).show()
            }
        })

        viewpager_devicesdetails_pager.adapter = DeviceDetailsPagerAdapter(this)
        TabLayoutMediator(
            viewpager_devicesdetails_tab_layout,
            viewpager_devicesdetails_pager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_devicedata)
                1 -> getString(R.string.tab_plots)
                else -> ""
            }
        }.attach()
    }

    private inner class DeviceDetailsPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> DeviceDataListFragment.newInstance()
            else -> throw RuntimeException("Invalid position")
        }
    }

}
