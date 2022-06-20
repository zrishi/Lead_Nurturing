package com.example.leadnurturing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leadnurturing.Data.Contract;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText mNameEditText;

    private EditText mAddressEditText;

    private EditText mEmailEditText;

    private EditText mPhoneEditText;

    private AutoCompleteTextView mStateEditText;

    private EditText m10boardEditText;

    private EditText m10schoolEditText;

    private EditText m10marksEditText;

    private EditText m10yearEditText;

    private EditText m12boardEditText;

    private EditText m12schoolEditText;

    private EditText m12marksEditText;

    private EditText m12yearEditText;

    private EditText mwbJEEditText;

    private EditText mJeeEditText;

    private Button btn;
    private String[] indian_states;

    private Uri mCurrentUri;

    private static final int EXISTING_LOADER = 0;

    private boolean mHasChanged = false;

    private int mAccess;


    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHasChanged = true;
            return false;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        init();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, indian_states);
        mStateEditText.setThreshold(1);
        mStateEditText.setAdapter(adapter);

        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        mAccess = intent.getIntExtra("Access", 0);

        if (mCurrentUri == null) {
            setTitle(getString(R.string.edit_tittle_add));
            LinearLayout linearLayout = findViewById(R.id.edit_qualification);
            LinearLayout linearLayout1 = findViewById(R.id.edit_rank);
            linearLayout.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.edit_tittle_view));
            if (mAccess == 1) {
                EditTextOff();
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(view -> {
                    updateStatus();
                    finish();
                });
            }
            LoaderManager.getInstance(this).initLoader(EXISTING_LOADER, null, this);
        }

        mNameEditText.setOnTouchListener(mTouchListener);
        mAddressEditText.setOnTouchListener(mTouchListener);
        mEmailEditText.setOnTouchListener(mTouchListener);
        mPhoneEditText.setOnTouchListener(mTouchListener);
        mStateEditText.setOnTouchListener(mTouchListener);
        m10boardEditText.setOnTouchListener(mTouchListener);
        m10schoolEditText.setOnTouchListener(mTouchListener);
        m10marksEditText.setOnTouchListener(mTouchListener);
        m10yearEditText.setOnTouchListener(mTouchListener);
        m12boardEditText.setOnTouchListener(mTouchListener);
        m12schoolEditText.setOnTouchListener(mTouchListener);
        m12marksEditText.setOnTouchListener(mTouchListener);
        m12yearEditText.setOnTouchListener(mTouchListener);

    }

    private static void setReadOnly(@NonNull final TextView view) {
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        view.setClickable(false);
        view.setLongClickable(false);
        view.setCursorVisible(false);
    }

    private void EditTextOff() {
        setReadOnly( mNameEditText);
        setReadOnly( mAddressEditText);
        setReadOnly( mStateEditText);
        setReadOnly( mEmailEditText);
        setReadOnly( mPhoneEditText);
        setReadOnly( m10boardEditText);
        setReadOnly( m10schoolEditText);
        setReadOnly( m10marksEditText);
        setReadOnly( m10yearEditText);
        setReadOnly( m12boardEditText);
        setReadOnly( m12schoolEditText);
        setReadOnly( m12marksEditText);
        setReadOnly( m12yearEditText);
        setReadOnly( mwbJEEditText);
        setReadOnly( mJeeEditText);
    }

    private void init() {
        mNameEditText = findViewById(R.id.edit_name);
        mAddressEditText = findViewById(R.id.edit_address);
        mEmailEditText = findViewById(R.id.edit_mail);
        mPhoneEditText = findViewById(R.id.edit_phNo);
        mStateEditText = findViewById(R.id.edit_state);
        m10boardEditText = findViewById(R.id.edit_10_board);
        m10schoolEditText = findViewById(R.id.edit_10_school);
        m10marksEditText = findViewById(R.id.edit_10_marks);
        m10yearEditText = findViewById(R.id.edit_10_year);
        m12boardEditText = findViewById(R.id.edit_12_board);
        m12schoolEditText = findViewById(R.id.edit_12_school);
        m12marksEditText = findViewById(R.id.edit_12_marks);
        m12yearEditText = findViewById(R.id.edit_12_year);
        mwbJEEditText = findViewById(R.id.edit_wb_jee);
        mJeeEditText = findViewById(R.id.edit_jee);

        btn = findViewById(R.id.btn_save);

        indian_states = new String[]{"Andaman and Nicobar Islands",
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chandigarh",
                "Chhattisgarh",
                "Dadra and Nagar Haveli and Daman and Diu",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Ladakh",
                "Lakshadweep",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Delhi",
                "Odisha",
                "Puducherry",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttarakhand",
                "Uttar Pradesh",
                "West Bengal"};
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mCurrentUri != null) {
            if(mAccess == 1)
            {
                MenuItem menuItem = menu.findItem(R.id.action_save);
                menuItem.setVisible(false);
            }
            if (mAccess == 2) {
                MenuItem menuItem1 = menu.findItem(R.id.action_delete);
                menuItem1.setVisible(false);
            }
        }
        if (mCurrentUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    private void updateStatus() {
        ContentValues values = new ContentValues();
        values.put(Contract.StudentEntry.COLUMN_STUDENT_STATUS, Contract.StudentEntry.STATUS_IN_PROGRESS);
        int rowsAffected = getContentResolver().update(mCurrentUri, values, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, getString(R.string.editor_status_update_student_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_status_update_student_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStudent() {
        String name = mNameEditText.getText().toString().trim();
        String address = mAddressEditText.getText().toString().trim();
        String state = mStateEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        String board10 = m10boardEditText.getText().toString().trim();
        String school10 = m10schoolEditText.getText().toString().trim();
        String marks10 = m10marksEditText.getText().toString().trim();
        String year10 = m10yearEditText.getText().toString().trim();
        String board12 = m12boardEditText.getText().toString().trim();
        String school12 = m12schoolEditText.getText().toString().trim();
        String marks12 = m12marksEditText.getText().toString().trim();
        String year12 = m12yearEditText.getText().toString().trim();
        String wbJee = mwbJEEditText.getText().toString().trim();
        String Jee = mJeeEditText.getText().toString().trim();

        ContentValues values = new ContentValues();

        values.put(Contract.StudentEntry.COLUMN_STUDENT_NAME, name);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_ADDRESS, address);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_STATE, state);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_EMAIL, email);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_PHONE, phone);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_10TH_BOARD, board10);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_10TH_SCHOOL, school10);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_10TH_MARKS, marks10);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_10TH_YEAR, year10);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_12TH_BOARD, board12);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_12TH_SCHOOL, school12);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_12TH_MARKS, marks12);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_12TH_YEAR, year12);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_WB_JEE_RANK, wbJee);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_JEE_RANK, Jee);

        int rowsAffected = getContentResolver().update(mCurrentUri, values, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, getString(R.string.editor_status_update_student_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_status_update_student_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get user input from editor and save pet into database.
     */
    private void saveStudent() {
        String name = mNameEditText.getText().toString().trim();
        String address = mAddressEditText.getText().toString().trim();
        String state = mStateEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();

        // Check if this is supposed to be a new student
        // and check if all the fields in the editor are blank
        if (mCurrentUri == null &&
                TextUtils.isEmpty(name) && TextUtils.isEmpty(address) &&
                TextUtils.isEmpty(state) && TextUtils.isEmpty(email) && TextUtils.isEmpty(phone)) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Contract.StudentEntry.COLUMN_STUDENT_NAME, name);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_ADDRESS, address);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_STATE, state);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_EMAIL, email);
        values.put(Contract.StudentEntry.COLUMN_STUDENT_PHONE, phone);

        if (mCurrentUri == null) {
            // This is a NEW Student, so insert a new student into the provider,
            // returning the content URI for the new student.
            Uri newUri = getContentResolver().insert(Contract.StudentEntry.CONTENT_STUDENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_student_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_student_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {

            if(mAccess == 2)
                updateStudent();

            if(mAccess == 0)
                 saveStudent();
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            showDeleteConfirmationDialog();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            if (!mHasChanged) {
                if (mAccess == 2) {
                    finish();
                    return true;
                }
                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                return true;
            }
            // Otherwise if there are unsaved changes, setup a dialog to warn the user.
            // Create a click listener to handle the user confirming that
            // changes should be discarded.
            DialogInterface.OnClickListener discardButtonClickListener =
                    (dialogInterface, i) -> {
                        // User clicked "Discard" button, navigate to parent activity.
                        if (mAccess == 2) {
                            finish();
                            return;
                        }
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    };

            // Show a dialog that notifies the user they have unsaved changes
            showUnsavedChangesDialog(discardButtonClickListener);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                (dialogInterface, i) -> {
                    // User clicked "Discard" button, close the current activity.
                    finish();
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                Contract.StudentEntry._ID,
                Contract.StudentEntry.COLUMN_STUDENT_NAME,
                Contract.StudentEntry.COLUMN_STUDENT_ADDRESS,
                Contract.StudentEntry.COLUMN_STUDENT_EMAIL,
                Contract.StudentEntry.COLUMN_STUDENT_PHONE,
                Contract.StudentEntry.COLUMN_STUDENT_STATE,
                Contract.StudentEntry.COLUMN_STUDENT_10TH_BOARD,
                Contract.StudentEntry.COLUMN_STUDENT_10TH_SCHOOL,
                Contract.StudentEntry.COLUMN_STUDENT_10TH_MARKS,
                Contract.StudentEntry.COLUMN_STUDENT_10TH_YEAR,
                Contract.StudentEntry.COLUMN_STUDENT_12TH_BOARD,
                Contract.StudentEntry.COLUMN_STUDENT_12TH_SCHOOL,
                Contract.StudentEntry.COLUMN_STUDENT_12TH_MARKS,
                Contract.StudentEntry.COLUMN_STUDENT_12TH_YEAR,
                Contract.StudentEntry.COLUMN_STUDENT_JEE_RANK,
                Contract.StudentEntry.COLUMN_STUDENT_WB_JEE_RANK
        };
        return new CursorLoader(this,
                mCurrentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_NAME);
            int addressColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_ADDRESS);
            int stateColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_STATE);
            int phoneColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_PHONE);
            int emailColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_EMAIL);
            int board10ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_10TH_BOARD);
            int school10ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_10TH_SCHOOL);
            int marks10ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_10TH_MARKS);
            int year10ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_10TH_YEAR);
            int board12ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_12TH_BOARD);
            int school12ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_12TH_SCHOOL);
            int marks12ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_12TH_MARKS);
            int year12ColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_12TH_YEAR);
            int wbJeeColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_WB_JEE_RANK);
            int jeeColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_JEE_RANK);
            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String address = cursor.getString(addressColumnIndex);
            String state = cursor.getString(stateColumnIndex);
            String phone = cursor.getString(phoneColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String board10 = cursor.getString(board10ColumnIndex);
            String school10 = cursor.getString(school10ColumnIndex);
            String marks10 = cursor.getString(marks10ColumnIndex);
            String year10 = cursor.getString(year10ColumnIndex);
            String board12 = cursor.getString(board12ColumnIndex);
            String school12 = cursor.getString(school12ColumnIndex);
            String marks12 = cursor.getString(marks12ColumnIndex);
            String year12 = cursor.getString(year12ColumnIndex);
            String wbJee = cursor.getString(wbJeeColumnIndex);
            String Jee = cursor.getString(jeeColumnIndex);
            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mAddressEditText.setText(address);
            mStateEditText.setText(state);
            mEmailEditText.setText(email);
            mPhoneEditText.setText(phone);
            m10boardEditText.setText(board10);
            m10schoolEditText.setText(school10);
            m10marksEditText.setText(marks10);
            m10yearEditText.setText(year10);
            m12boardEditText.setText(board12);
            m12schoolEditText.setText(school12);
            m12marksEditText.setText(marks12);
            m12yearEditText.setText(year12);
            mJeeEditText.setText(Jee);
            mwbJEEditText.setText(wbJee);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mNameEditText.setText("");
        mAddressEditText.setText("");
        mStateEditText.setText("");
        mEmailEditText.setText("");
        mPhoneEditText.setText("");
        m10boardEditText.setText("");
        m10schoolEditText.setText("");
        m10marksEditText.setText("");
        m10yearEditText.setText("");
        m12boardEditText.setText("");
        m12schoolEditText.setText("");
        m12marksEditText.setText("");
        m12yearEditText.setText("");
        mJeeEditText.setText("");
        mwbJEEditText.setText("");
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, (dialog, id) -> {
            // User clicked the "Keep editing" button, so dismiss the dialog
            // and continue editing the pet.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.action_delete, (dialogInterface, i) -> deleteStudent());
        builder.setNegativeButton(R.string.cancel, (dialog, i) -> {

            if (dialog != null)
                dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteStudent() {
        if (mCurrentUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_student_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_student_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }
}