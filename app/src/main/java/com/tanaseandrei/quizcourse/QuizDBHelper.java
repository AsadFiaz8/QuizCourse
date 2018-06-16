package com.tanaseandrei.quizcourse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asadf on 6/7/2018.
 */

public class QuizDBHelper extends SQLiteOpenHelper {

    //Database name
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    //database version code
    private static final int DATABASE_VERSION = 2;
    //context
    Context c;

    //database reference
    private SQLiteDatabase db;


    public QuizDBHelper(Context context) {
        //calling constructor of super call i-e sqliteopenhelper
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    //on create method is used to tables in database
    @Override
    public void onCreate(SQLiteDatabase db) {


        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.ChimiccTable.TABLE_NAME + " ( " +
                QuizContract.ChimiccTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.ChimiccTable.COLUMN_QUESTION + " VARCHAR, " +
                QuizContract.ChimiccTable.COLUMN_OPTION1 + " VARCHAR, " +
                QuizContract.ChimiccTable.COLUMN_OPTION2 + " VARCHAR, " +
                QuizContract.ChimiccTable.COLUMN_OPTION3 + " VARCHAR, " +
                QuizContract.ChimiccTable.COLUMN_OPTION4 + " VARCHAR, " +
                QuizContract.ChimiccTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);


    }

    //drop table if already eixte and creeate with new
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.ChimiccTable.TABLE_NAME);
        onCreate(db);
    }

    //adding question in database

    public void addQuestion(Question question) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.ChimiccTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.ChimiccTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.ChimiccTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.ChimiccTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.ChimiccTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuizContract.ChimiccTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        database.insert(QuizContract.ChimiccTable.TABLE_NAME, null, cv);
        Toast.makeText(c, "Inserted", Toast.LENGTH_SHORT).show();
    }

    //reteriving database question in dynacmic arraylist
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.ChimiccTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.ChimiccTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.ChimiccTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.ChimiccTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.ChimiccTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuizContract.ChimiccTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.ChimiccTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
