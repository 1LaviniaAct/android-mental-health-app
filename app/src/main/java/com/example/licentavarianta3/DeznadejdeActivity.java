package com.example.licentavarianta3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeznadejdeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestionsTextView;
    TextView questionTextView;
     Button ansA, ansB;
    Button nextBtn;
    //int score_test2=0;
    int totalQuestion=QuestionAnswerDeznadejde.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";
    int punctajTest2=0;

    //modificare
    int punctajStima=0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deznadejde);

        initializare();
    }

    private void initializare() {
        //totalQuestionsTextView=findViewById(R.id.total_question2);
        questionTextView=findViewById(R.id.question_test2);
        ansA=findViewById(R.id.adevarat_deznadejde);
        ansB=findViewById(R.id.fals_deznadejde);
        nextBtn=findViewById(R.id.next_btn2);

        intent=getIntent();
        punctajStima=intent.getIntExtra("punctaj_Test1",0);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        //totalQuestionsTextView.setText("Total questions: "+totalQuestion);

        //Toast.makeText(getApplicationContext(),"aaaaa"+String.valueOf(punctajStima),Toast.LENGTH_LONG).show();

        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }

        String questionText = QuestionAnswerDeznadejde.question[currentQuestionIndex];
        Log.d("DeznadejdeActivity", "Intrebare incarcata: " + questionText);

        questionTextView.setText(QuestionAnswerDeznadejde.question[currentQuestionIndex]);

    }

    private void finishQuiz() {
        String passStatus="";
        if(punctajTest2>=0 && punctajTest2<=3){
            passStatus="grad de deznadejde nul sau minim";
        }

        if(punctajTest2>=4 && punctajTest2<=8){
            passStatus="grad de deznadejde usor sau slab";
        }

        if(punctajTest2>=9 && punctajTest2<=14){
            passStatus="grad moderat";
        }
        if(punctajTest2>15){
            passStatus="grad sever";
        }

        Log.d("DeznadejdeActivity", "Test finalizat. Rezultat: " + passStatus + ", Punctaj: " + punctajTest2);

        //new AlertDialog.Builder(this).setTitle(passStatus).setMessage("punctajul este "+punctajTest2).show();

        Intent intent2=new Intent(getApplicationContext(), EmotivitateActivity.class);
        intent2.putExtra("punctaj_Test1",punctajStima);
        intent2.putExtra("punctaj_Test2",punctajTest2);
        startActivity(intent2);//daca nu merge asa, baga launch
    }

    @Override
    public void onClick(View view) {
        int culoare = getResources().getColor(R.color.roziu);
        ansA.setBackgroundColor(culoare);
        ansB.setBackgroundColor(culoare);


        Button clickedButton=(Button) view;

        Log.d("DeznadejdeActivity", "Buton apasat: " + clickedButton.getText().toString());

        if(clickedButton.getId()==R.id.next_btn2){
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Trebuie să selectezi un răspuns", Toast.LENGTH_LONG).show();
                return;
            }
            else{
                if(selectedAnswer.equals(QuestionAnswerDeznadejde.correctAnswers[currentQuestionIndex])){
                    punctajTest2++;
                    Log.d("DeznadejdeActivity", "Punctaj test: " + punctajTest2);
                }
            }
            currentQuestionIndex++;
            selectedAnswer = "";
            loadNewQuestion();

        }else{
            //inseamna ca a apasat pe una din cele 4 optiuni
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);

            Log.d("DeznadejdeActivity", "Raspuns selectat: " + selectedAnswer);
        }
    }
}