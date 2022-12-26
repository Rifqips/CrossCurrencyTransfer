package com.rifqipadisiliwangi.crosscurrencytransfer.features.home.fragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caverock.androidsvg.SVG
import com.rifqipadisiliwangi.crosscurrencytransfer.R
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentLayananBinding

class LayananFragment : Fragment() {

    private lateinit var binding : FragmentLayananBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLayananBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBitmapLokasi()
        getBitmapEmail()
        getBitmapWa()
        getBitmapCallCenter()

    }

    private fun getBitmapLokasi(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.icmap)

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
            binding.ivLokasi.background = BitmapDrawable(resources, bitmap)

        }
    }

    private fun getBitmapEmail(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.icmail)

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
            binding.ivEmail.background = BitmapDrawable(resources, bitmap)

        }
    }

    private fun getBitmapWa(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.icwahtsaap)

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
            binding.ivWa.background = BitmapDrawable(resources, bitmap)

        }
    }

    private fun getBitmapCallCenter(){

        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.icphone)

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
            binding.ivCallCenter.background = BitmapDrawable(resources, bitmap)

        }
    }
}