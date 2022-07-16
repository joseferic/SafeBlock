package com.example.safeblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.safeblock.databinding.ActivityChooseModeBinding;

public class ChooseModeActivity extends AppCompatActivity {

    ActivityChooseModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.buttonDeveloper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ChooseModeActivity.this, LoginDeveloperActivity.class);
//                startActivity(intent);
//            }
//        });

        binding.buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseModeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.buttonDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseModeActivity.this, LoginDokterActivity.class);
                startActivity(intent);
            }
        });

    }
}