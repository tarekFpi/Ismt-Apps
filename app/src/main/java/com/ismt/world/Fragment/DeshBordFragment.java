package com.ismt.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ismt.world.DailyTaskActivity;
import com.ismt.world.DashboardActivity;
import com.ismt.world.Helpers.MySingleTon;
import com.ismt.world.ImageUplodeActivity;
import com.ismt.world.JsonResponse.Home_page_Response;
import com.ismt.world.JsonResponse.PasswordChangeResponse;
import com.ismt.world.MainActivity;
import com.ismt.world.MemberFromLoadActivityActivity;
import com.ismt.world.MemberReportLoadActivity;
import com.ismt.world.R;
import com.ismt.world.RegistrationActivity;
import com.ismt.world.Share_MarketActivity;
import com.ismt.world.StoreSession.AppSessionManager;
import com.ismt.world.Team.TeamActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class DeshBordFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private static final int MY_SOCKET_TIMEOUT_MS = 20000;
    private CardView des_income_history, d_register, transctionlog;

    TextView day_date;

    TextView des_user_ID, mobile_recharge_text,withdraw_balance_text;

    String finddate;

    LinearLayout des_profile, team, des_transfer_reserve_balance, des_sending_report, des_password, des_processBalance, des_business_center,
            des_placement_list, des_sponsor_list, des_transaction_pin, des_transfer_balance, des_withdraw_balance,
            des_withdraw_method, des_daily_task, des_imageuplode, des_income_c_history,
            des_Mobile_recharge, des_Share_Market,des_Mobile_recharge2,des_rakuten;

    String webUrl = null;

    TextView balanceDataShow;
    CircleImageView des_profile_pic, des_image_uplode_pic;
    AppSessionManager sessionManager;

    CardView shadowVisible;

    ImageView logout;

    String imageUrl;

    String data = null;
    String data2 = null;
    String data3 = null;
    String data4 = null;
    String data5 = null;
    String data6 = null;
    String data7 = null;
    String data8 = null;
    String data9 = null;
    String data10 = null;
    String data11 = null;
    String data12 = null;
    String data13 = null;
    String id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13;
    String name, UID, ref_uid,team_designation;
    String team_A, team_B, team_C;
    Context context;
    String userId_login, mainUrl, security;

    TextView des_mainbalance, des_user_instant_income, des_daily_income, des_total_income, des_total_Withdraw
            ,des_shopping_balance,des_team_icome_balance,des_reward_icome_balance,des_royal_income_balance,des_bonus_income_balance;//Balance show

    HorizontalScrollView horizonScrollmoneyInfo;


    public DeshBordFragment() {
        // Required empty public constructor
    }


    public static DeshBordFragment newInstance(String param1, String param2) {
        DeshBordFragment fragment = new DeshBordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_desh_bord, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        if (sessionManager.checkLogin())
            getActivity().finish();
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security = user.get(AppSessionManager.KEY_SECURITY);

        // get email
        String email = user.get(AppSessionManager.KEY_USER_ID);

        des_profile_pic =view.findViewById(R.id.des_profile_pic);
        des_image_uplode_pic =view.findViewById(R.id.des_image_uplode_pic);

        des_rakuten = view.findViewById(R.id.des_rakuten);

        des_rakuten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtra("dashItem", "rakuten");
                startActivity(intent);
            }
        });

        des_mainbalance = view.findViewById(R.id.des_mainbalance);
        des_user_instant_income =view.findViewById(R.id.des_user_instant_income);
        des_daily_income =view.findViewById(R.id.des_daily_income);
        des_total_income =view.findViewById(R.id.des_total_income);
        des_total_Withdraw =view.findViewById(R.id.des_total_Withdraw);
        des_shopping_balance =view.findViewById(R.id.des_shopping_balance);

        des_transaction_pin =view.findViewById(R.id.des_transaction_pin);
        des_transfer_balance =view.findViewById(R.id.des_transfer_balance);
        des_withdraw_balance =view.findViewById(R.id.des_withdraw_balance);
        des_withdraw_method =view.findViewById(R.id.des_withdraw_method);
        des_royal_income_balance =view.findViewById(R.id.des_royal_income_balance);
        des_bonus_income_balance =view.findViewById(R.id.des_bonus_income_balance);

        des_imageuplode =view.findViewById(R.id.imageUplode);

        des_income_history =view.findViewById(R.id.des_income_history);

        d_register =view.findViewById(R.id.des_register);

        des_profile =view.findViewById(R.id.des_profile);



        balanceDataShow=view.findViewById(R.id.tapforInfo);
        shadowVisible=view.findViewById(R.id.shadowVisible);

        des_password =view.findViewById(R.id.des_password);

        des_processBalance =view.findViewById(R.id.des_processBalance);
     //   des_daily_task =view.findViewById(R.id.des_daily_task);

        des_Share_Market =view.findViewById(R.id.des_Share_Market);

        des_Share_Market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), Share_MarketActivity.class);
                startActivity(intent);
            }
        });



        team =view.findViewById(R.id.my_team);

        des_business_center =view.findViewById(R.id.des_business_center);



