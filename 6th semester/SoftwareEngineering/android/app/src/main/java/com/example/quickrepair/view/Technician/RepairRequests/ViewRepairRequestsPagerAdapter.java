package com.example.quickrepair.view.Technician.RepairRequests;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewRepairRequestsPagerAdapter extends FragmentPagerAdapter
{

    private static final String[] TAB_TITLES = new String[]{"Unconfirmed", "Confirmed", "Completed"};

    private final Context mContext;

    public ViewRepairRequestsPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        if (position == 0)
        {
            return TechnicianUnconfirmedRepairRequests.newInstance();
        }
        else if (position == 1)
        {
            return TechnicianConfirmedRepairRequests.newInstance();
        }
        else
        {
            return TechnicianCompletedRepairRequests.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount()
    {
        // Show 2 total pages.
        return 3;
    }
}