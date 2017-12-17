package com.zia.gankcqupt_mvp.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zia.gankcqupt_mvp.Bean.Student;
import com.zia.gankcqupt_mvp.R;
import com.zia.gankcqupt_mvp.View.Activity.Page.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zia on 2017/5/19.
 */

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.Holder>  {

    private List<Student> list = new ArrayList<>();
    private RecyclerAdapter.RecyclerOnClickListener listener;
    private Context context;
    private static final String TAG = "FirstAdapterTest";

    public FirstAdapter(Context context){
        this.context = context;
    }

    public void setListener(RecyclerAdapter.RecyclerOnClickListener listener) {
        this.listener = listener;
    }

    public void reFresh(List<Student> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Student student = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                listener.onClick(student);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("student", student);
                intent.putExtra("isfour",true);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((Activity) context,
                                    new Pair[]{Pair.create(holder.layout, "card_info")});
                    ActivityCompat.startActivity(context, intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
        holder.number.setText(student.getStudentid());
        holder.college.setText(student.getMajor());
        holder.name.setText(student.getName());
        holder.classId.setText(student.getClassid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView name;
        TextView college;
        TextView number;
        TextView classId;
        CardView layout;

        public Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.first_name);
            college = itemView.findViewById(R.id.first_college);
            number = itemView.findViewById(R.id.first_id);
            classId = itemView.findViewById(R.id.first_classId);
            layout = itemView.findViewById(R.id.first_layout);
        }
    }
}
