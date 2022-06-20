package com.example.leadnurturing;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.leadnurturing.Data.Contract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StudentProfile extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mCurrentUri;

    private EditText mNameEditText;

    private EditText mAddressEditText;

    private EditText mEmailEditText;

    private EditText mPhoneEditText;

    private EditText mStateEditText;

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

    private int mAccess = 0;

    private static final int Student_Loader = 0;

    private String name;
    private String address;
    private String state;
    private String email;
    private String phone;
    private String board10;
    private String school10;
    private String marks10;
    private String year10;
    private String board12;
    private String school12;
    private String marks12;
    private String year12;
    private String wbJee;
    private String Jee;

    private int leadScore = 0;

    private FloatingActionButton fab_Contacts;

    private FloatingActionButton fab_Call;

    private FloatingActionButton fab_Message;

    private FloatingActionButton fab_Mail;

    private Button Add;
    private Button Minus;
    private Button discard;
    private Button admit;
    private TextView mLeadScoreText;
    private TextView mStudentAdmitText;
    private TextView mStudentDiscardText;
    private LinearLayout mLeadLayout;
    @Override
    @SuppressLint("QueryPermissionsNeeded")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentprofile);

        init();

        Intent intent = getIntent();
        mCurrentUri = intent.getData();

        LoaderManager.getInstance(this).initLoader(Student_Loader, null, this);

        fab_Call.setOnClickListener(v -> {
            Intent intent13 = new Intent(Intent.ACTION_DIAL);
            intent13.setData(Uri.parse("tel:" + phone));
            startActivity(intent13);
        });

        fab_Message.setOnClickListener(v -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null)));
                }
        );

        fab_Mail.setOnClickListener(v -> {
            String[] TO = new String[]{email};
            Intent intent12 = new Intent(Intent.ACTION_SENDTO);
            intent12.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent12.putExtra(Intent.EXTRA_EMAIL, TO);
            if (intent12.resolveActivity(getPackageManager()) != null) {
                startActivity(intent12);

            }
        });
        Add.setOnClickListener(v -> updateLeadScore(1));
        Minus.setOnClickListener(v -> updateLeadScore(0));

        admit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAdmitConfirmationDialog();
            }
        });
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscardConfirmationDialog();
            }
        });
    }
    private void updateLeadScore(int i) {
        int lead_Score = leadScore;
        ContentValues values = new ContentValues();
        if (i == 0) {
            if (lead_Score<5) {
                return;
            }
            if(lead_Score == 100)
                values.put(Contract.StudentEntry.COLUMN_STUDENT_STATUS, Contract.StudentEntry.STATUS_IN_PROGRESS);
            lead_Score -= 5;
        }
        if(i == 1){
            if (lead_Score == 95) {
                return;
            }
                if(lead_Score == -1)
                {
                    lead_Score = 0;
                    values.put(Contract.StudentEntry.COLUMN_STUDENT_STATUS, Contract.StudentEntry.STATUS_IN_PROGRESS);
                }
            lead_Score += 5;
        }
        if(i == 2)
        {
            lead_Score=100;
        values.put(Contract.StudentEntry.COLUMN_STUDENT_STATUS, Contract.StudentEntry.STATUS_COMPLETED);
        }
        if(i == 3)
        {
            lead_Score=-1;
            values.put(Contract.StudentEntry.COLUMN_STUDENT_STATUS, Contract.StudentEntry.STATUS_DISCARDED);
        }
        values.put(Contract.StudentEntry.COLUMN_STUDENT_LEAD_SCORE, lead_Score);
        getContentResolver().update(mCurrentUri, values, null, null);
    }

    private void init() {
        mNameEditText = findViewById(R.id.text_name);
        mAddressEditText = findViewById(R.id.text_address);
        mEmailEditText = findViewById(R.id.text_mail);
        mPhoneEditText = findViewById(R.id.text_phNo);
        mStateEditText = findViewById(R.id.text_state);
        m10boardEditText = findViewById(R.id.text_10_board);
        m10schoolEditText = findViewById(R.id.text_10_school);
        m10marksEditText = findViewById(R.id.text_10_marks);
        m10yearEditText = findViewById(R.id.text_10_year);
        m12boardEditText = findViewById(R.id.text_12_board);
        m12schoolEditText = findViewById(R.id.text_12_school);
        m12marksEditText = findViewById(R.id.text_12_marks);
        m12yearEditText = findViewById(R.id.text_12_year);
        mJeeEditText = findViewById(R.id.text_jee);
        mwbJEEditText = findViewById(R.id.text_wb_jee);
        Add = findViewById(R.id.lead_add);
        Minus = findViewById(R.id.lead_minus);
        mLeadScoreText = findViewById(R.id.lead_score_view);
        admit = findViewById(R.id.action_admitted);
        discard = findViewById(R.id.action_discard);
        mStudentAdmitText = findViewById(R.id.student_admit);
        mStudentDiscardText = findViewById(R.id.student_discard);
        mLeadLayout = findViewById(R.id.edit_lead);
        fab_Contacts = findViewById(R.id.fab_Contacts);
        fab_Call = findViewById(R.id.fab_call);
        fab_Message = findViewById(R.id.fab_message);
        fab_Mail = findViewById(R.id.fab_mail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studentprofile_menu, menu);
        if (mAccess == 0) {
            EditTextOff();
            fab_Contacts.setVisibility(View.VISIBLE);
            MenuItem menuItem = menu.findItem(R.id.action_edit);
            menuItem.setVisible(true);
            MenuItem menuItem1 = menu.findItem(R.id.action_save_update);
            menuItem1.setVisible(false);

        }
        if (mAccess == 1) {
            EditTextOn();
            fab_Contacts.setVisibility(View.GONE);
            MenuItem menuItem = menu.findItem(R.id.action_edit);
            menuItem.setVisible(false);
            MenuItem menuItem1 = menu.findItem(R.id.action_save_update);
            menuItem1.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            mAccess = 1;
            mLeadLayout.setVisibility(View.GONE);
            invalidateOptionsMenu();
        }
        if (item.getItemId() == R.id.action_save_update) {
            updateStudent();
            mAccess = 0;
            mLeadLayout.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            LoaderManager.getInstance(this).initLoader(Student_Loader, null, this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStudent() {
        name = mNameEditText.getText().toString().trim();
        name = setValueOnly(name, mNameEditText);
        address = mAddressEditText.getText().toString().trim();
        address = setValueOnly(address, mAddressEditText);
        state = mStateEditText.getText().toString().trim();
        state = setValueOnly(state, mStateEditText);
        email = mEmailEditText.getText().toString().trim();
        email = setValueOnly(email, mEmailEditText);
        phone = mPhoneEditText.getText().toString().trim();
        phone = setValueOnly(phone, mPhoneEditText);
        board10 = m10boardEditText.getText().toString().trim();
        board10 = setValueOnly(board10, m10boardEditText);
        school10 = m10schoolEditText.getText().toString().trim();
        school10 = setValueOnly(school10, m10schoolEditText);
        marks10 = m10marksEditText.getText().toString().trim();
        marks10 = setValueOnly(marks10, m10marksEditText);
        year10 = m10yearEditText.getText().toString().trim();
        year10 = setValueOnly(year10, m10yearEditText);
        board12 = m12boardEditText.getText().toString().trim();
        board12 = setValueOnly(board12, m12boardEditText);
        school12 = m12schoolEditText.getText().toString().trim();
        school12 = setValueOnly(school12, m12schoolEditText);
        marks12 = m12marksEditText.getText().toString().trim();
        marks12 = setValueOnly(marks12, m12marksEditText);
        year12 = m12yearEditText.getText().toString().trim();
        year12 = setValueOnly(year12, m12yearEditText);
        wbJee = mwbJEEditText.getText().toString().trim();
        wbJee = setValueOnly(wbJee, mwbJEEditText);
        Jee = mJeeEditText.getText().toString().trim();
        Jee = setValueOnly(Jee, mJeeEditText);

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
                Contract.StudentEntry.COLUMN_STUDENT_WB_JEE_RANK,
                Contract.StudentEntry.COLUMN_STUDENT_JEE_RANK,
                Contract.StudentEntry.COLUMN_STUDENT_LEAD_SCORE
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
            int leadColumnIndex = cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_LEAD_SCORE);
            // Extract out the value from the Cursor for the given column index
            name = cursor.getString(nameColumnIndex);
            address = cursor.getString(addressColumnIndex);
            state = cursor.getString(stateColumnIndex);
            phone = cursor.getString(phoneColumnIndex);
            email = cursor.getString(emailColumnIndex);
            board10 = cursor.getString(board10ColumnIndex);
            school10 = cursor.getString(school10ColumnIndex);
            marks10 = cursor.getString(marks10ColumnIndex);
            year10 = cursor.getString(year10ColumnIndex);
            board12 = cursor.getString(board12ColumnIndex);
            school12 = cursor.getString(school12ColumnIndex);
            marks12 = cursor.getString(marks12ColumnIndex);
            year12 = cursor.getString(year12ColumnIndex);
            wbJee = cursor.getString(wbJeeColumnIndex);
            Jee = cursor.getString(jeeColumnIndex);
            leadScore = cursor.getInt(leadColumnIndex);
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
            mLeadScoreText.setText(String.valueOf(leadScore));

            if(leadScore == -1)
            {
                admit.setVisibility(View.GONE);
                discard.setVisibility(View.GONE);
                mStudentDiscardText.setVisibility(View.VISIBLE);
                mStudentAdmitText.setVisibility(View.GONE);
            }
            if(leadScore == 100)
            {
                admit.setVisibility(View.GONE);
                discard.setVisibility(View.GONE);
                mStudentAdmitText.setVisibility(View.VISIBLE);
                mStudentDiscardText.setVisibility(View.GONE);
            }
            if(leadScore >=0 && leadScore<100)
            {
                admit.setVisibility(View.VISIBLE);
                discard.setVisibility(View.VISIBLE);
                mStudentAdmitText.setVisibility(View.GONE);
                mStudentDiscardText.setVisibility(View.GONE);
            }
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
        mLeadScoreText.setText("0");
    }

    private void EditTextOn() {

        setReadOnly(mNameEditText, false);
        setReadOnly(mAddressEditText, false);
        setReadOnly(mStateEditText, false);
        setReadOnly(mEmailEditText, false);
        setReadOnly(mPhoneEditText, false);
        setReadOnly(m10boardEditText, false);
        setReadOnly(m10schoolEditText, false);
        setReadOnly(m10marksEditText, false);
        setReadOnly(m10yearEditText, false);
        setReadOnly(m12boardEditText, false);
        setReadOnly(m12schoolEditText, false);
        setReadOnly(m12marksEditText, false);
        setReadOnly(m12yearEditText, false);
        setReadOnly(mwbJEEditText, false);
        setReadOnly(mJeeEditText, false);
    }

    private void EditTextOff() {

        setReadOnly(mNameEditText, true);
        setReadOnly(mAddressEditText, true);
        setReadOnly(mStateEditText, true);
        setReadOnly(mEmailEditText, true);
        setReadOnly(mPhoneEditText, true);
        setReadOnly(m10boardEditText, true);
        setReadOnly(m10schoolEditText, true);
        setReadOnly(m10marksEditText, true);
        setReadOnly(m10yearEditText, true);
        setReadOnly(m12boardEditText, true);
        setReadOnly(m12schoolEditText, true);
        setReadOnly(m12marksEditText, true);
        setReadOnly(m12yearEditText, true);
        setReadOnly(mwbJEEditText, true);
        setReadOnly(mJeeEditText, true);
    }

    private static void setReadOnly(final TextView view, final boolean readOnly) {
        view.setFocusable(!readOnly);
        view.setFocusableInTouchMode(!readOnly);
        view.setClickable(!readOnly);
        view.setLongClickable(!readOnly);
        view.setCursorVisible(!readOnly);
    }

    private static String setValueOnly(String str, final TextView view) {
        if (str == null) {
            str = (String) view.getText();
        }
        return str;
    }

    private void showAdmitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Admit this Student?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateLeadScore(2);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, i) -> {

            if (dialog != null)
                dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDiscardConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard this Student?");
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            updateLeadScore(3);
            finish();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, i) -> {
            if (dialog != null)
                dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
