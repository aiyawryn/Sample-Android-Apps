package com.example.uibasic2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CheckBox checkBoxKimetsu, checkBoxSpy, checkBoxTokyo;
    private RadioGroup rgMaritalStatus;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxKimetsu = findViewById(R.id.checkboxKimetsu);
        checkBoxSpy = findViewById(R.id.checkboxSpy);
        checkBoxTokyo = findViewById(R.id.checkboxTokyo);

        rgMaritalStatus = findViewById(R.id.rgMaritalStatus);

        progressBar = findViewById(R.id.progressBar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    progressBar.incrementProgressBy(10);
                    SystemClock.sleep(500);
                }
            }
        });
        thread.start();

        int checkedButton = rgMaritalStatus.getCheckedRadioButtonId();
        if (checkedButton==R.id.rbMarried){
            Toast.makeText(MainActivity.this, "Married", Toast.LENGTH_SHORT).show();
        } else if (checkedButton==R.id.rbSingle) {
            Toast.makeText(MainActivity.this, "Single", Toast.LENGTH_SHORT).show();
        } else if (checkedButton==R.id.rbInrel) {
            Toast.makeText(MainActivity.this, "In a relationship", Toast.LENGTH_SHORT).show();
        }

        rgMaritalStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId==R.id.rbMarried){
                    Toast.makeText(MainActivity.this, "Married", Toast.LENGTH_SHORT).show();
                } else if (checkedId==R.id.rbSingle) {
                    Toast.makeText(MainActivity.this, "Single", Toast.LENGTH_SHORT).show();
                } else if (checkedId==R.id.rbInrel) {
                    Toast.makeText(MainActivity.this, "In a relationship", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBoxKimetsu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MainActivity.this,
                            "You have watched Kimetsu no Yaiba", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,
                            "You NEED to watch Kimetsu no Yaiba",Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBoxSpy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainActivity.this,
                            "You have watched Spy Family", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,
                            "You NEED to watch Spy Family", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkBoxTokyo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainActivity.this,
                            "You have watched Tokyo Revengers", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,
                            "You NEED to watch Tokyo Revengers", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}