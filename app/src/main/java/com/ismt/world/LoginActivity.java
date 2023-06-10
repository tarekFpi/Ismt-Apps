package com.ismt.world;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ismt.world.Adapter.Structure;
import com.ismt.world.Helpers.MainValues;
import com.ismt.world.Helpers.MySingleTon;
import com.ismt.world.JsonResponse.LoginResponse;
import com.ismt.world.StoreSession.AppSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private static final int MY_SOCKET_TIMEOUT_MS = 20000;
    EditText ed_username, ed_password;

    Button btn_login;

    String str_username, str_password;

    CheckBox rememberme;

    AppSessionManager sessionManager;

    String mainUrl = MainValues.URL;


    TextView txt_login_forgotPass, forgotpasstext;

    String security;

    Context context;

    String forgetmessage;

    String str;
    String pass = null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String pic_url,userName,userFullname,designation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("savePass", Context.MODE_PRIVATE);
        editor = preferences.edit();

        context = getApplicationContext();

        ed_username = findViewById(R.id.login_userId);
        ed_password = findViewById(R.id.login_pass);

        btn_login = findViewById(R.id.login_signin);

//        txt_login_forgotPass = findViewById(R.id.txt_login_forgotPass);
        txt_login_forgotPass = findViewById(R.id.txt_loging_forgotPass);
        forgotpasstext = findViewById(R.id.forgotpasstext);

        rememberme = findViewById(R.id.rememberMe);
        pass = preferences.getString("password", "");
        if (!pass.equals("")) {
            rememberme.setChecked(true);

            ed_password.setText(pass);
        }

        sessionManager = new AppSessionManager(this);

        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();

            }
        });

        txt_login_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });

        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        if (extras != null) {
            String data = intent.getExtras().getString("forgetpass").toString();
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
            forgotpasstext.setVisibility(View.VISIBLE);
        }

    }


    private void submit() {

        String url = mainUrl + "api/apps_user_login_info";


        security = Structure.SURL;


        if (ed_username.getText().toString().equals("")) {

            ed_username.setError("Enter User Name");
//            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            ed_password.setError("Enter Password");
//            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progrss_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


            str_username = ed_username.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();


            if (rememberme.isChecked()) {
                editor.putString("password", str_password);
                editor.commit();

            } else {

                editor.clear();
                editor.apply();
            }


            JSONObject postData = new JSONObject();
            try {
                postData.put("security_error", security);
                postData.put("username", str_username);
                postData.put("password", str_password);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    LoginResponse jsonArray = null;
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    Gson gson = gsonBuilder.create();
                    jsonArray = gson.fromJson(response.toString(), LoginResponse.class);


                    try {
                        if (jsonArray.getMessage().equals("Login successful!")) {
                            progressDialog.dismiss();

                            String sl_user_id = jsonArray.getUserId();

//                            String picture=response.getString("picture_Url");
//
//                            userName=response.getString("username");
//                            userFullname=response.getString("name");
//
//                            designation =response.getString("designation")  ;
//
//
//
//
//
//                            pic_url = mainUrl +picture;

                            sessionManager.createUserLoginSession(security,
                                    str_username, str_password, sl_user_id);


                            sessionManager.storeBaseUrl(mainUrl);


                            //process your steps if the value is success
                            goToDashboard();
                            Toast.makeText(LoginActivity.this, jsonArray.getMessage(), Toast.LENGTH_LONG).show();


                        } else {

                            progressDialog.dismiss();

                            //displaying toast when error occurs
                            Toast.makeText(LoginActivity.this, jsonArray.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {

                        Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    netWorkError(error);
                    Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            jsonObjectRequest.setShouldCache(false);
            MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);


        }
    }

    private void netWorkError(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(LoginActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(LoginActivity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(LoginActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(LoginActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(LoginActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }

    public void goToDashboard() {
        Intent i = new Intent(this, DashboardActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finishAffinity();
                    }
                }).show();

    }
}
