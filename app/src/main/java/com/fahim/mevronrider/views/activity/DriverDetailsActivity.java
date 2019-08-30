package com.fahim.mevronrider.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.fahim.mevronrider.R;


public class DriverDetailsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private ImageView[] ivArrayDotsPager;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.pager_dots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        email = findViewById(R.id.etDriverEmail);
//        if (!MySharedPreferences.Companion.getMySharedPreferences().isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }
        layouts = new int[]{
                R.layout.become_driver,
                R.layout.what_you_drive,
                R.layout.security_check,
                R.layout.residential_address,
                R.layout.your_phone,
                R.layout.account_verification};
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThanksActivity.class);
                startActivity(intent);

                /*int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                }*/


            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[6];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            dotsLayout.addView(ivArrayDotsPager[i]);
            dotsLayout.bringToFront();
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText("start");
                btnSkip.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
            } else {
                // still pages are left
                btnNext.setText("next");
                btnSkip.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
            }
            for (int i = 0; i < ivArrayDotsPager.length; i++) {
                ivArrayDotsPager[i].setImageResource(R.drawable.unselected);
            }
            ivArrayDotsPager[position].setImageResource(R.drawable.selected);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
