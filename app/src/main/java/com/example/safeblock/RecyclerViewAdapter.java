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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Data> data;
    Dialog dialog;
    user_data UserData;
    List<Data> otherUserData;

    public RecyclerViewAdapter(Context mContenxt, List<Data> mData, user_data UserData, List<Data> otherListData){
        this.context = mContenxt;
        this.data = mData;
        this.UserData = UserData;
        this.otherUserData = otherListData;
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
                Button dialog_button_sendEmail = dialog.findViewById(R.id.button_notifiy_other_user);

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

                dialog_button_sendEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (otherUserData.isEmpty()){
                            Toast.makeText(context,"Tidak ada user yang mengunjungi tempat ini",Toast.LENGTH_LONG);
                        }
                        else{
                            sendEmailtoOtherUser(UserData.email,data.get(viewHolder.getAdapterPosition()).place_visited, otherUserData);
                        }
                    }
                });

                dialog.show();
            }
        });

        return viewHolder;
    }

    public void sendEmailtoOtherUser(String emailUser,String placeName, List<Data> otherUserData){
        List<Data> emailFilteredSamePlaces = new ArrayList<>();
        for (int i=0; i<otherUserData.size(); i++){
            if (otherUserData.get(i).place_visited.equals(placeName)){
                emailFilteredSamePlaces.add(otherUserData.get(i));
            }
        }
        Set<String> email = new HashSet<>();
        for(final Data data: emailFilteredSamePlaces) {
            email.add(data.email);
        }

        // construct a new List from Set

        sendEmail(emailUser,placeName,email);
    }

    public void sendEmail(String emailUser,String placeName, Set<String> email){
        String subject = "PERINGATAN APLIKASI SAFEBLOCK: APLIKASI CONTACT TRACING BERBASIS BLOCKCHAIN";
        String message = "Peringatan: Seseorang dari tempat bernama " + placeName + " telah terinfeksi COVID-19."+
                "\nDimohon segera untuk melakukan pengujian COVID-19 dan juga isolasi mandiri.";

        String[] myArray = new String[email.size()];
        email.toArray(myArray);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,myArray);

        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        context.startActivity(Intent.createChooser(intent,"Mohon pilih aplikasi email"));

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
