package com.kelevnor.splash;

import android.app.ActionBar;
import android.app.Activity;
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

public class ItemDisplayActivity extends Activity implements View.OnClickListener, Adapter_ListItem.onItemClickListener {

    Adapter_ListItem listAdapter;

    ImageView backIV, addIV;
    RecyclerView listRV;
    LinearLayout eventNotificationsLL, directMessagesLL;
    TextView actionBarTitle, eventNotificationsTV, directMessagesTV;
    View belowEventNotificationsV, belowDirectMessagesV;
    Typeface openSansRegular, openSansSemiBold, fontAwesome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_messages_layout);

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

        eventNotificationsLL = findViewById(R.id.ll_eventnotifications);
        directMessagesLL = findViewById(R.id.ll_directmessages);

        actionBarTitle = mCustomView.findViewById(R.id.tv_title);

        eventNotificationsTV = findViewById(R.id.tv_eventnotifications);
        directMessagesTV = findViewById(R.id.tv_directmessages);

        belowEventNotificationsV = findViewById(R.id.v_eventnotifications);
        belowDirectMessagesV = findViewById(R.id.v_directmessages);

        eventNotificationsTV.setTypeface(openSansRegular);
        directMessagesTV.setTypeface(openSansRegular);
        actionBarTitle.setTypeface(openSansRegular);

        eventNotificationsLL.setOnClickListener(this);
        directMessagesLL.setOnClickListener(this);
        backIV.setOnClickListener(this);

        listAdapter = new Adapter_ListItem(ItemDisplayActivity.this, Config.inboxStored, this);
        listRV.setAdapter(listAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        listRV.setLayoutAnimation(animation);
        listRV.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ll_directmessages:
                setActive(directMessagesTV, belowDirectMessagesV, eventNotificationsTV, belowEventNotificationsV);
                break;

            case R.id.ll_eventnotifications:
                setActive(eventNotificationsTV, belowEventNotificationsV, directMessagesTV, belowDirectMessagesV);
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
}
