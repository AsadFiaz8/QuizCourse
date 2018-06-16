package com.tanaseandrei.quizcourse;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class UserPointsActivity extends AppCompatActivity {

    ListView loadPointsListView;
    ArrayList<UserPointModel> arrayList;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    String LOAD_USER_POINTS = "https://tanaseandrei0513.000webhostapp.com/encodeUserPoints.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_points);

        getSupportActionBar().setTitle("User Points");

        loadPointsListView = (ListView) findViewById(R.id.loadPointsListView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


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
                Toast.makeText(UserPointsActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void loadQuetions() {

        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, LOAD_USER_POINTS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);


                        String id = object.getString("id");
                        String email = object.getString("email");
                        String points = object.getString("points");
                        String attempt = object.getString("attempt");

                        arrayList.add(new UserPointModel(id, email, points, attempt));

                        CustomUserPointAdapter adapter = new CustomUserPointAdapter(UserPointsActivity.this, R.layout.custom_user_point_row, arrayList);
                        loadPointsListView.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(UserPointsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserPointsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(UserPointsActivity.this);
        queue.add(request);

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_points_activity_menu, menu);
        return true;
    }

    //click listener on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(UserPointsActivity.this, Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



