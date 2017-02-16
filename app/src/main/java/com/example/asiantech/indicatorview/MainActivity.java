package com.example.asiantech.indicatorview;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.indincator.IndicatorView;
import com.example.indincator.PagesException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
    }

    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<PaperItem> paperItems = new ArrayList<>();
        paperItems.add(new PaperItem());
        paperItems.add(new PaperItem());
        paperItems.add(new PaperItem());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, paperItems);
        viewPager.setAdapter(viewPagerAdapter);
        //
        indicatorView = (IndicatorView) findViewById(R.id.indicator);
        try {
            indicatorView.setViewPager(viewPager);
        } catch (PagesException e) {
            e.printStackTrace();
        }
    }
}
