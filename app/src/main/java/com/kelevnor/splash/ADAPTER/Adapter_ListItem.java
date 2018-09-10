package com.kelevnor.splash.ADAPTER;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelevnor.splash.ImageLoader.ImageLoader;
import com.kelevnor.splash.MODELS.Message;
import com.kelevnor.splash.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by kelevnor on 9/10/18.
 *
 **/

public class Adapter_ListItem extends RecyclerView.Adapter<Adapter_ListItem.ViewHolder> {
    private List<Message> searchList;
    Typeface fontAwesome, openSansRegular, openSansSemiBold;
    ImageLoader imageLoader;
    onItemClickListener listener;

    Activity act;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView image;
        TextView textLine1;
        TextView textLine2;
        TextView textTime;


        public ViewHolder(LinearLayout v) {
            super(v);
            layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Adapter_ListItem(Activity act, List<Message> searchList, onItemClickListener listener) {
        this.searchList = searchList;
        this.act = act;
        this.listener = listener;
        imageLoader = new ImageLoader(act);
        fontAwesome = Typeface.createFromAsset(act.getAssets(),"fonts/fontawesome-webfont.ttf");
        openSansRegular = Typeface.createFromAsset(act.getAssets(),"fonts/OpenSans-Regular.ttf");
        openSansSemiBold = Typeface.createFromAsset(act.getAssets(),"fonts/OpenSans-Semibold.ttf");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.layout = v.findViewById(R.id.ll_layout);
        vh.textLine1 = v.findViewById(R.id.tv_line1);
        vh.textLine2 = v.findViewById(R.id.tv_line2);
        vh.textTime = v.findViewById(R.id.tv_time);
        vh.image = v.findViewById(R.id.iv_image);

        vh.textLine1.setTypeface(openSansSemiBold);
        vh.textLine2.setTypeface(openSansRegular);
        vh.textTime.setTypeface(openSansRegular);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textLine1.setText(searchList.get(position).getFrom());
        holder.textLine2.setText(searchList.get(position).getMessage());

        holder.textTime.setText(convertTimestamp(searchList.get(position).getSentAt()));


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(searchList.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public interface onItemClickListener{
        void onItemClick(Message item);
    }

    private String convertTimestamp (String timestamp){

        long nowTime = Calendar.getInstance().getTimeInMillis()/1000;

        long diff = Math.abs(nowTime - Long.parseLong(timestamp));

        final int HOURS_IN_DAY = 24;
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        long totalMinutes = diff / SECONDS_IN_A_MINUTE;
        long minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        long hours = totalMinutes / MINUTES_IN_AN_HOUR;
        long days = hours/HOURS_IN_DAY;

        //Message sent before now time
        if(nowTime>Long.parseLong(timestamp)){
            if(hours>24){
                return "> "+days+" days ago";
            }
            else{
                return hours+" hr "+  minutes +" min ago";
            }

        }
        //Message sent after now time
        else{
            if(hours>24){
                return "> "+days+" days ahead";
            }
            else{
                return hours+" hr "+  minutes +" min ahead";
            }

        }

    }
}