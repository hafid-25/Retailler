package ma.ebertel.retailer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

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
