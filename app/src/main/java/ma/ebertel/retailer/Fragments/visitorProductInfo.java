package ma.ebertel.retailer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ma.ebertel.retailer.Adapters.GridAutoFitLayoutManager;
import ma.ebertel.retailer.Adapters.VisBiscuitProductAdapter;
import ma.ebertel.retailer.Adapters.VisCoucheProductAdapter;
import ma.ebertel.retailer.Adapters.VisDetergentProductAdapter;
import ma.ebertel.retailer.Adapters.VisLaitProductAdapter;
import ma.ebertel.retailer.Adapters.VisPateProductAdapter;
import ma.ebertel.retailer.Adapters.VisThéProductAdapter;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class visitorProductInfo extends Fragment {

    private MainActivity activity;

    private RecyclerView detergentVisRecycler,théVisRecycler,laitVisRecycler,biscuitsVisRecycler,patesVisRecycler,coucheRecycler;
    private VisDetergentProductAdapter visDetergentProductAdapter;
    private VisThéProductAdapter visThéProductAdapter;
    private VisLaitProductAdapter visLaitProductAdapter;
    private VisBiscuitProductAdapter visBiscuitProductAdapter;
    private VisPateProductAdapter visPateProductAdapter;
    private VisCoucheProductAdapter visCoucheProductAdapter;

    private SharedPreferences sharedPreferences;

    private ViewGroup remarks;
    private EditText edtVisRemark;
    private Button btnVisSubmitUser;

    public visitorProductInfo(MainActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.visitor_product_info,container,false);
        detergentVisRecycler = viewGroup.findViewById(R.id.detergentVisRecycler);
        théVisRecycler = viewGroup.findViewById(R.id.théVisRecycler);
        laitVisRecycler = viewGroup.findViewById(R.id.laitVisRecycler);
        biscuitsVisRecycler = viewGroup.findViewById(R.id.biscuitsVisRecycler);
        patesVisRecycler = viewGroup.findViewById(R.id.patesVisRecycler);
        coucheRecycler = viewGroup.findViewById(R.id.coucheRecycler);

        remarks = viewGroup.findViewById(R.id.remarks);
        edtVisRemark = viewGroup.findViewById(R.id.edtVisRemark);
        btnVisSubmitUser = viewGroup.findViewById(R.id.btnVisSubmitUser);

        btnVisSubmitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remark = edtVisRemark.getText().toString();
                if(!remark.equals("")){
                    AddVisRemark(remark);
                }else {
                    activity.finish();
                }
            }
        });

        setUpRecycler();

        sharedPreferences = activity.getSharedPreferences(getString(R.string.shared_name), Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role","0");
        if(role.equals("2")){
            remarks.setVisibility(View.VISIBLE);
        }else if (role.equals("1")){
            // hide the remark layout
            remarks.setVisibility(View.GONE);
        }
        return viewGroup;
    }

    private void setUpRecycler(){
        // set up detrgent recycler
        visDetergentProductAdapter = new VisDetergentProductAdapter(activity,activity.clientDetergent);
        detergentVisRecycler.setLayoutManager(new GridAutoFitLayoutManager(activity,150));
        detergentVisRecycler.setHasFixedSize(true);
        detergentVisRecycler.setAdapter(visDetergentProductAdapter);

        // set up thz recycler
        visThéProductAdapter = new VisThéProductAdapter(activity,activity.clientThe);
        théVisRecycler.setLayoutManager(new GridAutoFitLayoutManager(activity,150));
        théVisRecycler.setHasFixedSize(true);
        théVisRecycler.setAdapter(visThéProductAdapter);

        // set up lait recycler
        visLaitProductAdapter = new VisLaitProductAdapter(activity,activity.clientLait);
        laitVisRecycler.setLayoutManager(new GridAutoFitLayoutManager(activity,150));
        laitVisRecycler.setHasFixedSize(true);
        laitVisRecycler.setAdapter(visLaitProductAdapter);

        // set up biscuit recycler
        visBiscuitProductAdapter = new VisBiscuitProductAdapter(activity,activity.clientBiscuit);
        biscuitsVisRecycler.setLayoutManager(new GridAutoFitLayoutManager(activity,150));
        biscuitsVisRecycler.setHasFixedSize(true);
        biscuitsVisRecycler.setAdapter(visBiscuitProductAdapter);

        // set up pate recycler
        visPateProductAdapter = new VisPateProductAdapter(activity,activity.clientPate);
        patesVisRecycler.setLayoutManager(new GridAutoFitLayoutManager(activity,150));
        patesVisRecycler.setHasFixedSize(true);
        patesVisRecycler.setAdapter(visPateProductAdapter);

        // set up pate recycler
        visCoucheProductAdapter = new VisCoucheProductAdapter(activity,activity.clientCouche);
        coucheRecycler.setLayoutManager(new GridAutoFitLayoutManager(activity,150));
        coucheRecycler.setHasFixedSize(true);
        coucheRecycler.setAdapter(visCoucheProductAdapter);

    }

    private void AddVisRemark(final String remark) {
        String remarkUrl = "http://hafid.skandev.com/addRemark.php";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, remarkUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("success")){
                        activity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("clientId",activity.clientId);
                params.put("remark",remark);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }
}