//        des_sending_report = findViewById(R.id.des_sending_report);

        transctionlog =view.findViewById(R.id.des_transctionlog);

        des_placement_list =view.findViewById(R.id.des_placement_list);

        des_income_c_history =view.findViewById(R.id.des_income_c_history);
        //  des_rakuten = findViewById(R.id.des_rakuten);
        des_transfer_reserve_balance =view.findViewById(R.id.des_transfer_reserve_balance);

        des_sponsor_list =view.findViewById(R.id.des_sponsor_list);

        horizonScrollmoneyInfo =view.findViewById(R.id.horizonScrollmoneyInfo);

        des_Mobile_recharge2 =view.findViewById(R.id.des_Mobile_recharge2);




        mobile_recharge_text =view.findViewById(R.id.mobile_recharge_text);
//        findViewById(R.id.des_Mobile_recharge2).setSelected(true);
        mobile_recharge_text.setSelected(true);

        withdraw_balance_text =view.findViewById(R.id.withdraw_balance_text);
        des_team_icome_balance =view.findViewById(R.id.des_team_icome_balance);
        des_reward_icome_balance =view.findViewById(R.id.des_reward_icome_balance);
        withdraw_balance_text =view.findViewById(R.id.withdraw_balance_text);

        withdraw_balance_text.setSelected(true);


        d_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), RegistrationActivity.class);
                startActivity(intent);

                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });




        des_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentProfile = new Intent(getContext(), MemberFromLoadActivityActivity.class);
                intentProfile.putExtra("Techno", "01");

                startActivity(intentProfile);

            }
        });

        des_income_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "02");
                startActivity(intent5);

            }
        });

        des_processBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getActivity(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "06");
                startActivity(intent5);
            }
        });


        des_business_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "11");
                startActivity(intent5);
            }
        });
        des_placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "12");
                startActivity(intent5);
            }
        });
        des_sponsor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "13");
                startActivity(intent5);
            }
        });

//        des_transaction_pin,des_transfer_balance,des_withdraw_balance,des_withdraw_method;

        des_transaction_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "14");
                startActivity(intent5);
            }
        });
        des_transfer_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "15");
                startActivity(intent5);
            }
        });
        des_withdraw_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "16");
                startActivity(intent5);
            }
        });
        des_withdraw_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "17");
                startActivity(intent5);
            }
        });
        des_transfer_reserve_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "18");
                startActivity(intent5);
            }
        });
        des_Mobile_recharge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "20");
                startActivity(intent5);
            }
        });
        des_income_c_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("dashItem", "income");
                startActivity(intent);
            }
        });  /*des_rakuten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
                intent.putExtra("dashItem", "rakuten");
                startActivity(intent);
            }
        });*/

