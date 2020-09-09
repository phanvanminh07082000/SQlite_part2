package com.example.sqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqlite.R;
import com.example.sqlite.database.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edName;
    private EditText edDes;
    private Button btRegister;
    private Spinner spinner;
    private DBHelper db;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        db = new DBHelper(this);
        db.getReadableDatabase();
    }

    private void initView() {
        edName = (EditText) findViewById(R.id.edUser);
        edDes = (EditText) findViewById(R.id.edDes);
        btRegister = (Button) findViewById(R.id.btRegister);
        checkBox = (CheckBox) findViewById(R.id.ck);
        btRegister.setOnClickListener(this);

        String[] genders = {"Male", "Female", "Unknow"};
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btRegister){
            onRegister();
        }
    }

    private void onRegister() {
        if (edName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkBox.isChecked()){
            Toast.makeText(this, "Please agree rules", Toast.LENGTH_SHORT).show();
            return;
        }

        String isAdd = db.addUser(edName.getText().toString(),
                spinner.getSelectedItem().toString(),
                edDes.getText().toString());
        Toast.makeText(this, isAdd, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this,ListUserAct.class);
        startActivity(intent);
    }
}