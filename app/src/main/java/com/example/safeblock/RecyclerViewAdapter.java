package com.example.safeblock;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Data> data;
    Dialog dialog;


    public RecyclerViewAdapter(Context mContenxt, List<Data> mData){
        this.context = mContenxt;
        this.data = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context). inflate(R.layout.list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_detail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        viewHolder.item_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_place_name = dialog.findViewById(R.id.place_name_dialog);
                TextView dialog_place_date = dialog.findViewById(R.id.place_visited_date_dialog);
                Button dialog_button_etherscan = dialog.findViewById(R.id.button_see_on_etherscan);
                Log.d("Data List = ",data.get(viewHolder.getAdapterPosition()).toString());
                dialog_place_name.setText(data.get(viewHolder.getAdapterPosition()).place_visited);
                dialog_place_date.setText(data.get(viewHolder.getAdapterPosition()).time_visited);

                dialog_button_etherscan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = "https://rinkeby.etherscan.io/tx/" + data.get(viewHolder.getAdapterPosition())._transactionHash;
                        Uri uri = Uri.parse(s);
                        context.startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    }
                });

                dialog.show();
            }
        });

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

        private ConstraintLayout item_history;
        private TextView tv_placeName;
        private TextView tv_date;
        private TextView tv_dateVisited;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_history = itemView.findViewById(R.id.list_item_id);

            tv_placeName = itemView.findViewById(R.id.tv_title);
            //tv_date = itemView.findViewById(R.id.tv_date);
            tv_dateVisited = itemView.findViewById(R.id.tv_description);

        }
    }
}
