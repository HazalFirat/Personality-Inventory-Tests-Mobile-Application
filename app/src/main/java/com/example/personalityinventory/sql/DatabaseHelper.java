package com.example.personalityinventory.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.personalityinventory.R;
import com.example.personalityinventory.model.Question;
import com.example.personalityinventory.model.Result;
import com.example.personalityinventory.model.Subject;
import com.example.personalityinventory.model.Test;
import com.example.personalityinventory.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "PerInvDB.db";

    //region USER TABLE
    private static final String TABLE_USER = "User";


    private static final String COLUMN_USER_ID = "userID";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER__PASSWORD = "password";
    private static final String COLUMN_USER_USERTYPE = "usertype";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_USER_USERNAME
            + " TEXT NOT NULL, " + COLUMN_USER_EMAIL + " TEXT NOT NULL, " + COLUMN_USER__PASSWORD
            + " TEXT NOT NULL, " + COLUMN_USER_USERTYPE + " TEXT NOT NULL " + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
//endregion

    //region TEST TABLE
    private static final String TABLE_TEST = "Test";


    private static final String COLUMN_TEST_ID = "testID";
    private static final String COLUMN_TEST_TESTNAME = "testname";
    private static final String COLUMN_TEST_DURATION = "testDuration";
    private static final String COLUMN_TEST__DESC = "testDescription";
    private static final String COLUMN_TEST_DATE = "testDate";

    private String CREATE_TEST_TABLE = "CREATE TABLE " + TABLE_TEST + " (" + COLUMN_TEST_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_TEST_TESTNAME
            + " TEXT NOT NULL, " + COLUMN_TEST_DURATION + " INTEGER NOT NULL, " + COLUMN_TEST__DESC
            + " TEXT NOT NULL, " + COLUMN_TEST_DATE + " TEXT NOT NULL " + ")";

    private String DROP_TEST_TABLE = "DROP TABLE IF EXISTS " + TABLE_TEST;
//endregion

    //region SUBJECT TABLE
    private static final String TABLE_SUBJECT = "Subject";


    private static final String COLUMN_SUBJECT_ID = "subjectID";
    private static final String COLUMN_SUBJECT_SUBJECTNAME = "subjectname";


    private String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + " (" + COLUMN_SUBJECT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_SUBJECT_SUBJECTNAME
            + " TEXT NOT NULL " + ")";

    private String DROP_SUBJECT_TABLE = "DROP TABLE IF EXISTS " + TABLE_SUBJECT;
//endregion

    //region QUESTION TABLE
    private static final String TABLE_QUESTION = "Question";


    private static final String COLUMN_QUESTION_ID = "questionID";
    private static final String COLUMN_QUESTION_CONTENT = "questionContent";
    private static final String COLUMN_QUESTION_TESTID = "questionTestID";
    private static final String COLUMN_QUESTION_SUBJECTID = "questionSubjectID";

    private String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUESTION + " (" + COLUMN_QUESTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_QUESTION_CONTENT
            + " TEXT NOT NULL, " + COLUMN_QUESTION_TESTID + " INTEGER NOT NULL, " + COLUMN_QUESTION_SUBJECTID
            + " INTEGER NOT NULL, " + "FOREIGN KEY ( " + COLUMN_QUESTION_TESTID + " ) REFERENCES " + TABLE_TEST
            + " ( " + COLUMN_TEST_ID + " ), " + "FOREIGN KEY ( " + COLUMN_QUESTION_SUBJECTID + " ) REFERENCES "
            + TABLE_SUBJECT + " ( " + COLUMN_SUBJECT_ID + " ) )";

    private String DROP_QUESTION_TABLE = "DROP TABLE IF EXISTS " + TABLE_QUESTION;
