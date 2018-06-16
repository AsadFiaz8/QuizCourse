package com.tanaseandrei.quizcourse;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.tanaseandrei.quizcourse.Login.userEmail;
import static com.tanaseandrei.quizcourse.R.id.txtEmail;
import static com.tanaseandrei.quizcourse.SingleQuizBiologieActivity.Bilogiescore;
import static com.tanaseandrei.quizcourse.SingleQuizBiologieActivity.checkBiologyPoints;
import static com.tanaseandrei.quizcourse.SingleQuizGeographyActivity.Geographyscore;
import static com.tanaseandrei.quizcourse.SingleQuizGeographyActivity.checkGeographyPoints;
import static com.tanaseandrei.quizcourse.SingleQuizIstoreActivity.IstoriaSingleScoreActivity;
import static com.tanaseandrei.quizcourse.SingleQuizIstoreActivity.checkIstorePoints;
import static com.tanaseandrei.quizcourse.SingleQuizPsucholgieActivity.Psycholgiescore;
import static com.tanaseandrei.quizcourse.SingleQuizPsucholgieActivity.checkPsychologyPoints;
import static com.tanaseandrei.quizcourse.SingleQuizRomana.Romanascore;
import static com.tanaseandrei.quizcourse.SingleQuizRomana.checkRomanaPoints;

public class UserGradesGenerateActivity extends AppCompatActivity {


    //ShardPrefences to Hold Points
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static int finalAllSingleQuizPoints;
    public static int istoriaPoints, istoriaWrong;
    public static int romanaPoints, romanaWrong;
    public static int geographyPoints, geographyWrong;
    public static int psychologyPoints, psychologyWrong;
    public static int BiologyPoints, biologyWrong;

    //Classess
    TextView txtCorrect, txtPoints, txtAttempts, txtWrong;

    //Wrong Count
    public static int allWrongCount;

    //Check crieteria
    public static int checkCrateriaPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_grades_generate);

        getSupportActionBar().setTitle("Gemerate Grades");

        txtAttempts = (TextView) findViewById(R.id.txtAttempt);
        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtWrong = (TextView) findViewById(R.id.txtWrong);
        txtCorrect = (TextView) findViewById(R.id.txtCorrect);


        if (checkIstorePoints && checkRomanaPoints && checkGeographyPoints && checkPsychologyPoints && checkBiologyPoints) {
            preferences = this.getSharedPreferences("quiz_points", MODE_PRIVATE);

            //Points of All Random Actviites
            istoriaPoints = preferences.getInt("istoriaPoints", 0);
            romanaPoints = preferences.getInt("RomanaPoints", 0);
            geographyPoints = preferences.getInt("geographyPoints", 0);
            psychologyPoints = preferences.getInt("pscychoPoints", 0);
            BiologyPoints = preferences.getInt("biologiePoints", 0);

            //Wrong Ansers of All Activities
            istoriaWrong = preferences.getInt("istoriaWrong", 0);
            romanaWrong = preferences.getInt("romanaWrong", 0);
            geographyWrong = preferences.getInt("geographyWrong", 0);
            psychologyWrong = preferences.getInt("psychoWrong", 0);
            biologyWrong = preferences.getInt("biologieWrong", 0);

            //wrong ans
            allWrongCount = istoriaWrong + romanaWrong + geographyWrong + psychologyWrong + biologyWrong;

            //check points values for grad
            checkCrateriaPoint = istoriaPoints + romanaPoints + geographyPoints + psychologyPoints + BiologyPoints;
            finalAllSingleQuizPoints = checkCrateriaPoint / 5;

            //if less than 60 Launch Optional quiz else satys in and submit points to server
            if (finalAllSingleQuizPoints <= 60) {


                Toast.makeText(this, "Start Optional Quiz", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserGradesGenerateActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("Points are below Average ?");
                dialog.setMessage("Do you like start Optional Socialogie Quiz");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        startActivity(new Intent(UserGradesGenerateActivity.this, SingleQuizSocilogieActivity.class));
                        finish();

                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();

            } else {

                int correct = Romanascore + IstoriaSingleScoreActivity + Psycholgiescore + Geographyscore + Bilogiescore;


                txtCorrect.setText("Correct: " + correct);
                txtAttempts.setText("Attempts :" + 5);
                txtPoints.setText("Points: " + finalAllSingleQuizPoints);
                txtWrong.setText("Wrong:" + allWrongCount);

                UploadPointsToServer();
            }


        } else {
            Toast.makeText(this, "Please, Finish Quiz Befor Generating Grades", Toast.LENGTH_SHORT).show();
        }


    }

    private void UploadPointsToServer() {


        String INSERT_QUIZ_POINTS_URL = "https://tanaseandrei0513.000webhostapp.com/insertQuizPoints.php";

        //upload Points to Server
        RequestQueue queue = Volley.newRequestQueue(UserGradesGenerateActivity.this);

        final ProgressDialog progressDialog = new ProgressDialog(UserGradesGenerateActivity.this);
        progressDialog.show();


        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, INSERT_QUIZ_POINTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("SuccessFully Inserted")) {
                    progressDialog.cancel();
                    Toast.makeText(UserGradesGenerateActivity.this, "Points are saved", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //If have any error if our url doesnt hit
                //catch responce

                progressDialog.dismiss();
                Toast.makeText(UserGradesGenerateActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(UserGradesGenerateActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserGradesGenerateActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                String p = String.valueOf(finalAllSingleQuizPoints);

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
}
