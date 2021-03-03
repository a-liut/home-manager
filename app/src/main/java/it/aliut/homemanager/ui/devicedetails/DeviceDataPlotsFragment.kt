package it.aliut.homemanager.ui.devicedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.util.randomColor
import kotlinx.android.synthetic.main.fragment_devicedataplots.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DeviceDataPlotsFragment : Fragment() {

    companion object {
        fun newInstance() = DeviceDataPlotsFragment()
    }

    private val deviceDetailsViewModel: DeviceDetailsViewModel by sharedViewModel(from = { parentFragment!! })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_devicedataplots, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceDetailsViewModel.devicesData.observe(this, Observer { pagedList ->
            pagedList.addWeakLoadStateListener { _, state, _ ->
                if (state == PagedList.LoadState.DONE) {
                    updatePlot(pagedList.snapshot())
                }
            }

            pagedList.addWeakCallback(pagedList.snapshot(), object : PagedList.Callback() {
                override fun onChanged(position: Int, count: Int) {
                    updatePlot(pagedList.snapshot())
                }

                override fun onInserted(position: Int, count: Int) {

                }

                override fun onRemoved(position: Int, count: Int) {

                }
            })
        })
    }

    private fun updatePlot(data: List<Data>) {
        chart_devicedataplots_chart.clear()

        val lineData = data
            .groupBy { d -> d.name }.entries
            .map { entry ->
                val plotEntries =
                    entry.value.mapIndexed { index, data ->
                        Entry(index.toFloat(), data.value.toFloat())
                    }

                LineDataSet(plotEntries, entry.key).apply {
                    randomColor().let { c ->
                        color = c
                        setCircleColor(c)
                    }
                }
            }
            .fold(LineData()) { acc, lineDataSet ->
                acc.addDataSet(lineDataSet)
                acc
            }

        chart_devicedataplots_chart.data = lineData
        chart_devicedataplots_chart.invalidate()
    }
}