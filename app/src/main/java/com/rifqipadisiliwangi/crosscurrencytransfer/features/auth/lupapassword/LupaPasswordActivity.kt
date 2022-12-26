package com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.lupapassword

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityLupaPasswordBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity

class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLupaPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBitmap()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun getBitmap() {

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.ic_lupapassword)

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
            binding.ivLupaPW.background = BitmapDrawable(resources, bitmap)

        }
    }
}