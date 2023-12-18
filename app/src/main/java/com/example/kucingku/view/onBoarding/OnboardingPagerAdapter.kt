package com.example.kucingku.view.onBoarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class OnboardingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return OnboardingFragment()
    }

    override fun getCount(): Int {
        return 3 // Number of onboarding screens
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }
}
