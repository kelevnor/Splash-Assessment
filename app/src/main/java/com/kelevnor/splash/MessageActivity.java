package com.kelevnor.splash;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelevnor.splash.ADAPTER.Adapter_ListItem;
import com.kelevnor.splash.MODELS.Message;
import com.kelevnor.splash.UTILITY.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageActivity extends Activity implements View.OnClickListener, Adapter_ListItem.onItemClickListener {

    Adapter_ListItem listAdapter;

    ImageView backIV, addIV;
    RecyclerView listRV;
    LinearLayout eventNotificationsLL, directMessagesLL;
    TextView actionBarTitle, messageTV, timestampTV;
    View belowEventNotificationsV, belowDirectMessagesV;
    Typeface openSansRegular, openSansSemiBold, fontAwesome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_message);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        //Actionbar view
        View mCustomView = mInflater.inflate(R.layout.actionbar_layout_inbox, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        openSansRegular = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        openSansSemiBold = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Semibold.ttf");
        fontAwesome = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        backIV = mCustomView.findViewById(R.id.iv_back);
        addIV = mCustomView.findViewById(R.id.iv_add);
        listRV = findViewById(R.id.list);

        addIV.setVisibility(View.INVISIBLE);
        eventNotificationsLL = findViewById(R.id.ll_eventnotifications);
        directMessagesLL = findViewById(R.id.ll_directmessages);

        actionBarTitle = mCustomView.findViewById(R.id.tv_title);

        messageTV = findViewById(R.id.tv_message);
        timestampTV = findViewById(R.id.tv_timestamp);

        belowEventNotificationsV = findViewById(R.id.v_eventnotifications);
        belowDirectMessagesV = findViewById(R.id.v_directmessages);

        messageTV.setTypeface(openSansRegular);
        timestampTV.setTypeface(openSansRegular);
        actionBarTitle.setTypeface(openSansRegular);
        backIV.setOnClickListener(this);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String message = i.getStringExtra("message");
        String timestamp = i.getStringExtra("timestamp");
        actionBarTitle.setText(name.toUpperCase());
        messageTV.setText(message);
        timestampTV.setText(convertTimestamp(timestamp));
//        listAdapter = new Adapter_ListItem(MessageActivity.this, Config.inboxStored, this);
//        listRV.setAdapter(listAdapter);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        int resId = R.anim.layout_animation_fall_down;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
//        listRV.setLayoutAnimation(animation);
//        listRV.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ll_directmessages:
//                setActive(directMessagesTV, belowDirectMessagesV, eventNotificationsTV, belowEventNotificationsV);
                break;

            case R.id.ll_eventnotifications:
//                setActive(eventNotificationsTV, belowEventNotificationsV, directMessagesTV, belowDirectMessagesV);
                break;

            case  R.id.iv_back:
                finish();
                break;
        }
    }

    private void setActive(TextView active,  View activeView, TextView notActive, View notActiveView){
        active.setTextColor(getResources().getColor(R.color.colorBlack));
        activeView.setVisibility(View.VISIBLE);

        notActive.setTextColor(getResources().getColor(R.color.colorNotPickedTitle));
        notActiveView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(Message item) {

    }

    private String convertTimestamp (String timestamp){

        Date date = new Date(Long.parseLong(timestamp)*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy h:mm a", Locale.ENGLISH);
        return sdf.format(date);

    }

}
