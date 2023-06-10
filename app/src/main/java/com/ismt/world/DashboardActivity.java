package com.ismt.world;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ismt.world.Fragment.DeshBordFragment;
import com.ismt.world.Fragment.PasswordChangeFragment;
import com.ismt.world.Fragment.TransactionPinFragment;
import com.ismt.world.Helpers.MySingleTon;
import com.ismt.world.JsonResponse.Home_page_Response;
import com.ismt.world.JsonResponse.PasswordChangeResponse;
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

public class DashboardActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 20000;
    private CardView des_income_history, d_register, transctionlog;

    TextView day_date;

    TextView des_user_ID, mobile_recharge_text,withdraw_balance_text;

    String finddate;

    LinearLayout des_profile, team, des_transfer_reserve_balance, des_sending_report, des_password, des_processBalance, des_business_center,
            des_placement_list, des_sponsor_list, des_transaction_pin, des_transfer_balance, des_withdraw_balance,
            des_withdraw_method, des_daily_task, des_imageuplode, des_income_c_history,
            des_Mobile_recharge, des_daily_task2,des_Mobile_recharge2,des_rakuten;

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

    private MeowBottomNavigation bottomNavigation;




        private final static int transaction_log = 1;
          private final static int Home_id = 2;
        private final static int password = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new AppSessionManager(this);
        if (sessionManager.checkLogin())
            finish();
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security = user.get(AppSessionManager.KEY_SECURITY);

        // get email
        String email = user.get(AppSessionManager.KEY_USER_ID);

        day_date =findViewById(R.id.day_date_text);
        des_user_ID = findViewById(R.id.des_user_Id);

        date();
        day_date.setText("Today: " + finddate);

        des_user_ID.setText(email);
        logout = findViewById(R.id.des_logout_btn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

                finish();

            }
        });

       bottomNavigation =(MeowBottomNavigation)findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(transaction_log, R.drawable.transaction));
        bottomNavigation.add(new MeowBottomNavigation.Model(Home_id, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(password, R.drawable.pass1));

        getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,new DeshBordFragment()).commit();

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                String name="";
                Fragment fragment=null;

                switch (item.getId()){
                    case 1:

                        fragment = new TransactionPinFragment();
                       // report_load_tab_bar.setText("Transaction Pin");
                     break;

                      case 2:
                        fragment = new DeshBordFragment();
                        // report_load_tab_bar.setText("Transaction Pin");
                        break;

                    case 3:

                        fragment = new PasswordChangeFragment();
                        // report_load_tab_bar.setText("Transaction Pin");
                        break;

                    default:name="";
                  }
                getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,fragment).commit();
            }
        });
        bottomNavigation.show(Home_id,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
              //  Toast.makeText(DashboardActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
              //  Toast.makeText(DashboardActivity.this, "reselected item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
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

    public void date() {


        finddate = new SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()).format(Calendar.getInstance().getTime());


    }
}