package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClick(View view){

        EditText edtTxName = findViewById(R.id.edTxtName);
        EditText edtTxLastName = findViewById(R.id.edTxtLastName);
        EditText edtTxEmail = findViewById(R.id.edtTxtEmail);

        TextView txtName  = findViewById(R.id.txtMsgName);
        TextView txtLastName  = findViewById(R.id.txtMsgLastName);
        TextView txtEmail  = findViewById(R.id.txtMsgEmail);

        txtName.setText("Name: " + edtTxName.getText().toString());
        txtLastName.setText("Last Name: " + edtTxLastName.getText().toString());
        txtEmail.setText("Email: " + edtTxEmail.getText().toString());
    }
}