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
        getBitmap()

        binding.btnLanjut.setOnClickListener {
            startActivity(Intent(this,VerifikasiActivity::class.java))
        }
    }
    private fun getBitmap() {

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.ic_data_diri)

        if (svg.getDocumentWidth() !== -1F) {

            // set your custom height and width for the svg
            svg.documentHeight = 30f
            svg.documentWidth = 37f

            // create a canvas to draw onto
            val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // canvas - white background
            canvas.drawARGB(0, 255, 255, 255)

            // Render our document onto our canvas
            svg.renderToCanvas(canvas)

            // set the bitmap to imageView
            binding.ivImageUser.background = BitmapDrawable(resources, bitmap)

        }
    }
}