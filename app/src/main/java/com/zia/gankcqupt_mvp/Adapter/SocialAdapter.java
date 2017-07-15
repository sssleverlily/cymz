package com.zia.gankcqupt_mvp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.zia.gankcqupt_mvp.Bean.Title;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Page.ReplyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/29.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.Holder> {

    private List<Title> titles = new ArrayList<>();
    private Context context;
    private final static String TAG = "SocialAdapterTest";

    public SocialAdapter(Context context){
        this.context = context;
    }

    public void refreshData(List<Title> titles){
        this.titles = titles;
        notifyDataSetChanged();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Title title = titles.get(position);
        holder.title.setText(title.getTitle());
        holder.nickname.setText(title.getAuthor());
        holder.count.setText(title.getCount());
        holder.time.setText(title.getTime());
        holder.content.setText(title.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,title.getObjectId());
                Intent intent = new Intent(context, ReplyActivity.class);
                intent.putExtra("objectId",title.getObjectId());
                intent.putExtra("userId",title.getUserId());
                intent.putExtra("title",title);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(title.getHeadUrl()).into(holder.headImage);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView title,content,time,count,nickname;
        ImageView headImage;
        public Holder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.social_item_title);
            content = (TextView)itemView.findViewById(R.id.social_item_content);
            time = (TextView)itemView.findViewById(R.id.social_item_time);
            count = (TextView)itemView.findViewById(R.id.social_item_count);
            nickname = (TextView)itemView.findViewById(R.id.social_item_nickname);
            headImage = (ImageView)itemView.findViewById(R.id.social_item_image);
        }
    }
}
