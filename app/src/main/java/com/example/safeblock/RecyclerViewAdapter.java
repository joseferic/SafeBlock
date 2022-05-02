package com.example.safeblock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Data> data;

    public RecyclerViewAdapter(Context mContenxt, List<Data> mData){
        this.context = mContenxt;
        this.data = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_placeName.setText(data.get(position).place_visited);
        //holder.tv_date.setText(data.get(position).time_visited);
        holder.tv_dateVisited.setText(data.get(position).time_visited);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_placeName;
        private TextView tv_date;
        private TextView tv_dateVisited;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_placeName = itemView.findViewById(R.id.tv_title);
            //tv_date = itemView.findViewById(R.id.tv_date);
            tv_dateVisited = itemView.findViewById(R.id.tv_description);

        }
    }
}
