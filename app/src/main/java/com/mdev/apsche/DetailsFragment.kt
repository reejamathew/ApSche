package com.mdev.apsche

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.mdev.apsche.database.ApartmentDatabase
import com.mdev.apsche.model.Apartment


class DetailsFragment : Fragment() {
    lateinit var apartment:Apartment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  Toast.makeText(this@DetailsFragment.requireActivity(), "Apartment details added successfully", Toast.LENGTH_SHORT).show()
       val aptId = DetailsFragmentArgs.fromBundle(requireArguments()).apartmentId
       Log.d("details screen",aptId.toString())
        val database = ApartmentDatabase(requireActivity())
        // Set the adapter
        val apartmentList= database.getApartmentDetailsById(aptId.toString())
        apartment = apartmentList[0]
        Log.d("apartment details ",apartment.aptNo.toString()+apartment.tenant_name+apartment.phone_no+apartment.lease_information)

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
        Log.d("apartment no:",apartment.aptNo.toString())
        view.findViewById<TextView>(R.id.Aptno).text = apartment.aptNo.toString()
        view.findViewById<TextView>(R.id.tenant_name).text = apartment.tenant_name
        view.findViewById<TextView>(R.id.phone_no).text = apartment.phone_no
        view.findViewById<TextView>(R.id.lease_period).text = apartment.lease_information
        view.findViewById<TextView>(R.id.lease_amount).text = apartment.lease_amount.toString()
        view.findViewById<TextView>(R.id.beds).text = apartment.beds_bath

        val editButton = view.findViewById<Button>(R.id.editButton);

        editButton.setOnClickListener(View.OnClickListener {
            view.findNavController().navigate(R.id.action_detailsFragment_to_editFragment, Bundle().apply {
               putString("aptId", "1")
            })
        })
        val currencyText = view.findViewById<TextView>(R.id.currencyEditText)
        val currencyButton = view.findViewById<Button>(R.id.currencyConvertButton);

        currencyButton.setOnClickListener(View.OnClickListener {
            if(validateCurrency(currencyText.text.toString())){

            }
            })
        }

    fun validateCurrency(currencytext:String):Boolean{
        val arrayCurrency = arrayOf("USD","INR","AED","EUR")
        return arrayCurrency.contains(currencytext)
    }

}