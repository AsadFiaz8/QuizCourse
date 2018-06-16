package com.tanaseandrei.quizcourse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private long backPressedTime;
    //Radio Buttons of category
    RadioButton btnRd1RegisteredLearner, btnRd2Teacher;
    //Radio Group
    RadioGroup radiogroup;
    //EditTexts
    EditText txtUserName, txtUserEmail, txtUserPassword, txtConformPassword;
    //Button Register
    Button btnRegister;
    //Url to hit
    String url = "https://tanaseandrei0513.000webhostapp.com/userSignUp.php";
    //TextView Login
    TextView txtLogin;
    //Loading Library
    AVLoadingIndicatorView avi;
    //String Checkedvalue
    String radioButtonChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Sign Up");

        //Casting Classes to XML id's
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        txtConformPassword = (EditText) findViewById(R.id.txtConformPassword);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        avi = (AVLoadingIndicatorView) findViewById(R.id.aviSignup);
        //Setting Loading Library to Invisible First time
        avi.setVisibility(View.INVISIBLE);

        //Radio Buttons
        btnRd1RegisteredLearner = (RadioButton) findViewById(R.id.btnRd1RegisteredLearner);
        btnRd2Teacher = (RadioButton) findViewById(R.id.btnRd2Teacher);

        //RadioGroup
        radiogroup = (RadioGroup) findViewById(R.id.radio_group1);
        //Callbacks on Radiogroup
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub


                if (btnRd1RegisteredLearner.isChecked()) {
                    radioButtonChecked = "Registered Learner";

                } else {
                    radioButtonChecked = "Teacher";
                }


            }
        });

        //CallBack on TextView's Login
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        //Callback on Button Resgitser
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //First Time Run this Function
                validateCredentials();
            }
        });

    }

    public void validateCredentials() {

        //Checking Fields are Empty
        if (txtUserName.getText().toString().equals("")) {
            txtUserName.setError("User Name is Required");

        } else if (txtUserEmail.getText().toString().equals("")) {
            txtUserEmail.setError("Email is required!");

        } else if (txtUserPassword.getText().toString().equals("")) {
            txtUserPassword.setError("Password is required!");

        } else if (!txtUserPassword.getText().toString().equals(txtConformPassword.getText().toString())) {
            txtUserPassword.setText(null);
            txtConformPassword.setText(null);
            Toast.makeText(SignUp.this, "passwords do not match", Toast.LENGTH_SHORT).show();

        } else if (!isValidEmail(txtUserEmail.getText().toString())) {
            txtUserEmail.setError("Invalid Email!");
            txtUserEmail.setText(null);

        } else {
            //If not any field empty simply signup
            signUp();
        }

    }

    public void signUp() {

        //close keyboard on pressing button register
        closeKeyboard();

        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        //Visbile Loading Bar
        avi.setVisibility(View.VISIBLE);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Setting Empety Fields
                Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                avi.setVisibility(View.INVISIBLE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //If have any error if our url doesnt hit
                //catch responce
                avi.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, error.toString(), Toast.LENGTH_SHORT).show();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(SignUp.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("userFullName", txtUserName.getText().toString());
                params.put("userEmail", txtUserEmail.getText().toString());
                params.put("userPassword", txtUserPassword.getText().toString());
                params.put("userType", radioButtonChecked);


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

    //Check Email is Valid or not
    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
}
