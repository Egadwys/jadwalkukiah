package com.egads.jadwalkuliah

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.afollestad.viewpagerdots.DotsIndicator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content.*
import kotlinx.android.synthetic.main.content_bottomsheet.*
import kotlinx.android.synthetic.main.schedules.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SET FULLSCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //SET FULLSCREEN

        //MAIN CONTENT
        setContentView(R.layout.activity_main)
        //MAIN CONTENT

        //HIDE CONTENT
        layhome.visibility = View.VISIBLE
        layschedules.visibility = View.GONE
        laycalendar.visibility = View.GONE
        //HIDE CONTENT

        //VIEWPAGER + INDICATOR
        val viewPager: ViewPager = findViewById(R.id.viewpager)
        val indicator: DotsIndicator = findViewById(R.id.indicators)
        val adapter = TabAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter
        indicator.attachViewPager(viewPager)
        //VIEWPAGER + INDICATOR

        //BOTTOMSHEET
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout)
        bottomSheetBehavior.setPeekHeight(0)
        bottomSheetBehavior.isHideable = true
        slide_up.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottom_sheet_layout.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        //BOTTOMSHEET

        //DIALOG
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val day = SimpleDateFormat("EEE")
        val today = day.format(Date())
        when (today) {
            "Mon" -> {
                viewPager.setCurrentItem(0)
                dialog.setContentView(R.layout.senin)
                dialog.show()
            }
            "Tue" -> {
                viewPager.setCurrentItem(1)
                dialog.setContentView(R.layout.selasa)
                dialog.show()
            }
            "Wed" -> {
                viewPager.setCurrentItem(2)
                dialog.setContentView(R.layout.rabu)
                dialog.show()
            }
            "Thu" -> {
                viewPager.setCurrentItem(3)
                dialog.setContentView(R.layout.kamis)
                dialog.show()
            }
            "Fri" -> {
                viewPager.setCurrentItem(4)
                dialog.setContentView(R.layout.jumat)
                dialog.show()
            }
            "Sat" -> {
                viewPager.setCurrentItem(5)
                dialog.setContentView(R.layout.sabtu)
                dialog.show()
            }
            else -> {
                viewPager.setCurrentItem(6)
                dialog.setContentView(R.layout.minggu)
                dialog.show()
            }
        }
        //DIALOG

        //ANIM FADE
        val fadein = AlphaAnimation(0f, 1f)
        fadein.duration = 1000
        fadein.interpolator = DecelerateInterpolator()

        val fadeout = AlphaAnimation(1f, 0f)
        fadeout.duration = 0
        fadeout.interpolator = AccelerateInterpolator()

        val animation = AnimationSet(false)
        animation.addAnimation(fadein)
        animation.addAnimation(fadeout)
        //ANIM FADE

        //MENU BOTTOM
        bottommenu.setItemSelected(R.id.item_home)
        bottommenu.setOnItemSelectedListener{ id -> when (id){
            R.id.item_home -> {
                layhome.visibility = View.VISIBLE
                layschedules.visibility = View.GONE
                laycalendar.visibility = View.GONE
                layhome.startAnimation(fadein)
                layschedules.startAnimation(fadeout)
                laycalendar.startAnimation(fadeout)
            }
            R.id.item_schedules -> {
                layhome.visibility = View.GONE
                layschedules.visibility = View.VISIBLE
                laycalendar.visibility = View.GONE
                layschedules.startAnimation(fadein)
                layhome.startAnimation(fadeout)
                laycalendar.startAnimation(fadeout)
            }
            R.id.item_calendar -> {
                layhome.visibility = View.GONE
                layschedules.visibility = View.GONE
                laycalendar.visibility = View.VISIBLE
                laycalendar.startAnimation(fadein)
                layhome.startAnimation(fadeout)
                layschedules.startAnimation(fadeout)
            }
        }}
        //MENU BOTTOM

    }

    //VIEWPAGER ADAPTER
    class TabAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
        override fun getItem(position: Int): Fragment = when (position) {
            0 -> senin()
            1 -> selasa()
            2 -> rabu()
            3 -> kamis()
            4 -> jumat()
            5 -> sabtu()
            else -> minggu()
        }

        override fun getCount(): Int = 7
    }
    //VIEWPAGER ADAPTER

    //2xBACK TO EXIT
    private var twice = false

    override fun onBackPressed() {
        if (twice){
            super.onBackPressed()
            return
        }
        this.twice = true
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { twice = false }, 1000)
    }
    //2xBACK TO EXIT

}
