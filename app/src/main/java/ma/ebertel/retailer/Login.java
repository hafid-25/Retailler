package ma.ebertel.retailer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import static android.app.PendingIntent.getActivity;

public class Login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText loginUsername,loginPassword;
    public String loginUrl = "http://hafid.skandev.com/verifyLogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPassword = findViewById(R.id.loginPassword);
        loginUsername = findViewById(R.id.loginUsername);
        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_name), Context.MODE_PRIVATE);

        isUserAlreadyLoggedIn();
    }

    public void Login(View view){
        String username = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        if(!username.equals("") && !password.equals("")){
            verifyLogin(username,password);
        }else {
            Toast.makeText(this, "Please UserName And Password", Toast.LENGTH_LONG).show();
        }
    }

    private void verifyLogin(final String username, final String pass) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String login = jsonObject.getString("login");
                            String role = jsonObject.getString("role");
                            String email = jsonObject.getString("email");
                            String name = jsonObject.getString("name");
                            int id = jsonObject.getInt("userId");
                            if(login.equals("1")){
                                editor = sharedPreferences.edit();
                                editor.putString("username",username);
                                editor.putString("login",login);
                                editor.putString("role",role);
                                editor.putString("email",email);
                                editor.putString("name",name);
                                editor.putInt("userId",id);

                                if(role.equals("1")){
                                    editor.putString("roleName","vond");
                                }else {
                                    editor.putString("roleName","vis");
                                }
                                editor.commit();
                                Intent loginIntent = new Intent(Login.this,MainActivity.class);
                                startActivity(loginIntent );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error,Please Try Again Later", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void isUserAlreadyLoggedIn() {
        String login = sharedPreferences.getString("login","0");
        if(login.equals("1")){
            Intent redirect = new Intent(Login.this,MainActivity.class);
            startActivity(redirect);
        }

    }
}
