package www.charles.whatsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {


    int mNumberOfTabs;

    //Creating a constructor

    public PagerAdapter (FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNumberOfTabs = NumberOfTabs;
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                TabCamera  tabCamera = new TabCamera();
                return tabCamera;

            case 1:
                TabChats tabChats = new TabChats();
                return tabChats;

            case 2:
                TabStatus tabStatus = new TabStatus();
                return  tabStatus;

            case 3:
                TabCalls tabCalls = new TabCalls();
                return tabCalls;

             default:
                 return null;
        }

    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
