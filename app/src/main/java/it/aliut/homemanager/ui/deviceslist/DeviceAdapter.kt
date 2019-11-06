package it.aliut.homemanager.ui.deviceslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Device
import it.aliut.homemanager.ui.deviceslist.views.DeviceViewHolder

class DeviceAdapter(private val clickListener: OnItemClickListener) :
    PagedListAdapter<Device, DeviceViewHolder>(diffCallback) {

    interface OnItemClickListener {
        fun onDeviceClicked(device: Device)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_deviceitem, parent, false)

        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = getItem(position)

        holder.bind(device, clickListener)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem == newItem
            }

        }
    }
}