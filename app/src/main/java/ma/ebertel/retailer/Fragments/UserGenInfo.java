package ma.ebertel.retailer.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import ma.ebertel.retailer.Helpers.BitmapHelper;
import ma.ebertel.retailer.Login;
import ma.ebertel.retailer.MainActivity;
import ma.ebertel.retailer.R;

public class UserGenInfo extends Fragment implements
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener {

    MainActivity activity;
    public CheckBox chkPotInwi,chkPotOrange,chkPotIam,chkPotTaba
            ,chkHasOrgDealer,chkHasInwiDealer,chkHasIamDealer
            ,chkHasOrgDealerActiva,chkHasInwiDealerActiva,chkHasIamDealerActiva
            ,chkHasOrgDealerRecharge,chkHasInwiDealerRecharge,chkHasIamDealerRecharge
            ,chkHasOrgDealerAdv,chkHasInwiDealerAdv,chkHasIamDealerAdv
            ,chkHasOrgRecharge,chkHasInwiRecharge,chkHasIamRecharge
            ,chkHasOrgCim,chkHasInwiCim,chkHasIamCim;
    public Button btnAddValidUser;
    public EditText authorMark
            ,edtOrgDealerMaxQte,edtInwiDealerMaxQte,edtIamDealerMaxQte
            ,edtOrgRechargeMax,edtInwiRechargeMax,edtIamRechargeMax
            ,edtOrgCimPrixu,edtInwiCimPrixu,edtIamCimPrixu
            ,edtOrgCimMaxQte,edtInwiCimMaxQte,edtIamCimMaxQte;
    public RadioGroup radioPropose,radioInterests,radioTelephony,TelephonyRadioGroup,radioAccessoir,AccessGammeRadioGroup;
    public RadioButton btnAccssBasGamme,btnAccessHautGamme
                        ,btnTelHautGamme,btnTelBasGamme;

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

        btnAccssBasGamme = viewGroup.findViewById(R.id.btnAccssBasGamme);
        btnAccessHautGamme = viewGroup.findViewById(R.id.btnAccessHautGamme);
        btnTelBasGamme = viewGroup.findViewById(R.id.btnTelBasGamme);
        btnTelHautGamme = viewGroup.findViewById(R.id.btnTelHautGamme);

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
        chkPotTaba = viewGroup.findViewById(R.id.chkPotTaba);
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
        // recharge max qte
        edtOrgRechargeMax = viewGroup.findViewById(R.id.edtOrgRechargeMax);
        edtInwiRechargeMax = viewGroup.findViewById(R.id.edtInwiRechargeMax);
        edtIamRechargeMax = viewGroup.findViewById(R.id.edtIamRechargeMax);

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

        authorMark = viewGroup.findViewById(R.id.authorMark);

        // potence listeners
        chkPotInwi.setOnCheckedChangeListener(this);
        chkPotOrange.setOnCheckedChangeListener(this);
        chkPotIam.setOnCheckedChangeListener(this);
        chkPotTaba.setOnCheckedChangeListener(this);
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

        btnAddValidUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!authorMark.getText().toString().equals("")){
                    activity.authorPot = authorMark.getText().toString();
                }

                //set Potence data
                setPotenceData();
                // set the dealler info
                setDealerData();
                // set the rechage data
                setRechargeData();
                // set the sims info
                setSimsData();

                Log.d("gen", "onClick: mobile mony inter "+activity.MobileMonyInteress);
                Log.d("gen", "onClick: mobile mony prop "+activity.MobileMonyPropose);
                Log.d("gen", "onClick: Telephony "+activity.Telephony);
                Log.d("gen", "onClick: Telephony gamme "+activity.TelephonyGamme);
                Log.d("gen", "onClick: Accessoire "+activity.Accessoire);
                Log.d("gen", "onClick: Accessoire gamme "+activity.AccessoireGamme);

                // check if the user clicked the button ont the first fragment and the uder info are valied
                // and redy to be submitted to the server
                if(isUserDateValide()){
                    // data is ready to be submitted
                    Toast.makeText(activity, "data submition", Toast.LENGTH_SHORT).show();
                    submitData();

                    Toast.makeText(activity, "get your data", Toast.LENGTH_SHORT).show();
                    Log.d("dealer", "onClick:dealer data "+getDealerAsString());
                    Log.d("dealer", "onClick:recharge data "+getRechargeAsString());
                    Log.d("dealer", "onClick:sims data "+getSimsAsString());
                    Log.d("dealer", "onClick:mobile data "+getMobileMonyAsString());
                    Log.d("dealer", "onClick:Telephony data "+getTelephonyAsString());
                    Log.d("dealer", "onClick:Accessoir data "+getAccessoireAsString());
                    Log.d("dealer", "onClick:Potence data "+getPotenceAsString());
                    Log.d("dealer", "onClick:converted Image "+getPotenceAsString());


                }else {
                    // show message to user
                    Toast.makeText(activity, "get your data", Toast.LENGTH_SHORT).show();
                    Log.d("dealer", "onClick:dealer data "+getDealerAsString());
                    Log.d("dealer", "onClick:recharge data "+getRechargeAsString());
                    Log.d("dealer", "onClick:sims data "+getSimsAsString());
                    Log.d("dealer", "onClick:mobile data "+getMobileMonyAsString());
                    Log.d("dealer", "onClick:Telephony data "+getTelephonyAsString());
                    Log.d("dealer", "onClick:Accessoir data "+getAccessoireAsString());
                    Log.d("dealer", "onClick:Potence data "+getPotenceAsString());
                }
            }

        });
        return viewGroup;
    }

    private boolean isUserDateValide() {
        if(!activity.clientFullName.equals("") && !activity.clientLocation.equals("")
                && !activity.clientPhoneNumber.equals("") && !activity.codeBarContent.equals("")
        && activity.clientImage != null && !activity.clientAddress.equals("")
        && !activity.selectedCityCode.equals("") && !activity.selectedRegionCode.equals("")){
            return true;
        }else {
            return false;
        }
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
        if(!activity.authorPot.equals("")){
            if(activity.authorPot.split(",").length > 1){
                for (String ap:activity.authorPot.split(",")) {
                    builder.append(ap+",");
                }
            }else {
                builder.append(activity.authorPot+",");
            }
        }

        String newP = builder.toString().trim();
        if(!newP.equals("")){
            newP = newP.substring(0,newP.length()-1);
            return newP;
        }
        return null;

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
        if(chkPotTaba.isChecked()){
            activity.Potence.add("Taba");
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
        String RIamqtemax = "0";
        String RInwoqtemax = "0";
        String ROrgqtemax = "0";
        activity.Rechargs.clear();
        if(chkHasIamRecharge.isChecked()){
            Rname = "Iam";
            if(!edtIamRechargeMax.getText().toString().equals("")){
                RIamqtemax = edtIamRechargeMax.getText().toString();
            }
            String[] val = new String[]{Rname,RIamqtemax};
            activity.Rechargs.add(val);
        }
        if(chkHasOrgRecharge.isChecked()){
            Rname = "Org";
            if(!edtOrgRechargeMax.getText().toString().equals("")){
                RInwoqtemax = edtOrgRechargeMax.getText().toString();
            }
            String[] val = new String[]{Rname,RInwoqtemax};
            activity.Rechargs.add(val);
        }
        if(chkHasInwiRecharge.isChecked()){
            Rname = "Inwi";
            if(!edtInwiRechargeMax.getText().toString().equals("")){
                ROrgqtemax = edtInwiRechargeMax.getText().toString();
            }
            String[] val = new String[]{Rname,ROrgqtemax};
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
                    edtOrgRechargeMax.setEnabled(true);
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
                    edtInwiRechargeMax.setEnabled(true);
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
                    edtIamRechargeMax.setEnabled(true);
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
                    edtOrgRechargeMax.setEnabled(false);
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
                    edtInwiRechargeMax.setEnabled(false);
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
                    edtIamRechargeMax.setEnabled(false);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Error,Please Try Again Later", Toast.LENGTH_LONG).show();
                Log.d("err", "onErrorResponse: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
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


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public class UploadTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            submitData();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
