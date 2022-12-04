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
    private lateinit var aptNo: TextView
    private lateinit var tenantName: TextView
    private lateinit var phoneNo: TextView
    private lateinit var leaseAmount: TextView
    //private lateinit var currency:TextView
    private lateinit var leasePeriod: TextView
    private lateinit var beds: TextView

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

        //intializing fields
        val editButton = view.findViewById<Button>(R.id.editButton);
        aptNo = view.findViewById<TextView>(R.id.aptNoEditText)
        tenantName = view.findViewById<TextView>(R.id.tenantNameEditText)
        phoneNo = view.findViewById<TextView>(R.id.phoneNoEditText)
        leaseAmount = view.findViewById<TextView>(R.id.leaseAmountEditText)
        leasePeriod = view.findViewById<TextView>(R.id.leasePeriodEditText)
        beds = view.findViewById<TextView>(R.id.bedsEditText)

        view.findViewById<TextView>(R.id.aptNoEditText).text = apartment.aptNo.toString()
        view.findViewById<TextView>(R.id.tenantNameEditText).text = apartment.tenant_name.toString()
        view.findViewById<TextView>(R.id.phoneNoEditText).text = apartment.phone_no.toString()
        view.findViewById<TextView>(R.id.leasePeriodEditText).text = apartment.lease_information
        view.findViewById<TextView>(R.id.leaseAmountEditText).text = apartment.lease_amount.toString()
        view.findViewById<TextView>(R.id.bedsEditText).text = apartment.beds_bath

        //button action
        var isAllFieldsChecked = false

        editButton.setOnClickListener(View.OnClickListener {
            //updating values
            isAllFieldsChecked = checkAllFields()

            if (isAllFieldsChecked) {
                databaseClass.updateApartment(
                    aptId,
                    view.findViewById<TextView>(R.id.aptNoEditText).text.toString(),
                    view.findViewById<TextView>(R.id.tenantNameEditText).text.toString(),
                    view.findViewById<TextView>(R.id.phoneNoEditText).text.toString(),
                    view.findViewById<TextView>(R.id.leasePeriodEditText).text.toString(),
                    view.findViewById<TextView>(R.id.leaseAmountEditText).text.toString(),
                    view.findViewById<TextView>(R.id.bedsEditText).text.toString(),
                    ApScheConstValues.useremail
                )
                view.findNavController().popBackStack()
                view.findNavController().popBackStack()
            }
        })
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