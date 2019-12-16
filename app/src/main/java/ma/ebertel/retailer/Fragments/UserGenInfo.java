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
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import ma.ebertel.retailer.Adapters.MobileMonnyAdapter;
import ma.ebertel.retailer.Helpers.BitmapHelper;
import ma.ebertel.retailer.Login;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class UserGenInfo extends Fragment implements
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener {

    MainActivity activity;
    private CheckBox chkPotInwi,chkPotOrange,chkPotIam
            ,chkHasOrgDealer,chkHasInwiDealer,chkHasIamDealer
            ,chkHasOrgDealerActiva,chkHasInwiDealerActiva,chkHasIamDealerActiva
            ,chkHasOrgDealerAdv,chkHasInwiDealerAdv,chkHasIamDealerAdv
            ,chkHasOrgRechargeExp,chkHasInwiRechargeExp,chkHasIamRechargeExp
            ,chkHasOrgRechargeGra,chkHasInwiRechargeGra,chkHasIamRechargeGra
            ,chkHasOrgCim,chkHasInwiCim,chkHasIamCim;
    private ImageButton btnAddValidUser;
    private EditText edtOrgCimPrixu,edtInwiCimPrixu,edtIamCimPrixu
            ,edtOrgCimMaxQte,edtInwiCimMaxQte,edtIamCimMaxQte
            ,edtShowIamChifre,edtShowOrgChifre,edtShowInwiChifre;
    private RadioGroup radioTelephony,TelephonyRadioGroup,radioAccessoir,AccessGammeRadioGroup;
    private RadioButton btnAccssBasGamme,btnAccessHautGamme
                        ,btnTelHautGamme,btnTelBasGamme
                        ,btnInterestsYes,btnInterestsNo
                        ,btnProposeYes,btnProposeNo
                        ,btnTelephonyNo,btnTelephonyYes
                        ,btnAccessYes,btnAccessNo;
    private Spinner OrgRechargeChefre,InwiRechargeChefre,IamRechargeChefre
                ,authorMarksList;
    private ViewGroup showIamChifre,showOrgChifre,showInwiChifre;
    private TableRow rechargeChSpinners;
    private String rechargeIamChifre="",rechargeInwiChifre="",rechargeOrgChifre="",mobileMonnyType="",auhtorMark="";
    private String[] chifers;
    private List<String> mobiles;
    private List<String> Markes;

    private RecyclerView mobileMonnyRecycler;
    private MobileMonnyAdapter mobileMonnyAdapter;

    private ViewGroup remarks;

    private SharedPreferences sharedPreferences;

    public String submitUrl = "http://hafid.skandev.com/addClient.php";

    public UserGenInfo(MainActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.user_gen_info,container,false);

        radioTelephony = viewGroup.findViewById(R.id.radioTelephony);
        TelephonyRadioGroup = viewGroup.findViewById(R.id.TelephonyRadioGroup);
        radioAccessoir = viewGroup.findViewById(R.id.radioAccessoir);
        AccessGammeRadioGroup = viewGroup.findViewById(R.id.AccessGammeRadioGroup);
        remarks = viewGroup.findViewById(R.id.remarks);
        mobileMonnyRecycler = viewGroup.findViewById(R.id.mobileMonnyRecycler);

        btnAccssBasGamme = viewGroup.findViewById(R.id.btnAccssBasGamme);
        btnAccessHautGamme = viewGroup.findViewById(R.id.btnAccessHautGamme);
        btnTelBasGamme = viewGroup.findViewById(R.id.btnTelBasGamme);
        btnTelHautGamme = viewGroup.findViewById(R.id.btnTelHautGamme);

        showIamChifre = viewGroup.findViewById(R.id.showIamChifre);
        showOrgChifre = viewGroup.findViewById(R.id.showOrgChifre);
        showInwiChifre = viewGroup.findViewById(R.id.showInwiChifre);

        edtShowIamChifre = viewGroup.findViewById(R.id.edtShowIamChifre);
        edtShowOrgChifre = viewGroup.findViewById(R.id.edtShowOrgChifre);
        edtShowInwiChifre = viewGroup.findViewById(R.id.edtShowInwiChifre);

        rechargeChSpinners = viewGroup.findViewById(R.id.rechargeChSpinners);

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
        // dealer adventagous option
        chkHasOrgDealerAdv = viewGroup.findViewById(R.id.chkHasOrgDealerAdv);
        chkHasInwiDealerAdv = viewGroup.findViewById(R.id.chkHasInwiDealerAdv);
        chkHasIamDealerAdv = viewGroup.findViewById(R.id.chkHasIamDealerAdv);

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

        // cims
        chkHasOrgCim.setOnCheckedChangeListener(this);
        chkHasInwiCim.setOnCheckedChangeListener(this);
        chkHasIamCim.setOnCheckedChangeListener(this);


        //chkHasOrgDealerActiva.setOnCheckedChangeListener(this);
        //chkHasInwiDealerActiva.setOnCheckedChangeListener(this);
        //chkHasIamDealerActiva.setOnCheckedChangeListener(this);

        // potence or visibility spinner
        authorMarksList = viewGroup.findViewById(R.id.authorMarksList);

        Markes = new ArrayList<>();
        Markes.add("");
        authorMarksList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sharedPreferences.getString("role","0").equals("1")){
                    auhtorMark = Markes.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // mobile monny spinner
        mobiles = new ArrayList<>();


        btnAddValidUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = sharedPreferences.getString("role","0");
                if(role.equals("2")){
                    activity.pager.setCurrentItem(2,true);
                    return;
                }
                //set Potence data
                setPotenceData();
                // set the dealler info
                setDealerData();
                // set the rechage data
                // we don't need recharge data any more
                //setRechargeData();
                // set the sims info
                setSimsData();
                // check if the user clicked the button ont the first fragment and the uder info are valied
                // and redy to be submitted to the server
                if(isUserDateValide()){
                    // data is ready to be submitted
                    activity.pager.setCurrentItem(2,true);
                }else {
                    // show message to user
                    Toast.makeText(activity, "Please Check All Your Fields Something is Messing", Toast.LENGTH_LONG).show();
                }
            }


        });

        sharedPreferences = activity.getSharedPreferences(getString(R.string.shared_name), Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role","0");
        if(role.equals("2")){
            desibleOptionForVis();
        }else if (role.equals("1")){
            showInwiChifre.setVisibility(View.GONE);
            showIamChifre.setVisibility(View.GONE);
            showOrgChifre.setVisibility(View.GONE);
            getMarks();
            getMobiles();
        }

        // set the mobile monny recycler
        return viewGroup;
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
        String checked = compoundButton.getTag().toString();;

        if(compoundButton.getId() == R.id.chkHasIamDealer || compoundButton.getId() == R.id.chkHasInwiDealer ||
                compoundButton.getId() == R.id.chkHasOrgDealer){
            if(b){
                enableOptions(checked,"del");
            }else {
                desibleOptions(checked,"del");
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
        String drechExp;
        String drechGra;
        String dadv;
        String dchifre;
        activity.Dealers.clear();
        if(chkHasIamDealer.isChecked()){
            // insert the iam dealer info
            // todo update here
            dname = "Iam";
            dactiv = "0";
            drechExp = "0";
            drechGra = "0";
            dadv = "0";
            if(chkHasIamDealerActiva.isChecked() && chkHasIamDealerActiva.isEnabled()){
                dactiv = "1";
            }
            if(chkHasIamDealerAdv.isChecked() && chkHasIamDealerAdv.isEnabled()){
                dadv = "1";
            }
            if(chkHasOrgRechargeExp.isChecked() && chkHasOrgRechargeExp.isEnabled()){
                drechExp = "1";
            }
            if(chkHasOrgRechargeGra.isChecked() && chkHasOrgRechargeGra.isEnabled()){
                drechGra = "1";
            }

            String[] d = new String[]{dname,dactiv,drechExp,drechGra,dadv,rechargeIamChifre};
            activity.Dealers.add(d);
        }
        if(chkHasInwiDealer.isChecked()){
            // insert the inwi dealer info
            // todo update here
            dname = "Inwi";
            dactiv = "0";
            drechExp = "0";
            drechGra = "0";
            dadv = "0";
            if(chkHasInwiDealerActiva.isChecked() && chkHasInwiDealerActiva.isEnabled()){
                dactiv = "1";
            }
            if(chkHasInwiDealerAdv.isChecked() && chkHasInwiDealerAdv.isEnabled()){
                dadv = "1";
            }
            if(chkHasInwiRechargeExp.isChecked() && chkHasInwiRechargeExp.isEnabled()){
                drechExp = "1";
            }
            if(chkHasInwiRechargeGra.isChecked() && chkHasInwiRechargeGra.isEnabled()){
                drechGra = "1";
            }

            String[] d = new String[]{dname,dactiv,drechExp,drechGra,dadv,rechargeInwiChifre};
            activity.Dealers.add(d);
        }
        if(chkHasOrgDealer.isChecked()){
            // insert the org dealer info
            // todo update here
            dname = "Org";
            dactiv = "0";
            drechExp = "0";
            drechGra = "0";
            dadv = "0";
            if(chkHasOrgDealerActiva.isChecked() && chkHasOrgDealerActiva.isEnabled()){
                dactiv = "1";
            }
            if(chkHasOrgDealerAdv.isChecked() && chkHasOrgDealerAdv.isEnabled()){
                dadv = "1";
            }
            if(chkHasOrgRechargeExp.isChecked() && chkHasOrgRechargeExp.isEnabled()){
                drechExp = "1";
            }
            if(chkHasOrgRechargeGra.isChecked() && chkHasOrgRechargeGra.isEnabled()){
                drechGra = "1";
            }

            String[] d = new String[]{dname,dactiv,drechExp,drechGra,dadv,rechargeOrgChifre};
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
        /*if(chkHasIamRecharge.isChecked()){
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
        }*/
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
        // todo update here if he needs updates
        switch (type){
            case "Orng":
                if(lay.equals("del")){
                    chkHasOrgDealerActiva.setEnabled(true);
                    chkHasOrgDealerAdv.setEnabled(true);
                    chkHasOrgRechargeExp.setEnabled(true);
                    chkHasOrgRechargeGra.setEnabled(true);
                    OrgRechargeChefre.setEnabled(true);
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
                    chkHasInwiRechargeExp.setEnabled(true);
                    chkHasInwiRechargeGra.setEnabled(true);
                    InwiRechargeChefre.setEnabled(true);
                }else if (lay.equals("rech")){
                    chkHasInwiRechargeExp.setEnabled(true);
                    chkHasInwiRechargeGra.setEnabled(true);
                    InwiRechargeChefre.setEnabled(true);
                    chkHasInwiRechargeGra.setEnabled(true);
                }else if (lay.equals("cim")){
                    edtInwiCimMaxQte.setEnabled(true);
                    edtInwiCimPrixu.setEnabled(true);
                }
                break;
            case "Iam":
                if(lay.equals("del")){
                    chkHasIamDealerActiva.setEnabled(true);
                    chkHasIamDealerAdv.setEnabled(true);
                    chkHasIamRechargeExp.setEnabled(true);
                    chkHasIamRechargeGra.setEnabled(true);
                    IamRechargeChefre.setEnabled(true);
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
        // todo update here if he needs updates
        switch (type){
            case "Orng":
                if(lay.equals("del")){
                    chkHasOrgDealerActiva.setEnabled(false);
                    chkHasOrgDealerAdv.setEnabled(false);
                    chkHasOrgRechargeExp.setEnabled(false);
                    chkHasOrgRechargeGra.setEnabled(false);
                    OrgRechargeChefre.setEnabled(false);
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
                    chkHasInwiRechargeExp.setEnabled(false);
                    chkHasInwiRechargeGra.setEnabled(false);
                    InwiRechargeChefre.setEnabled(false);
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
                    chkHasIamRechargeExp.setEnabled(false);
                    chkHasIamRechargeGra.setEnabled(false);
                    IamRechargeChefre.setEnabled(false);
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
        try{
            String tag = radioGroup.getTag().toString();
            int btnCh = radioGroup.getCheckedRadioButtonId();
            switch (tag){
                case "propose":
                    switch (btnCh){
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
                    }
                    break;
                case "interest":
                    Toast.makeText(activity, "interests item clicked", Toast.LENGTH_LONG).show();
                    break;
            }
            return;
        }catch (Exception ex){

        }
        int btnCh = radioGroup.getCheckedRadioButtonId();
        switch (radioGroup.getId()){
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

    private void desibleOptionForVis(){

        chkPotInwi.setEnabled(false);
        chkPotOrange.setEnabled(false);
        chkPotIam.setEnabled(false);
        chkHasOrgDealer.setEnabled(false);
        chkHasInwiDealer.setEnabled(false);
        chkHasIamDealer.setEnabled(false);
        chkHasOrgCim.setEnabled(false);
        chkHasInwiCim.setEnabled(false);
        chkHasIamCim.setEnabled(false);

        btnTelephonyNo.setEnabled(false);
        //btnTelephonyNo.setChecked(false);
        btnTelephonyYes.setEnabled(false);
        //btnTelephonyYes.setChecked(false);
        // mobil mony
        btnInterestsYes.setEnabled(false);
        //btnInterestsYes.setChecked(false);
        btnInterestsNo.setEnabled(false);
        //btnInterestsNo.setChecked(false);
        //btnProposeYes.setChecked(false);
        btnProposeYes.setEnabled(false);
        btnProposeNo.setEnabled(false);
        //btnProposeNo.setChecked(false);

        btnAccessNo.setChecked(false);
        btnAccessNo.setEnabled(false);
        btnAccessYes.setEnabled(false);
        btnAccessYes.setChecked(false);

        rechargeChSpinners.setVisibility(View.GONE);

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
            String d = jsonObject.getString("Libelle").toLowerCase();

            switch (d){
                case "org":
                    chkPotOrange.setChecked(true);
                    break;
                case "iam":
                    chkPotIam.setChecked(true);
                    break;
                case "inwi":
                    chkPotInwi.setChecked(true);
                    break;
                default:
                    ArrayAdapter<String> MarkAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item,
                            new String[]{d});
                    authorMarksList.setAdapter(MarkAdapter);
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
        // todo update the new dealer information
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

                    if(adv.equals("1")){
                        chkHasOrgDealerAdv.setChecked(true);
                    }
                    break;
                case "Iam":
                    chkHasIamDealer.setChecked(true);
                    if(activ.equals("1")){
                        chkHasIamDealerActiva.setChecked(true);
                    }
                    if(adv.equals("1")){
                        chkHasIamDealerAdv.setChecked(true);
                    }
                    break;
                case "Inwi":
                    chkHasInwiDealer.setChecked(true);
                    if(activ.equals("1")){
                        chkHasInwiDealerActiva.setChecked(true);
                    }
                    if(adv.equals("1")){
                        chkHasInwiDealerAdv.setChecked(true);
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
        // todo get data from this function and deleted
        try {
            String op = jsonObject.getString("operateur");
            String exp = jsonObject.getString("express");
            String gat = jsonObject.getString("grattage");
            String chfr = jsonObject.getString("chefre");

            switch (op){
                case "Org":
                    if(exp.equals("1")){
                        chkHasOrgRechargeExp.setChecked(true);
                    }
                    if(gat.equals("1")){
                        chkHasOrgRechargeGra.setChecked(true);
                    }
                    edtShowOrgChifre.setText(chfr);
                    showOrgChifre.setVisibility(View.VISIBLE);
                    break;
                case "Iam":
                    if(exp.equals("1")){
                        chkHasIamRechargeExp.setChecked(true);
                    }
                    if(gat.equals("1")){
                        chkHasIamRechargeGra.setChecked(true);
                    }
                    edtShowIamChifre.setText(chfr);
                    showIamChifre.setVisibility(View.VISIBLE);
                    break;
                case "Inwi":
                    if(exp.equals("1")){
                        chkHasInwiRechargeExp.setChecked(true);
                    }
                    if(gat.equals("1")){
                        chkHasInwiRechargeGra.setChecked(true);
                    }
                    edtShowInwiChifre.setText(chfr);
                    showInwiChifre.setVisibility(View.VISIBLE);
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

    private void setMobile(JSONArray mobile){
        //[{"idClient":9,"idMobileM":2,"interesse":1,"propose":0}]
        if(mobile != null){
            try {
                JSONObject jsonObject = mobile.getJSONObject(0);
                String interess = jsonObject.getString("interesse");
                String propos = jsonObject.getString("propose");
                String operateur = jsonObject.getString("operateur");
                // set Spinner data
                Log.d("jsona", "setMobile: interesse = "+interess+" propose = "+propos);
                if(interess.equals("1")){
                    btnInterestsYes.setChecked(true);
                    btnInterestsNo.setChecked(false);
                }else {
                    btnInterestsYes.setChecked(false);
                    btnInterestsNo.setChecked(true);
                }
                if(propos.equals("1")){
                    btnProposeYes.setChecked(true);
                    btnProposeNo.setChecked(false);
                }else {
                    btnProposeYes.setChecked(false);
                    btnProposeNo.setChecked(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setTelephony(JSONArray telephony){
        if(telephony != null){
            try {
                JSONObject tel = telephony.getJSONObject(0);
                String inter = tel.getString("interess");
                String type = tel.getString("type");
                if(inter.equals("1")){
                    // show the type
                    btnTelephonyYes.setChecked(true);
                    btnTelephonyNo.setChecked(false);
                    if(type.equals("Bas Gamme")){
                        btnTelBasGamme.setChecked(true);
                        btnTelHautGamme.setChecked(false);
                    }else {
                        btnTelBasGamme.setChecked(false);
                        btnTelHautGamme.setChecked(true);
                    }
                }else {
                    btnTelephonyYes.setChecked(false);
                    btnTelephonyNo.setChecked(true);
                    btnTelBasGamme.setChecked(false);
                    btnTelHautGamme.setChecked(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            btnTelephonyYes.setChecked(false);
            btnTelephonyNo.setChecked(false);
            btnTelBasGamme.setChecked(false);
            btnTelHautGamme.setChecked(false);
        }
    }

    private void setAccessoir(JSONArray access){
        if(access != null){
            try {
                JSONObject acc = access.getJSONObject(0);
                String inter = acc.getString("interess");
                String type = acc.getString("type");
                if(inter.equals("1")){
                    // show the type
                    btnAccessYes.setChecked(true);
                    btnAccessNo.setChecked(false);
                    if(type.equals("Bas Gamme")){
                        btnAccssBasGamme.setChecked(true);
                        btnAccessHautGamme.setChecked(false);
                    }else {
                        btnAccssBasGamme.setChecked(false);
                        btnAccessHautGamme.setChecked(true);
                    }
                }else {
                    btnAccessYes.setChecked(false);
                    btnAccessNo.setChecked(true);
                    btnAccssBasGamme.setChecked(false);
                    btnAccessHautGamme.setChecked(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            btnAccessYes.setChecked(false);
            btnAccessNo.setChecked(false);
            btnAccssBasGamme.setChecked(false);
            btnAccessHautGamme.setChecked(false);
        }
    }

    private void setDetergent(JSONArray detergent){
        if(detergent != null){
            activity.clientDetergent = new ArrayList<>();
            for(int i=0;i<detergent.length();i++){
                try {
                    JSONObject jsonObject = detergent.getJSONObject(i);
                    activity.clientDetergent.add(jsonObject.getString("libelle"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setThe(JSONArray thee){
        if(thee != null){
            activity.clientThe = new ArrayList<>();
            for (int i=0;i<thee.length();i++){
                try {
                    JSONObject jsonObject = thee.getJSONObject(i);
                    activity.clientThe.add(jsonObject.getString("libelle"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setlait(JSONArray lait){
        if(lait != null){
            activity.clientLait = new ArrayList<>();
            for (int i=0;i<lait.length();i++){
                try {
                    JSONObject jsonObject = lait.getJSONObject(i);
                    activity.clientLait.add(jsonObject.getString("libelle"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setBiscuit(JSONArray biscuit){
        if(biscuit != null){
            activity.clientBiscuit = new ArrayList<>();
            for (int i=0;i<biscuit.length();i++){
                try {
                    JSONObject jsonObject = biscuit.getJSONObject(i);
                    activity.clientBiscuit.add(jsonObject.getString("libelle"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPate(JSONArray pate){
        if(pate != null){
            activity.clientPate = new ArrayList<>();
            for (int i=0;i<pate.length();i++){
                try {
                    JSONObject jsonObject = pate.getJSONObject(i);
                    activity.clientPate.add(jsonObject.getString("libelle"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setCouche(JSONArray couche){
        if(couche != null){
            activity.clientCouche = new ArrayList<>();
            for (int i=0;i<couche.length();i++){
                try {
                    JSONObject jsonObject = couche.getJSONObject(i);
                    activity.clientCouche.add(jsonObject.getString("libelle"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                        try{
                            // for the case of one dealer
                            JSONObject jsonObject = json.getJSONObject("visibility");
                            setPotence(jsonObject);
                        }catch (Exception ex){
                            // for the case of more than one dealer
                            JSONArray jsonArray = json.getJSONArray("visibility");
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
                            Log.d("recharge", "onResponse: recharge is object");
                            setRecharge(jsonObject);
                        }catch (Exception ex){
                            JSONArray jsonArray = json.getJSONArray("recharge");
                            Log.d("recharge", "onResponse: recharge is array");
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
                            JSONArray mobileString = json.getJSONArray("mobile");
                            setMobile(mobileString);

                        }catch (Exception ex){
                            Toast.makeText(activity, "Mobile Monny error", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray telehonyData = json.getJSONArray("telephony");
                            setTelephony(telehonyData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Telephony Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray accessoirData = json.getJSONArray("accessoin");
                            setAccessoir(accessoirData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Accessoir Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray pDetergentData = json.getJSONArray("detergent");
                            setDetergent(pDetergentData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Detergent Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray pTheData = json.getJSONArray("the");
                            setThe(pTheData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Thé Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray pTheData = json.getJSONArray("lait");
                            setlait(pTheData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Lait Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray pTheData = json.getJSONArray("biscuit");
                            setBiscuit(pTheData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Biscuit Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray pTheData = json.getJSONArray("pate");
                            setPate(pTheData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Pate Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        try{
                            JSONArray pTheData = json.getJSONArray("couche");
                            setCouche(pTheData);
                        }catch (Exception ex){
                            Toast.makeText(activity, "Coche Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("json", "visitor data: "+json);
                    } catch (JSONException e) {
                        activity.finish();
                        Toast.makeText(activity, "This Client Not Exists", Toast.LENGTH_LONG).show();
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
                    // set the recycler data
                    mobileMonnyAdapter = new MobileMonnyAdapter(activity,mobiles,UserGenInfo.this);
                    mobileMonnyRecycler.setLayoutManager(new LinearLayoutManager(activity));
                    mobileMonnyRecycler.setAdapter(mobileMonnyAdapter);
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
