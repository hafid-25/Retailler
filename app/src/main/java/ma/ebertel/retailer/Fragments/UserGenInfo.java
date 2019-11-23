package ma.ebertel.retailer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class UserGenInfo extends Fragment {
    MainActivity activity;

    public UserGenInfo(MainActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.user_gen_info,container,false);
        return viewGroup;
    }
}
