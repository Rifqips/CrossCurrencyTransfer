package com.rifqipadisiliwangi.crosscurrencytransfer.features.onboarding

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
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.FragmentOnBoardingTwoBinding

class OnBoardingTwoFragment : Fragment() {

    private lateinit var binding : FragmentOnBoardingTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingTwoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBitmap()

    }

    private fun getBitmap(){



        // Read an SVG from the assets folder
        val svg = SVG.getFromResource(resources, R.raw.onboarding_dua)

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
            binding.ivOnboardSatu.background = BitmapDrawable(resources, bitmap)

        }
    }
}