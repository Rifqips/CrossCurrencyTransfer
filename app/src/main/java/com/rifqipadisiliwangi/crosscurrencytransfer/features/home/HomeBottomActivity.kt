package com.rifqipadisiliwangi.crosscurrencytransfer.features.home

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityHomeBottomBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.HistoryFragment
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment.HomeFragment

class HomeBottomActivity : AppCompatActivity(), OrderFragmentInterface {

    private lateinit var binding : ActivityHomeBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView

        val navController =
            findNavController(R.id.nav_host_fragment_activity_home_bottom_navigation)
        navView.setupWithNavController(navController)

    }

    override fun onClickOrder() {
        supportFragmentManager.beginTransaction()
            .add(HomeFragment(), HomeFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setTitle("Tutup Aplikasi")
            .setMessage("Yakin tutup dari aplikasi?")
            .setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                finishAffinity()
            }
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
}