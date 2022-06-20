package com.example.leadnurturing.Data;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Lead.db";
    private static final int DATABASE_VERSION = 3;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " + Contract.StudentEntry.TABLE_NAME + "("
                + Contract.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.StudentEntry.COLUMN_STUDENT_IMAGE + "BLOB, "
                + Contract.StudentEntry.COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
                + Contract.StudentEntry.COLUMN_STUDENT_ADDRESS + " TEXT NOT NULL, "
                + Contract.StudentEntry.COLUMN_STUDENT_PHONE + " TEXT NOT NULL, "
                + Contract.StudentEntry.COLUMN_STUDENT_EMAIL + " TEXT NOT NULL, "
                + Contract.StudentEntry.COLUMN_STUDENT_STATE + " TEXT NOT NULL, "
                + Contract.StudentEntry.COLUMN_STUDENT_LEAD_SCORE + " INTEGER NOT NULL DEFAULT 0, "
                + Contract.StudentEntry.COLUMN_STUDENT_10TH_BOARD + " TEXT, "
                + Contract.StudentEntry.COLUMN_STUDENT_10TH_SCHOOL + " TEXT, "
                + Contract.StudentEntry.COLUMN_STUDENT_10TH_MARKS + " REAL, "
                + Contract.StudentEntry.COLUMN_STUDENT_10TH_YEAR + " INTEGER, "
                + Contract.StudentEntry.COLUMN_STUDENT_12TH_BOARD + " TEXT, "
                + Contract.StudentEntry.COLUMN_STUDENT_12TH_SCHOOL + " TEXT, "
                + Contract.StudentEntry.COLUMN_STUDENT_12TH_MARKS + " REAL, "
                + Contract.StudentEntry.COLUMN_STUDENT_12TH_YEAR + " INTEGER, "
                + Contract.StudentEntry.COLUMN_STUDENT_JEE_RANK + " REAL, "
                + Contract.StudentEntry.COLUMN_STUDENT_WB_JEE_RANK + " REAL, "
                + Contract.StudentEntry.COLUMN_STUDENT_COUNSELOR_ASSIGNED + " INTEGER DEFAULT 0, "
                + Contract.StudentEntry.COLUMN_STUDENT_STATUS + " INTEGER DEFAULT "+Contract.StudentEntry.STATUS_IDLE+");";

        String SQL_CREATE_COUNSELOR_TABLE = "CREATE TABLE " + Contract.CounselorEntry.TABLE_NAME + "("
                + Contract.CounselorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.CounselorEntry.COLUMN_COUNSELOR_NAME + " TEXT NOT NULL, "
                + Contract.CounselorEntry.COLUMN_COUNSELOR_ADDRESS + " TEXT NOT NULL, "
                + Contract.CounselorEntry.COLUMN_COUNSELOR_PHONE + " TEXT NOT NULL, "
                + Contract.CounselorEntry.COLUMN_COUNSELOR_EMAIL + " VARCHAR(30) NOT NULL, "
                +Contract.CounselorEntry.COLUMN_COUNSELOR_PASSWORD + "TEXT NOT NULL, "
                + Contract.CounselorEntry.COLUMN_COUNSELOR_APPLICANT + " INTEGER NOT NULL DEFAULT 0, "
                + Contract.CounselorEntry.COLUMN_COUNSELOR_SUCCESSFUL_APPLICANT + "INTEGER NOT NULL DEFAULT 0);";

       try {
           db.execSQL(SQL_CREATE_STUDENT_TABLE);
           db.execSQL(SQL_CREATE_COUNSELOR_TABLE);

       } catch (SQLException e) {
           Log.v("ERROR","ERROR CREATING TABLE");
       }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Contract.StudentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.CounselorEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }
}
