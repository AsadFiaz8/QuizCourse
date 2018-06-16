package com.tanaseandrei.quizcourse;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asadf on 6/14/2018.
 */
//Adapetre For UserPoints
public class CustomUserPointAdapter extends ArrayAdapter<UserPointModel> {

    //Model Class
    UserPointModel userPointModel;

    //constructor
    public CustomUserPointAdapter(Context context, int resource, ArrayList<UserPointModel> userPointModelArrayList) {
        super(context, R.layout.custom_question_row, userPointModelArrayList);
    }

    //findviews form layout and set data
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_user_point_row, parent, false);
        }
        userPointModel = getItem(position);


        //getting views from layout and get data from arraylist of custom objetcs
        TextView userEmail = (TextView) convertView.findViewById(R.id.txtUserPointEmail);
        userEmail.setText(userPointModel.getEmail());

        TextView txtUserAttempt = (TextView) convertView.findViewById(R.id.txtUserAttempt);
        txtUserAttempt.setText("Test: " + userPointModel.getAttempt());

        TextView txtUserPoints = (TextView) convertView.findViewById(R.id.txtUserPoints);
        txtUserPoints.setText("P: " + userPointModel.getPoints());

        //return the view
        return convertView;


    }
}
