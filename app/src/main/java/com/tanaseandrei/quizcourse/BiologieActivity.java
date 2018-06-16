package com.tanaseandrei.quizcourse;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.tanaseandrei.quizcourse.GeografieActivity.GeografiePoints;
import static com.tanaseandrei.quizcourse.GeografieActivity.scoreGeoGraphy;
import static com.tanaseandrei.quizcourse.IstoreActivity.Istoriascore;
import static com.tanaseandrei.quizcourse.IstoreActivity.QuizPoints;
import static com.tanaseandrei.quizcourse.IstoreActivity.istoriaPoints;
import static com.tanaseandrei.quizcourse.Login.userEmail;
import static com.tanaseandrei.quizcourse.PsihologieActivity.PshilogyPoints;
import static com.tanaseandrei.quizcourse.PsihologieActivity.scorePshilogy;
import static com.tanaseandrei.quizcourse.RomanaActivity.RomanaPoints;
import static com.tanaseandrei.quizcourse.RomanaActivity.romanaScore;

public class BiologieActivity extends AppCompatActivity {


    //url that gives questions arrays
    String url = "https://tanaseandrei0513.000webhostapp.com/encodeBiology.php";

    //ArrayList To Hold Questions
    ArrayList<Question> questionList;
    //SeekBar
    SeekBar seekBar;
    //Question question statement
    private TextView textViewQuestion;
    //Text Score
    private TextView textViewScore;
    //Text Counter Question
    private TextView textViewQuestionCount;
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
    public static int questionCountTotal;
    //Score
    public static int Biologyscore;
    //got points
    public static int BiologiePoints;

    //wrong answers
    public static int BiologieWrongAns;

    //final All Quizes Score
    public static int finalScore;
    //final Quizes points
    public static int finalAllQuizPoints;


