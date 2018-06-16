package com.tanaseandrei.quizcourse;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class QuizSelectionActivity extends AppCompatActivity {

    Button btnOneTimeQuiz, btnRandomQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        getSupportActionBar().setTitle("Quiz Selection");

        btnOneTimeQuiz = (Button) findViewById(R.id.btnOneTimeQuiz);
        btnRandomQuiz = (Button) findViewById(R.id.btnRandomQuiz);

        btnOneTimeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRandomQuiz.setEnabled(false);
                Intent intent = new Intent(QuizSelectionActivity.this, IstoreActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRandomQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnOneTimeQuiz.setEnabled(false);
                Intent intent = new Intent(QuizSelectionActivity.this, QuizCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(QuizSelectionActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Are You Sure You Want to Logout ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(QuizSelectionActivity.this, Login.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_points_menu, menu);
        return true;
    }

    //click listener on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_grades:
                Intent intent1 = new Intent(QuizSelectionActivity.this, UserPointsActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
