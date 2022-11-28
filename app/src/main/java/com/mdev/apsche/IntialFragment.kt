package com.mdev.apsche

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

class IntialFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intial, container, false)
        return view
    }


}