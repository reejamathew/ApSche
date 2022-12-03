package com.mdev.apsche

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mdev.apsche.database.ApartmentDatabase
import com.mdev.apsche.model.Apartment
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class DetailsFragment : Fragment() {
    lateinit var apartment:Apartment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //receiving the apartmentId from previous screen
       val aptId = DetailsFragmentArgs.fromBundle(requireArguments()).apartmentId

      //intializing database
        val database = ApartmentDatabase(requireActivity())
        // fetching data from database using Id
        val apartmentList= database.getApartmentDetailsById(aptId.toString())
        apartment = apartmentList[0]

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

        //intializing all fields with values

        view.findViewById<TextView>(R.id.Aptno).text = apartment.aptNo.toString()
        view.findViewById<TextView>(R.id.tenant_name).text = apartment.tenant_name
        view.findViewById<TextView>(R.id.phone_no).text = apartment.phone_no
        view.findViewById<TextView>(R.id.lease_period).text = apartment.lease_information
        view.findViewById<TextView>(R.id.lease_amount).text = apartment.lease_amount.toString()
        view.findViewById<TextView>(R.id.beds).text = apartment.beds_bath
        val editButton = view.findViewById<Button>(R.id.editButton);
        val deleteButton = view.findViewById<Button>(R.id.deleteButton);
        val currencyText = view.findViewById<TextView>(R.id.currencyEditText)
        val currencyButton = view.findViewById<Button>(R.id.currencyConvertButton);

        //setting listeners for the buttons

        editButton.setOnClickListener(View.OnClickListener {
            view.findNavController().navigate(R.id.action_detailsFragment_to_editFragment, Bundle().apply {
               putString("aptId", apartment.aptId.toString())
            })
        })

        deleteButton.setOnClickListener(View.OnClickListener {
            //intializing database and deleting the apartment details for the id
            val database = ApartmentDatabase(requireActivity())
            if (!database.deleteApartment(apartment.aptId.toString())){
                //on successful deletion
                val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
                alert.setMessage("Deleted Successfully")
                    .setPositiveButton("Ok",null)
                val alert1: android.app.AlertDialog? = alert.create()
                if (alert1 != null) {
                    alert1.show()
                }
                view.findNavController().popBackStack()
            }
            else{
                val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
                alert.setMessage("Unable to delete. Please try again")
                    .setPositiveButton("Ok",null)
                val alert1: android.app.AlertDialog? = alert.create()
                if (alert1 != null) {
                    alert1.show()
                }
            }
        })

        currencyButton.setOnClickListener(View.OnClickListener {
            getCurrencyResponse(currencyText.text.toString()) { isCurrencyValid ->

            }
            })
        }

    // getting response from server for currency conversion

    private  fun getCurrencyResponse(currencyText:String, callback: (Boolean?) -> Unit) {
        val url = "https://api.exchangeratesapi.io/v1/latest?" +
                "access_key=6e8202ba3b24f80931a9b44ba11a4a77&base=CAD" +
                "&symbols="+currencyText;
        var isSuccess = false
        //connecting with server
        AsyncHttpClient().get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray?) {
                val response = String(responseBody!!)
                val obj = JSONObject(response)
                val rates = obj.getJSONObject("rates")
                val currencyVal = rates.getString(currencyText.toUpperCase())
                isSuccess = obj.getBoolean("success")

                //on successful fetch
                callback.invoke(isSuccess);
                if(isSuccess) {
                    val activity = activity as AppCompatActivity?
                    val convertAmount = apartment.lease_amount?.times(currencyVal.toDouble())
                    val message = "Your lease amount in currency "+currencyText+" is "+convertAmount.toString()

                    val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
                    alert.setMessage(message)
                        .setPositiveButton("Ok",null)
                    val alert1: android.app.AlertDialog? = alert.create()
                    if (alert1 != null) {
                        alert1.show()
                    }

                }
            }
            //on failure to get response
            override fun onFailure(
                statusCode: Int,
                headers: Array<Header?>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                callback.invoke(isSuccess);
                val activity = activity as AppCompatActivity?
                val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
                alert.setMessage("Please enter valid currency code")
                    .setPositiveButton("Ok",null)
                val alert1: android.app.AlertDialog? = alert.create()
                if (alert1 != null) {
                    alert1.show()
                }

            }
        })
    }

}