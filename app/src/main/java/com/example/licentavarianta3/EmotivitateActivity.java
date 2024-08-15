package com.example.licentavarianta3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmotivitateActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB;
    Button nextBtn;
    int totalQuestion=QuestionAnswerEmotivitate.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";
    int punctajTest3=0;

    //modificare
    int punctajStima=0;
    int punctajDeznadejde=0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotivitate);

        initializare();

    }

    private void initializare() {
        //totalQuestionsTextView=findViewById(R.id.total_question3);
        questionTextView=findViewById(R.id.question_test3);

        ansA=findViewById(R.id.da);
        ansB=findViewById(R.id.nu);
        nextBtn=findViewById(R.id.next_btn3);

        intent=getIntent();
        punctajStima=intent.getIntExtra("punctaj_Test1",0);
        punctajDeznadejde=intent.getIntExtra("punctaj_Test2",0);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        //totalQuestionsTextView.setText("Total questions: "+totalQuestion);

        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if(currentQuestionIndex==totalQuestion){
            finishQuiz();
            return;
        }

        String questionText=QuestionAnswerEmotivitate.question[currentQuestionIndex];

        questionTextView.setText(QuestionAnswerEmotivitate.question[currentQuestionIndex]);
    }

    private void finishQuiz() {
        String passStatus="";
        if(punctajTest3<7){
            passStatus="incordat si crispat";
        }
        else{
            if(punctajTest3>=8 && punctajTest3<=16){
                passStatus="stii cum sa iti exteriorizezi emotiile, dar ai dificultati in a face acest lucru";
            }
            else{
                passStatus="atitudinea fata de emotii este una sanatoasa";
            }
        }

        //new AlertDialog.Builder(this).setTitle(passStatus).setMessage("punctajul este "+punctajTest3).show();

        //16 aprilie
        Intent intent3=new Intent(getApplicationContext(), RezultatChestionarGraficActivity.class);
        intent3.putExtra("punctaj_Test1",punctajStima);
        intent3.putExtra("punctaj_Test2",punctajDeznadejde);
        intent3.putExtra("punctaj_Test3",punctajTest3);
        startActivity(intent3);
    }

    @Override
    public void onClick(View view) {
        int culoare = getResources().getColor(R.color.roziu);
        ansA.setBackgroundColor(culoare);
        ansB.setBackgroundColor(culoare);

        Button clickedButton=(Button) view;

        if(clickedButton.getId()==R.id.next_btn3){
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Trebuie să selectezi un răspuns", Toast.LENGTH_LONG).show();
                return;
            }
            else{
                if(selectedAnswer.equals(QuestionAnswerEmotivitate.correctAnswers[currentQuestionIndex])){
                    punctajTest3++;
                }
            }
            currentQuestionIndex++;
            selectedAnswer = "";
            loadNewQuestion();
        }else{
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
        }

    }
}