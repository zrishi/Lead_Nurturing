package com.example.leadnurturing.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {
   private Contract()
    {}

    public static final String CONTENT_AUTHORITY = "com.example.leadnurturing";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class StudentEntry implements BaseColumns
    {
        public static final String PATH_STUDENT = "students";
        public static final Uri CONTENT_STUDENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STUDENT);
        public final static String TABLE_NAME = "students";
        public final static String TABLE_NAME_DETAILS = "studentDetails";
        public static final String CONTENT_LIST_STUDENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENT;
        public static final String CONTENT_ITEM_STUDENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENT;

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_STUDENT_IMAGE = "image";
        public final static String COLUMN_STUDENT_NAME = "name";
        public final static String COLUMN_STUDENT_ADDRESS="address";
        public final static String COLUMN_STUDENT_PHONE="phone";
        public final static String COLUMN_STUDENT_EMAIL="email";
        public final static String COLUMN_STUDENT_STATE="state";
        public final static String COLUMN_STUDENT_STATUS="status";
        public final static String COLUMN_STUDENT_LEAD_SCORE="leadScore";
        public final static String COLUMN_STUDENT_10TH_BOARD="tenBoard";
        public final static String COLUMN_STUDENT_10TH_SCHOOL="tenSchool";
        public final static String COLUMN_STUDENT_10TH_MARKS="tenMarks";
        public final static String COLUMN_STUDENT_12TH_BOARD="twelveBoard";
        public final static String COLUMN_STUDENT_12TH_SCHOOL="twelveSchool";
        public final static String COLUMN_STUDENT_12TH_MARKS="twelveMarks";
        public final static String COLUMN_STUDENT_COUNSELOR_ASSIGNED="C_assigned";
        public final static String COLUMN_STUDENT_JEE_RANK="JEE_RANK";
        public final static String COLUMN_STUDENT_WB_JEE_RANK="WB_JEE_RANK";
        public final static String COLUMN_STUDENT_10TH_YEAR="tenPassingYear";
        public final static String COLUMN_STUDENT_12TH_YEAR="twelvePassingYear";

        public static final int STATUS_IDLE= 0;
        public static final int STATUS_IN_PROGRESS = 1;
        public static final int STATUS_COMPLETED = 2;
        public static final int STATUS_DISCARDED = 3;

    }

    public static final class CounselorEntry implements BaseColumns
    {

        public static final String PATH_COUNSELOR = "counselor";
        public static final Uri CONTENT_COUNSELOR_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COUNSELOR);
        public final static String TABLE_NAME = "counselor";

        public static final String CONTENT_LIST_COUNSELOR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COUNSELOR;

        public static final String CONTENT_ITEM_COUNSELOR_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COUNSELOR;

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_COUNSELOR_NAME = "name";
        public final static String COLUMN_COUNSELOR_ADDRESS="address";
        public final static String COLUMN_COUNSELOR_PHONE="phone";
        public final static String COLUMN_COUNSELOR_EMAIL="email";
        public final static String COLUMN_COUNSELOR_PASSWORD="password";
        public final static String COLUMN_COUNSELOR_APPLICANT="applicant";
        public final static String COLUMN_COUNSELOR_SUCCESSFUL_APPLICANT="S_applicant";

    }
}
