package com.example.asiantech.indicatorview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Copyright@ AsianTech.Inc
 * Created by ly.ho.sumiu on 16/02/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<PaperItem> mPaperItems;

    public ViewPagerAdapter(Context context, List<PaperItem> paperItems) {
        mContext = context;
        mPaperItems = paperItems;
    }

    @Override
    public int getCount() {
        return mPaperItems != null ? mPaperItems.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemView itemView = new ItemView(position);
        View view = itemView.getView(container);
        container.addView(view);
        return view;
    }

    private final class ItemView {
        private int position;

        ItemView(int position) {
            this.position = position;
        }

        View getView(ViewGroup viewGroup) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, viewGroup, false);
            loadView(view);
            return view;
        }

        private void loadView(View view) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            switch (position) {
                case 0:
                    imageView.setImageResource(R.drawable.img_item1);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.img_item2);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.img_item3);
                    break;
            }
        }
    }
}
