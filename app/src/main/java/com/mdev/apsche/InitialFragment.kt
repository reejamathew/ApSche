package com.mdev.apsche

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


class InitialFragment : Fragment() {



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
        val view = inflater.inflate(R.layout.fragment_initial, container, false)

        //button and actions
        val signInButton =  view.findViewById<Button>(R.id.initialSignIn)
        signInButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_initialFragment_to_SignInFragment)

        }
        val signUpButton =  view.findViewById<Button>(R.id.initialSignUp)
        signUpButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_initialFragment_to_signUpFragment)
        }


        return view
    }


}