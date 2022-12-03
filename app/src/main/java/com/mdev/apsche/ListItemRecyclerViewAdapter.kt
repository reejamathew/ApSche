package com.mdev.apsche

import android.util.Log
import android.util.Log.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mdev.apsche.model.Apartment
import com.mdev.apsche.placeholder.PlaceholderContent.PlaceholderItem


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ListItemRecyclerViewAdapter(
    private val aptList: List<Apartment>
) : RecyclerView.Adapter<ListItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list, parent, false)
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //setting each view value
        val aptDetailModel = aptList[position]
        holder.aptNo.text = aptDetailModel.aptNo.toString()
        holder.tenant.text=aptDetailModel.tenant_name
        holder.phone.text=aptDetailModel.phone_no
       var  apartId = aptList[position].aptId!!

        //click action
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(apartId)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {

        return aptList.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val aptNo: TextView = itemView.findViewById(R.id.aptNo)
        val tenant: TextView = itemView.findViewById(R.id.aptTenantName)
        val phone: TextView = itemView.findViewById(R.id.aptTenantPhone)


    }
}