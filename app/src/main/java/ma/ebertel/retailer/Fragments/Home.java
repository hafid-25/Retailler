package ma.ebertel.retailer.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class Home extends Fragment {

    private MainActivity activity;

    public Home(MainActivity a){
        activity = a;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.home,container,false);
        Button btnAddNewUser = viewGroup.findViewById(R.id.btnAddUser);
        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.lunshScanner();
            }
        });
        return viewGroup;
    }
}
