package com.example.leadnurturing.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbProvider extends ContentProvider {

    public static final String LOG_TAG = DbProvider.class.getSimpleName();

    private static final int STUDENT = 100;
    private static final int COUNSELOR = 200;
    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int STUDENT_ID = 101;
    private static final int COUNSELOR_ID = 201;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.StudentEntry.PATH_STUDENT, STUDENT);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.CounselorEntry.PATH_COUNSELOR, COUNSELOR);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PET_ID}. This URI is used to provide access to ONE single row
        // of the pets table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.StudentEntry.PATH_STUDENT + "/#", STUDENT_ID);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.CounselorEntry.PATH_COUNSELOR+ "/#", COUNSELOR_ID);
    }

    private DbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        mDbHelper= new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database=mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match= sUriMatcher.match(uri);
        switch(match)
        {
            case STUDENT:
                cursor = database.query(Contract.StudentEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case COUNSELOR:
                cursor = database.query(Contract.CounselorEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case STUDENT_ID:
                selection= Contract.StudentEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(Contract.StudentEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case COUNSELOR_ID:
                selection= Contract.CounselorEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(Contract.CounselorEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default: throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENT:
                // Delete all rows that match the selection and selection args
                return Contract.StudentEntry.CONTENT_LIST_STUDENT_TYPE;
            case STUDENT_ID:
                return Contract.StudentEntry.CONTENT_ITEM_STUDENT_TYPE;
            case COUNSELOR:
                return Contract.CounselorEntry.CONTENT_LIST_COUNSELOR_TYPE;
            case COUNSELOR_ID:
                return Contract.CounselorEntry.CONTENT_ITEM_COUNSELOR_TYPE;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENT:
                assert contentValues != null;
                return insertStudent(uri, contentValues);
            case COUNSELOR:
                assert contentValues != null;
                return insertCounselor(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertStudent(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Student requires a name");
        }
        String address = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_ADDRESS);
        if (address == null) {
            throw new IllegalArgumentException("Student requires a address");
        }
        String state = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_STATE);
        if (state== null) {
            throw new IllegalArgumentException("Student requires a state");
        }
        String mail = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_EMAIL);
        if (mail == null) {
            throw new IllegalArgumentException("Student requires a email");
        }
        String phone = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_PHONE);
        if (phone == null && phone.length()<10) {
            throw new IllegalArgumentException("Student requires valid phone number");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new pet with the given values
        long id = database.insert(Contract.StudentEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertCounselor(Uri uri, ContentValues values)
    {
        String name = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Counselor requires a name");
        }
        String address = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_ADDRESS);
        if (address == null) {
            throw new IllegalArgumentException("Counselor requires a address");
        }
        String mail = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_EMAIL);
        if (mail == null) {
            throw new IllegalArgumentException("Counselor requires a email");
        }
        String phone = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_PHONE);
        if (phone == null && phone.length()<10) {
            throw new IllegalArgumentException("Counselor requires valid phone number");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new pet with the given values
        long id = database.insert(Contract.StudentEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
         int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENT:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(Contract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_ID:
                // Delete a single row given by the ID in the URI
                selection = Contract.StudentEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(Contract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case COUNSELOR:
                rowsDeleted = database.delete(Contract.CounselorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case COUNSELOR_ID:
                selection = Contract.CounselorEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(Contract.CounselorEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENT:
                return updateStudent(uri, contentValues, selection, selectionArgs);
            case STUDENT_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = Contract.StudentEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateStudent(uri, contentValues, selection, selectionArgs);
            case COUNSELOR:
                return updateCounselor(uri, contentValues, selection, selectionArgs);
            case COUNSELOR_ID:
                selection = Contract.CounselorEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateCounselor(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateStudent(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(Contract.StudentEntry.COLUMN_STUDENT_NAME)) {
            String name = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Student requires a name");
            }
        }

        if (values.containsKey(Contract.StudentEntry.COLUMN_STUDENT_ADDRESS)) {
            String address = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_ADDRESS);
            if (address == null) {
                throw new IllegalArgumentException("Student requires a address");
            }
        }
        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(Contract.StudentEntry.COLUMN_STUDENT_STATE)) {
            String state = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_STATE);
            if (state == null) {
                throw new IllegalArgumentException("Student requires a state");
            }
        }
        if (values.containsKey(Contract.StudentEntry.COLUMN_STUDENT_EMAIL)) {
            String email= values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_EMAIL);
            if (email == null) {
                throw new IllegalArgumentException("Student requires a Email");
            }
        }
        if (values.containsKey(Contract.StudentEntry.COLUMN_STUDENT_PHONE)) {
            String phone = values.getAsString(Contract.StudentEntry.COLUMN_STUDENT_PHONE);
            if (phone == null && phone.length()<10) {
                throw new IllegalArgumentException("Student requires valid phone number");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Returns the number of database rows affected by the update statement
        int rowsUpdated = database.update(Contract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
    private int updateCounselor(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        if (values.containsKey(Contract.CounselorEntry.COLUMN_COUNSELOR_NAME)) {
            String name = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Counselor requires a name");
            }
        }

        if (values.containsKey(Contract.CounselorEntry.COLUMN_COUNSELOR_ADDRESS)) {
            String address = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_ADDRESS);
            if (address == null) {
                throw new IllegalArgumentException("Counselor requires a address");
            }
        }
        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.

        if (values.containsKey(Contract.CounselorEntry.COLUMN_COUNSELOR_EMAIL)) {
            Integer email= values.getAsInteger(Contract.CounselorEntry.COLUMN_COUNSELOR_EMAIL);
            if (email == null) {
                throw new IllegalArgumentException("Counselor requires a Email");
            }
        }
        if (values.containsKey(Contract.CounselorEntry.COLUMN_COUNSELOR_PHONE)) {

            String phone = values.getAsString(Contract.CounselorEntry.COLUMN_COUNSELOR_PHONE);
            if (phone == null && phone.length()<10) {
                throw new IllegalArgumentException("Counselor requires valid phone number");
            }
        }

        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(Contract.CounselorEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
