package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.verifikasi

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityVerifikasiBinding

class VerifikasiActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVerifikasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBitmap()
    }

    private fun getBitmap(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.ic_delete_otp)

        if (svg.getDocumentWidth() !== -1F) {

            // set your custom height and width for the svg
            svg.documentHeight = 100f
            svg.documentWidth = 100f

            // create a canvas to draw onto
            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // canvas - white background
            canvas.drawARGB(0, 255, 255, 255)

            // Render our document onto our canvas
            svg.renderToCanvas(canvas)

            // set the bitmap to imageView
            binding.ivDeleteOtp.background = BitmapDrawable(resources, bitmap)

        }
    }
}