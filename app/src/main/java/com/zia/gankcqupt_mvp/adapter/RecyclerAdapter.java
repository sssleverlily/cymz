package com.zia.gankcqupt_mvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.bean.Student;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.utils.API;
import com.zia.gankcqupt_mvp.utils.GlideUtil;
import com.zia.gankcqupt_mvp.view.Activity.Page.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/19.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private RecyclerOnClickListener listener;
    private List<Student> list = new ArrayList<>();
    public static boolean ISFOUR = false;
    private Context context;
    private static int itemWidth = 0;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public interface RecyclerOnClickListener {
        void onClick(Student student);
    }

    public void reFresh(List<Student> list, boolean isfour) {
        this.list = list;
        ISFOUR = isfour;
        notifyDataSetChanged();
    }

    public void setListener(RecyclerOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        if (itemWidth != 0) {
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            params.width = itemWidth;
            params.height = itemWidth * 640 / 480;
            holder.imageView.setLayoutParams(params);
        }
        final Student student = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClick(student);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("student", student);
                intent.putExtra("isfour", ISFOUR);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                        new Pair(holder.imageView, DetailActivity.ELEMENT)
                );
                ActivityCompat.startActivity(context, intent, options.toBundle());
            }
        });
        holder.tv_year.setText(student.getYear());
        holder.tv_name.setText(student.getName());
        holder.tv_college.setText(student.getCollege());
        String url;
        if (ISFOUR) {
            url = API.getInstance(context).getCET(student.studentid);
        } else {
            url = API.getInstance(context).getYKT(student.studentid);
        }
        GlideUtil.INSTANCE.loadthumbnail(context, holder.imageView, url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name, tv_year, tv_college;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            tv_college = (TextView) itemView.findViewById(R.id.tv_college);
        }
    }
}
