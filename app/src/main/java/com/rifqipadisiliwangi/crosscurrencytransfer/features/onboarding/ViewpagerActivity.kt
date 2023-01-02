package com.rifqipadisiliwangi.crosscurrencytransfer.features.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.rifqipadisiliwangi.crosscurrencytransfer.databinding.ActivityViewpagerBinding
import com.rifqipadisiliwangi.crosscurrencytransfer.features.adapters.onboarding.ViewPagerFragmentAdapter
import com.rifqipadisiliwangi.crosscurrencytransfer.features.auth.login.LoginActivity
import me.relex.circleindicator.CircleIndicator3

class ViewpagerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewpagerBinding

    private var fragmentList = ArrayList<Fragment>()
    private lateinit var viewPager: ViewPager2
    private lateinit var indicator: CircleIndicator3
    private lateinit var btnNext: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        castView()

        fragmentList.add(OnBoardingOneFragment())
        fragmentList.add(OnBoardingTwoFragment())
        fragmentList.add(OnBoardingThreeFragment())

        // viewPager.adapter = ViewPagerAdapter(data, this)
        viewPager.adapter = ViewPagerFragmentAdapter(this, fragmentList)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicator.setViewPager(viewPager)

        binding.btnBack.setOnClickListener {
            viewPager.apply {
                beginFakeDrag()
                fakeDragBy(10f)
                endFakeDrag()
            }
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun castView() {
        viewPager = binding.viewPager2
        indicator = binding.indicator
//        btnNext = binding.btnNext
        btnBack = binding.btnBack

    }
}