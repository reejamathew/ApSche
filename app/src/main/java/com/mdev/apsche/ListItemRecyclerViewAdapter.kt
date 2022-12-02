package com.mdev.apsche

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mdev.apsche.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ListItemRecyclerViewAdapter(
    private val aptList: List<ApartmentMainModel>
) : RecyclerView.Adapter<ListItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item_list, parent, false)
        view.setOnClickListener{

        }
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aptDetailModel = aptList[position]
       holder.aptNo.text = aptDetailModel.aptNo.toString()
        holder.tenant.text=aptDetailModel.tenant
        holder.phone.text=aptDetailModel.phoneno.toString()
    }

    override fun getItemCount(): Int {
        Log.d("appt sixze", aptList.size.toString())
        return aptList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val aptNo: TextView = itemView.findViewById(R.id.aptNo)
        val tenant: TextView = itemView.findViewById(R.id.aptTenantName)
        val phone: TextView = itemView.findViewById(R.id.aptTenantPhone)


    }
}