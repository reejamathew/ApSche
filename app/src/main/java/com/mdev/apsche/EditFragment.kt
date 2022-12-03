package com.mdev.apsche

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.mdev.apsche.database.ApartmentDatabase
import com.mdev.apsche.model.Apartment

class EditFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aptId = requireArguments().getString("aptId")
        val databaseClass = ApartmentDatabase(requireActivity())
        val apartment: Apartment = databaseClass.getApartmentDetailsById(aptId)[0]
        view.findViewById<TextView>(R.id.Aptno).text = apartment.aptNo.toString()
        view.findViewById<TextView>(R.id.tenant_name).text = apartment.tenant_name.toString()
        view.findViewById<TextView>(R.id.phone_no).text = apartment.phone_no.toString()
        view.findViewById<TextView>(R.id.lease_period).text = apartment.lease_information
        view.findViewById<TextView>(R.id.lease_amount).text = apartment.lease_amount.toString()
        view.findViewById<TextView>(R.id.beds).text = apartment.beds_bath
        val editButton = view.findViewById<Button>(R.id.editButton);

        editButton.setOnClickListener(View.OnClickListener {
            //insertion
            val updateAppartment = databaseClass.updateApartment(
                aptId,
                view.findViewById<TextView>(R.id.Aptno).text.toString(),
                view.findViewById<TextView>(R.id.tenant_name).text.toString(),
                view.findViewById<TextView>(R.id.phone_no).text.toString(),
                view.findViewById<TextView>(R.id.lease_period).text.toString(),
                view.findViewById<TextView>(R.id.lease_amount).text.toString(),
                view.findViewById<TextView>(R.id.beds).text.toString(),ApScheConstValues.useremail
            )
        })

//            view.findNavController().navigate(R.id.action_detailsFragment_to_editFragment)

    }
}