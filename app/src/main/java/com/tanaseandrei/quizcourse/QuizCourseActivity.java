package com.tanaseandrei.quizcourse;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

import static com.tanaseandrei.quizcourse.SingleQuizBiologieActivity.checkBiologyPoints;
import static com.tanaseandrei.quizcourse.SingleQuizGeographyActivity.checkGeographyPoints;
import static com.tanaseandrei.quizcourse.SingleQuizIstoreActivity.checkIstorePoints;
import static com.tanaseandrei.quizcourse.SingleQuizPsucholgieActivity.checkPsychologyPoints;
import static com.tanaseandrei.quizcourse.SingleQuizRomana.checkRomanaPoints;

public class QuizCourseActivity extends AppCompatActivity {

    //buttons
    Button istore, romana, Geografie, Psihologie, Biologie;
    private long backPressedTime;
    //scrollview
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_course);

        //title
        getSupportActionBar().setTitle("Quiz Courses List");

        //cating
        istore = (Button) findViewById(R.id.btnIstorie);
        romana = (Button) findViewById(R.id.btnRomână);
        Geografie = (Button) findViewById(R.id.btnGeografie);
        Psihologie = (Button) findViewById(R.id.btnPsihologie);
        Biologie = (Button) findViewById(R.id.btnBiologie);
        scrollView = (ScrollView) findViewById(R.id.ScrollViewQuizCousre);

        //toast
        Toast.makeText(this, "Welcome on Random Quiz", Toast.LENGTH_SHORT).show();


        //click listener
        istore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if istor quiz are taken ...if yes disable button else move to quiz activity  and same for rest of buttons logic
                if (checkIstorePoints == true) {
                    istore.setEnabled(false);
                    Toast.makeText(QuizCourseActivity.this, "Quiz Take", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QuizCourseActivity.this, SingleQuizIstoreActivity.class);
                    startActivity(intent);

                }

            }
        });

        romana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkRomanaPoints == true) {
                    romana.setEnabled(false);
                    Toast.makeText(QuizCourseActivity.this, "Quiz Take", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QuizCourseActivity.this, SingleQuizRomana.class);
                    startActivity(intent);
                }
            }
        });

        Geografie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkGeographyPoints == true) {
                    Geografie.setEnabled(false);
                    Toast.makeText(QuizCourseActivity.this, "Quiz Take", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QuizCourseActivity.this, SingleQuizGeographyActivity.class);
                    startActivity(intent);
                }
            }
        });

        Psihologie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPsychologyPoints == true) {
                    Psihologie.setEnabled(false);
                    Toast.makeText(QuizCourseActivity.this, "Quiz Take", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(QuizCourseActivity.this, SingleQuizPsucholgieActivity.class);
                    startActivity(intent);
                }
            }
        });

        Biologie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBiologyPoints == true) {
                    Biologie.setEnabled(false);
                    Toast.makeText(QuizCourseActivity.this, "Quiz Take", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QuizCourseActivity.this, SingleQuizBiologieActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(QuizCourseActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Are You Sure You Want to Logout ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(QuizCourseActivity.this, Login.class);
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

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_generate_points, menu);
        return true;
    }

    //click listener on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_grades:
                Intent intent1 = new Intent(QuizCourseActivity.this, UserPointsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.generate_grades:
                Intent intent = new Intent(QuizCourseActivity.this, UserGradesGenerateActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //when applicatons starts agains check all quiz taken and mark margins as green
    @Override
    protected void onStart() {
        super.onStart();


        if (checkIstorePoints == true
                && checkRomanaPoints == true
                && checkGeographyPoints == true
                && checkPsychologyPoints == true
                && checkBiologyPoints == true) {

            scrollView.setBackgroundColor(Color.GREEN);

            istore.setBackgroundResource(R.drawable.locked);
            istore.setText("Istorie");
            istore.setTextColor(Color.WHITE);

            romana.setBackgroundResource(R.drawable.locked);
            romana.setText("Română");
            romana.setTextColor(Color.WHITE);

            Geografie.setBackgroundResource(R.drawable.locked);
            Geografie.setText("Geografie");
            Geografie.setTextColor(Color.WHITE);


            Psihologie.setBackgroundResource(R.drawable.locked);
            Psihologie.setText("Psihologie");
            Psihologie.setTextColor(Color.WHITE);


            Biologie.setBackgroundResource(R.drawable.locked);
            Biologie.setText("Biologie");
            Biologie.setTextColor(Color.WHITE);


            Toast.makeText(QuizCourseActivity.this, "All Quiz Take", Toast.LENGTH_SHORT).show();
        }
        if (checkIstorePoints) {
            istore.setBackgroundResource(R.drawable.locked);
            istore.setText("Istorie");
            istore.setTextColor(Color.WHITE);
        }
        if (checkIstorePoints == true && checkRomanaPoints == true) {

            istore.setBackgroundResource(R.drawable.locked);
            istore.setText("Istorie");
            istore.setTextColor(Color.WHITE);

            romana.setBackgroundResource(R.drawable.locked);
            romana.setText("Română");
            romana.setTextColor(Color.WHITE);
        }
        if (checkIstorePoints && checkRomanaPoints && checkGeographyPoints) {

            istore.setBackgroundResource(R.drawable.locked);
            istore.setText("Istorie");
            istore.setTextColor(Color.WHITE);

            romana.setBackgroundResource(R.drawable.locked);
            romana.setText("Română");
            romana.setTextColor(Color.WHITE);


            Geografie.setBackgroundResource(R.drawable.locked);
            Geografie.setText("Geografie");
            Geografie.setTextColor(Color.WHITE);
        }
        if (checkIstorePoints && checkRomanaPoints && checkGeographyPoints && checkPsychologyPoints) {

            istore.setBackgroundResource(R.drawable.locked);
            istore.setText("Istorie");
            istore.setTextColor(Color.WHITE);

            romana.setBackgroundResource(R.drawable.locked);
            romana.setText("Română");
            romana.setTextColor(Color.WHITE);

            Geografie.setBackgroundResource(R.drawable.locked);
            Geografie.setText("Geografie");
            Geografie.setTextColor(Color.WHITE);


            Psihologie.setBackgroundResource(R.drawable.locked);
            Psihologie.setText("Geografie");
            Psihologie.setTextColor(Color.WHITE);
        }

    }
}
