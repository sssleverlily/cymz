package com.zia.gankcqupt_mvp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private RecyclerOnClickListener listener;
    private Context context;
    private static final String TAG = "FirstAdapterTest";

    public FirstAdapter(Context context){
        this.context = context;
    }

    public void setListener(RecyclerOnClickListener listener){
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
                /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,
                        new Pair(holder.itemView, DetailActivity.ELEMENT)
                );
                ActivityCompat.startActivity(context, intent, options.toBundle());*/
                context.startActivity(intent);
            }
        });
        holder.number.setText(student.getStudentid());
        holder.college.setText(student.getCollege());
        holder.name.setText(student.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView name;
        TextView college;
        TextView number;
        LinearLayout layout;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.first_name);
            college = (TextView)itemView.findViewById(R.id.first_college);
            number = (TextView)itemView.findViewById(R.id.first_id);
            layout = (LinearLayout)itemView.findViewById(R.id.first_layout);
        }
    }
}
