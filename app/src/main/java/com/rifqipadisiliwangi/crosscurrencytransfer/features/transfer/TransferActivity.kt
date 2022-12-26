package com.rifqipadisiliwangi.crosscurrencytransfer.features.transfer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityTransferBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.home.HomeBottomActivity

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBitmapPilihan()
        getBitmapTujuan()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeBottomActivity::class.java))
        }
    }

    private fun getBitmapPilihan(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.ic_indonesia)

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
            binding.ivAsalTf.background = BitmapDrawable(resources, bitmap)

        }
    }

    private fun getBitmapTujuan(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.ic_us)

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
            binding.ivTujuanTf.background = BitmapDrawable(resources, bitmap)

        }
    }
}