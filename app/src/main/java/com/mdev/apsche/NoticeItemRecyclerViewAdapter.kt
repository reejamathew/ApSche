package com.mdev.apsche

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController

import com.mdev.apsche.placeholder.PlaceholderContent.PlaceholderItem
import com.mdev.apsche.databinding.FragmentNoticeBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class NoticeItemRecyclerViewAdapter(
    private val aptList: List<ApartmentMainModel>
) : RecyclerView.Adapter<NoticeItemRecyclerViewAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeItemRecyclerViewAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notice_list, parent, false)
        view.setOnClickListener{
             view.findNavController().navigate(R.id.action_noticeFragment_to_detailsFragment)
        }
        return NoticeItemRecyclerViewAdapter.ViewHolder(view)

    }
    override fun onBindViewHolder(holder: NoticeItemRecyclerViewAdapter.ViewHolder, position: Int) {
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
        val aptNo: TextView = itemView.findViewById(R.id.aptNoNotice)
        val tenant: TextView = itemView.findViewById(R.id.aptTenantNameNotice)
        val phone: TextView = itemView.findViewById(R.id.aptTenantPhoneNotice)


    }



}