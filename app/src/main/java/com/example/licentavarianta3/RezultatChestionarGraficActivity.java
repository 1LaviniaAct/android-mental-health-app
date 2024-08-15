package com.example.licentavarianta3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.licentavarianta3.Services.SendDataTask;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

//import com.anychart.AnyChart;
//import com.anychart.AnyChartView;
//import com.anychart.chart.common.dataentry.DataEntry;
//import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class RezultatChestionarGraficActivity extends AppCompatActivity {

//    TextView textView;
//    TextView textView2;
//    TextView textView3;
    int punctajStima;
    int punctajDeznadejde;
    int punctajEmotivitate;
    Intent intent;

    //String[] stima={"stima de sine"};

    //incercarea a 3-a de grafic
    TextView tvStima;
    TextView tvDeznadejde;
    TextView tvEmotivitate;
    PieChart pieChart,pieChart2,pieChart3;
    Button viewRecomandari;

    Button buton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat_chestionar_grafic);
//        textView=findViewById(R.id.textViewProba);
//        textView3=findViewById(R.id.textViewProba3);
//        textView2=findViewById(R.id.textViewProba2);
        intent = getIntent();
        if(intent != null) {
           punctajStima = intent.getIntExtra("punctaj_Test1",0);
           punctajDeznadejde=intent.getIntExtra("punctaj_Test2",0);
           punctajEmotivitate=intent.getIntExtra("punctaj_Test3",0);
//            textView.setText("Punctajul testului de stima de sine este: " + punctajStima);
//            textView2.setText("Punctajul testului de deznadejde este: " + punctajDeznadejde);
//            textView3.setText("Punctajul testului de emotivitate este: " + punctajEmotivitate);
        }

        // Obține ID-ul utilizatorului logat din SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", ""); // Cheia ar trebui să fie aceeași cu cea folosită la salvare
        Log.d("USEEER ID: ", userId);
        // Apelează SendDataTask pentru a trimite datele către server
        new SendDataTask(punctajStima, punctajDeznadejde, punctajEmotivitate, userId).execute();



        tvStima=findViewById(R.id.tvR);
        tvDeznadejde=findViewById(R.id.tvDeznadejde);
        tvEmotivitate=findViewById(R.id.tvEmpatie);
        pieChart=findViewById(R.id.piechart);
        pieChart2=findViewById(R.id.piechart2);
        pieChart3=findViewById(R.id.piechart3);
        setData();

        // Inițializare buton
        //viewRecomandari = findViewById(R.id.viewRecomandari);
        buton=findViewById(R.id.viewRecomandariProprii);
        // Adăugare OnClickListener pentru buton
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecționare către activitatea de recomandări
                Intent intent = new Intent(RezultatChestionarGraficActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setData(){
        int punctaj=(punctajStima*100)/40;
        int diferenta;
        if(punctajStima>=40){
            diferenta=0;
        }
        else{
            diferenta=100-punctaj;
        }

        int punctaj2=(punctajDeznadejde*100)/20;
        int diferenta2;
        if(punctajDeznadejde>=20){
            diferenta2=0;
        }
        else{
            diferenta2=100-punctaj2;
        }

        int punctaj3=(punctajEmotivitate*100)/30;
        int diferenta3;
        if(punctajEmotivitate>=30){
            diferenta3=0;
        }
        else{
            diferenta3=100-punctaj3;
        }



        //setare procentaj
        tvStima.setText(Integer.toString(punctaj));
        tvEmotivitate.setText(Integer.toString(punctaj3));
        tvDeznadejde.setText(Integer.toString(punctaj2));

        //setare data and color to the pie chart
//        pieChart.addPieSlice(new PieModel("stima de sine",Integer.parseInt(tvStima.getText().toString()),Color.parseColor("#EF5350")));
//        pieChart.addPieSlice(new PieModel("maxim stima de sine",Integer.parseInt(String.valueOf(100-((punctajStima*100)/40)))),Color.parseColor("#29B6F6"));
        pieChart.addPieSlice(new PieModel("Stima de sine", punctaj, Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Maxim stima de sine", diferenta, Color.parseColor("#ededf2")));

        //ca sa animam pie chart-ul-->ce o fi si asta?
        pieChart.startAnimation();

        //pie chart 2
        pieChart2.addPieSlice(new PieModel("Deznădejde",punctaj2,Color.parseColor("#FFA726")));
        pieChart2.addPieSlice(new PieModel("Maxim deznădejde",diferenta2,Color.parseColor("#ededf2")));
        pieChart2.startAnimation();

        //pie chart 3
        pieChart3.addPieSlice(new PieModel("Empatie",punctaj3,Color.parseColor("#66BB6A")));
        pieChart3.addPieSlice(new PieModel("Maxim empatie",diferenta3,Color.parseColor("#ededf2")));
        pieChart3.startAnimation();
    }



}