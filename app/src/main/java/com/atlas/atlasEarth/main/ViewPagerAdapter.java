package com.atlas.atlasEarth.main;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Jonas on 2/25/2017.
 */

public class ViewPagerAdapter extends PagerAdapter{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
