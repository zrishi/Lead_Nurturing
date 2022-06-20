package com.example.leadnurturing;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.leadnurturing.Data.Contract;

import java.util.ArrayList;

public class StudentCursorAdapter extends CursorAdapter {
    private int studentLeadScore;
    String lead_score = "Lead Score: ";
    private static int mAccessId;
    public StudentCursorAdapter(Context context, Cursor c,int AccessId) {
        super(context, c, 0);
        mAccessId=AccessId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView=view.findViewById(R.id.name);
        TextView state_or_leadTextView=view.findViewById(R.id.state);
        TextView statusTextView=view.findViewById(R.id.status);

        ArrayList<String> statusTittle=new ArrayList<>();
        statusTittle.add("IDLE");
        statusTittle.add("IN PROGRESS");
        statusTittle.add("COMPLETED");
        statusTittle.add("DISCARDED");

        ArrayList<Integer>statusColor=new ArrayList<>();

        statusColor.add(R.color.IDLE);
        statusColor.add(R.color.PROGRESS);
        statusColor.add(R.color.COMPLETED);
        statusColor.add(R.color.DISCARDED);

        int nameColumnIndex=cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_NAME);

        if(mAccessId==1){
            int stateColumnIndex=cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_STATE);
            String studentState = cursor.getString(stateColumnIndex);
            state_or_leadTextView.setText(studentState);
        }
        if(mAccessId == 2)
        {
            int leadColumnIndex=cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_LEAD_SCORE);
            studentLeadScore = cursor.getInt(leadColumnIndex);
            state_or_leadTextView.setText(String.format("%s%s", lead_score, String.valueOf(studentLeadScore)));
        }
        int statusColumnIndex=cursor.getColumnIndexOrThrow(Contract.StudentEntry.COLUMN_STUDENT_STATUS);

        String studentName = cursor.getString(nameColumnIndex);

        int studentStatus = cursor.getInt(statusColumnIndex);

        nameTextView.setText(studentName);
        statusTextView.setText(statusTittle.get(studentStatus));
        statusTextView.setTextColor(ContextCompat.getColor(context,statusColor.get(studentStatus)));
        }
    }

