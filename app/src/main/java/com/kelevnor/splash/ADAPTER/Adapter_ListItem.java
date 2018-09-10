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

import java.util.List;

/**
 * Created by kelevnor on 08/25/2018.
 */

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
        TextView textLine3;

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
//        vh.textLine2 = v.findViewById(R.id.tv_line2);
//        vh.textLine3 = v.findViewById(R.id.tv_line3);
        vh.image = v.findViewById(R.id.iv_image);

        vh.textLine1.setTypeface(openSansSemiBold);
//        vh.textLine2.setTypeface(openSansRegular);
//        vh.textLine3.setTypeface(openSansRegular);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        imageLoader.DisplayImage(Config.BASE_URL+searchList.get(position).getImage(), holder.image, act);
//        holder.textLine1.setText(searchList.get(position).getTitle());


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
}