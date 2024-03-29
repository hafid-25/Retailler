package ma.ebertel.retailer.Fragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.ForwardingObject;

import net.cachapa.expandablelayout.ExpandableLayout;

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
import ma.ebertel.retailer.Tasks.GetBiscuitNamesTask;
import ma.ebertel.retailer.Tasks.GetCoucheNamesTask;
import ma.ebertel.retailer.Tasks.GetDetergentNamesTask;
import ma.ebertel.retailer.Tasks.GetLaitNamesTask;
import ma.ebertel.retailer.Tasks.GetPateNamesTask;
import ma.ebertel.retailer.Tasks.GetThéNamesTask;

public class UserProductInfo extends Fragment implements CompoundButton.OnCheckedChangeListener,
RadioGroup.OnCheckedChangeListener{

    private MainActivity activity;
    private Button btnSubmitUser;
    private SharedPreferences sharedPreferences;

    private RecyclerView detergentRecycler,théRecycler,laitRecycler,biscuitsRecycler,patesRecycler,coucheRecycler;
    private DetergentProductAdapter detergentProductAdapter;
    private List<String> checkedDetIds;
    public List<String> detergentNames;
    public List<String> detergentIds;

    private ThéProductAdapter théProductAdapter;
    private List<String> checkedThéIds;
    public List<String> théNames;
    public List<String> théIds;

    private LaitProductAdapter laitProductAdapter;
    private List<String> checkedLaitIds;
    public List<String> laitNames;
    public List<String> laitIds;

    private BiscuitProductAdapter biscuitProductAdapter;
    private List<String> checkedbiscuitIds;
    public List<String> biscuitNames;
    public List<String> biscuitIds;

    private PateProductAdapter pateProductAdapter;
    private List<String> checkedPateIds;
    public List<String> pateNames;
    public List<String> pateIds;

    private CoucheProductAdapter coucheProductAdapter;
    private List<String> checkedCoucheIds;
    public List<String> coucheNames;
    public List<String> coucheIds;

    private TextView detergentItemTitle,ThéItemTitle,LaitItemTitle,BiscuitsItemTitle,PatesItemTitle,CoucheItemTitle;

    private ProgressBar detergentWaiter,théWaiter,laitWaiter,biscuitWaiter,pateWaiter,coucheWaiter;
    private ExpandableLayout detExpendedLay,théExpandLayout,laitExpandLayout,biscuitExpendLayout,pateExpandeLayout,coucheExpendLayout;

    private RadioGroup rgSatisfaction;

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
        //getDetergentTypes();
        // get all the types from database
        //getThéTypes();
        // get all the lait (milk) types from database
        //getLaitTypes();
        // get all the biscuit types from database
        //getBiscuitTypes();
        // get all pate types from database
        //getPateTypes();
        // get all the couche and papier from the database
        //getCoucheTypes();
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
        rgSatisfaction = viewGroup.findViewById(R.id.rgSatisfaction);
        detExpendedLay = viewGroup.findViewById(R.id.detExpendedLay);
        théExpandLayout = viewGroup.findViewById(R.id.théExpandLayout);
        laitExpandLayout = viewGroup.findViewById(R.id.laitExpandLayout);
        biscuitExpendLayout = viewGroup.findViewById(R.id.biscuitExpendLayout);
        pateExpandeLayout = viewGroup.findViewById(R.id.pateExpandeLayout);
        coucheExpendLayout = viewGroup.findViewById(R.id.coucheExpendLayout);
        // get the detergent item
        detergentItemTitle = viewGroup.findViewById(R.id.detergentItemTitle);
        detergentWaiter = viewGroup.findViewById(R.id.detergentWaiter);
        detergentItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detergentNames.size() == 0){
                    GetDetergentNamesTask dTask = new GetDetergentNamesTask(activity,UserProductInfo.this,
                            detergentRecycler,detergentWaiter,detExpendedLay);
                    dTask.execute();
                }else {
                    detExpendedLay.toggle();
                }
            }
        });
        // get the thé items
        ThéItemTitle = viewGroup.findViewById(R.id.ThéItemTitle);
        théWaiter = viewGroup.findViewById(R.id.théWaiter);
        ThéItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(théNames.size() == 0){
                    GetThéNamesTask tTask = new GetThéNamesTask(activity,UserProductInfo.this,théRecycler,théWaiter,théExpandLayout);
                    tTask.execute();
                }else {
                    théExpandLayout.toggle();
                }
            }
        });

        // get the lait items
        LaitItemTitle = viewGroup.findViewById(R.id.LaitItemTitle);
        laitWaiter = viewGroup.findViewById(R.id.laitWaiter);
        LaitItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(laitNames.size() == 0){
                    GetLaitNamesTask lTask = new GetLaitNamesTask(activity,UserProductInfo.this,laitRecycler,laitWaiter,laitExpandLayout);
                    lTask.execute();
                }else {
                    laitExpandLayout.toggle();
                }
            }
        });

        // get biscuit items
        BiscuitsItemTitle = viewGroup.findViewById(R.id.BiscuitsItemTitle);
        biscuitWaiter = viewGroup.findViewById(R.id.biscuitWaiter);
        BiscuitsItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(biscuitNames.size() == 0){
                    GetBiscuitNamesTask bTask = new GetBiscuitNamesTask(activity,UserProductInfo.this,biscuitsRecycler,biscuitWaiter,biscuitExpendLayout);
                    bTask.execute();
                }else {
                    biscuitExpendLayout.toggle();
                }
            }
        });
        // get pate Items
        PatesItemTitle = viewGroup.findViewById(R.id.PatesItemTitle);
        pateWaiter = viewGroup.findViewById(R.id.pateWaiter);
        PatesItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pateNames.size()== 0){
                    GetPateNamesTask pTask = new GetPateNamesTask(activity,UserProductInfo.this,patesRecycler,pateWaiter,pateExpandeLayout);
                    pTask.execute();
                }else {
                    pateExpandeLayout.toggle();
                }
            }
        });

        // get couche items
        CoucheItemTitle = viewGroup.findViewById(R.id.CoucheItemTitle);
        coucheWaiter = viewGroup.findViewById(R.id.coucheWaiter);
        CoucheItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coucheNames.size() == 0){
                    GetCoucheNamesTask cTask = new GetCoucheNamesTask(activity,UserProductInfo.this,coucheRecycler,coucheWaiter,coucheExpendLayout);
                    cTask.execute();
                }else {
                    coucheExpendLayout.toggle();
                }
            }
        });






        sharedPreferences = activity.getSharedPreferences(getString(R.string.shared_name),Context.MODE_PRIVATE);

        rgSatisfaction.setOnCheckedChangeListener(this);

        btnSubmitUser = viewGroup.findViewById(R.id.btnSubmitUser);
        btnSubmitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ids", "onClick: detergent "+getIdsAsString("detergent"));
                Log.d("ids", "onClick: thé "+getIdsAsString("thé"));
                Log.d("ids", "onClick: lait "+getIdsAsString("lait"));
                Log.d("ids", "onClick: biscuit "+getIdsAsString("biscuit"));
                Log.d("ids", "onClick: pate "+getIdsAsString("pate"));
                Log.d("ids", "onClick: couche "+getIdsAsString("couche"));
                Log.d("ids", "onClick: satisfaction "+activity.clientSatisfacion);

                Log.d("ids","clientName "+activity.clientFullName);
                Log.d("ids","clientCode "+activity.codeBarContent);
                Log.d("ids","clientPhone "+activity.clientPhoneNumber);
                Log.d("ids","clientAddr "+activity.clientAddress);
                Log.d("ids","clientEmail "+activity.clientEmail);
                Log.d("ids","clientLoc "+activity.clientLocation);
                Log.d("ids","type "+ activity.clientType);
                Log.d("ids","clientCity "+ activity.selectedCityCode);
                Log.d("ids","clientRegion "+ activity.selectedRegionCode);
                Log.d("ids","clientDealers "+(getDealerAsString() != null ? getDealerAsString() : ""));
                Log.d("ids","clientSims "+(getSimsAsString() != null ? getSimsAsString() : ""));
                Log.d("ids","clientPot "+(getPotenceAsString() != null ? getPotenceAsString() : ""));
                Log.d("ids","clientMobile "+getMobileMonyAsString());
                Log.d("ids","clientTelephony "+getTelephonyAsString());
                Log.d("ids","clientAccessoire "+getAccessoireAsString());


                if(sharedPreferences.getString("role","0").equals("2")){
                    return;
                }
                submitData();
            }
        });


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
                Log.d("error", "onErrorResponse: "+error.toString());
                if(error.toString().equals("com.android.volley.TimeoutError")
                        || error.toString().equals("com.android.volley.ServerError")){
                    Toast.makeText(activity, getString(R.string.client_timeOut_text), Toast.LENGTH_LONG).show();
                    activity.finish();
                }
                Toast.makeText(activity, getString(R.string.client_error_text), Toast.LENGTH_LONG).show();
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
                // recherge data is removed
                //params.put("clientRecharge",getRechargeAsString() != null ? getRechargeAsString() : "");
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
        for (HashMap.Entry entry:activity.MobileMonny.entrySet()) {
            String[] values = entry.getValue().toString().split(",");
            if(values[0].equals("1") || values[1].equals("1")){
                builder.append(entry.getKey()+","+values[0]+","+values[1]+";");
            }
        }
        builder.append(",");
        String value = builder.toString().replace(";,","").trim();
        if(value.equals(",")){
            return "";
        }else {
            return value;
        }
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.btnSatisfactionYes:
                activity.clientSatisfacion = true;
                break;
            case R.id.btnSatisfactionNo:
                activity.clientSatisfacion = false;
                break;
        }
    }
}
