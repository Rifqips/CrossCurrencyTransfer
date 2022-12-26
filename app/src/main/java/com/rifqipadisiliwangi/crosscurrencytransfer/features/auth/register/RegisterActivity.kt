package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.register

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityRegisterBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.datadiri.DataDiriActivity
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi.VerifikasiActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBitmap()

        binding.btnkirim.setOnClickListener {
            startActivity(Intent(this, DataDiriActivity::class.java))
        }
    }

    private fun getBitmap() {

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.ic_registrasi)

        if (svg.getDocumentWidth() !== -1F) {

            // set your custom height and width for the svg
            svg.documentHeight = 850F
            svg.documentWidth = 700F

            // create a canvas to draw onto
            val bitmap = Bitmap.createBitmap(700, 700, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // canvas - white background
            canvas.drawARGB(0, 255, 255, 255)

            // Render our document onto our canvas
            svg.renderToCanvas(canvas)

            // set the bitmap to imageView
            binding.ivRegist.background = BitmapDrawable(resources, bitmap)

        }
    }
}