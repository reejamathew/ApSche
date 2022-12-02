package com.mdev.apsche

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.mdev.apsche.database.ApartmentDatabase


class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this@DetailsFragment.requireActivity(), "Apartment details added successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.Aptno).text = requireArguments().getString("aptNo")
        view.findViewById<TextView>(R.id.tenant_name).text = requireArguments().getString("tenant_name")
        view.findViewById<TextView>(R.id.phone_no).text = requireArguments().getString("phone_no")
        view.findViewById<TextView>(R.id.lease_period).text = requireArguments().getString("lease_period")
        view.findViewById<TextView>(R.id.lease_amount).text = requireArguments().getString("lease_amount")
        view.findViewById<TextView>(R.id.beds).text = requireArguments().getString("beds")

        val editButton = view.findViewById<Button>(R.id.editButton);

        editButton.setOnClickListener(View.OnClickListener {
            view.findNavController().navigate(R.id.action_detailsFragment_to_editFragment, Bundle().apply {
                putString("aptId", "1")
            })
        })
    }

}