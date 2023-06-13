package com.example.listviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView citiesList;
    private Spinner studentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citiesList = findViewById(R.id.citiesList);
        studentSpinner = findViewById(R.id.studentSpinner);

        // Strings.xml で定義
//        final ArrayList<String> students = new ArrayList<>();
//        students.add("Bejimaru");
//        students.add("Lisa");
//        students.add("Rose");
//        students.add("Jennie");
//        students.add("Anya");
//
//        ArrayAdapter<String> studentsAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_dropdown_item,
//                students
//        );
//        studentSpinner.setAdapter(studentsAdapter);

        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this,studentSpinner.getSelectedItem().toString() +
                        " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayList<String> cities = new ArrayList<>();
        cities.add("Tokyo");
        cities.add("Osaka");
        cities.add("Kyoto");
        cities.add("Hokkaido");
        cities.add("Hiroshima");

        // Fetch the data to List view
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cities
        );
        // pass Adapter
        citiesList.setAdapter(citiesAdapter);

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this, cities.get(position) +
                        " selected", Toast.LENGTH_SHORT).show();
            }
        });

    }
}