    //Check Question is Answerd or not
    private boolean answered;
    //backPressed Time Variable
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biologie);

        getSupportActionBar().setTitle("Biologie Quiz");

        //Cast Variables
        textViewQuestion = (TextView) findViewById(R.id.text_view_question);
        textViewQuestionCount = (TextView) findViewById(R.id.text_view_question_count);
        textViewScore = (TextView) findViewById(R.id.text_view_score);
        rbGroup = (RadioGroup) findViewById(R.id.radio_group);
        rb1 = (RadioButton) findViewById(R.id.radio_button1);
        rb2 = (RadioButton) findViewById(R.id.radio_button2);
        rb3 = (RadioButton) findViewById(R.id.radio_button3);
        rb4 = (RadioButton) findViewById(R.id.radio_button4);
        buttonConfirmNext = (Button) findViewById(R.id.button_confirm_next);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


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


                //question arraylist
                questionList = new ArrayList<Question>();

                //loop to get get from server
                for (int i = 0; i < response.length(); i++) {

                    try {
                        //parsing jsonarray
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
                //check is array is empty

                if (questionList.isEmpty()) {
                    Toast.makeText(BiologieActivity.this, "Empty Array", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(BiologieActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //Show NextQuestion Method
                                Toast.makeText(BiologieActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });

                }


            }


        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

                //check network connection
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(BiologieActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BiologieActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(BiologieActivity.this);
        queue.add(request);

    }

    //Answer Check Method
    private void checkAnswer() {
        answered = true;

        //find the checked radio button
        RadioButton rbSelected = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());

        //getting slected index
        int answerNr = rbGroup.indexOfChild(rbSelected);


        if (answerNr == currentQuestion.getAnswerNr()) {
            Biologyscore++;
        }

        //if questioncounter is less than the questions in arraylist then move to next questions
        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }

    }

    //Next Question From ArrayList
    public void showNextQuestion() {


        //setting default color on radiobutons
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        //if question counter is less than the question question yotal -> i-e then get the
        //question at the question counter index
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            //setting the options and title of question
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            //increment counter
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Next");

            //increment seekvar
            seekBar.setProgress(questionCounter);
        } else {
            finishQuiz();
        }
    }

    //finish methid of quiz
    private void finishQuiz() {

        Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
        //diable views
        seekBar.setProgress(10);
        buttonConfirmNext.setEnabled(false);
        textViewQuestion.setEnabled(false);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        rb4.setEnabled(false);


        //alertdialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(BiologieActivity.this);
        dialog.setCancelable(false);

        //biology wrong answers
        BiologieWrongAns = questionCountTotal - Biologyscore;


        dialog.setTitle("Correct=" + Biologyscore + " Wrong=" + BiologieWrongAns);
        dialog.setMessage("Generate Result");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                //getting points of IstorialQuiz
                int pointPsy = 100 / questionCountTotal;
                BiologiePoints = Biologyscore * pointPsy;

                //Check Final Points:
                int AllQUizesPoints = istoriaPoints + RomanaPoints + GeografiePoints + PshilogyPoints + BiologiePoints;
                finalAllQuizPoints = AllQUizesPoints / 5;


                //check poinsts match criteria or not if yes then turn the layout into green and submit points to server
                if (finalAllQuizPoints <= 60) {

                    AlertDialog.Builder innserDialog = new AlertDialog.Builder(BiologieActivity.this);
                    innserDialog.setCancelable(false);
                    innserDialog.setTitle("Points are below Average ?");
                    innserDialog.setMessage("Do you like start Optional Socialogie Quiz");
                    innserDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                            Intent intent = new Intent(BiologieActivity.this, OptionalSocilogieQuizActivity.class);
                            startActivity(intent);
                            finish();


                        }
                    });
                    final AlertDialog alert = innserDialog.create();
                    alert.show();


                } else {

                    //if points >60 turn layout to green
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.biologieLayout);
                    layout.setBackgroundColor(Color.GREEN);

                    finalScore = Istoriascore + romanaScore + scoreGeoGraphy + scorePshilogy + Biologyscore;

                    AlertDialog.Builder innserDialog = new AlertDialog.Builder(BiologieActivity.this);
                    innserDialog.setCancelable(false);
                    innserDialog.setTitle("Final Result");

                    //Whole Wrong Anwers
                    int wholeWrongAnsers = IstoreActivity.istoreWrongAns + RomanaActivity.RomanaWrongAns +
                            PsihologieActivity.PsichlogieWrongAns +
                            GeografieActivity.geographieWrongAns +
                            BiologieActivity.BiologieWrongAns;

                    innserDialog.setMessage("Correct= " + finalScore + " Wrong= " + wholeWrongAnsers + " \nPoints= " + finalAllQuizPoints);
                    innserDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            //upload Points to Server
                            final ProgressDialog progressDialog = new ProgressDialog(BiologieActivity.this);
                            progressDialog.show();

                            String INSERT_QUIZ_POINTS_URL = "https://tanaseandrei0513.000webhostapp.com/insertQuizPoints.php";

                            //upload Points to Server
                            RequestQueue queue = Volley.newRequestQueue(BiologieActivity.this);

                            //Make A String Request
                            StringRequest request = new StringRequest(
                                    Request.Method.POST, INSERT_QUIZ_POINTS_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equalsIgnoreCase("SuccessFully Inserted")) {
                                        Toast.makeText(BiologieActivity.this, "Points are Saved", Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                        finish();

                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                    //If have any error if our url doesnt hit
                                    //catch responce

                                    progressDialog.dismiss();
                                    Toast.makeText(BiologieActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                                    ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                                    if (networkInfo == null) {
                                        Toast.makeText(BiologieActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(BiologieActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {

                                    //Initialze Map Class
                                    Map<String, String> params = new HashMap<String, String>();

                                    String p = String.valueOf(finalAllQuizPoints);

                                    //Setting KeyValue to data
                                    params.put("userEmail", userEmail);
                                    params.put("points", p);
                                    params.put("attempt", "1");


                                    return params;


                                }
                            };
                            //Add String request to Quuee
                            queue.add(request);


                        }
                    });

                    final AlertDialog alert = innserDialog.create();
                    alert.show();

                }


            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BiologieActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Are You Sure You Want to Logout ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(BiologieActivity.this, Login.class);
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
