package com.mdev.apsche

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.mdev.apsche.database.ApartmentDatabase

class SignInFragment : Fragment() {
    var email:String = ""
    var password:String = ""
    var errorMessage:String=""



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

        //inflate view
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        //intializing fields
        val emailTextView = view.findViewById<TextView>(R.id.inputEmailSignIn)
        val passwordTextView = view.findViewById<TextView>(R.id.inputPasswordSignIn)
        val errorTextView = view.findViewById<TextView>(R.id.errorTextViewSignIn)
        val signInButton =  view.findViewById<Button>(R.id.signInScreenSignInButton)
        val signUpTextView =  view.findViewById<TextView>(R.id.signUpInSignInTextView)

        //intialize database
        val database = ApartmentDatabase(requireActivity())

        //button actions
        signInButton.setOnClickListener{
            email = emailTextView.text.toString()
            password = passwordTextView.text.toString()
            errorTextView.text=""
            //validating and checking database for email and password
            if(validateFields()){
                if(database.checkLogin(email,password)){
                    ApScheConstValues.useremail=email
                    ApScheConstValues.password=password
                    view.findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

                }
                else{
                    //email and password doesn't match with database
                    errorTextView.text = "Invalid credentials"
                }
            }else{
                //validation error
                errorTextView.text = errorMessage
            }
        }

        signUpTextView.setOnClickListener{
            errorTextView.text=""
            view.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

        }
        return view
    }
    //validating email and password
    fun validateFields(): Boolean {

        if (email == "") {
            errorMessage = "Please enter the email"
            return false
        } else if (password == "") {
            errorMessage = "Please enter the password"
            return false
        }  else {
            return true
        }
    }

}