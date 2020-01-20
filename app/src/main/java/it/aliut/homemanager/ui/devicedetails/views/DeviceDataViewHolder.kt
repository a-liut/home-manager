package it.aliut.homemanager.ui.devicedetails.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.ui.devicedetails.DeviceDataAdapter
import kotlinx.android.synthetic.main.item_devicedata.view.*

class DeviceDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        data: Data?,
        clickListener: DeviceDataAdapter.OnItemClickListener
    ) {
        data?.let {
            with(itemView) {
                textview_devicedata_name.text = data.name
                textview_devicedata_value.text =
                    context.getString(R.string.devicedata_value, data.value)
                textview_devicedata_unit.text =
                    context.getString(R.string.devicedata_unit, data.unit)

                setOnClickListener {
                    clickListener.onDeviceDataClicked(data)
                }
            }
        }
    }
}