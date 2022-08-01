package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InputDataTestCovidActivity extends AppCompatActivity {

    private static final String TAG = "InputDataTestCovidActivity";
    public final String contractAddress = "0xBb5a99B6B526671FE500e48FFf99aFCfFa88EBb3";

    MaterialEditText date_in;
    MaterialEditText time_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_test_covid);
        // TextView tvTestDataIntent = findViewById(R.id.tv_data);
        Button btnSendEther = findViewById(R.id.button_confirmTesTCovidData);
        Spinner spinner = findViewById(R.id.spinner);
        date_in = findViewById(R.id.input_date);
        time_in = findViewById(R.id.input_time);

        //Get Data From Prev Activity
        user_data UserData = getIntent().getParcelableExtra("UserData");
        Dokter dokter = getIntent().getParcelableExtra("DokterData");

        // Pilih Jenis Tes Covid
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.JeniSTesCovid, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String text = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Pilih Tanggal
        date_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(date_in);
            }
        });

        // Pilih Jam
        time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(time_in);
            }
        });





        btnSendEther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String priceRupiah;
                String priceEther;
                String priceWei;
                if(spinner.getSelectedItem().toString().equals("Tes Cepat Molekuler")){
                    priceRupiah = "Rp1.300.000,00";
                    priceEther = "0.065 Ether";
                    priceWei = "65000000000000000";
                } else if(spinner.getSelectedItem().toString().equals("Polymerase Chain Reaction")){
                    priceRupiah = "Rp300.000,00";
                    priceEther = "0.015 Ether";
                    priceWei = "15000000000000000";
                } else if(spinner.getSelectedItem().toString().equals("Rapid Test Antigen")){
                    priceRupiah = "Rp100.000,00";
                    priceEther = "0.005 Ether";
                    priceWei = "5000000000000000";
                } else if(spinner.getSelectedItem().toString().equals("Rapid Test Antibodi")){
                    priceRupiah = "Rp150.000,00";
                    priceEther = "0.007 Ether";
                    priceWei = "7000000000000000";
                } else{
                    priceRupiah = null;
                    priceEther = null;
                    priceWei = null;
                }

                Intent intent = new Intent(InputDataTestCovidActivity.this, ConfirmTestDataActivity.class);
                intent.putExtra("UserData",UserData);
                intent.putExtra("DokterData",dokter);
                intent.putExtra("TestType",spinner.getSelectedItem().toString());
                intent.putExtra("Date",date_in.getText().toString());
                intent.putExtra("Time",time_in.getText().toString());

                intent.putExtra("PriceRupiah", priceRupiah);
                intent.putExtra("PriceEther", priceEther);
                intent.putExtra("PriceWei", priceWei);
                startActivity(intent);
                // payTestCovid(UserData.privateKey, dokter.name,dokter.publicAddress,"1000000000000000000");
            }
        });

    }

    private void showTimeDialog(MaterialEditText time_in) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(new java.sql.Date(calendar.getTime().getTime())));
            }
        };

        new TimePickerDialog(InputDataTestCovidActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDateDialog(MaterialEditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date_in.setText(simpleDateFormat.format(new java.sql.Date(calendar.getTime().getTime())));
            }
        };

        new DatePickerDialog(InputDataTestCovidActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


    }
}