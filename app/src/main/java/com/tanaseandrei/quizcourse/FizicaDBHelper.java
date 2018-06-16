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

public class FizicaDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "MyAwesomeQuizFizica.db";
    private static final int DATABASE_VERSION = 1;
    Context c;

    private SQLiteDatabase db;

    public FizicaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.FizicaTable.TABLE_NAME + " ( " +
                QuizContract.FizicaTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.FizicaTable.COLUMN_QUESTION + " VARCHAR, " +
                QuizContract.FizicaTable.COLUMN_OPTION1 + " VARCHAR, " +
                QuizContract.FizicaTable.COLUMN_OPTION2 + " VARCHAR, " +
                QuizContract.FizicaTable.COLUMN_OPTION3 + " VARCHAR, " +
                QuizContract.FizicaTable.COLUMN_OPTION4 + " VARCHAR, " +
                QuizContract.FizicaTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.FizicaTable.TABLE_NAME);
        onCreate(db);
    }

    public void addQuestion(Question question) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.FizicaTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.FizicaTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.FizicaTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.FizicaTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.FizicaTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuizContract.FizicaTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        database.insert(QuizContract.FizicaTable.TABLE_NAME, null, cv);
        Toast.makeText(c, "Inserted", Toast.LENGTH_SHORT).show();
    }

    //Retreienig Questions from database
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.FizicaTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.FizicaTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.FizicaTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.FizicaTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.FizicaTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuizContract.FizicaTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.FizicaTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}


