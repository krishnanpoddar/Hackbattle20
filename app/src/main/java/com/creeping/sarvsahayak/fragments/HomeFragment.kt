package com.creeping.sarvsahayak.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.creeping.sarvsahayak.ReportForm
import com.creeping.sarvsahayak.R
import com.creeping.sarvsahayak.RaggingForm
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    lateinit var domesticVoilence:CardView
    lateinit var childAbuse:CardView
    lateinit var AnimalHarrasment:CardView
    lateinit var ragging:CardView
//    lateinit var anxiety:CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        //HOOK
        domesticVoilence=view.findViewById(R.id.DomesticVoilence)
        childAbuse=view.findViewById(R.id.ChildAbuse)
        AnimalHarrasment=view.findViewById(R.id.AnimalHarrasment)
        ragging=view.findViewById(R.id.Ragging)
//        anxiety=view.findViewById(R.id.Anxiety)

        //click listner on card view so that whe user click on any cardview item he /she/any gender is re-directed to form page
        domesticVoilence.setOnClickListener {
            val i=Intent(activity, ReportForm::class.java)
            startActivity(i)



        }
        childAbuse.setOnClickListener {
            val i=Intent(activity, ReportForm::class.java)
            startActivity(i)


        }
        AnimalHarrasment.setOnClickListener {
            val i=Intent(activity, ReportForm::class.java)
            startActivity(i)


        }
        ragging.setOnClickListener {
            val i=Intent(activity, RaggingForm::class.java)
            startActivity(i)


        }

//        anxiety.setOnClickListener {
//            val i=Intent(activity, ReportForm::class.java)
//            startActivity(i)


//        }



        return view
    }
}