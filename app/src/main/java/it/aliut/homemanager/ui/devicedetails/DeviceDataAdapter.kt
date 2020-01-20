package it.aliut.homemanager.ui.devicedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import it.aliut.homemanager.R
import it.aliut.homemanager.model.Data
import it.aliut.homemanager.ui.devicedetails.views.DeviceDataViewHolder

class DeviceDataAdapter(private val clickListener: OnItemClickListener) :
    PagedListAdapter<Data, DeviceDataViewHolder>(diffCallback) {

    interface OnItemClickListener {
        fun onDeviceDataClicked(data: Data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceDataViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_devicedata, parent, false)

        return DeviceDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceDataViewHolder, position: Int) {
        val deviceData = getItem(position)

        holder.bind(deviceData, clickListener)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }
}