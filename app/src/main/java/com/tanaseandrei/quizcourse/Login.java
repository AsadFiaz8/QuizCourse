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

public class Login extends AppCompatActivity {

    //variable to exit application in 2 seconds
    private long backPressedTime;
    //EditTexts Classess
    EditText txtUserEmail, txtUserPassword;
    //Button Class
    Button btnLogin;
    //Textveiw SIgnup
    TextView txtSignUp;
    //Useremail in variable to use in application
    public static String userEmail = "";
    //Loading library
    AVLoadingIndicatorView avi;
    //Url/APi to Hit for login
    final String loginScript = "https://tanaseandrei0513.000webhostapp.com/userLogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        //Cast Varibales
        txtUserEmail = (EditText) findViewById(R.id.txtEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setVisibility(View.INVISIBLE);

        //button login clicklistbner
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(v);
                closeKeyboard();


            }
        });

        //button signup click listener
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    //close keyboard
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //login funtion on button login click
    public void login(View view) {

        userEmail = txtUserEmail.getText().toString();

        //checking fields are empty
        if (txtUserEmail.getText().toString().equals("") || txtUserPassword.getText().toString().equals("")) {
            Toast.makeText(Login.this, "Enter All Fields", Toast.LENGTH_LONG).show();
        } else {

            //visinle ladojng
            avi.setVisibility(View.VISIBLE);
            final StringRequest request = new StringRequest(
                    Request.Method.POST, loginScript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //checking responce and launch activity on responce base

                    if (response.equalsIgnoreCase("Registered Learner")) {
                        txtUserEmail.setText("");
                        txtUserPassword.setText("");
                        Toast.makeText(Login.this, "Hi, you just logged in as a Registered Learner", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, QuizSelectionActivity.class);
                        startActivity(intent);
                        finish();
                        avi.setVisibility(View.GONE);
                    } else if (response.equalsIgnoreCase("Teacher")) {
                        txtUserEmail.setText("");
                        txtUserPassword.setText("");
                        Toast.makeText(Login.this, "Hi, you just logged in as a Teacher", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, TeacherActivity.class);
                        avi.setVisibility(View.GONE);
                        startActivity(i);

                    } else {
                        avi.setVisibility(View.GONE);
                        txtUserEmail.setText("");
                        txtUserPassword.setText("");
                        Toast.makeText(Login.this, "Please Enter Valid Fields", Toast.LENGTH_SHORT).show();
                    }
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //release loading bar memory permantely
                    //there is no difference betwen view.gone and view.invisible
                    avi.setVisibility(View.GONE);
                    //checking wifi satate
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                    if (networkInfo == null) {
                        Toast.makeText(Login.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        //if server have error
                        Toast.makeText(Login.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {

                    //maping value to keys
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userEmail", txtUserEmail.getText().toString());
                    params.put("userPassword", txtUserPassword.getText().toString());

                    return params;


                }
            };
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(request);
        }
    }

    //function to close application within 2 seocds
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
