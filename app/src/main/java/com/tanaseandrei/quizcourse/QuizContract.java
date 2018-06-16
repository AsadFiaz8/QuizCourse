package com.tanaseandrei.quizcourse;

import android.provider.BaseColumns;

/**
 * Created by Asad Fiaz on 2/22/2018.
 */

//class that contain all fields of tables
public final class QuizContract {

    //consttructor
    private QuizContract() {

    }

    //class that contains tables cloumn names for database in quiz
    public static class ChimiccTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_chimic";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

    //same as above
    public static class IstorieTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_istorie";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

    public static class RomanaTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_romana";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

    public static class MathsTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_maths";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }

    public static class FizicaTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_fizica";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }


}
