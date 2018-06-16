package com.tanaseandrei.quizcourse;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static com.tanaseandrei.quizcourse.IstoreActivity.QuizPoints;

public class RomanaActivity extends AppCompatActivity {


    String url = "https://tanaseandrei0513.000webhostapp.com/encodeRomana.php";

    //ArrayList To Hold Questions
    ArrayList<Question> questionList;
    //SeekBar
    SeekBar seekBar;
    //file to store score of quiz
    public static final String EXTRA_SCORE = "extraScore";
    //Question question statement
    private TextView textViewQuestion;
    //Text Score
    private TextView textViewScore;
    //Text Counter Question
    private TextView textViewQuestionCount;
    //Counter TextView
    private TextView textViewCountDown;
    //Radio Group
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    //Current Question
    Question currentQuestion;
    //Confirm Button
    private Button buttonConfirmNext;
    //COlor
    private ColorStateList textColorDefaultRb;
    //Question Counter
    private int questionCounter;
    //Question Total in ListArray
    private int questionCountTotal;

    //wrong answers
    public static int RomanaWrongAns;

    //Score
    public static int romanaScore;
    //got points
    public static int RomanaPoints;

    //Check Question is Answerd or not
    private boolean answered;
    //backPressed Time Variable
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romana);

        getSupportActionBar().setTitle("Romana Quiz");

        //Cast Variables
        textViewQuestion = (TextView) findViewById(R.id.text_view_question);
//        textViewCountDown = (TextView) findViewById(R.id.txtCountDown);
        textViewQuestionCount = (TextView) findViewById(R.id.text_view_question_count);
        textViewScore = (TextView) findViewById(R.id.text_view_score);
        rbGroup = (RadioGroup) findViewById(R.id.radio_group);
        rb1 = (RadioButton) findViewById(R.id.radio_button1);
        rb2 = (RadioButton) findViewById(R.id.radio_button2);
        rb3 = (RadioButton) findViewById(R.id.radio_button3);
        rb4 = (RadioButton) findViewById(R.id.radio_button4);
        buttonConfirmNext = (Button) findViewById(R.id.button_confirm_next);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        int score = getIntent().getIntExtra("score", 0);


        //Getting the color of radio Button
        textColorDefaultRb = rb1.getTextColors();

        //Load Question Method from server
        loadServerQuestions();

    }

    private void loadServerQuestions() {

        //Parse Json Array of Questions
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                questionList = new ArrayList<Question>();

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String q = object.getString("question");
                        String option1 = object.getString("option1");
                        String option2 = object.getString("option2");
                        String option3 = object.getString("option3");
                        String option4 = object.getString("option4");

                        int answer_no = Integer.parseInt(object.getString("answer"));

                        //Add Server Questions to Arraylist
                        questionList.add(new Question(q, option1, option2, option3, option4, answer_no));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (questionList.isEmpty()) {
                    Toast.makeText(RomanaActivity.this, "Empty Array", Toast.LENGTH_SHORT).show();
                } else {

                    //getting the Size of Array
                    questionCountTotal = questionList.size();
                    //shuffle the Whole ArrayList of Questions that user will random Questions
                    Collections.shuffle(questionList);

                    //Show NextQuestion to User
                    showNextQuestion();

                    //Conform Button
                    buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Check If answerd
                            if (!answered) {
                                //check which button is select
                                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                                    //check answer and showNextQuestions Methods will run
                                    checkAnswer();
                                    showNextQuestion();
                                } else {
                                    Toast.makeText(RomanaActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //Show NextQuestion Method
                                Toast.makeText(RomanaActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });

                }


            }


        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(RomanaActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RomanaActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(RomanaActivity.this);
        queue.add(request);

    }

    //Answer Check Method
    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbSelected);

        if (answerNr == currentQuestion.getAnswerNr()) {
            romanaScore++;

        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }

    }

    //Next Question From ArrayList
    public void showNextQuestion() {

        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Next");
            seekBar.setProgress(questionCounter);
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
        seekBar.setProgress(10);
        buttonConfirmNext.setEnabled(false);
        textViewQuestion.setEnabled(false);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        rb4.setEnabled(false);


        AlertDialog.Builder dialog = new AlertDialog.Builder(RomanaActivity.this);
        dialog.setCancelable(false);


        //getting points of IstorialQuiz
        int p3 = 100 / questionCountTotal;
        RomanaPoints = romanaScore * p3;

        RomanaWrongAns = questionCountTotal - romanaScore;
        dialog.setTitle("Correct=" + romanaScore + " Wrong=" + RomanaWrongAns + "\nPoints =" + RomanaPoints);


        dialog.setMessage("Start Geografie Quiz ");
        dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(RomanaActivity.this, GeografieActivity.class);
                intent.putExtra("score", romanaScore);
                startActivity(intent);
                finish();

            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();

        //showSolution();
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra(EXTRA_SCORE, score);
//        setResult(RESULT_OK, resultIntent);
//        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RomanaActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Are You Sure You Want to Logout ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(RomanaActivity.this, Login.class);
                startActivity(intent);
                finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();

    }
}
