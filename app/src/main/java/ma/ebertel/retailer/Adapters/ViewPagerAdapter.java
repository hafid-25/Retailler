package ma.ebertel.retailer.Adapters;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ma.ebertel.retailer.Fragments.UserGenInfo;
import ma.ebertel.retailer.Fragments.UserPerInfo;
import ma.ebertel.retailer.Fragments.UserProductInfo;
import ma.ebertel.retailer.Fragments.visitorProductInfo;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private SharedPreferences sharedPreferences;

    public ViewPagerAdapter(MainActivity activity, FragmentManager fm, String Code) {
        super(fm);
        sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.shared_name), Context.MODE_PRIVATE);
        fragments = new ArrayList<>();
        fragments.add(new UserPerInfo(Code,activity));
        fragments.add(new UserGenInfo(activity));
        String role = sharedPreferences.getString("role","0");
        if(role.equals("1")){
            fragments.add(new UserProductInfo(activity));
        }else if(role.equals("2")){
            fragments.add(new visitorProductInfo(activity));
        }
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
