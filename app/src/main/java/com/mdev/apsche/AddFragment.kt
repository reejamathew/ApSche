package com.mdev.apsche

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.Button
import android.widget.TextView
import com.mdev.apsche.database.UserDetailsDatabase

class AddDetailsFragment : Fragment() {
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var recipeName: TextView
    private lateinit var ingredients: TextView
    private lateinit var description: TextView


    var part_image: String? = null


    var filePath: ValueCallback<Array<Uri>>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add_details, container, false)

        val submitButton = view.findViewById<Button>(R.id.submitButton);

        recipeName = view.findViewById<TextView>(R.id.recipeNameEditText);
        ingredients = view.findViewById<TextView>(R.id.ingredientsEditText)
        description = view.findViewById<TextView>(R.id.descriptionEditText)
        //video = view.findViewById<Button>(R.id.videoInputButton);

        var isAllFieldsChecked = false

//        video.setOnClickListener {
//            println("clicked on video upload!");
//        }


        submitButton.setOnClickListener(View.OnClickListener { // store the returned value of the dedicated function which checks
            // whether the entered data is valid or if any fields are left blank.
            isAllFieldsChecked = checkAllFields()

            // the boolean variable turns to be true then
            // only the user must be proceed to the activity2
            if (isAllFieldsChecked) {

                // initialise db
                val databaseClass = UserDetailsDatabase(requireActivity())
//                val sharedPreferences =  activity?.getSharedPreferences("userDetails", Context.MODE_PRIVATE)
//                val emailId = sharedPreferences?.getString("emailId","")
                //insertion
                val insertRecipe = databaseClass.insertRecipe(
                    recipeName.text.toString(),
                    ingredients.text.toString(),
                    description.text.toString()
                )
                Log.d("insert123", insertRecipe.toString())
            }
        })

        return view
    }


    private fun checkAllFields(): Boolean {
        if (recipeName.length() === 0) {
            recipeName.error = "Recipe Name is required"
            return false
        }
        if (ingredients.length() === 0) {
            ingredients.error = "Ingredients is required"
            return false
        }
        if (description.length() === 0) {
            description.error = "Description is required"
            return false
        }
        // after all validation return true.
        return true
    }

}