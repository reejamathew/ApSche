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
import com.mdev.apsche.database.UserDetailsDatabase

class SignInFragment : Fragment() {
    var email:String = ""
    var password:String = ""
    var errorMessage:String=""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ApScheConstValues.showMenu = false
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.invalidateOptionsMenu()
        }

        val view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        val emailTextView = view.findViewById<TextView>(R.id.inputEmailSignIn)
        val passwordTextView = view.findViewById<TextView>(R.id.inputPasswordSignIn)
        val errorTextView = view.findViewById<TextView>(R.id.errorTextViewSignIn)
        val database = UserDetailsDatabase(requireActivity())

        val signInButton =  view.findViewById<Button>(R.id.signInScreenSignInButton)
        signInButton.setOnClickListener{
            email = emailTextView.text.toString()
            password = passwordTextView.text.toString()
            errorTextView.text=""
            if(validateFields()){
                if(database.checkLogin(email,password)){
                    ApScheConstValues.useremail=email
                    ApScheConstValues.password=password
                    view.findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

                }
                else{
                    errorTextView.text = "Invalid credentials"
                }
            }else{
                errorTextView.text = errorMessage
            }
        }
        val signUpTextView =  view.findViewById<TextView>(R.id.signUpInSignInTextView)
        signUpTextView.setOnClickListener{
            errorTextView.text=""
            view.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

        }
        return view
    }
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