package com.example.indincator;

import android.support.v4.view.ViewPager;

/**
 * Copyright@ AsianTech.Inc
 * Created by ly.ho.sumiu on 16/02/2017.
 */

public interface IndicatorInterface {
    void setViewPager(ViewPager viewPager) throws PagesException;

    void setAnimateDuration(long duration);

    /**
     *
     * @param radius: radius in pixel
     */
    void setRadiusSelected(int radius);

    /**
     *
     * @param radius: radius in pixel
     */
    void setRadiusUnselected(int radius);

    /**
     *
     * @param distance: distance in pixel
     */
    void setDistanceDot(int distance);
}
