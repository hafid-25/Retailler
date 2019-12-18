package ma.ebertel.retailer.Tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

import ma.ebertel.retailer.Adapters.DetergentProductAdapter;
import ma.ebertel.retailer.Adapters.GridAutoFitLayoutManager;
import ma.ebertel.retailer.Adapters.ThéProductAdapter;
import ma.ebertel.retailer.Fragments.UserProductInfo;
import ma.ebertel.retailer.MainActivity;

public class GetThéNamesTask extends AsyncTask<Void,Void,Void> {

    UserProductInfo userProductInfo;
    MainActivity activity;
    RecyclerView recyclerView;
    ProgressBar waiter;
    ExpandableLayout expandableLayout;

    public GetThéNamesTask(MainActivity activity, UserProductInfo userProductInfo, RecyclerView recyclerView, ProgressBar waiter,
                           ExpandableLayout expandableLayout){
        this.userProductInfo = userProductInfo;
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.waiter = waiter;
        this.expandableLayout = expandableLayout;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getThéTypes();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        waiter.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void data) {
        super.onPostExecute(data);
        waiter.setVisibility(View.GONE);

        expandableLayout.toggle();
    }

    private void getThéTypes(){
        String detergentUrl = "http://hafid.skandev.com/getDetergentTypes.php";
        StringRequest detergentRequest = new StringRequest(StringRequest.Method.POST, detergentUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    userProductInfo.théNames.clear();
                    userProductInfo.théIds.clear();
                    for(int i=0; i < jsonArray.length(); i++){
                        userProductInfo.théNames.add(jsonArray.getJSONObject(i).getString("libelle"));
                        userProductInfo.théIds.add(jsonArray.getJSONObject(i).getString("id"));
                        // thé recycler
                        ThéProductAdapter théProductAdapter = new ThéProductAdapter(userProductInfo.getContext(),userProductInfo.théNames,userProductInfo);
                        recyclerView.setAdapter(théProductAdapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridAutoFitLayoutManager(userProductInfo.getContext(),150));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Coonection Error", Toast.LENGTH_LONG).show();
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
}
