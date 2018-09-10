package com.kelevnor.splash;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.kelevnor.splash.MODELS.Message;
import com.kelevnor.splash.REST.PullData;
import com.kelevnor.splash.UTILITY.Config;
import com.kelevnor.splash.UTILITY.Permission_Request_Helper;
import com.kelevnor.splash.UTILITY.UtilityHelper;
import com.kelevnor.splash.ADAPTER.Adapter_ListItem;

import java.util.List;

public class   MainActivity extends Activity implements PullData.OnAsyncResult, Permission_Request_Helper.OnAsyncResult, Switch.OnCheckedChangeListener, View.OnClickListener{
    Permission_Request_Helper permissionHelper;
    RecyclerView list;
    Switch enablePerm;
    Switch enableInternet;
    LinearLayout layout;
    Adapter_ListItem listAdapter;
    Typeface openSansRegular, openSansSemiBold, fontAwesome;
    ImageView actionbarBack, actionbarMessages, actionbarInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_layout_mainactivity, null);

        actionbarBack = mCustomView.findViewById(R.id.iv_back);
        actionbarInfo = mCustomView.findViewById(R.id.iv_info);
        actionbarMessages = mCustomView.findViewById(R.id.iv_message);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);



        layout = findViewById(R.id.ll_layout);
        list = findViewById(R.id.list);
        enablePerm = findViewById(R.id.sw_enableperm);
        enableInternet = findViewById(R.id.sw_enableinternet);

        openSansRegular = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        openSansSemiBold = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Semibold.ttf");
        fontAwesome = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        enablePerm.setTypeface(openSansSemiBold);
        enableInternet.setTypeface(openSansSemiBold);

        enablePerm.setOnCheckedChangeListener(this);
        enableInternet.setOnCheckedChangeListener(this);

        permissionHelper = new Permission_Request_Helper(this, this);
        permissionHelper.CheckGenericPermissions();

        actionbarMessages.setOnClickListener(this);
        actionbarInfo.setOnClickListener(this);
        actionbarBack.setOnClickListener(this);
    }

    //PullData Interface Listener
    @Override
    public void onResultSuccess(int resultCode, List<Message> objList) {
        Config.inboxStored = objList;
    }

    @Override
    public void onResultFail(int resultCode, String errorMessage) {
        if(resultCode== Config.RESULT_INTERNET_FAIL){
            UtilityHelper.displayDialogOneButton(this,getResources().getString(R.string.error_label),getResources().getString(R.string.error_internet),getResources().getString(R.string.bottombtn_dismiss));

            if(!UtilityHelper.isWifiAvailable(this)){
                layout.setVisibility(View.VISIBLE);
                enablePerm.setVisibility(View.GONE);
                enableInternet.setVisibility(View.VISIBLE);
            }
        }
        else if(resultCode==Config.RESULT_PULLDATA_FAIL){
            UtilityHelper.displayDialogOneButton(this,getResources().getString(R.string.error_label),getResources().getString(R.string.error_pull_data),getResources().getString(R.string.bottombtn_dismiss));
        }
        Log.e("Pull Data","FAIL");
    }

    //Permission Request Interface Listener
    @Override
    public void onInternetForPermissionSuccess(int resultCode, String message) {
        Log.d("SUCCESS", "INTERNET_STORAGE_ALLOWED");
        new PullData(this,this);
    }

    @Override
    public void onInternetForPermissionFail(int resultCode, String errorMessage) {

        layout.setVisibility(View.VISIBLE);
        if(resultCode == Config.RESULT_EXT_STORAGE_FAIL){
            Log.d("FAIL", "RESULT_EXT_STORAGE_FAIL");
            enableInternet.setVisibility(View.GONE);
            enablePerm.setVisibility(View.VISIBLE);
        }
        else if(resultCode == Config.RESULT_EXT_STORAGE_SUCCESS_INTERNET_FAIL){
            Log.d("FAIL", "RESULT_EXT_STORAGE_SUCCESS_INTERNET_FAIL");
            enablePerm.setVisibility(View.GONE);
            enableInternet.setVisibility(View.VISIBLE);
        }
        else if(resultCode == Config.RESULT_EXT_STORAGE_INTERNET_FAIL){
            Log.d("FAIL", "RESULT_EXT_STORAGE_INTERNET_FAIL");
            enablePerm.setVisibility(View.VISIBLE);
            enableInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        permissionHelper.onRequestPermissionAction(requestCode, permissions, grantResults);
    }

    //
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()){
            case R.id.sw_enableperm:
                enablePerm.setChecked(false);
                enablePerm.setVisibility(View.GONE);
                permissionHelper.CheckGenericPermissions();
                break;

            case R.id.sw_enableinternet:
                enableInternet.setChecked(false);
                enableInternet.setVisibility(View.GONE);
                WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);

//                actionbarLoader.setVisibility(View.VISIBLE);
                setTimerUntilFetch(this, this);
                break;
        }
    }

    //Method that uses CoundDownTimer to start PullData Rest after 3 secods
    public void setTimerUntilFetch(final Activity act, final PullData.OnAsyncResult dataOnAsync){
        CountDownTimer timer = new CountDownTimer(3000, 1000)
        {
            public void onTick(final long millisUntilFinished){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        actionbarLoader.setText((int)millisUntilFinished/1000);
                    }
                });

            }
            public void onFinish() {
//                actionbarLoader.setVisibility(View.GONE);
                new PullData(act, dataOnAsync);}
        };
        timer.start();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_message:

                Intent i = new Intent(this, ItemDisplayActivity.class);
                startActivity(i);

                break;

            case R.id.iv_info:

//                Intent j = new Intent(this, ItemDisplayActivity.class);
//                startActivity(j);

                break;
        }
    }
}
