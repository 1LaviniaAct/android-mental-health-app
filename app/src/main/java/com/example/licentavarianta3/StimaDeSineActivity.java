package com.example.licentavarianta3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class StimaDeSineActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button nextBtn;

    int score_test1=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";
    int punctajTest1=0;

    //ma tot chinui la intent
    private ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stima_de_sine);

        initializareComponente();
    }

    private void initializareComponente() {
        //totalQuestionsTextView=findViewById(R.id.total_question);
        questionTextView=findViewById(R.id.question_test1);
        ansA=findViewById(R.id.dezacord_puternic);
        ansB=findViewById(R.id.partial_dezacord);
        ansC=findViewById(R.id.partial_acord);
        ansD=findViewById(R.id.acord_puternic);
        nextBtn=findViewById(R.id.next_btn);

        launcher=getLauncher();

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        //totalQuestionsTextView.setText("Total questions: "+totalQuestion);

        loadNewQuestion();


    }

    private ActivityResultLauncher<Intent> getLauncher() {
        ActivityResultCallback<ActivityResult> callback=getCeva();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),callback);
    }

    //de asta mai mult ca sigur n-am nevoie
    private ActivityResultCallback<ActivityResult> getCeva() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

            }
        };
    }

    private void loadNewQuestion() {
        if(currentQuestionIndex == totalQuestion){
            //aici cred ca ar trebui sa schimb, sa trimit prin Intent ceva, dar mai intai
//            Intent intent1=new Intent(getApplicationContext(), RezultatChestionarGraficActivity.class);
//            intent1.putExtra("punctaj_Test1",punctajTest1);
//            launcher.launch(intent1);//asta am adaugat doamne ajuta
            finishQuiz1();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
    }

    private void finishQuiz1() {
        String rezultat1="";
        if(punctajTest1<=20){
            rezultat1="stima de sine scazuta";
        }else{
            if(punctajTest1>=21&&punctajTest1<=30){
                rezultat1="stima de sine pertinenta";
            }
            else{
                rezultat1="stima de sine exacerbata";
            }
        }

        //new AlertDialog.Builder(this).setTitle(rezultat1).setMessage("punctajul este "+punctajTest1).show();
        //aici cred ca ar trebui sa schimb, sa trimit prin Intent ceva, dar mai intai incerc asa -->incerc sa rezolv acum

//        Intent intent1=new Intent(getApplicationContext(), RezultatChestionarGraficActivity.class);
//        intent1.putExtra("punctaj_Test1",punctajTest1);
        //setResult(RESULT_OK, intent1);
        //finish(); // închideți activitatea StimaDeSineActivity --> nu cred ca trebuie asta

        Intent intent=new Intent(getApplicationContext(), DeznadejdeActivity.class);
        intent.putExtra("punctaj_Test1",punctajTest1);
        launcher.launch(intent);
        //Toast.makeText(getApplicationContext(),String.valueOf(punctajTest1),Toast.LENGTH_LONG).show();
        //startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int culoare = getResources().getColor(R.color.roziu);
        ansA.setBackgroundColor(culoare);
        ansB.setBackgroundColor(culoare);
        ansC.setBackgroundColor(culoare);
        ansD.setBackgroundColor(culoare);

        Button clickedButton = (Button) view;

        if(clickedButton.getId()==R.id.next_btn){
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Trebuie să selectezi un răspuns", Toast.LENGTH_LONG).show();
                return;
            }
            else{
                if(selectedAnswer.equals("Dezacord puternic")){
                    punctajTest1+=1;
                }
                else{
                    if(selectedAnswer.equals("Parțial în dezacord")){
                        punctajTest1+=2;
                    }
                    else{
                        if(selectedAnswer.equals("Acord puternic")){
                            punctajTest1+=4;
                        }
                        else{
                            if(selectedAnswer.equals("Parțial de acord")){
                                punctajTest1+=3;
                            }
                        }
                    }
                }
            }

            currentQuestionIndex++;
            selectedAnswer = "";
            loadNewQuestion();
        }else{
            //inseamna ca a apasat pe una din cele 4 optiuni
            //selectedAnswer=clickedButton.getText().toString();
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
        }
    }
}