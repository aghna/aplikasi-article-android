package id.ac.uin_malang.application;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lenovo on 28/11/17.
 */

public class ViewPageAdapter extends FragmentStatePagerAdapter {




    private String[] title=new String[]{"ARTICLE","LOGIN"};

   // Context context;
    private int pagecont=2;
    public ViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);
       // this.context=context;

    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new home();
            case 1:
                return new grid();

        }
        return null;
    }

    @Override
    public int getCount() {
        return pagecont;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
