package com.atandroidlabs.musicplayer.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.atandroidlabs.musicplayer.*

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
       var fragment: Fragment? = null
        when(position) {
            0-> fragment = SongsFragment()
            1-> fragment = AlbumsFragment()
            2-> fragment = ListsFragment()
            3-> fragment = ArtistsFragment()
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0-> "Songs"
            1-> "Albums"
            2-> "Lists"
            3-> "Artists"
            else -> null
        }
    }

    override fun getCount(): Int {
        // Show 4 total pages.
        return 4
    }
}