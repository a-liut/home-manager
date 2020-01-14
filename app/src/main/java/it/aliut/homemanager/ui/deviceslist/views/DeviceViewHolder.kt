package it.aliut.homemanager.ui.deviceslist.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.ui.deviceslist.DeviceAdapter
import kotlinx.android.synthetic.main.item_deviceitem.view.*

class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        device: Device?,
        clickListener: DeviceAdapter.OnItemClickListener
    ) {
        device?.let {
            with(itemView) {
                textview_deviceitem_name.text = device.name
                textview_deviceitem_address.text = device.address
                textview_deviceitem_lastupdate.text =
                    context.getString(R.string.deviceitem_lastupdate, device.updatedAt)

                setOnClickListener {
                    clickListener.onDeviceClicked(device)
                }
            }
        }
    }
}