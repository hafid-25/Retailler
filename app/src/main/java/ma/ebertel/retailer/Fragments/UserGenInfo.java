package ma.ebertel.retailer.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ebertel.retailer.Helpers.BitmapHelper;
import ma.ebertel.retailer.Login;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class UserGenInfo extends Fragment implements
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener {

    MainActivity activity;
    public CheckBox chkPotInwi,chkPotOrange,chkPotIam
            ,chkHasOrgDealer,chkHasInwiDealer,chkHasIamDealer
            ,chkHasOrgDealerActiva,chkHasInwiDealerActiva,chkHasIamDealerActiva
            ,chkHasOrgDealerRecharge,chkHasInwiDealerRecharge,chkHasIamDealerRecharge
            ,chkHasOrgDealerAdv,chkHasInwiDealerAdv,chkHasIamDealerAdv
            ,chkHasOrgRecharge,chkHasInwiRecharge,chkHasIamRecharge
            ,chkHasOrgRechargeExp,chkHasInwiRechargeExp,chkHasIamRechargeExp
            ,chkHasOrgRechargeGra,chkHasInwiRechargeGra,chkHasIamRechargeGra
            ,chkHasOrgCim,chkHasInwiCim,chkHasIamCim;
    public ImageButton btnAddValidUser;
    public EditText edtOrgDealerMaxQte,edtInwiDealerMaxQte,edtIamDealerMaxQte
            ,edtOrgCimPrixu,edtInwiCimPrixu,edtIamCimPrixu
            ,edtOrgCimMaxQte,edtInwiCimMaxQte,edtIamCimMaxQte
            ,edtVisRemark;
    public RadioGroup radioPropose,radioInterests,radioTelephony,TelephonyRadioGroup,radioAccessoir,AccessGammeRadioGroup;
    public RadioButton btnAccssBasGamme,btnAccessHautGamme
                        ,btnTelHautGamme,btnTelBasGamme
                        ,btnInterestsYes,btnInterestsNo
                        ,btnProposeYes,btnProposeNo
                        ,btnTelephonyNo,btnTelephonyYes
                        ,btnAccessYes,btnAccessNo;
    public Spinner OrgRechargeChefre,InwiRechargeChefre,IamRechargeChefre
                ,mobileMoneyTypeList,authorMarksList;
    public String rechargeIamChifre="",rechargeInwiChifre="",rechargeOrgChifre="",mobileMonnyType="",auhtorMark="";
    public String[] chifers;
    public List<String> mobiles;
    public List<String> Markes;

    public ViewGroup remarks;

    public SharedPreferences sharedPreferences;

    public String submitUrl = "http://hafid.skandev.com/addClient.php";

    public UserGenInfo(MainActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.user_gen_info,container,false);

        radioPropose = viewGroup.findViewById(R.id.radioPropose);
        radioInterests = viewGroup.findViewById(R.id.radioInterests);
        radioTelephony = viewGroup.findViewById(R.id.radioTelephony);
        TelephonyRadioGroup = viewGroup.findViewById(R.id.TelephonyRadioGroup);
        radioAccessoir = viewGroup.findViewById(R.id.radioAccessoir);
        AccessGammeRadioGroup = viewGroup.findViewById(R.id.AccessGammeRadioGroup);
        remarks = viewGroup.findViewById(R.id.remarks);

        btnAccssBasGamme = viewGroup.findViewById(R.id.btnAccssBasGamme);
        btnAccessHautGamme = viewGroup.findViewById(R.id.btnAccessHautGamme);
        btnTelBasGamme = viewGroup.findViewById(R.id.btnTelBasGamme);
        btnTelHautGamme = viewGroup.findViewById(R.id.btnTelHautGamme);
        edtVisRemark = viewGroup.findViewById(R.id.edtVisRemark);

        radioInterests.setOnCheckedChangeListener(this);
        radioPropose.setOnCheckedChangeListener(this);
        radioTelephony.setOnCheckedChangeListener(this);
        TelephonyRadioGroup.setOnCheckedChangeListener(this);
        radioAccessoir.setOnCheckedChangeListener(this);
        AccessGammeRadioGroup.setOnCheckedChangeListener(this);
        //
        chkPotInwi = viewGroup.findViewById(R.id.chkPotInwi);
        chkPotOrange = viewGroup.findViewById(R.id.chkPotOrange);
        chkPotIam = viewGroup.findViewById(R.id.chkPotIam);
        // dealers
        chkHasIamDealer = viewGroup.findViewById(R.id.chkHasIamDealer);
        chkHasInwiDealer = viewGroup.findViewById(R.id.chkHasInwiDealer);
        chkHasOrgDealer = viewGroup.findViewById(R.id.chkHasOrgDealer);
        // dealer activation option
        chkHasOrgDealerActiva = viewGroup.findViewById(R.id.chkHasOrgDealerActiva);
        chkHasInwiDealerActiva = viewGroup.findViewById(R.id.chkHasInwiDealerActiva);
        chkHasIamDealerActiva = viewGroup.findViewById(R.id.chkHasIamDealerActiva);
        // dealer recharge option
        chkHasOrgDealerRecharge = viewGroup.findViewById(R.id.chkHasOrgDealerRecharge);
        chkHasInwiDealerRecharge = viewGroup.findViewById(R.id.chkHasInwiDealerRecharge);
        chkHasIamDealerRecharge = viewGroup.findViewById(R.id.chkHasIamDealerRecharge);
        // dealer adventagous option
        chkHasOrgDealerAdv = viewGroup.findViewById(R.id.chkHasOrgDealerAdv);
        chkHasInwiDealerAdv = viewGroup.findViewById(R.id.chkHasInwiDealerAdv);
        chkHasIamDealerAdv = viewGroup.findViewById(R.id.chkHasIamDealerAdv);
        // dealer max qte
        edtOrgDealerMaxQte = viewGroup.findViewById(R.id.edtOrgDealerMaxQte);
        edtInwiDealerMaxQte = viewGroup.findViewById(R.id.edtInwiDealerMaxQte);
        edtIamDealerMaxQte = viewGroup.findViewById(R.id.edtIamDealerMaxQte);

        // rechareg
        chkHasOrgRecharge = viewGroup.findViewById(R.id.chkHasOrgRecharge);
        chkHasInwiRecharge = viewGroup.findViewById(R.id.chkHasInwiRecharge);
        chkHasIamRecharge = viewGroup.findViewById(R.id.chkHasIamRecharge);
        // recharge chifre d'affaire
        OrgRechargeChefre = viewGroup.findViewById(R.id.OrgRechargeChefre);
        InwiRechargeChefre = viewGroup.findViewById(R.id.InwiRechargeChefre);
        IamRechargeChefre = viewGroup.findViewById(R.id.IamRechargeChefre);
        // desible all the chifre spinners
        OrgRechargeChefre.setEnabled(false);
        InwiRechargeChefre.setEnabled(false);
        IamRechargeChefre.setEnabled(false);

        chifers = new String[]{"1000-5000","5000-10000",">10000"};
        // set recharge spinner adapters
        ArrayAdapter<String> rechargeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,
                chifers);
        OrgRechargeChefre.setAdapter(rechargeAdapter);
        InwiRechargeChefre.setAdapter(rechargeAdapter);
        IamRechargeChefre.setAdapter(rechargeAdapter);

        // set onccheckLiteners
        OrgRechargeChefre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rechargeOrgChifre =  chifers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        InwiRechargeChefre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rechargeInwiChifre =  chifers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        IamRechargeChefre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rechargeIamChifre =  chifers[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // rechareg express
        chkHasIamRechargeExp = viewGroup.findViewById(R.id.chkHasIamRechargeExp);
        chkHasOrgRechargeExp = viewGroup.findViewById(R.id.chkHasOrgRechargeExp);
        chkHasInwiRechargeExp = viewGroup.findViewById(R.id.chkHasInwiRechargeExp);
        // recharge car
        chkHasOrgRechargeGra = viewGroup.findViewById(R.id.chkHasOrgRechargeGra);
        chkHasInwiRechargeGra = viewGroup.findViewById(R.id.chkHasInwiRechargeGra);
        chkHasIamRechargeGra = viewGroup.findViewById(R.id.chkHasIamRechargeGra);

        // cims
        chkHasOrgCim = viewGroup.findViewById(R.id.chkHasOrgCim);
        chkHasInwiCim = viewGroup.findViewById(R.id.chkHasInwiCim);
        chkHasIamCim = viewGroup.findViewById(R.id.chkHasIamCim);
        // cims prix u
        edtOrgCimPrixu = viewGroup.findViewById(R.id.edtOrgCimPrixu);
        edtInwiCimPrixu = viewGroup.findViewById(R.id.edtInwiCimPrixu);
        edtIamCimPrixu = viewGroup.findViewById(R.id.edtIamCimPrixu);
        // cims max qte
        edtOrgCimMaxQte = viewGroup.findViewById(R.id.edtOrgCimMaxQte);
        edtInwiCimMaxQte = viewGroup.findViewById(R.id.edtInwiCimMaxQte);
        edtIamCimMaxQte = viewGroup.findViewById(R.id.edtIamCimMaxQte);


        btnAddValidUser = viewGroup.findViewById(R.id.btnAddValidUser);

        btnInterestsNo = viewGroup.findViewById(R.id.btnInterestsNo);
        btnInterestsYes = viewGroup.findViewById(R.id.btnInterestsYes);
        btnProposeNo = viewGroup.findViewById(R.id.btnProposeNo);
        btnProposeYes = viewGroup.findViewById(R.id.btnProposeYes);

        btnTelephonyYes = viewGroup.findViewById(R.id.btnTelephonyYes);
        btnTelephonyNo = viewGroup.findViewById(R.id.btnTelephonyNo);

        btnAccessNo = viewGroup.findViewById(R.id.btnAccessNo);
        btnAccessYes = viewGroup.findViewById(R.id.btnAccessYes);

        // potence listeners
        chkPotInwi.setOnCheckedChangeListener(this);
        chkPotOrange.setOnCheckedChangeListener(this);
        chkPotIam.setOnCheckedChangeListener(this);
        // dealers
        chkHasIamDealer.setOnCheckedChangeListener(this);
        chkHasInwiDealer.setOnCheckedChangeListener(this);
        chkHasOrgDealer.setOnCheckedChangeListener(this);
        // recharge
        chkHasOrgRecharge.setOnCheckedChangeListener(this);
        chkHasInwiRecharge.setOnCheckedChangeListener(this);
        chkHasIamRecharge.setOnCheckedChangeListener(this);

        // cims
        chkHasOrgCim.setOnCheckedChangeListener(this);
        chkHasInwiCim.setOnCheckedChangeListener(this);
        chkHasIamCim.setOnCheckedChangeListener(this);


        chkHasOrgDealerActiva.setOnCheckedChangeListener(this);
        chkHasInwiDealerActiva.setOnCheckedChangeListener(this);
        chkHasIamDealerActiva.setOnCheckedChangeListener(this);

        // potence or visibility spinner
        authorMarksList = viewGroup.findViewById(R.id.authorMarksList);

        Markes = new ArrayList<>();
        Markes.add("");
        authorMarksList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                auhtorMark = Markes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // mobile monny spinner
        mobileMoneyTypeList = viewGroup.findViewById(R.id.mobileMoneyTypeList);
        mobiles = new ArrayList<>();
        mobiles.add("");

        mobileMoneyTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mobileMonnyType = mobiles.get(i);
                activity.MobileMonnyType = mobileMonnyType;
                if(mobileMonnyType.equals("")){
                    // deactivate all the radio buttons
                    btnInterestsYes.setEnabled(false);
                    btnInterestsNo.setEnabled(false);
                    btnProposeYes.setEnabled(false);
                    btnProposeNo.setEnabled(false);
                }else {
                    // reactivate all the authir radio buttons
                    btnInterestsYes.setEnabled(true);
                    btnInterestsNo.setEnabled(true);
                    btnProposeYes.setEnabled(true);
                    btnProposeNo.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddValidUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set Potence data
                setPotenceData();
                // set the dealler info
                setDealerData();
                // set the rechage data
                setRechargeData();
                // set the sims info
                setSimsData();
                // check if the user clicked the button ont the first fragment and the uder info are valied
                // and redy to be submitted to the server
                if(isUserDateValide()){
                    // data is ready to be submitted
                    activity.pager.setCurrentItem(2,true);

                }else {
                    // show message to user
                    Toast.makeText(activity, "Please Check All Your Fields Something is Messing", Toast.LENGTH_SHORT).show();
                }
            }


        });

        sharedPreferences = activity.getSharedPreferences(getString(R.string.shared_name), Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role","0");
        if(role.equals("2")){
            desibleOptionForVis();
            remarks.setVisibility(View.VISIBLE);
        }else if (role.equals("1")){
            // hide the remark layout
            remarks.setVisibility(View.GONE);
            getMarks();
            getMobiles();
        }
        return viewGroup;
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

    @Override
    public void onStart() {
        super.onStart();
        getVisitorData();
    }

    private boolean isUserDateValide() {
        if(!activity.clientFullName.equals("") && !activity.clientLocation.equals("")
                && !activity.clientPhoneNumber.equals("") && !activity.codeBarContent.equals("")
        && activity.clientImage != null && !activity.clientAddress.equals("")
        && !activity.selectedCityCode.equals("") && !activity.selectedRegionCode.equals("")
        && !activity.clientType.equals("")){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String checked = compoundButton.getText().toString();
        if(compoundButton.getId() == R.id.chkHasIamDealer || compoundButton.getId() == R.id.chkHasInwiDealer ||
                compoundButton.getId() == R.id.chkHasOrgDealer){
            if(b){
                enableOptions(checked,"del");
            }else {
                desibleOptions(checked,"del");
            }
        }
        if(compoundButton.getId() == R.id.chkHasOrgRecharge || compoundButton.getId() == R.id.chkHasInwiRecharge ||
                compoundButton.getId() == R.id.chkHasIamRecharge){
            if(b){
                enableOptions(checked,"rech");
            }else {
                desibleOptions(checked,"rech");
            }
        }
        if(compoundButton.getId() == R.id.chkHasOrgCim || compoundButton.getId() == R.id.chkHasInwiCim ||
                compoundButton.getId() == R.id.chkHasIamCim){
            if(b){
                enableOptions(checked,"cim");
            }else {
                desibleOptions(checked,"cim");
            }
        }
    }

    private void setPotenceData(){
        activity.Potence.clear();
        if(chkPotInwi.isChecked()){
            activity.Potence.add("Inwi");
        }
        if(chkPotOrange.isChecked()){
            activity.Potence.add("Org");
        }
        if(chkPotIam.isChecked()){
            activity.Potence.add("Iam");
        }
        if(!auhtorMark.equals("")){
            activity.Potence.add(auhtorMark);
        }
    }

    private void setDealerData(){
        String dname ;
        String dactiv;
        String drech;
        String dadv;
        String dqtemax;
        activity.Dealers.clear();
        if(chkHasIamDealer.isChecked()){
            // insert the iam dealer info
            dname = "Iam";
            dactiv = "0";
            drech = "0";
            dadv = "0";
            dqtemax = "0";
            if(chkHasIamDealerActiva.isChecked() && chkHasIamDealerActiva.isEnabled()){
                dactiv = "1";
            }
            if(chkHasIamDealerRecharge.isChecked() && chkHasIamDealerRecharge.isEnabled()){
                drech = "1";
            }
            if(chkHasIamDealerAdv.isChecked() && chkHasIamDealerAdv.isEnabled()){
                dadv = "1";
            }
            if(!edtIamDealerMaxQte.getText().toString().equals("")){
                dqtemax = edtIamDealerMaxQte.getText().toString();
            }

            String[] d = new String[]{dname,dactiv,drech,dadv,dqtemax};
            activity.Dealers.add(d);
        }
        if(chkHasInwiDealer.isChecked()){
            // insert the inwi dealer info
            dname = "Inwi";
            dactiv = "0";
            drech = "0";
            dadv = "0";
            dqtemax = "0";
            if(chkHasInwiDealerActiva.isChecked() && chkHasInwiDealerActiva.isEnabled()){
                dactiv = "1";
            }
            if(chkHasInwiDealerRecharge.isChecked() && chkHasInwiDealerRecharge.isEnabled()){
                drech = "1";
            }
            if(chkHasInwiDealerAdv.isChecked() && chkHasInwiDealerAdv.isEnabled()){
                dadv = "1";
            }
            if(!edtInwiDealerMaxQte.getText().toString().equals("")){
                dqtemax = edtInwiDealerMaxQte.getText().toString();
            }

            String[] d = new String[]{dname,dactiv,drech,dadv,dqtemax};
            activity.Dealers.add(d);
        }
        if(chkHasOrgDealer.isChecked()){
            // insert the org dealer info
            dname = "Org";
            dactiv = "0";
            drech = "0";
            dadv = "0";
            dqtemax = "0";
            if(chkHasOrgDealerActiva.isChecked() && chkHasOrgDealerActiva.isEnabled()){
                dactiv = "1";
            }
            if(chkHasOrgDealerRecharge.isChecked() && chkHasOrgDealerRecharge.isEnabled()){
                drech = "1";
            }
            if(chkHasOrgDealerAdv.isChecked() && chkHasOrgDealerAdv.isEnabled()){
                dadv = "1";
            }
            if(!edtOrgDealerMaxQte.getText().toString().equals("")){
                dqtemax = edtOrgDealerMaxQte.getText().toString();
            }

            String[] d = new String[]{dname,dactiv,drech,dadv,dqtemax};
            activity.Dealers.add(d);
        }
    }

    private void setRechargeData(){
        String Rname ;
        // recharge express data
        String RIamExp = "0";
        String ROrgExp = "0";
        String RInwiExp = "0";
        // recharge grattage data
        String RIamGra = "0";
        String ROrgGra = "0";
        String RInwiGra = "0";
        activity.Rechargs.clear();
        if(chkHasIamRecharge.isChecked()){
            Rname = "Iam";
            if(chkHasIamRechargeExp.isChecked()){
                RIamExp = "1";
            }
            if(chkHasIamRechargeGra.isChecked()){
                RIamGra = "1";
            }

            String[] val = new String[]{Rname,RIamExp,RIamGra,rechargeIamChifre};
            activity.Rechargs.add(val);
        }
        if(chkHasOrgRecharge.isChecked()){
            Rname = "Org";
            if(chkHasOrgRechargeExp.isChecked()){
                ROrgExp = "1";
            }
            if(chkHasOrgRechargeGra.isChecked()){
                ROrgGra = "1";
            }

            String[] val = new String[]{Rname,ROrgExp,ROrgGra,rechargeOrgChifre};
            activity.Rechargs.add(val);
        }
        if(chkHasInwiRecharge.isChecked()){
            Rname = "Inwi";
            if(chkHasInwiRechargeExp.isChecked()){
                RInwiExp = "1";
            }
            if(chkHasInwiRechargeGra.isChecked()){
                RInwiGra = "1";
            }

            String[] val = new String[]{Rname,RInwiExp,RInwiGra,rechargeInwiChifre};
            activity.Rechargs.add(val);
        }
    }

    private void setSimsData(){
        String Sname ;
        String SprixU = "0";
        String Sqtemax = "0";
        activity.Sims.clear();
        if(chkHasIamCim.isChecked()){
            Sname = "Iam";
            if(!edtIamCimMaxQte.getText().toString().equals("")){
                Sqtemax = edtIamCimMaxQte.getText().toString();
            }
            if(!edtIamCimPrixu.getText().toString().equals("")){
                SprixU = edtIamCimPrixu.getText().toString();
            }
            String[] val = new String[]{Sname,SprixU,Sqtemax};
            activity.Sims.add(val);
        }
        if(chkHasInwiCim.isChecked()){
            Sname = "Inwi";
            if(!edtInwiCimMaxQte.getText().toString().equals("")){
                Sqtemax = edtInwiCimMaxQte.getText().toString();
            }
            if(!edtInwiCimPrixu.getText().toString().equals("")){
                SprixU = edtInwiCimPrixu.getText().toString();
            }
            String[] val = new String[]{Sname,SprixU,Sqtemax};
            activity.Sims.add(val);
        }
        if(chkHasOrgCim.isChecked()){
            Sname = "Org";
            if(!edtOrgCimMaxQte.getText().toString().equals("")){
                Sqtemax = edtOrgCimMaxQte.getText().toString();
            }
            if(!edtOrgCimPrixu.getText().toString().equals("")){
                SprixU = edtOrgCimPrixu.getText().toString();
            }
            String[] val = new String[]{Sname,SprixU,Sqtemax};
            activity.Sims.add(val);
        }
    }

    private void enableOptions(String type,String lay) {
        switch (type){
            case "Orng":
                if(lay.equals("del")){
                    chkHasOrgDealerActiva.setEnabled(true);
                    chkHasOrgDealerAdv.setEnabled(true);
                    chkHasOrgDealerRecharge.setEnabled(true);
                    edtOrgDealerMaxQte.setEnabled(true);
                }else if (lay.equals("rech")){
                    chkHasOrgRechargeExp.setEnabled(true);
                    chkHasOrgRechargeGra.setEnabled(true);
                    OrgRechargeChefre.setEnabled(true);
                }else if (lay.equals("cim")){
                    edtOrgCimMaxQte.setEnabled(true);
                    edtOrgCimPrixu.setEnabled(true);
                }
                break;
            case "Inwi":
                if(lay.equals("del")){
                    chkHasInwiDealerActiva.setEnabled(true);
                    chkHasInwiDealerAdv.setEnabled(true);
                    chkHasInwiDealerRecharge.setEnabled(true);
                    edtInwiDealerMaxQte.setEnabled(true);
                }else if (lay.equals("rech")){
                    chkHasInwiRechargeExp.setEnabled(true);
                    chkHasInwiRechargeGra.setEnabled(true);
                    InwiRechargeChefre.setEnabled(true);
                }else if (lay.equals("cim")){
                    edtInwiCimMaxQte.setEnabled(true);
                    edtInwiCimPrixu.setEnabled(true);
                }
                break;
            case "Iam":
                if(lay.equals("del")){
                    chkHasIamDealerActiva.setEnabled(true);
                    chkHasIamDealerAdv.setEnabled(true);
                    chkHasIamDealerRecharge.setEnabled(true);
                    edtIamDealerMaxQte.setEnabled(true);
                }else if (lay.equals("rech")){
                    chkHasIamRechargeExp.setEnabled(true);
                    chkHasIamRechargeGra.setEnabled(true);
                    IamRechargeChefre.setEnabled(true);
                }else if (lay.equals("cim")){
                    edtIamCimMaxQte.setEnabled(true);
                    edtIamCimPrixu.setEnabled(true);
                }
                break;
        }
    }

    private void desibleOptions(String type,String lay) {
        switch (type){
            case "Orng":
                if(lay.equals("del")){
                    chkHasOrgDealerActiva.setEnabled(false);
                    chkHasOrgDealerAdv.setEnabled(false);
                    chkHasOrgDealerRecharge.setEnabled(false);
                    edtOrgDealerMaxQte.setEnabled(false);
                }else if (lay.equals("rech")){
                    chkHasOrgRechargeExp.setEnabled(false);
                    chkHasOrgRechargeGra.setEnabled(false);
                    OrgRechargeChefre.setEnabled(false);
                }else if (lay.equals("cim")){
                    edtOrgCimMaxQte.setEnabled(false);
                    edtOrgCimPrixu.setEnabled(false);
                }
                break;
            case "Inwi":
                if(lay.equals("del")){
                    chkHasInwiDealerActiva.setEnabled(false);
                    chkHasInwiDealerAdv.setEnabled(false);
                    chkHasInwiDealerRecharge.setEnabled(false);
                    edtInwiDealerMaxQte.setEnabled(false);
                }else if (lay.equals("rech")){
                    chkHasInwiRechargeExp.setEnabled(false);
                    chkHasInwiRechargeGra.setEnabled(false);
                    InwiRechargeChefre.setEnabled(false);
                }else if (lay.equals("cim")){
                    edtInwiCimMaxQte.setEnabled(false);
                    edtInwiCimPrixu.setEnabled(false);
                }
                break;
            case "Iam":
                if(lay.equals("del")){
                    chkHasIamDealerActiva.setEnabled(false);
                    chkHasIamDealerAdv.setEnabled(false);
                    chkHasIamDealerRecharge.setEnabled(false);
                    edtIamDealerMaxQte.setEnabled(false);
                }else if (lay.equals("rech")){
                    chkHasIamRechargeExp.setEnabled(false);
                    chkHasIamRechargeGra.setEnabled(false);
                    IamRechargeChefre.setEnabled(false);
                }else if (lay.equals("cim")){
                    edtIamCimMaxQte.setEnabled(false);
                    edtIamCimPrixu.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int btnCh = radioGroup.getCheckedRadioButtonId();
        switch (radioGroup.getId()){
            case R.id.radioInterests:
                if(btnCh == R.id.btnInterestsYes){
                    activity.MobileMonyInteress = true;
                }else {
                    activity.MobileMonyInteress = false;
                }
                break;
            case R.id.radioPropose:
                if(btnCh == R.id.btnProposeYes){
                    activity.MobileMonyPropose = true;
                }else {
                    activity.MobileMonyPropose = false;
                }
                break;
            case R.id.radioTelephony:
                if(btnCh == R.id.btnTelephonyYes){
                    activity.Telephony = true;
                    btnTelHautGamme.setEnabled(true);
                    btnTelBasGamme.setEnabled(true);
                }else {
                    activity.Telephony = false;
                    btnTelHautGamme.setEnabled(false);
                    btnTelBasGamme.setEnabled(false);
                }
                break;
            case R.id.TelephonyRadioGroup:
                if(btnCh == R.id.btnTelBasGamme){
                    activity.TelephonyGamme = "Bas Gamme";
                }else {
                    activity.TelephonyGamme = "Haut Gamme";
                }
                break;

            case R.id.radioAccessoir:
                if(btnCh == R.id.btnAccessYes){
                    activity.Accessoire = true;
                    btnAccessHautGamme.setEnabled(true);
                    btnAccssBasGamme.setEnabled(true);
                }else {
                    activity.Accessoire = false;
                    btnAccessHautGamme.setEnabled(false);
                    btnAccssBasGamme.setEnabled(false);
                }
                break;
            case R.id.AccessGammeRadioGroup:
                if(btnCh == R.id.btnAccssBasGamme){
                    activity.AccessoireGamme = "Bas Gamme";
                }else {
                    activity.AccessoireGamme = "Haut Gamme";
                }
                break;
        }
    }

    public void submitData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, submitUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
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
                Map<String,String> params = new HashMap<>();
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
                //params.put("clientDealers",getDealerAsString() != null ? getDealerAsString() : "");
                //params.put("clientRecharge",getRechargeAsString() != null ? getRechargeAsString() : "");
                //params.put("clientSims",getSimsAsString() != null ? getSimsAsString() : "");
                //params.put("clientPot",getPotenceAsString() != null ? getPotenceAsString() : "");

                //params.put("clientMobile",getMobileMonyAsString());
                //params.put("clientTelephony",getTelephonyAsString());
                //params.put("clientAccessoire",getAccessoireAsString());


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void desibleOptionForVis(){

        chkPotInwi.setEnabled(false);
        chkPotOrange.setEnabled(false);
        chkPotIam.setEnabled(false);
        chkHasOrgDealer.setEnabled(false);
        chkHasInwiDealer.setEnabled(false);
        chkHasIamDealer.setEnabled(false);
        chkHasOrgRecharge.setEnabled(false);
        chkHasIamRecharge.setEnabled(false);
        chkHasOrgCim.setEnabled(false);
        chkHasInwiCim.setEnabled(false);
        chkHasIamCim.setEnabled(false);

        btnTelephonyNo.setEnabled(false);
        btnTelephonyNo.setChecked(false);
        btnTelephonyYes.setEnabled(false);
        btnTelephonyYes.setChecked(false);
        // mobil mony
        btnInterestsYes.setEnabled(false);
        btnInterestsYes.setChecked(false);
        btnInterestsNo.setEnabled(false);
        btnInterestsNo.setChecked(false);
        btnProposeYes.setChecked(false);
        btnProposeYes.setEnabled(false);
        btnProposeNo.setEnabled(false);
        btnProposeNo.setChecked(false);

        btnAccessNo.setChecked(false);
        btnAccessNo.setEnabled(false);
        btnAccessYes.setEnabled(false);
        btnAccessYes.setChecked(false);

        chkHasInwiRecharge.setEnabled(false);
        chkHasInwiRecharge.setChecked(false);

        // todo desible all the new added op for the vis

    }

    private void setPotence(JSONArray jsonArray){
        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                setPotence(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPotence(JSONObject jsonObject){
        try {
            String d = jsonObject.getString("operateur");

            switch (d){
                case "Org":
                    chkPotOrange.setChecked(true);
                    break;
                case "Iam":
                    chkPotIam.setChecked(true);
                    break;
                case "Inwi":
                    chkPotInwi.setChecked(true);
                    break;
                default:
                    // todo set the spinner text
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDealer(JSONArray jsonArray){
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                setDealer(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDealer(JSONObject jsonObject){
        try {
            String op = jsonObject.getString("operateur");
            String activ = jsonObject.getString("activation");
            String recharge = jsonObject.getString("recharge");
            String adv = jsonObject.getString("avantageous");
            String max = jsonObject.getString("numDealer");

            switch (op){
                case "Org":
                    chkHasOrgDealer.setChecked(true);
                    if(activ.equals("1")){
                        chkHasOrgDealerActiva.setChecked(true);
                    }
                    if(recharge.equals("1")){
                        chkHasOrgDealerRecharge.setChecked(true);
                    }
                    if(adv.equals("1")){
                        chkHasOrgDealerAdv.setChecked(true);
                    }
                    if(!max.equals("")){
                        edtOrgDealerMaxQte.setText(max);
                    }
                    break;
                case "Iam":
                    chkHasIamDealer.setChecked(true);
                    if(activ.equals("1")){
                        chkHasIamDealerActiva.setChecked(true);
                    }
                    if(recharge.equals("1")){
                        chkHasIamDealerRecharge.setChecked(true);
                    }
                    if(adv.equals("1")){
                        chkHasIamDealerAdv.setChecked(true);
                    }
                    if(!max.equals("")){
                        edtIamDealerMaxQte.setText(max);
                    }
                    break;
                case "Inwi":
                    chkHasInwiDealer.setChecked(true);
                    if(activ.equals("1")){
                        chkHasInwiDealerActiva.setChecked(true);
                    }
                    if(recharge.equals("1")){
                        chkHasInwiDealerRecharge.setChecked(true);
                    }
                    if(adv.equals("1")){
                        chkHasInwiDealerAdv.setChecked(true);
                    }
                    if(!max.equals("")){
                        edtInwiDealerMaxQte.setText(max);
                    }
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setRecharge(JSONArray jsonArray){
        for (int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                setRecharge(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setRecharge(JSONObject jsonObject){
        try {
            String op = jsonObject.getString("operateur");
            String max = jsonObject.getString("QteMax");
            switch (op){
                case "Org":
                    chkHasOrgRecharge.setChecked(true);
                    if(!max.equals("")){
                        // todo set recharge data
                    }
                    break;
                case "Iam":
                    chkHasIamRecharge.setChecked(true);
                    if(!max.equals("")){
                        // todo set recharge data
                    }
                    break;
                case "Inwi":
                    chkHasInwiRecharge.setChecked(true);
                    if(!max.equals("")){
                        // todo set recharge data
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSim(JSONArray jsonArray){
        for (int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                setSim(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setSim(JSONObject jsonObject){
        try {
            String op = jsonObject.getString("operateur");
            String pu = jsonObject.getString("prixU");
            String max = jsonObject.getString("qteMax");

            switch (op){
                case "Org":
                    chkHasOrgCim.setChecked(true);
                    if(!max.equals("")){
                        edtOrgCimMaxQte.setText(max);
                    }
                    if(!pu.equals("")){
                        edtOrgCimPrixu.setText(pu);
                    }
                    break;
                case "Iam":
                    chkHasIamCim.setChecked(true);
                    if(!max.equals("")){
                        edtIamCimMaxQte.setText(max);
                    }
                    if(!pu.equals("")){
                        edtIamCimPrixu.setText(pu);
                    }
                    break;
                case "Inwi":
                    chkHasInwiCim.setChecked(true);
                    if(!max.equals("")){
                        edtInwiCimMaxQte.setText(max);
                    }
                    if(!pu.equals("")){
                        edtInwiCimPrixu.setText(pu);
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMobile(String mobileString){
        //[{"idClient":1,"idMobileM":0,"interesse":1,"propose":1}]
        String mobile = mobileString.replace("]","").replace("[","").
                replace("{","").replace("}","");
        String[] mobileParts = mobile.split(",");
        for (String p: mobileParts) {
            String[] parts = p.split(":");
            switch (parts[0]){
                case "interesse":
                    if(parts[1].equals("1")){
                        btnInterestsYes.setChecked(true);
                    }else {
                        btnInterestsYes.setChecked(false);
                    }
                    break;
                case "propose":
                    if(parts[1].equals("1")){
                        btnProposeYes.setChecked(true);
                    }else {
                        btnProposeYes.setChecked(false);
                    }
                    break;
            }
        }
    }

    private void  getVisitorData(){
        // first check if the user type is visiture
        SharedPreferences sharedPreferences = activity.getSharedPreferences(getString(R.string.shared_name), Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role","0");
        if(role.equals("2")){
            String visUrl = "http://hafid.skandev.com/get_vis_info.php";
            // the role is visiture
            StringRequest stringRequest = new StringRequest(Request.Method.POST, visUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject json;
                    try {
                        json = new JSONObject(response);
                        JSONObject client = json.getJSONObject("client");
                        Log.d("json", "onResponse: "+json);
                        try{
                            // for the case of one dealer
                            JSONObject jsonObject = json.getJSONObject("dealer");
                            setPotence(jsonObject);
                        }catch (Exception ex){
                            // for the case of more than one dealer
                            JSONArray jsonArray = json.getJSONArray("dealer");
                            setPotence(jsonArray);
                        }
                        try{
                            JSONObject jsonObject = json.getJSONObject("dealer");
                            setDealer(jsonObject);
                        }catch (Exception ex){
                            JSONArray jsonArray = json.getJSONArray("dealer");
                            setDealer(jsonArray);
                        }
                        try{
                            JSONObject jsonObject = json.getJSONObject("recharge");
                            setRecharge(jsonObject);
                        }catch (Exception ex){
                            JSONArray jsonArray = json.getJSONArray("recharge");
                            setRecharge(jsonArray);
                        }
                        try{
                            JSONObject jsonObject = json.getJSONObject("sims");
                            setSim(jsonObject);
                        }catch (Exception ex){
                            JSONArray jsonArray = json.getJSONArray("sims");
                            setSim(jsonArray);
                        }
                        try{
                            String mobileString = json.getString("mobile");
                            setMobile(mobileString);

                        }catch (Exception ex){
                            Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show();
                        }
                        //Log.d("json", "onResponse: "+dealer);
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
                    params.put("code",activity.codeBarContent);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(stringRequest);
        }
    }

    private void getMarks(){
        final String typesUrl = "http://hafid.skandev.com/getVisibility.php";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, typesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    for(int i=0;i<json.length();i++){
                        String lib = json.getJSONObject(i).getString("Libelle").toLowerCase();
                        if(!lib.equals("iam") && !lib.equals("inwi") && !lib.equals("org")){
                            Markes.add(lib);
                        }
                    }
                    ArrayAdapter<String> MarkAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,
                            Markes);
                    authorMarksList.setAdapter(MarkAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("clientType", "onResponse: success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void getMobiles(){
        final String typesUrl = "http://hafid.skandev.com/getMobile.php";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, typesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    for(int i=0;i<json.length();i++){
                        mobiles.add(json.getJSONObject(i).getString("operateur"));
                    }
                    ArrayAdapter<String> mobileAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,
                            mobiles);
                    mobileMoneyTypeList.setAdapter(mobileAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("clientType", "onResponse: success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

}
