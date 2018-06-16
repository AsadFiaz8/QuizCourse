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
 * Created by asadf on 6/9/2018.
 */

//CustomAdapter
public class CustomShowQuestionAdapter extends ArrayAdapter<SHowQuestionsModel> {

    //Modeil Class of Show All Questions from custom listview
    SHowQuestionsModel sHowQuestionsModel;

    //constructor for Adapter
    public CustomShowQuestionAdapter(Context context, int resource, ArrayList<SHowQuestionsModel> sHowQuestionsModelArrayList) {
        super(context, R.layout.custom_question_row, sHowQuestionsModelArrayList);
    }

    //Get refernce of particular coustom row in java code
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //if layout is null
        //attatch layout to Adpater
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //inflate cusotm layout
            convertView = inflater.inflate(R.layout.custom_question_row, parent, false);
        }
        //setting the current position for item to hold
        sHowQuestionsModel = getItem(position);


        //find view from java layout resource and show quetsion of current poistion of  index
        TextView userFullName = (TextView) convertView.findViewById(R.id.txtQuestion);
        userFullName.setText(sHowQuestionsModel.getQuestion());


        //return view i-e which is a layout resource
        return convertView;


    }
}
