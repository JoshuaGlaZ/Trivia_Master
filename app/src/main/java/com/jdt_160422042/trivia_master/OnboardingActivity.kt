package com.jdt_160422042.trivia_master

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.jdt_160422042.trivia_master.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    val fragments:ArrayList<Fragment> = ArrayList()
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragments.add(Onboarding1Fragment())
        fragments.add(Onboarding2Fragment())
        fragments.add(Onboarding3Fragment())

        binding.viewPager.adapter = OnboardingAdapter(this, fragments)
    }
}