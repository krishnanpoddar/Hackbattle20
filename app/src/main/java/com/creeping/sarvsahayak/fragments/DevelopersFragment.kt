package com.creeping.sarvsahayak.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.creeping.sarvsahayak.R

class DevelopersFragment : Fragment() {
    lateinit var txtKrishnanlink: TextView
    lateinit var txtaishwaryalink: TextView
    lateinit var txtsakshilink: TextView
    lateinit var txtsimranlink: TextView
    lateinit var txtkumudlink: TextView
    lateinit var txtkushagralink: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_developers, container, false)


        txtKrishnanlink=view.findViewById(R.id.txtKrishnanlink)
        txtaishwaryalink=view.findViewById(R.id.txtaishwaryalink)
        txtsakshilink=view.findViewById(R.id.txtsakshilink)
        txtsimranlink=view.findViewById(R.id.txtsimranlink)
        txtkumudlink=view.findViewById(R.id.txtkumudlink)
        txtkushagralink=view.findViewById(R.id.txtkushagralink)

        txtKrishnanlink.movementMethod= LinkMovementMethod.getInstance()
        txtaishwaryalink.movementMethod= LinkMovementMethod.getInstance()
        txtsimranlink.movementMethod= LinkMovementMethod.getInstance()
        txtKrishnanlink.movementMethod= LinkMovementMethod.getInstance()
        txtkumudlink.movementMethod= LinkMovementMethod.getInstance()
        txtkushagralink.movementMethod= LinkMovementMethod.getInstance()


        return view
    }
}