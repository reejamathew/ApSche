package com.mdev.apsche

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdev.apsche.database.ApartmentDatabase


class NoticeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ApScheConstValues.showMenu = true
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.invalidateOptionsMenu()
        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notice, container, false)

        //intializing database and fetching values
        val database = ApartmentDatabase(requireActivity())
        val arrayList= database.getApartmentDetailsByNoticeInformation("notice",ApScheConstValues.useremail)

        // Set the adapter
        val itemList: RecyclerView = view.findViewById(R.id.aptNoticeList)
        itemList.layoutManager = LinearLayoutManager(view.context);
        val aptAdapter = NoticeItemRecyclerViewAdapter(arrayList)
        itemList.adapter =aptAdapter

        //button and action
        val homeButton =  view.findViewById<ImageButton>(R.id.homeButton)
        homeButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_noticeFragment_to_homeFragment)
        }

        return view
    }



}