//endregion

    //region RESULT TABLE
    private static final String TABLE_RESULT = "Result";


    private static final String COLUMN_RESULT_ID = "resultID";
    private static final String COLUMN_RESULT_USERID = "resultUserID";
    private static final String COLUMN_RESULT_TESTID = "resultTestID";
    private static final String COLUMN_RESULT_SUBJECTID = "resultSubjectID";
    private static final String COLUMN_RESULT_PERCENT = "resultPercent";
    private static final String COLUMN_RESULT_QUESTIONID = "resultQuestionID";

    private String CREATE_RESULT_TABLE = "CREATE TABLE " + TABLE_RESULT + " (" + COLUMN_RESULT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_RESULT_USERID + " INTEGER NOT NULL, "
            + COLUMN_RESULT_TESTID + " INTEGER NOT NULL, " + COLUMN_RESULT_SUBJECTID + " INTEGER NOT NULL, "
            + COLUMN_RESULT_PERCENT + " INTEGER NOT NULL, " + COLUMN_RESULT_QUESTIONID + " INTEGER NOT NULL, "
            + "FOREIGN KEY ( " + COLUMN_RESULT_USERID + " ) REFERENCES " + TABLE_USER + " ( " + COLUMN_USER_ID + " ), "
            + "FOREIGN KEY ( " + COLUMN_RESULT_TESTID + " ) REFERENCES " + TABLE_TEST + " ( " + COLUMN_TEST_ID + " ), "
            + "FOREIGN KEY ( " + COLUMN_RESULT_SUBJECTID + " ) REFERENCES " + TABLE_SUBJECT + " ( " + COLUMN_SUBJECT_ID + " ), "
            + "FOREIGN KEY ( " + COLUMN_RESULT_QUESTIONID + " ) REFERENCES " + TABLE_QUESTION + " ( " + COLUMN_QUESTION_ID + " ) )";

    private String DROP_RESULT_TABLE = "DROP TABLE IF EXISTS " + TABLE_RESULT;
