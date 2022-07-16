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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<User> data;
    Dialog dialog;
    String privateKeyAdmin;

    public UserRecyclerViewAdapter(Context mContenxt, List<User> mData,String privateKeyAdmin) {
        this.context = mContenxt;
        this.data = mData;
        this.privateKeyAdmin = privateKeyAdmin;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.list_username, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_detail_username);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        viewHolder.item_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_place_name = dialog.findViewById(R.id.place_name_dialog);
                TextView dialog_place_address = dialog.findViewById(R.id.place_visited_date_dialog);
                Button dialog_button_changeUserStatus = dialog.findViewById(R.id.button_change_user_status);
                Button dialog_button_sendEmail = dialog.findViewById(R.id.button_notifiy_other_user);

                Log.d("Data List = ", data.get(viewHolder.getAdapterPosition()).toString());
                dialog_place_name.setText(data.get(viewHolder.getAdapterPosition()).name);
                if(!(data.get(viewHolder.getAdapterPosition()).status)){
                    dialog_place_address.setText("Negatif COVID-19");
                }
                else {
                    dialog_place_address.setText("Positif COVID-19");
                }


                dialog_button_changeUserStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeStateUser(data.get(viewHolder.getAdapterPosition()).name,data.get(viewHolder.getAdapterPosition()).status);
                    }
                });
                dialog.show();
            }
        });

        return viewHolder;
    }

    public void changeStateUser(String username,Boolean statusUser){
        final Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/v3/d9100bd917c6448695785e26e5f0a095"
                )
        );
        String contractAddress = "0x7530003161c4F2dcA9Ff994144Fa4fCC5a2d57F2";
        String privateKey = privateKeyAdmin;

        Credentials credentials = Credentials.create(privateKey);

        Log.v("Data length list", Credentials.create(privateKey).getAddress().trim());
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        NewSafeBlock_sol_NewSafeBlock contract = NewSafeBlock_sol_NewSafeBlock.load(contractAddress, web3j, credentials, contractGasProvider);
        try {
            Boolean newStatusUser = !statusUser;
            contract.updateStatusInfected(username,newStatusUser).sendAsync().join();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

//    public void sendEmailtoOtherUser(String emailUser,String placeName, List<Data> otherUserData){
//        List<Data> emailFilteredSamePlaces = new ArrayList<>();
//        for (int i=0; i<otherUserData.size(); i++){
//            if (otherUserData.get(i).place_visited.equals(placeName)){
//                emailFilteredSamePlaces.add(otherUserData.get(i));
//            }
//        }
//        Set<String> email = new HashSet<>();
//        for(final Data data: emailFilteredSamePlaces) {
//            email.add(data.email);
//        }
//
//        // construct a new List from Set
//
//        sendEmail(emailUser,placeName,email);
//    }

//    public void sendEmail(String emailUser,String placeName, Set<String> email){
//        String subject = "PERINGATAN APLIKASI SAFEBLOCK: APLIKASI CONTACT TRACING BERBASIS BLOCKCHAIN";
//        String message = "Peringatan: Seseorang dari tempat bernama " + placeName + " telah terinfeksi COVID-19."+
//                "\nDimohon segera untuk melakukan pengujian COVID-19 dan juga isolasi mandiri.";
//
//        String[] myArray = new String[email.size()];
//        email.toArray(myArray);
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_EMAIL,myArray);
//
//        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
//        intent.putExtra(Intent.EXTRA_TEXT,message);
//
//        intent.setType("message/rfc822");
//        context.startActivity(Intent.createChooser(intent,"Mohon pilih aplikasi email"));
//
//    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_adminName.setText(data.get(position).name);
        //holder.tv_date.setText(data.get(position).time_visited);
        if(data.get(position).status == false){

            holder.tv_statusUser.setText("Negatif COVID-19");
        }
        else {
            holder.tv_statusUser.setText("Positif COVID-19");
        }
        
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout item_history;
        private TextView tv_adminName;
        private TextView tv_date;
        private TextView tv_statusUser;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_history = itemView.findViewById(R.id.list_item_id);

            tv_adminName = itemView.findViewById(R.id.tv_title);
            //tv_date = itemView.findViewById(R.id.tv_date);
            tv_statusUser = itemView.findViewById(R.id.tv_description);

        }
    }
}