//        des_income_c_history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent5 = new Intent(DashboardActivity.this, MemberFromLoadActivityActivity.class);
//                intent5.putExtra("Techno", "19");
//                startActivity(intent5);
//            }
//        });

      /*  des_daily_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTaskUrl();

            }
        });*/

     /*   des_daily_task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyTaskUrl();

            }
        });*/

        des_imageuplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), ImageUplodeActivity.class);
                intent5.putExtra("imageUrl", imageUrl);
                startActivity(intent5);


            }
        });

        balanceDataShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersonalData();
            }
        });

        transctionlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "03");
                startActivity(intent5);
            }
        });

        des_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getContext(), MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "04");
                startActivity(intent5);
            }
        });

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContent();


            }
        });
        return view;
    }


    private void DailyTaskUrl() {

        String personalUri = mainUrl + "api/app_dailytask";

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject personalData = new JSONObject();

        try {
            personalData.put("security_error", security);
            personalData.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, personalUri, personalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                try {
                    progressDialog.dismiss();
                    JSONArray jsonArrayb = response.getJSONArray("tbl_web_link");
                    JSONObject method_list = jsonArrayb.getJSONObject(0);

                    webUrl = method_list.getString("link");


//                    if (webUrl != null) {
//                        Intent intent5 = new Intent(DashboardActivity.this, DailyTaskActivity.class);
//
//                        intent5.putExtra("webUrl", webUrl);
//                        startActivity(intent5);
//                        Toast.makeText(context, webUrl, Toast.LENGTH_SHORT).show();
//                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

                try {
                    String err = response.getString("error");


                    if (err.equals("available")) {
                        //  int count_add = response.getInt("count_add");
                        if (webUrl != null) {
                            Intent intent5 = new Intent(getContext(), DailyTaskActivity.class);
                            intent5.putExtra("webUrl", webUrl);
                            // intent5.putExtra("count_add", count_add);
                            startActivity(intent5);
//                            Toast.makeText(context, webUrl, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (err.equals("done")) {
                        if (webUrl == null) {
                            Intent intent5 = new Intent(getContext(), DailyTaskActivity.class);
                            intent5.putExtra("webUrl", webUrl);
                            startActivity(intent5);
//                            Toast.makeText(context, webUrl, Toast.LENGTH_SHORT).show();
                        }
                    }
//                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                netWorkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void PersonalData() {

        String personalUri = mainUrl + "api/app_homepages_data";

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        JSONObject personalData = new JSONObject();

        try {
            personalData.put("security_error", security);
            personalData.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, personalUri, personalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Home_page_Response jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), Home_page_Response.class);

                progressDialog.dismiss();



                try {
                    String currency = response.getString("currency");

                    imageUrl = mainUrl + response.getString("main_picture_Url");

                    Picasso.get()
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_profile)
                            .into(des_profile_pic);

                    Picasso.get()
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_profile)
                            .into(des_image_uplode_pic);

                    des_mainbalance.setText("" +response.getString("main_balance")+ " " + currency);
                    shadowVisible.setVisibility(View.GONE);
                    horizonScrollmoneyInfo.setVisibility(View.VISIBLE);
                    des_user_instant_income.setText("" +response.getString("user_instant_income") + " " + currency);
                    des_daily_income.setText("" + response.getString("daily_income")+ " " + currency);
                    des_total_income.setText("" + response.getString("total_income") + " " + currency);
                    des_total_Withdraw.setText("" +response.getString("total_withdraw") + " " + currency);
                    des_team_icome_balance.setText("" +response.getString("total_withdraw") + " " + currency);
                    des_team_icome_balance.setText("" +response.getString("user_team_db") + " " + currency);
                    des_reward_icome_balance.setText("" +response.getString("reward_income") + " " + currency);
                    des_total_Withdraw.setText("" +response.getString("total_withdraw") + " " + currency);

                    des_shopping_balance.setText("" +response.getString("main_shop_balance")+" " + currency);
                    des_royal_income_balance.setText("" +response.getString("royal_income")+" " + currency);
                    des_bonus_income_balance.setText("" +response.getString("bonus_income")+" " + currency);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                netWorkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void loadContent() {

        String treeUrl = mainUrl + "api/app_user_tree";

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, treeUrl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Intent teamIntent = new Intent(getContext(), TeamActivity.class);

                try {

                    JSONArray jsonArray = response.getJSONArray("user_tree_main");
                    JSONArray jsonArray2 = response.getJSONArray("user_tree_1_1");
                    JSONArray jsonArray3 = response.getJSONArray("user_tree_1_2");
                    JSONArray jsonArray4 = response.getJSONArray("user_tree_1_3");

                    JSONArray jsonArray5 = response.getJSONArray("user_tree_2_1");

                    JSONArray jsonArray6 = response.getJSONArray("user_tree_2_2");

                    JSONArray jsonArray7 = response.getJSONArray("user_tree_2_3");

                    JSONArray jsonArray8 = response.getJSONArray("user_tree_3_1");
                    JSONArray jsonArray9 = response.getJSONArray("user_tree_3_2");

                    JSONArray jsonArray10 = response.getJSONArray("user_tree_3_3");
                    JSONArray jsonArray11 = response.getJSONArray("user_tree_4_1");

                    JSONArray jsonArray12 = response.getJSONArray("user_tree_4_2");
                    JSONArray jsonArray13 = response.getJSONArray("user_tree_4_3");


                    if (response.getJSONArray("user_tree_main").equals(jsonArray)) {
                        String reff = ref_uid;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject treeData = jsonArray.getJSONObject(i);

                            id1 = treeData.getString("id");
                            name = treeData.getString("name");
                            UID = treeData.getString("username");
                            // team_designation = treeData.getString("designation");
                            team_A = treeData.getString("carry1");
                            team_B = treeData.getString("carry2");
                            team_C = treeData.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_main_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2 = jsonArraref.getJSONObject(b);

                                reff = treeData2.getString("username");



                            }


                            data = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";

                            teamIntent.putExtra("data", data);
                            teamIntent.putExtra("id", id1);
                        }


                    }

                    if (response.getJSONArray("user_tree_1_1").equals(jsonArray2)) {
                        String reff2 = ref_uid;
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject treeData2 = jsonArray2.getJSONObject(i);

                            id2 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            //   team_designation = treeData2.getString("designation");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff2 = treeData2r.getString("username");


                            }


                            data2 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff2 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data2", data2);
                            teamIntent.putExtra("id2", id2);
                        }

                    }

                    if (response.getJSONArray("user_tree_1_2").equals(jsonArray3)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray3.length(); i++) {
                            JSONObject treeData2 = jsonArray3.getJSONObject(i);

                            id3 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //   team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data3 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data3", data3);
                            teamIntent.putExtra("id3", id3);
                        }

                    }

                    if (response.getJSONArray("user_tree_1_3").equals(jsonArray4)) {
                        String reff4 = null;
                        for (int i = 0; i < jsonArray4.length(); i++) {
                            JSONObject treeData2 = jsonArray4.getJSONObject(i);
                            id4 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            // team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff4 = treeData2r.getString("username");


                            }


                            data4 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff4 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data4", data4);
                            teamIntent.putExtra("id4", id4);
                        }

                    }

                    if (response.getJSONArray("user_tree_2_1").equals(jsonArray5)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray5.length(); i++) {
                            JSONObject treeData2 = jsonArray5.getJSONObject(i);
                            id5 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //   team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data5 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data5", data5);
                            teamIntent.putExtra("id5", id5);
                        }

                    }


                    if (response.getJSONArray("user_tree_2_2").equals(jsonArray6)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray6.length(); i++) {
                            JSONObject treeData2 = jsonArray6.getJSONObject(i);
                            id6 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //  team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data6 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data6", data6);
                            teamIntent.putExtra("id6", id6);
                        }

                    }

                    if (response.getJSONArray("user_tree_2_3").equals(jsonArray7)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray7.length(); i++) {
                            JSONObject treeData2 = jsonArray7.getJSONObject(i);
                            id7 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //    team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data7 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data7", data7);
                            teamIntent.putExtra("id7", id7);
                        }

                    }

                    if (response.getJSONArray("user_tree_3_1").equals(jsonArray8)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray8.length(); i++) {
                            JSONObject treeData2 = jsonArray8.getJSONObject(i);
                            id8 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //  team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data8 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data8", data8);
                            teamIntent.putExtra("id8", id8);
                        }

                    }

                    if (response.getJSONArray("user_tree_3_2").equals(jsonArray9)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray9.length(); i++) {
                            JSONObject treeData2 = jsonArray9.getJSONObject(i);
                            id9 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //  team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data9 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference BID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data9", data9);
                            teamIntent.putExtra("id9", id9);

                        }

                    }

                    if (response.getJSONArray("user_tree_3_3").equals(jsonArray10)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray10.length(); i++) {
                            JSONObject treeData2 = jsonArray10.getJSONObject(i);
                            id10 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //  team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_3_r_n");

                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data10 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data10", data10);
                            teamIntent.putExtra("id10", id10);
                        }

                    }

                    if (response.getJSONArray("user_tree_4_1").equals(jsonArray11)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray11.length(); i++) {
                            JSONObject treeData2 = jsonArray11.getJSONObject(i);
                            id11 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //    team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_4_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data11 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data11", data11);
                            teamIntent.putExtra("id11", id11);
                        }

                    }

                    if (response.getJSONArray("user_tree_4_2").equals(jsonArray12)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray12.length(); i++) {
                            JSONObject treeData2 = jsonArray12.getJSONObject(i);
                            id12 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //   team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_4_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data12 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data12", data12);
                            teamIntent.putExtra("id12", id12);
                        }

                    }

                    if (response.getJSONArray("user_tree_4_3").equals(jsonArray13)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray13.length(); i++) {
                            JSONObject treeData2 = jsonArray13.getJSONObject(i);
                            id13 = treeData2.getString("id");
                            //  team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_4_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data13 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data13", data13);
                            teamIntent.putExtra("id13", id13);
                        }

                    }
                    startActivity(teamIntent);
                     getActivity().finish();


                }

                catch (JSONException e) {

                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                netWorkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }

    private void netWorkError(VolleyError error) {
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