package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityDataDiriBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity

class DataDiriActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDataDiriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDiriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLanjut.setOnClickListener {
            startActivity(Intent(this,VerifikasiActivity::class.java))
        }
    }
}