package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.fahim.mevronrider.FragmentToActivity
import com.fahim.mevronrider.R
import com.fahim.mevronrider.views.adapters.PagerCollectionAdapter
import com.fahim.mevronrider.views.fragments.FragmentEmail
import kotlinx.android.synthetic.main.activity_pager.*


class PagerActivity : AppCompatActivity(), FragmentToActivity {

    val email = FragmentEmail()

    lateinit var emaill: String
    override fun communicate(background: String) {
        Toast.makeText(
            applicationContext, background,
            Toast.LENGTH_SHORT
        ).show()
    }

    lateinit var viewPager: ViewPager
    private lateinit var fragmentCollectionAdapter: PagerCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        viewPager = findViewById(R.id.viewPager)
        fragmentCollectionAdapter = PagerCollectionAdapter(supportFragmentManager)
        viewPager.adapter = fragmentCollectionAdapter
        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)

        ivBack.setOnClickListener {
            val intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.LoginActivity::class.java)
            startActivity(intent)
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when (position) {

                    1 -> {

                        Toast.makeText(applicationContext, "najish", Toast.LENGTH_SHORT).show()

                    }


                }
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)
                        two.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        three.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        four.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        five.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        six.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)

                    }


                    1 -> {
                        two.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)
                        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        three.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        four.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        five.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        six.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)


                    }
                    2 -> {
                        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        two.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        three.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)
                        four.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        five.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        six.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)

                    }
                    3 -> {
                        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        two.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        three.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        four.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)
                        five.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        six.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                    }
                    4 -> {
                        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        two.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        three.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        four.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        five.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)
                        six.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        dotsBackground.background = ContextCompat.getDrawable(applicationContext, R.drawable.bg_main)
                        btnFinishSignUp.visibility = View.GONE
                    }
                    5 -> {
                        one.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        two.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        three.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        four.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        five.background = ContextCompat.getDrawable(applicationContext, R.drawable.unselected)
                        six.background = ContextCompat.getDrawable(applicationContext, R.drawable.selected)
                        dotsBackground.setBackgroundColor(resources.getColor(R.color.yellow))
                        btnFinishSignUp.visibility = View.VISIBLE
                        btnFinishSignUp.setOnClickListener {
                            val intent = Intent(
                                applicationContext,
                                com.fahim.mevronrider.views.activity.ThanksActivity::class.java
                            )
                            startActivity(intent)
                        }
                    }

                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.pagerContainer, fragment, tag)
        ft.commitAllowingStateLoss()
    }

}

