package com.javlonrahimov1212.footbalteams.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.javlonrahimov1212.footbalteams.ItemsFragment
import com.javlonrahimov1212.footbalteams.MainActivity
import com.javlonrahimov1212.footbalteams.models.Clubs


class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ItemsFragment(Clubs.CHE)
            }
            1 -> {
                ItemsFragment(Clubs.PSG)
            }
            2 -> {
                ItemsFragment(Clubs.RMA)
            }
            else -> {
                ItemsFragment(Clubs.MCI)
            }
        }
    }

}

