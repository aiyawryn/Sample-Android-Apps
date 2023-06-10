package com.example.uibasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    private EditText editTxtName;
    private TextView txtHello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHello = findViewById(R.id.btnHello);
        btnHello.setOnClickListener(this);

        editTxtName = findViewById(R.id.editTxtName);
        txtHello = findViewById(R.id.txtHello);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnHello){
            Toast.makeText(this, "Hello Button Clicked",
                    Toast.LENGTH_SHORT).show();
            txtHello.setText("Hello " + editTxtName.getText().toString());
        }
    }
}