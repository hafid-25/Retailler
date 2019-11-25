package ma.ebertel.retailer.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class Home extends Fragment {

    private MainActivity activity;
    private TextView username,fullname,email,userRole;
    private Button btnAddNewUser;
    private SharedPreferences sharedPreferences;

    public Home(MainActivity a,SharedPreferences sh){
        activity = a;
        sharedPreferences = sh;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.home,container,false);
        btnAddNewUser = viewGroup.findViewById(R.id.btnAddUser);

        username = viewGroup.findViewById(R.id.username);
        fullname = viewGroup.findViewById(R.id.fullname);
        email = viewGroup.findViewById(R.id.email);
        userRole = viewGroup.findViewById(R.id.userRole);
        btnAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.lunshScanner();
            }
        });

        setUserInfo();

        return viewGroup;
    }

    private void setUserInfo(){
        username.setText(sharedPreferences.getString("username","not found"));
        fullname.setText(sharedPreferences.getString("name","not found"));
        email.setText(sharedPreferences.getString("email","not found"));
        String role = sharedPreferences.getString("role","0");
        if(role.equals("1")){
            userRole.setText("Vondeur");
            btnAddNewUser.setText(getString(R.string.btn_add_new_user_text));
        }else if (role.equals("2")){
            userRole.setText("Visiteur");
            btnAddNewUser.setText(getString(R.string.btn_visit_user_text));
        }else if(role.equals("0")){
            userRole.setText("Not Found");
        }

    }
}