//endregion

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TEST_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_RESULT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TEST_TABLE);
        db.execSQL(DROP_SUBJECT_TABLE);
        db.execSQL(DROP_QUESTION_TABLE);
        db.execSQL(DROP_RESULT_TABLE);
        onCreate(db);
    }

    public void addResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESULT_USERID, result.getUserID());
        values.put(COLUMN_RESULT_TESTID, result.getTestID());
        values.put(COLUMN_RESULT_PERCENT, (int)result.getPercent());
        values.put(COLUMN_RESULT_SUBJECTID, result.getSubjectID());
        values.put(COLUMN_RESULT_QUESTIONID, result.getQuestionID());

        db.insert(TABLE_RESULT, null, values);
        db.close();
    }

    public List<Result> getResults(int testID, int userID) {
        List<Result> resultList = new ArrayList<Result>();

        String selectQuery = "SELECT " + COLUMN_RESULT_ID + ", " + COLUMN_RESULT_USERID + ", " + COLUMN_RESULT_TESTID + ", " + COLUMN_RESULT_SUBJECTID + ", "
                + "ROUND(AVG( " + COLUMN_RESULT_PERCENT + " ), 2) AVG, " + COLUMN_RESULT_QUESTIONID + " FROM " + TABLE_RESULT + " WHERE " + COLUMN_RESULT_TESTID
                + " = " + testID + " AND " + COLUMN_RESULT_USERID + " = " + userID +" GROUP BY "+COLUMN_RESULT_SUBJECTID+ " ORDER BY " + COLUMN_RESULT_SUBJECTID + " ASC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setResultID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RESULT_ID))));
                result.setUserID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RESULT_USERID))));
                result.setTestID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RESULT_TESTID))));
                result.setPercent(Float.parseFloat(cursor.getString(cursor.getColumnIndex("AVG"))));
                result.setSubjectID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RESULT_SUBJECTID))));
                result.setQuestionID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RESULT_QUESTIONID))));
                resultList.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return resultList;
    }

    public void updateResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESULT_USERID, result.getUserID());
        values.put(COLUMN_RESULT_TESTID, result.getTestID());
        values.put(COLUMN_RESULT_PERCENT, (int)result.getPercent());
        values.put(COLUMN_RESULT_SUBJECTID, result.getSubjectID());
        values.put(COLUMN_RESULT_QUESTIONID, result.getQuestionID());

        db.update(TABLE_RESULT, values, COLUMN_RESULT_QUESTIONID + " = ?", new String[]{String.valueOf(result.getQuestionID())});
        db.close();
    }

    public void deleteResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RESULT, COLUMN_RESULT_ID + " = ?", new String[]{String.valueOf(result.getResultID())});
        db.close();
    }

    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_CONTENT, question.getQuestionContent());
        values.put(COLUMN_QUESTION_TESTID, question.getTestID());
        values.put(COLUMN_QUESTION_SUBJECTID, question.getSubjectID());

        db.insert(TABLE_QUESTION, null, values);
        db.close();
    }

    public List<Question> getQuestions(int testID) {
        List<Question> questionList = new ArrayList<Question>();

        String selectQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_TESTID + " = " + testID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestionID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_ID))));
                question.setQuestionContent(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_CONTENT)));
                question.setTestID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_TESTID))));
                question.setSubjectID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_SUBJECTID))));
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return questionList;
    }

    public void updateQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_CONTENT, question.getQuestionContent());
        values.put(COLUMN_QUESTION_TESTID, question.getTestID());
        values.put(COLUMN_QUESTION_SUBJECTID, question.getSubjectID());

        db.update(TABLE_QUESTION, values, COLUMN_QUESTION_ID + " = ?", new String[]{String.valueOf(question.getQuestionID())});
        db.close();
    }

    public void deleteQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_QUESTION, COLUMN_QUESTION_ID + " = ?", new String[]{String.valueOf(question.getQuestionID())});
        db.close();
    }

    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT_SUBJECTNAME, subject.getSubjectName());

        db.insert(TABLE_SUBJECT, null, values);
        db.close();
    }

    public String getSubjectName(int id) {

        String selectQuery = "SELECT * FROM " + TABLE_SUBJECT + " WHERE " + COLUMN_SUBJECT_ID + " = " + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Subject subject = new Subject();
        if (cursor.moveToFirst()) {
            do {

                subject.setSubjectID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_ID))));
                subject.setSubjectName(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_SUBJECTNAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return subject.getSubjectName();
    }

    public List<Subject> getAllSubject() {
        String[] columns = {
                COLUMN_SUBJECT_ID,
                COLUMN_SUBJECT_SUBJECTNAME
        };

        String sortOrder = COLUMN_SUBJECT_ID + " ASC";
        List<Subject> subjectList = new ArrayList<Subject>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUBJECT,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject();
                subject.setSubjectID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_ID))));
                subject.setSubjectName(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_SUBJECTNAME)));
                subjectList.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return subjectList;
    }

    public void updateSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT_SUBJECTNAME, subject.getSubjectName());

        db.update(TABLE_SUBJECT, values, COLUMN_SUBJECT_ID + " = ?", new String[]{String.valueOf(subject.getSubjectID())});
        db.close();
    }

    public void deleteSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SUBJECT, COLUMN_SUBJECT_ID + " = ?", new String[]{String.valueOf(subject.getSubjectID())});
        db.close();
    }

    public void addTest(Test test) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEST_TESTNAME, test.getTestName());
        values.put(COLUMN_TEST_DURATION, test.getDuration());
        values.put(COLUMN_TEST__DESC, test.getDescription());
        values.put(COLUMN_TEST_DATE, test.getDateAdded());

        db.insert(TABLE_TEST, null, values);
        db.close();
    }

    public List<Test> getAllTest() {
        String[] columns = {
                COLUMN_TEST_ID,
                COLUMN_TEST_TESTNAME,
                COLUMN_TEST_DURATION,
                COLUMN_TEST__DESC,
                COLUMN_TEST_DATE
        };

        String sortOrder = COLUMN_TEST_TESTNAME + " ASC";
        List<Test> testList = new ArrayList<Test>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TEST,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test();
                test.setTestID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TEST_ID))));
                test.setTestName(cursor.getString(cursor.getColumnIndex(COLUMN_TEST_TESTNAME)));
                test.setDuration(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TEST_DURATION))));
                test.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TEST__DESC)));
                test.setDateAdded(cursor.getString(cursor.getColumnIndex(COLUMN_TEST_DATE)));
                testList.add(test);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return testList;
    }

    public void updateTest(Test test) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEST_TESTNAME, test.getTestName());
        values.put(COLUMN_TEST_DURATION, test.getDuration());
        values.put(COLUMN_TEST__DESC, test.getDescription());
        values.put(COLUMN_TEST_DATE, test.getDateAdded());

        db.update(TABLE_TEST, values, COLUMN_TEST_ID + " = ?", new String[]{String.valueOf(test.getTestID())});
        db.close();
    }

    public void deleteTest(Test test) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TEST, COLUMN_TEST_ID + " = ?", new String[]{String.valueOf(test.getTestID())});
        db.close();
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER__PASSWORD, user.getPassword());
        values.put(COLUMN_USER_USERTYPE, user.getUserType());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public List<User> getAllUser() {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_USERNAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER__PASSWORD,
                COLUMN_USER_USERTYPE
        };

        String sortOrder = COLUMN_USER_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER__PASSWORD)));
                user.setUserType(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERTYPE)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public User getUser(String username) {
        User user = new User();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = ?";
        String[] selectionArgs = {username};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            user.setUserID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER__PASSWORD)));
            user.setUserType(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERTYPE)));
        }
        cursor.close();
        db.close();
        return user;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER__PASSWORD, user.getPassword());
        values.put(COLUMN_USER_USERTYPE, user.getUserType());

        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getUserID())});
        db.close();
    }

    public boolean checkUser(String email) {
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER__PASSWORD + " = ?";

        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

/*
        Test test1 = new Test("Honesty Test", 10, "Persons with very high scores on the Honesty-Humility scale avoid manipulating others for personal gain, feel little temptation to break rules, are uninterested in lavish wealth and luxuries, and feel no special entitlement to elevated social status. Conversely, persons with very low scores on this scale will flatter others to get what they want, are inclined to break rules for personal profit, are motivated by material gain, and feel a strong sense of self-importance.", "2018-04-21 01:36:27.000");
        addTest(test1);
        Test test2 = new Test("Emotionality Test", 15, "Persons with very high scores on the Emotionality scale experience fear of physical dangers, experience anxiety in response to life's stresses, feel a need for emotional support from others, and feel empathy and sentimental attachments with others. Conversely, persons with very low scores on this scale are not deterred by the prospect of physical harm, feel little worry even in stressful situations, have little need to share their concerns with others, and feel emotionally detached from others.", "2018-07-24 16:57:05.890");
        addTest(test2);
        Test test3 = new Test("Agreeableness Test", 10, "Persons with very high scores on the Agreeableness scale forgive the wrongs that they suffered, are lenient in judging others, are willing to compromise and cooperate with others, and can easily control their temper. Conversely, persons with very low scores on this scale hold grudges against those who have harmed them, are rather critical of others' shortcomings, are stubborn in defending their point of view, and feel anger readily in response to mistreatment.", "2018-07-25 09:34:35.527");
        addTest(test3);*/

/*
        Subject subject1 = new Subject("Sincerity");
        Subject subject2 = new Subject("Fairness");
        Subject subject3 = new Subject("Greed Avoidance");
        Subject subject4 = new Subject("Modesty");
        Subject subject5 = new Subject("Fearfulness");
        Subject subject6 = new Subject("Anxiety");
        Subject subject7 = new Subject("Dependence");
        Subject subject8 = new Subject("Sentimentality");
        Subject subject9 = new Subject("Extraversion");
        Subject subject10 = new Subject("Social Boldness");
        Subject subject11 = new Subject("Sociability");
        Subject subject12 = new Subject("Liveliness");
        Subject subject13 = new Subject("Forgiveness");
        Subject subject14 = new Subject("Gentleness");
        Subject subject15 = new Subject("Flexibility");
        Subject subject16 = new Subject("Patience");
        Subject subject17 = new Subject("Organization");
        Subject subject18 = new Subject("Diligence");
        Subject subject19 = new Subject("Perfectionism");
        Subject subject20 = new Subject("Prudence");
        Subject subject21 = new Subject("Aesthetic Appreciation");
        Subject subject22 = new Subject("Inquisitiveness");
        Subject subject23 = new Subject("Creativity");
        Subject subject24 = new Subject("Unconventionality");

        addSubject(subject1);
        addSubject(subject2);
        addSubject(subject3);
        addSubject(subject4);
        addSubject(subject5);
        addSubject(subject6);
        addSubject(subject7);
        addSubject(subject8);
        addSubject(subject9);
        addSubject(subject10);
        addSubject(subject11);
        addSubject(subject12);
        addSubject(subject13);
        addSubject(subject14);
        addSubject(subject15);
        addSubject(subject16);
        addSubject(subject17);
        addSubject(subject18);
        addSubject(subject19);
        addSubject(subject20);
        addSubject(subject21);
        addSubject(subject22);
        addSubject(subject23);
        addSubject(subject24);
*/
/*
       Question question1 = new Question("I do not pretend to be more than I am.", 1, 1);
        addQuestion(question1);
        Question question2 = new Question("I would never take things that are not mine.", 1, 2);
        addQuestion(question2);
        Question question3 = new Question("I would not enjoy being a famous celebrity.", 1, 3);
        addQuestion(question3);
        Question question4 = new Question("I do not think that I am better than other people.", 1, 4);
        addQuestion(question4);
        */
/*
        Question question1 = new Question("I am not willing to take risks.", 2, 5);
        addQuestion(question1);
        Question question2 = new Question("I get stressed out easily.", 2, 6);
        addQuestion(question2);
        Question question3 = new Question("I need the approval of others.", 2, 7);
        addQuestion(question3);
        Question question4 = new Question("I often worry about things that turn out to be unimportant.", 2, 6);
        addQuestion(question4);
        Question question5 = new Question("I don't like dangerous situations.", 2, 5);
        addQuestion(question5);
        Question question6 = new Question("I cry during movies.", 2, 8);
        addQuestion(question6);
        Question question7 = new Question("I panic easily.", 2, 6);
        addQuestion(question7);
        Question question8 = new Question("I am sensitive to the needs of others.", 2, 8);
        addQuestion(question8);
        Question question9 = new Question("I suspect that my facial expressions give me away when I feel sad.", 2, 7);
        addQuestion(question9);
        Question question10 = new Question("I am deeply moved by others' misfortunes.", 2, 8);
        addQuestion(question10);

*/
/*
        Question question1 = new Question("I am nice to people I should be angry at.", 3, 13);
        addQuestion(question1);
        Question question2 = new Question("I accept people as they are.", 3, 14);
        addQuestion(question2);
        Question question3 = new Question("I do not understand people who get emotional.", 3, 8);
        addQuestion(question3);
        Question question4 = new Question("I have a good word for everyone.", 3, 14);
        addQuestion(question4);
        Question question5 = new Question("I do not get back at people who insult me.", 3, 13);
        addQuestion(question5);
        Question question6 = new Question("I feel others' emotions.", 3, 8);
        addQuestion(question6);
        Question question7 = new Question("I do not have a sharp tongue.", 3, 14);
        addQuestion(question7);
        Question question8 = new Question("I seldom feel weepy while reading the sad part of a story.", 3, 8);
        addQuestion(question8);
*/


        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
