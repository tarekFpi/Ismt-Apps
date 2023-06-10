package com.ismt.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.ismt.world.Helpers.MySingleTon;
import com.ismt.world.JsonResponse.PasswordChangeResponse;
import com.ismt.world.R;
import com.ismt.world.StoreSession.AppSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PasswordChangeFragment extends Fragment {

    EditText et_password, et_new_password, et_confirm_password;
    Button btn_updatePassword;

    AppSessionManager sessionManager;

    String mainUrl, id, security;

    Context context;


    public PasswordChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_change, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        et_password = view.findViewById(R.id.et_password);
        et_new_password = view.findViewById(R.id.et_new_password);
        et_confirm_password = view.findViewById(R.id.et_confirm_password);
        btn_updatePassword = view.findViewById(R.id.btn_updatePassword);


        btn_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updatePasswordButton();

            }
        });


        return view;
    }

    private void updatePasswordButton() {
        String password = et_password.getText().toString().trim();
        String newPass = et_new_password.getText().toString().trim();
        String confirmPass = et_confirm_password.getText().toString().trim();

        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(newPass) && !TextUtils.isEmpty(confirmPass)) {
            changePassword();
        } else if (TextUtils.isEmpty(password)) {
            et_password.setError("Enter Your Current Password");
            return;

        } else if (TextUtils.isEmpty(newPass)) {
            et_new_password.setError("Enter Your New Password");
            return;
        } else if (TextUtils.isEmpty(confirmPass)) {
            et_confirm_password.setError("Enter Your New  Password again");
            return;
        }


    }

    private void changePassword() {
        String passwordUpdate_url = mainUrl + "api/app_change_password";

        String password = et_password.getText().toString().trim();
        String newPass = et_new_password.getText().toString().trim();
        String confirmPass = et_confirm_password.getText().toString().trim();


        JSONObject reg_data = new JSONObject();


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        try {
            reg_data.put("security_error", security);
            reg_data.put("id", id);
            reg_data.put("current_password", password);
            reg_data.put("new_password", newPass);
            reg_data.put("new_confirm_password", confirmPass);
//            reg_data.put("sponser_u_id", str_sponsorUserId);
//            reg_data.put("placement_u_id", str_placementUserId);
//            reg_data.put("selected_n", str_placementUserType);



        } catch (JSONException e) {

            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, passwordUpdate_url, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals("password change successfully")) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Password change successfully", Toast.LENGTH_SHORT).show();

                }
                if (jsonArray.getError().equals("Password Not Match")) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Password Not Match", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                networkError(error);

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void networkError(VolleyError error) {

        if (error instanceof NetworkError) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(context, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(context, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(context, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }
}