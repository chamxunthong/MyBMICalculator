package com.example.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float fltWeight = Float.parseFloat(etWeight.getText().toString());
                Float fltHeight = Float.parseFloat(etHeight.getText().toString());
                Float fltBMI = fltWeight/(fltHeight*fltHeight);

                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH)+"/"+
                        (now.get(Calendar.MONTH)+1)+"/"+
                        now.get(Calendar.YEAR)+" "+
                        now.get(Calendar.HOUR_OF_DAY)+":"+
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date: "+ datetime);
                tvBMI.setText("Last Calculated BMI: " + fltBMI);
                etWeight.setText(" ");
                etHeight.setText(" ");

                String outcome ="";
                if (fltBMI<18.5){
                    outcome = "You are underweight";
                }
                else if(fltBMI<25){
                    outcome = "Your BMI is normal";
                }
                else if (fltBMI<30){
                    outcome = "You are overweight";
                }
                else{
                    outcome = "You are obese";
                }
                tvOutcome.setText(outcome);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor preEdit = prefs.edit();

                preEdit.putFloat("Weight", fltWeight);
                preEdit.putFloat("Height", fltHeight);
                preEdit.putString("Date", "Last Calculated Date: " + datetime);
                preEdit.putString("BMI", "Last Calculated BMI: " + fltBMI);
                preEdit.putString("Outcome", outcome);

                preEdit.commit();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor preEdit = prefs.edit();

                preEdit.putFloat("Weight", 0);
                preEdit.putFloat("Height", 0);
                preEdit.putString("Date", "Last Calculated Date: " + " ");
                preEdit.putString("BMI", "Last Calculated BMI: " + " ");

                preEdit.commit();

                etWeight.setText(" ");
                etHeight.setText(" ");
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
                tvOutcome.setText(" ");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float weight = prefs.getFloat("Weight", 0);
        Float height = prefs.getFloat("Height", 0);
        String date = prefs.getString("Date", " ");
        String bmi = prefs.getString("BMI", "Last Calculated BMI: 0.0");
        String outcome = prefs.getString("Outcome", " ");

        tvDate.setText(date);
        tvBMI.setText(bmi);
        tvOutcome.setText(outcome);
    }
}
