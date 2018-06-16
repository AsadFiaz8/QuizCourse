package com.tanaseandrei.quizcourse;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.HashMap;
import java.util.Map;

import static com.tanaseandrei.quizcourse.TeacherActivity.checkBiologie;
import static com.tanaseandrei.quizcourse.TeacherActivity.checkGeografie;
import static com.tanaseandrei.quizcourse.TeacherActivity.checkPsihologie;
import static com.tanaseandrei.quizcourse.TeacherActivity.checkRomana;
import static com.tanaseandrei.quizcourse.TeacherActivity.checkistore;

public class ShowQuestionsActivity extends AppCompatActivity {

    ListView listViewLoadQUestions;
    String url;
    ArrayList<SHowQuestionsModel> arrayList;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    String QuestionID;
    String DROP_QUESTION_URL = "https://tanaseandrei0513.000webhostapp.com/dropQuestion.php";
    String tableName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);

        listViewLoadQUestions = (ListView) findViewById(R.id.loadQuestionsListView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        if (checkistore == true) {
            url = "https://tanaseandrei0513.000webhostapp.com/encodeHistory.php";
            tableName = "history";
        } else if (checkRomana == true) {
            url = "https://tanaseandrei0513.000webhostapp.com/encodeRomana.php";
            tableName = "romania";

        } else if (checkBiologie == true) {
            url = "https://tanaseandrei0513.000webhostapp.com/encodeBiology.php";
            tableName = "biology";

        } else if (checkGeografie == true) {
            url = "https://tanaseandrei0513.000webhostapp.com/encodeGeoGraphy.php";
            tableName = "geography";

        } else if (checkPsihologie == true) {
            url = "https://tanaseandrei0513.000webhostapp.com/encodePsychology.php";
            tableName = "psychology";

        }

        arrayList = new ArrayList<>();
        //loadData
        loadQuetions();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                arrayList.clear();
                loadQuetions();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ShowQuestionsActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void loadQuetions() {

        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);


                        String id = object.getString("id");
                        String question = object.getString("question");

                        arrayList.add(new SHowQuestionsModel(id, question));

                        CustomShowQuestionAdapter adapter = new CustomShowQuestionAdapter(ShowQuestionsActivity.this, R.layout.custom_question_row, arrayList);
                        listViewLoadQUestions.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                listViewLoadQUestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShowQuestionsActivity.this);

                        alertDialogBuilder.setTitle("Alert").setMessage("Are You Sure You want to delete Question ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        deleteQuestion(position);
                                        //Toast.makeText(ShowQuestionsActivity.this, "DROP Question", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(ShowQuestionsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowQuestionsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(ShowQuestionsActivity.this);
        queue.add(request);

    }

    public void deleteQuestion(int pos) {

        SHowQuestionsModel teacherModel = arrayList.get(pos);

        QuestionID = teacherModel.getId();

        RequestQueue queue = Volley.newRequestQueue(ShowQuestionsActivity.this);

        StringRequest request = new StringRequest(
                Request.Method.POST, DROP_QUESTION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ShowQuestionsActivity.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(ShowQuestionsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowQuestionsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("QuestionID", QuestionID);
                params.put("tableName", tableName);

                return params;


            }
        };
        queue.add(request);


    }

}


