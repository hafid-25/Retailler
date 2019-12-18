package ma.ebertel.retailer.Tasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;

import ma.ebertel.retailer.Adapters.MobileMonnyAdapter;
import ma.ebertel.retailer.Fragments.UserGenInfo;
import ma.ebertel.retailer.MainActivity;

public class GetMobileMoneyTypesTask extends AsyncTask<Void,Void,Void> {
    MainActivity activity;
    ExpandableLayout expandableLayout;
    UserGenInfo userGenInfo;
    RecyclerView mobileMonnyRecycler;
    ProgressBar progressWaiting;

    public GetMobileMoneyTypesTask(MainActivity activity,ExpandableLayout expandableLayout,UserGenInfo userGenInfo,RecyclerView mobileMonnyRecycler,
                                   ProgressBar progressWaiting){
        this.activity = activity;
        this.expandableLayout = expandableLayout;
        this.userGenInfo = userGenInfo;
        this.mobileMonnyRecycler = mobileMonnyRecycler;
        this.progressWaiting = progressWaiting;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressWaiting.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getMobiles();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        expandableLayout.toggle();
        progressWaiting.setVisibility(View.GONE);
    }

    private void getMobiles(){
        final String typesUrl = "http://hafid.skandev.com/getMobile.php";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, typesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    userGenInfo.mobiles.clear();
                    for(int i=0;i<json.length();i++){
                        userGenInfo.mobiles.add(json.getJSONObject(i).getString("operateur"));
                    }
                    // set the recycler data
                    MobileMonnyAdapter mobileMonnyAdapter = new MobileMonnyAdapter(activity,userGenInfo.mobiles, userGenInfo,null,1);
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
