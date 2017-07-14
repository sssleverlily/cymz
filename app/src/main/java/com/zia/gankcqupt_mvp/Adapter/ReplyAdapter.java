package com.zia.gankcqupt_mvp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.zia.gankcqupt_mvp.Bean.Comment;
import com.zia.gankcqupt_mvp.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zia on 17-7-12.
 */

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.Holder>  {

    private List<Comment> list = new ArrayList<>();
    private Context context = null;
    private static final String TAG = "ReplyAdapterTest";

    public ReplyAdapter(Context context){
        this.context = context;
    }

    public void refreshData(List<Comment> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Comment comment = list.get(position);
        Observable.create(new ObservableOnSubscribe<AVUser>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<AVUser> e) throws Exception {
                try {
                    AVQuery<AVObject> avQuery = new AVQuery<>("_User");
                    e.onNext((AVUser)avQuery.get(comment.getUserId()));
                    e.onComplete();
                }catch (Exception e1){
                    e1.printStackTrace();
                }

            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<AVUser>() {
                    @Override
                    public void accept(@NonNull AVUser avUser) throws Exception {
                        holder.nickname.setText(avUser.getString("nickname"));
                        AVFile avFile = (AVFile)avUser.get("headImage");
                        Glide.with(context).load(avFile.getUrl()).into(holder.imageView);
                        holder.time.setText(comment.getTime());
                        holder.content.setText(comment.getContent());
                        if(comment.islz()){
                            Log.d(TAG,"comment.islz()");
                            holder.lz.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nickname,lz,time,content;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.reply_item_image);
            nickname = (TextView)itemView.findViewById(R.id.reply_item_nickname);
            lz = (TextView)itemView.findViewById(R.id.reply_item_islz);
            time = (TextView)itemView.findViewById(R.id.reply_item_time);
            content = (TextView)itemView.findViewById(R.id.reply_item_content);
        }
    }
}
