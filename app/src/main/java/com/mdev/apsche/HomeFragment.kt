package com.mdev.apsche

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mdev.apsche.database.ApartmentDatabase

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate layout
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //setting value to show the menu in actionbar
        ApScheConstValues.showMenu = true
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.invalidateOptionsMenu()
        }

        //intialize database and getting details by email
        val database = ApartmentDatabase(requireActivity())
        val arrayList= database.getApartmentDetails(ApScheConstValues.useremail)

        // Set the adapter
        val itemList: RecyclerView = view.findViewById(R.id.aptMainList)
        itemList.layoutManager = LinearLayoutManager(view.context);
        val aptAdapter = ListItemRecyclerViewAdapter(arrayList)
        itemList.adapter =aptAdapter

        //button and actions
        val noticeButton =  view.findViewById<ImageButton>(R.id.noticeButton)
        noticeButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_homeFragment_to_noticeFragment)
        }
        val addButton = view.findViewById<FloatingActionButton>(R.id.addNew)
        addButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_homeFragment_to_addDetailsFragment)
        }

        return view
    }


}