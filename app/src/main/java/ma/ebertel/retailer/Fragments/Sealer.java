package ma.ebertel.retailer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import ma.ebertel.retailer.Adapters.ViewPagerAdapter;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class Sealer extends Fragment {

    MainActivity activity;
    String codeBare = "";


    public Sealer(MainActivity a,String Code){
        this.activity = a;
        this.codeBare = Code;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.sealer,container,false);
        ViewPager pager = viewGroup.findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(activity,activity.getSupportFragmentManager(),codeBare);
        pager.setAdapter(pagerAdapter);
        activity.pager = pager;
        return viewGroup;
    }
}
