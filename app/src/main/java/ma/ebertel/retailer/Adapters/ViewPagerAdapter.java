package ma.ebertel.retailer.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ma.ebertel.retailer.Fragments.UserGenInfo;
import ma.ebertel.retailer.Fragments.UserPerInfo;
import ma.ebertel.retailer.Fragments.UserProductInfo;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public ViewPagerAdapter(MainActivity activity, FragmentManager fm, String Code) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new UserPerInfo(Code,activity));
        fragments.add(new UserGenInfo(activity));
        fragments.add(new UserProductInfo(activity));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
