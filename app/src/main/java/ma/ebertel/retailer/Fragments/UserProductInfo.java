package ma.ebertel.retailer.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ebertel.retailer.Adapters.BiscuitProductAdapter;
import ma.ebertel.retailer.Adapters.CoucheProductAdapter;
import ma.ebertel.retailer.Adapters.DetergentProductAdapter;
import ma.ebertel.retailer.Adapters.GridAutoFitLayoutManager;
import ma.ebertel.retailer.Adapters.LaitProductAdapter;
import ma.ebertel.retailer.Adapters.PateProductAdapter;
import ma.ebertel.retailer.Adapters.ThéProductAdapter;
import ma.ebertel.retailer.Helpers.BitmapHelper;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class UserProductInfo extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private MainActivity activity;
    private Button btnSubmitUser;
    private SharedPreferences sharedPreferences;

    private RecyclerView detergentRecycler,théRecycler,laitRecycler,biscuitsRecycler,patesRecycler,coucheRecycler;
    private DetergentProductAdapter detergentProductAdapter;
    private List<String> checkedDetIds;
    private List<String> detergentNames;
    private List<String> detergentIds;

    private ThéProductAdapter théProductAdapter;
    private List<String> checkedThéIds;
    private List<String> théNames;
    private List<String> théIds;

    private LaitProductAdapter laitProductAdapter;
    private List<String> checkedLaitIds;
    private List<String> laitNames;
    private List<String> laitIds;

    private BiscuitProductAdapter biscuitProductAdapter;
    private List<String> checkedbiscuitIds;
    private List<String> biscuitNames;
    private List<String> biscuitIds;

    private PateProductAdapter pateProductAdapter;
    private List<String> checkedPateIds;
    private List<String> pateNames;
    private List<String> pateIds;

    private CoucheProductAdapter coucheProductAdapter;
    private List<String> checkedCoucheIds;
    private List<String> coucheNames;
    private List<String> coucheIds;

    public UserProductInfo(MainActivity activity){
        this.activity = activity;

        checkedDetIds = new ArrayList<>();
        detergentNames = new ArrayList<>();
        detergentIds = new ArrayList<>();

        checkedThéIds = new ArrayList<>();
        théNames = new ArrayList<>();
        théIds = new ArrayList<>();

        checkedLaitIds = new ArrayList<>();
        laitNames = new ArrayList<>();
        laitIds = new ArrayList<>();

        checkedbiscuitIds = new ArrayList<>();
        biscuitNames = new ArrayList<>();
        biscuitIds = new ArrayList<>();

        checkedPateIds = new ArrayList<>();
        pateNames = new ArrayList<>();
        pateIds = new ArrayList<>();

        checkedCoucheIds = new ArrayList<>();
        coucheNames = new ArrayList<>();
        coucheIds = new ArrayList<>();

        //get all the product types from database
        getDetergentTypes();
        // get all the types from database
        getThéTypes();
        // get all the lait (milk) types from database
        getLaitTypes();
        // get all the biscuit types from database
        getBiscuitTypes();
        // get all pate types from database
        getPateTypes();
        // get all the couche and papier from the database
        getCoucheTypes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.user_product_info, container, false);
        detergentRecycler = viewGroup.findViewById(R.id.detergentRecycler);
        théRecycler = viewGroup.findViewById(R.id.théRecycler);
        laitRecycler = viewGroup.findViewById(R.id.laitRecycler);
        biscuitsRecycler = viewGroup.findViewById(R.id.biscuitsRecycler);
        patesRecycler = viewGroup.findViewById(R.id.patesRecycler);
        coucheRecycler = viewGroup.findViewById(R.id.coucheRecycler);

        sharedPreferences = activity.getSharedPreferences(getString(R.string.shared_name),Context.MODE_PRIVATE);

        btnSubmitUser = viewGroup.findViewById(R.id.btnSubmitUser);
        btnSubmitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "I think you are ready to finish you jobe", Toast.LENGTH_SHORT).show();
                Log.d("ids", "onClick: detergent "+getIdsAsString("detergent"));
                Log.d("ids", "onClick: thé "+getIdsAsString("thé"));
                Log.d("ids", "onClick: lait "+getIdsAsString("lait"));
                Log.d("ids", "onClick: biscuit "+getIdsAsString("biscuit"));
                Log.d("ids", "onClick: pate "+getIdsAsString("pate"));
                Log.d("ids", "onClick: couche "+getIdsAsString("couche"));
                if(sharedPreferences.getString("role","0").equals("2")){
                    // add visiteur remark and return
                    // todo add the remark edite text to this fragment and decoment the bellow lines
                    /*String remark = edtVisRemark.getText().toString();
                    if(!remark.equals("") && !activity.clientId.equals("")){
                        AddVisRemark(remark);
                    }*/
                    return;
                }
                submitData();
            }
        });

        // set Up the detergent adapter
        setUpRecycler();

        return viewGroup;

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String itemTag = compoundButton.getTag().toString();
        String itemName = compoundButton.getText().toString();
        switch (itemTag){
            case "detergent":
                int pos = detergentNames.indexOf(itemName);
                if(b){
                    checkedDetIds.add(detergentIds.get(pos));
                }else {
                    checkedDetIds.remove(detergentIds.get(pos));
                }
                break;
            case "thé":
                int tpos = théNames.indexOf(itemName);
                if(b){
                    checkedThéIds.add(théIds.get(tpos));
                }else {
                    checkedThéIds.remove(théIds.get(tpos));
                }
                break;
            case "lait":
                int lpos = laitNames.indexOf(itemName);
                if(b){
                    checkedLaitIds.add(laitIds.get(lpos));
                }else {
                    checkedLaitIds.remove(laitIds.get(lpos));
                }
                break;
            case "biscuit":
                int bpos = biscuitNames.indexOf(itemName);
                if(b){
                    checkedbiscuitIds.add(biscuitIds.get(bpos));
                }else {
                    checkedbiscuitIds.remove(biscuitIds.get(bpos));
                }
                break;
            case "pate":
                int Ppos = pateNames.indexOf(itemName);
                if(b){
                    checkedPateIds.add(pateIds.get(Ppos));
                }else {
                    checkedPateIds.remove(pateIds.get(Ppos));
                }
                break;
            case "couche":
                int cpos = coucheNames.indexOf(itemName);
                if(b){
                    checkedCoucheIds.add(coucheIds.get(cpos));
                }else {
                    checkedCoucheIds.remove(coucheIds.get(cpos));
                }
                break;
        }
    }

    private void setUpRecycler(){
        detergentProductAdapter = new DetergentProductAdapter(this.getContext(),detergentNames,this);
        detergentRecycler.setAdapter(detergentProductAdapter);
        detergentRecycler.setHasFixedSize(true);
        detergentRecycler.setLayoutManager(new GridAutoFitLayoutManager(this.getContext(),150));
        // thé recycler
        théProductAdapter = new ThéProductAdapter(this.getContext(),théNames,this);
        théRecycler.setAdapter(théProductAdapter);
        théRecycler.setHasFixedSize(true);
        théRecycler.setLayoutManager(new GridAutoFitLayoutManager(this.getContext(),150));
        // lait recycler
        laitProductAdapter = new LaitProductAdapter(this.getContext(),laitNames,this);
        laitRecycler.setAdapter(laitProductAdapter);
        laitRecycler.setHasFixedSize(true);
        laitRecycler.setLayoutManager(new GridAutoFitLayoutManager(this.getContext(),150));
        // biscuit recycler
        biscuitProductAdapter = new BiscuitProductAdapter(this.getContext(),biscuitNames,this);
        biscuitsRecycler.setAdapter(biscuitProductAdapter);
        biscuitsRecycler.setHasFixedSize(false);
        biscuitsRecycler.setLayoutManager(new GridAutoFitLayoutManager(this.getContext(),150));
        // pates recycler
        pateProductAdapter = new PateProductAdapter(this.getContext(),pateNames,this);
        patesRecycler.setAdapter(pateProductAdapter);
        patesRecycler.setHasFixedSize(true);
        patesRecycler.setLayoutManager(new GridAutoFitLayoutManager(this.getContext(),150));
        // couche recycler
        coucheProductAdapter = new CoucheProductAdapter(this.getContext(),coucheNames,this);
        coucheRecycler.setAdapter(coucheProductAdapter);
        coucheRecycler.setHasFixedSize(true);
        coucheRecycler.setLayoutManager(new GridAutoFitLayoutManager(this.getContext(),150));
    }

    private void getDetergentTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++){
                        detergentNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        detergentIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("type","detergent");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(detergentRequest);
    }

    private void getThéTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++){
                        théNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        théIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("type","thé");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(detergentRequest);
    }

    private void getLaitTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++){
                        laitNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        laitIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("type","lait");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(detergentRequest);
    }

    private void getBiscuitTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++){
                        biscuitNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        biscuitIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("type","biscuit");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(detergentRequest);
    }

    private void getPateTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++){
                        pateNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        pateIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("type","pate");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(detergentRequest);
    }

    private void getCoucheTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++){
                        coucheNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        coucheIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("type","couche");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(detergentRequest);
    }

    private String getIdsAsString(String type){
        List<String> checkedIds = new ArrayList<>();
        switch (type){
            case "detergent":
                checkedIds = checkedDetIds;
                break;
            case "thé":
                checkedIds = checkedThéIds;
                break;
            case "lait":
                checkedIds = checkedLaitIds;
                break;
            case "biscuit":
                checkedIds = checkedbiscuitIds;
                break;
            case "pate":
                checkedIds = checkedPateIds;
                break;
            case "couche":
                checkedIds = checkedCoucheIds;
                break;
        }
        StringBuilder builder = new StringBuilder();
        for (String id: checkedIds) {
            builder.append(id+",");
        }
        if(!builder.toString().equals("")){
            String ids = builder.toString().trim().substring(0,builder.toString().length()-1);
            return ids;
        }else {
            return "";
        }
    }

    private void submitData(){
        String url = "http://hafid.skandev.com/addClient.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("messgae");
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    if(message.equals("Client Added Successfully")){
                        activity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Connection Error Or This Client Is Already Exists", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                // client peronnel data
                params.put("clientName",activity.clientFullName);
                params.put("clientCode",activity.codeBarContent);
                params.put("clientPhone",activity.clientPhoneNumber);
                params.put("clientAddr",activity.clientAddress);
                params.put("clientEmail",activity.clientEmail);
                params.put("clientPer",activity.clientPeriority ? "1" : "0");
                params.put("clientSat",activity.clientSatisfacion ? "1" : "0");
                params.put("clientCnss",activity.clientInterssCnss ? "1" : "0");
                params.put("clientLoc",activity.clientLocation);
                params.put("real_pic", BitmapHelper.convertBitmapToString(activity.clientImage));
                params.put("type", activity.clientType);


                params.put("clientCity", activity.selectedCityCode);
                params.put("clientRegion", activity.selectedRegionCode);


                //complex data
                params.put("clientDealers",getDealerAsString() != null ? getDealerAsString() : "");
                params.put("clientRecharge",getRechargeAsString() != null ? getRechargeAsString() : "");
                params.put("clientSims",getSimsAsString() != null ? getSimsAsString() : "");
                params.put("clientPot",getPotenceAsString() != null ? getPotenceAsString() : "");

                params.put("clientMobile",getMobileMonyAsString());
                params.put("clientTelephony",getTelephonyAsString());
                params.put("clientAccessoire",getAccessoireAsString());

                //client produc data
                params.put("detergent",getIdsAsString("detergent"));
                params.put("thee",getIdsAsString("thé"));
                params.put("lait",getIdsAsString("lait"));
                params.put("biscuit",getIdsAsString("biscuit"));
                params.put("pate",getIdsAsString("pate"));
                params.put("couche",getIdsAsString("couche"));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }

    private String getDealerAsString(){
        StringBuilder dealerString = new StringBuilder();

        for (String[] d: activity.Dealers) {
            for (String sVal:d) {
                dealerString.append(sVal+",");
            }
            dealerString.append(";");
        }
        String newDel = dealerString.toString().replace(",;",";").trim();
        if(!newDel.equals("")){
            newDel = newDel.substring(0,newDel.length()-1);
            return newDel;
        }
        return null;
}

    private String getRechargeAsString(){
        StringBuilder rechargeString = new StringBuilder();

        for (String[] d: activity.Rechargs) {
            for (String sVal:d) {
                rechargeString.append(sVal+",");
            }
            rechargeString.append(";");
        }
        String newRech = rechargeString.toString().replace(",;",";").trim();
        if(!newRech.equals("")){
            newRech = newRech.substring(0,newRech.length()-1);
            return newRech;
        }
        return null;
    }

    private String getSimsAsString(){
        StringBuilder simsString = new StringBuilder();

        for (String[] d: activity.Sims) {
            for (String sVal:d) {
                simsString.append(sVal+",");
            }
            simsString.append(";");
        }
        String newSim = simsString.toString().replace(",;",";").trim();
        if(!newSim.equals("")){
            newSim = newSim.substring(0,newSim.length()-1);
            return newSim;
        }
        return null;
    }

    private String getMobileMonyAsString(){
        StringBuilder builder = new StringBuilder();
        if(!activity.MobileMonnyType.equals("")){
            builder.append(activity.MobileMonnyType+",");
            if(activity.MobileMonyInteress){
                builder.append("true,");
            }else {
                builder.append("false,");
            }

            if(activity.MobileMonyPropose){
                builder.append("true");
            }else {
                builder.append("false");
            }
        }else {
            builder.append("");
        }

        return builder.toString();
    }

    private String getTelephonyAsString(){
        StringBuilder builder = new StringBuilder();
        if(activity.Telephony){
            builder.append("true,"+activity.TelephonyGamme);
        }else {
            builder.append("false,Non");
        }
        return builder.toString();
    }

    private String getAccessoireAsString(){
        StringBuilder builder = new StringBuilder();
        if(activity.Accessoire){
            builder.append("true,"+activity.AccessoireGamme);
        }else {
            builder.append("false,Non");
        }
        return builder.toString();
    }

    private String getPotenceAsString(){
        StringBuilder builder = new StringBuilder();
        for (String p: activity.Potence) {
            builder.append(p+",");
        }
        String newP = builder.toString().trim();
        if(!newP.equals("")){
            newP = newP.substring(0,newP.length()-1);
            return newP;
        }
        return null;

    }
}
