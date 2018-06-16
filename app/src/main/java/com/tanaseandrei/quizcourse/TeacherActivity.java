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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity {

    //Button which will shown on after login teacher
    Button istore, romana, Geografie, Psihologie, Biologie;
    //Boolean to check which course is selected
    public static boolean checkistore = false;
    public static boolean checkRomana = false;
    public static boolean checkGeografie = false;
    public static boolean checkPsihologie = false;
    public static boolean checkBiologie = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //cast varibles to id
        istore = (Button) findViewById(R.id.btnIstorie);
        romana = (Button) findViewById(R.id.btnRomână);
        Geografie = (Button) findViewById(R.id.btnGeografie);
        Psihologie = (Button) findViewById(R.id.btnPsihologie);
        Biologie = (Button) findViewById(R.id.btnBiologie);

        //click listeber and launching acctivity base on course
        istore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean to check later which activity is clicked
                checkistore = true;
                checkRomana = false;
                checkGeografie = false;
                checkPsihologie = false;
                checkBiologie = false;
                Intent intent = new Intent(TeacherActivity.this, IstorieQuizFormActivity.class);
                startActivity(intent);
            }
        });

        //click listenr on button
        romana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean to check later which activity is clicked
                checkRomana = true;
                checkistore = false;
                checkGeografie = false;
                checkPsihologie = false;
                checkBiologie = false;
                Intent intent = new Intent(TeacherActivity.this, RomanaQuizFormAcitivity.class);
                startActivity(intent);
            }
        });


        Geografie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean to check later which activity is clicked
                checkGeografie = true;
                checkRomana = false;
                checkistore = false;
                checkPsihologie = false;
                checkBiologie = false;
                Intent intent = new Intent(TeacherActivity.this, GeographyQuizFormActivity.class);
                startActivity(intent);
            }
        });

        Psihologie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean to check later which activity is clicked
                checkPsihologie = true;
                checkGeografie = false;
                checkRomana = false;
                checkistore = false;
                checkBiologie = false;
                Intent intent = new Intent(TeacherActivity.this, PsychologyQuizFormActivity.class);
                startActivity(intent);
            }
        });

        Biologie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean to check later which activity is clicked
                checkBiologie = true;
                checkGeografie = false;
                checkRomana = false;
                checkistore = false;
                checkPsihologie = false;
                Intent intent = new Intent(TeacherActivity.this, BiologyQUizFormActivity.class);
                startActivity(intent);
            }
        });


    }

    //menu files quiz form
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_menu, menu);
        return true;
    }

    //click listener on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_grades:
                Intent intent1 = new Intent(TeacherActivity.this, UserPointsActivity.class);
                startActivity(intent1);
                return true;
            case R.id.logout:
                Intent intent = new Intent(TeacherActivity.this, Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(TeacherActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Are You Sure You Want to Logout ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(TeacherActivity.this, Login.class);
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
