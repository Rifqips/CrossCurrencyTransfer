package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentHomeBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.internasional.InternationalTransferActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.metodetransfer.lokal.LokalTransferActivity

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"

    }

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contraintTfInter.setOnClickListener {
            startActivity(Intent(context, InternationalTransferActivity::class.java))
        }
        binding.contraintTfLokal.setOnClickListener {
            startActivity(Intent(context, LokalTransferActivity::class.java))
        }
    }
}