package com.musicplayer.moviecatch.ui.fragments.appintro


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.musicplayer.moviecatch.adapter.ViewPagerAdapter
import com.musicplayer.moviecatch.databinding.FragmentAppIntroBinding
import com.musicplayer.moviecatch.ui.fragments.appintro.pages.FifthFragment
import com.musicplayer.moviecatch.ui.fragments.appintro.pages.FirstFragment
import com.musicplayer.moviecatch.ui.fragments.appintro.pages.FourthFragment
import com.musicplayer.moviecatch.ui.fragments.appintro.pages.SecondFragment
import com.musicplayer.moviecatch.ui.fragments.appintro.pages.ThirdFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppIntroFragment : Fragment() {
    private var _binding: FragmentAppIntroBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAppIntroBinding.inflate(inflater, container, false)

        fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment(),
            FourthFragment(),
            FifthFragment()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter


        setButtonsAndPages(0, null)

        binding.prevButton.setOnClickListener {
            setButtonsAndPages(binding.viewPager.currentItem - 1, false)
            Log.d("R/A", binding.viewPager.currentItem.toString())
        }

        binding.nextButton.setOnClickListener {
            setButtonsAndPages(binding.viewPager.currentItem + 1, true)
            Log.d("R/A", binding.viewPager.currentItem.toString())
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setButtonsAndPages(position, null)
            }
        })

        return binding.root
    }

    private fun setButtonsAndPages(position: Int, isNext: Boolean? = null) {
        val prvBtn = binding.prevButton
        val nxtBtn = binding.nextButton

        if (position == 0) {
            prvBtn.visibility = View.GONE
        } else {
            prvBtn.visibility = View.VISIBLE
        }

        if (position == fragmentList.size-1) {
            nxtBtn.visibility = View.GONE
        } else {
            nxtBtn.visibility = View.VISIBLE
        }


        if (isNext != null) {
            if (isNext && (position < fragmentList.size)) {
                binding.viewPager.currentItem = position
            }

            if (!isNext && position >= 0) {
                binding.viewPager.currentItem = position
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}