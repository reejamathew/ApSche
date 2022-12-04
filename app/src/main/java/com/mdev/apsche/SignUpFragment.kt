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
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.json.JSONObject
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mdev.apsche.database.ApartmentDatabase
import cz.msebera.android.httpclient.Header

interface OnJSONResponseCallback {
    fun onJSONResponse(success: Boolean, response: Boolean)
}
class SignUpFragment : Fragment() {
    //intializing variables
    var name: String = ""
    var email: String = ""
    var password: String = ""
    var confirmpassword: String = ""
    var errorMessage: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setting value to show the menu in actionbar to false
        ApScheConstValues.showMenu = false
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.invalidateOptionsMenu()
        }

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //intializing the fields
        val nameTextView = view.findViewById<TextView>(R.id.inputNameSignUp)
        val emailTextView = view.findViewById<TextView>(R.id.inputEmailSignUp)
        val passwordTextView = view.findViewById<TextView>(R.id.inputPasswordSignUp)
        val confirmPasswordTextView = view.findViewById<TextView>(R.id.inputConfirmPasswordSignUp)
        val errorTextView = view.findViewById<TextView>(R.id.errorTextViewSignUp)
        val signUpButton = view.findViewById<Button>(R.id.signUpScreenSignUpButton)
        val signInTextview = view.findViewById<TextView>(R.id.signInInSignUpTextView)

        //intialize database
        val database = ApartmentDatabase(requireActivity())

        //button actions
        signUpButton.setOnClickListener {
            errorTextView.text = ""
            name = nameTextView.text.toString()
            email = emailTextView.text.toString()
            password = passwordTextView.text.toString()
            confirmpassword = confirmPasswordTextView.text.toString()
            if (validateFields()) {

                        if (!database.checkEmail(email)) {
                            database.insertUser(email, name, password)
                            Log.d("reached here", "signup")
                            view.findNavController().popBackStack()
                        } else {
                            errorTextView.text = "Emailid already exists"
                        }


                }else{
                    errorTextView.text = errorMessage
                }

            }
            signInTextview.setOnClickListener {
                errorTextView.text = ""
                view.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)

            }
            return view
        }
//validating all the fields
        fun validateFields(): Boolean {

            if (name == "") {
                errorMessage = "Please enter the name"
                return false
            } else if (email == "") {
                errorMessage = "Please enter the email"
                return false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                errorMessage = "Please enter valid email"
                return false
            } else if (password == "") {
                errorMessage = "Please enter the password"
                return false
            } else if (confirmpassword == "") {
                errorMessage = "Please confirm the password"
                return false
            } else if (password != confirmpassword) {
                errorMessage = "Password and Confirm password doesnot match"
                return false
            } else {
                return true
            }
        }
    }


