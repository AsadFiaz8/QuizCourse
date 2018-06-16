package com.tanaseandrei.quizcourse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PsychologyQuizFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private long backPressedTime;
    Spinner spinnerCorrectOption;
    String correctOption;
    Button addQUestion;
    EditText txtQuestion, txtOption1, txtOption2, txtOption3, txtOption4;
    String url = "https://tanaseandrei0513.000webhostapp.com/insertPsychology.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology_quiz_form);

        getSupportActionBar().setTitle("Psihologie");

        spinnerCorrectOption = (Spinner) findViewById(R.id.spinnerCorrectOption);
        addQUestion = (Button) findViewById(R.id.addQUestion);

        txtQuestion = (EditText) findViewById(R.id.txtQuestion);
        txtOption1 = (EditText) findViewById(R.id.txtOption1);
        txtOption2 = (EditText) findViewById(R.id.txtOption2);
        txtOption3 = (EditText) findViewById(R.id.txtOption3);
        txtOption4 = (EditText) findViewById(R.id.txtOption4);


        addQUestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateQuizCredientials();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_item_option, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCorrectOption.setAdapter(adapter);
        spinnerCorrectOption.setOnItemSelectedListener(this);
    }

    private void validateQuizCredientials() {

        if (txtOption1.getText().toString().equals("")) {
            txtOption1.setError("Empty Option 1");

        } else if (txtOption2.getText().toString().equals("")) {
            txtOption2.setError("Empty Option 2");

        } else if (txtOption3.getText().toString().equals("")) {
            txtOption3.setError("Empty Option 3");

        } else if (txtOption4.getText().toString().equals("")) {
            txtOption4.setError("Empty Option 4");

        } else if (txtQuestion.getText().toString().equals("")) {
            txtQuestion.setError("Enter Question");
        } else {
            addQUestionInDatabase();
        }

    }

    private void addQUestionInDatabase() {

        closeKeyboard();

        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(PsychologyQuizFormActivity.this);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //Setting Empety Fields
                Toast.makeText(PsychologyQuizFormActivity.this, response, Toast.LENGTH_SHORT).show();
                txtQuestion.setText("");
                txtOption1.setText("");
                txtOption2.setText("");
                txtOption3.setText("");
                txtOption4.setText("");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //If have any error if our url doesnt hit
                //catch responce

                Toast.makeText(PsychologyQuizFormActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(PsychologyQuizFormActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PsychologyQuizFormActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("question", txtQuestion.getText().toString());
                params.put("option1", txtOption1.getText().toString());
                params.put("option2", txtOption2.getText().toString());
                params.put("option3", txtOption3.getText().toString());
                params.put("option4", txtOption4.getText().toString());
                params.put("answer", correctOption);


                return params;


            }
        };
        //Add String request to Quuee
        queue.add(request);

    }

    //Cloase KeyBoard
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        correctOption = String.valueOf(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_question:
                Intent intent1 = new Intent(PsychologyQuizFormActivity.this, ShowQuestionsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.logout:
                Intent intent = new Intent(PsychologyQuizFormActivity.this, Login.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearNewQuizEntryFields() {
        Intent intent = new Intent(PsychologyQuizFormActivity.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}
