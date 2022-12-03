package com.mdev.apsche

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.mdev.apsche.database.ApartmentDatabase

class AddDetailsFragment : Fragment() {
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var tenantName: TextView
    private lateinit var aptNo: TextView
    private lateinit var phoneNo: TextView
    private lateinit var leaseAmount: TextView
    //private lateinit var currency:TextView
    private lateinit var leasePeriod: TextView
    private lateinit var beds: TextView


    var filePath: ValueCallback<Array<Uri>>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add_details, container, false)

        //intializing buttons and text fields

        val submitButton = view.findViewById<Button>(R.id.submitButton)
        tenantName = view.findViewById<TextView>(R.id.tenantNameEditText)
        aptNo = view.findViewById<TextView>(R.id.aptNoEditText)
        phoneNo = view.findViewById<TextView>(R.id.phoneNoEditText)
        leaseAmount = view.findViewById<TextView>(R.id.leaseAmountEditText)
        leasePeriod = view.findViewById<TextView>(R.id.leasePeriodEditText)
        beds = view.findViewById<TextView>(R.id.bedsEditText)

        var isAllFieldsChecked = false


        submitButton.setOnClickListener(View.OnClickListener { // store the returned value of the dedicated function which checks
            // whether the entered data is valid or if any fields are left blank.
            isAllFieldsChecked = checkAllFields()

            // the boolean variable turns to be true then
            // only the user must be proceed to the activity2
            if (isAllFieldsChecked) {

                // initialise db
                val databaseClass = ApartmentDatabase(requireActivity())
//                val sharedPreferences =  activity?.getSharedPreferences("userDetails", Context.MODE_PRIVATE)
                val emailId = ApScheConstValues.useremail
                //insertion

                databaseClass.insertApartment(tenantName.text.toString(),aptNo.text.toString(),phoneNo.text.toString(),leaseAmount.text.toString(),leasePeriod.text.toString(),beds.text.toString(),emailId)
                view.findNavController().popBackStack()


            }
        })

        return view
    }


    private fun checkAllFields(): Boolean {
        if (aptNo.length() === 0) {
            aptNo.error = "Apartment number is required"
            return false
        }
        if (tenantName.length() === 0) {
            tenantName.error = "Tenant Name is required"
            return false
        }
        if (phoneNo.length() === 0) {
            phoneNo.error = "Phone number is required"
            return false
        }
        if (leaseAmount.length() === 0) {
            leaseAmount.error = "Lease amount is required"
            return false
        }
        if (leasePeriod.length() === 0) {
            leasePeriod.error = "Lease information is required"
            return false
        }
        if (beds.length() === 0) {
            beds.error = "Beds and bath count is required"
            return false
        }
        // after all validation return true.
        return true
    }

}