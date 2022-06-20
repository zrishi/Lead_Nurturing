package com.example.leadnurturing.ui.applicant;


import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.leadnurturing.Data.Contract;
import com.example.leadnurturing.EditorActivity;
import com.example.leadnurturing.R;
import com.example.leadnurturing.StudentCursorAdapter;
import com.example.leadnurturing.databinding.FragmentApplicantBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ApplicantFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentApplicantBinding binding;
    private static final int Student_Loader = 0;
    StudentCursorAdapter mCursorLoader;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_applicant, container, false);

        FloatingActionButton fab=view.findViewById(R.id.fab);
        fab.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), EditorActivity.class);
            startActivity(intent);
        });

        ListView studentListView = view.findViewById(R.id.list_applicant);
        View emptyView = view.findViewById(R.id.empty_applicant_view);
        studentListView.setEmptyView(emptyView);

        mCursorLoader = new StudentCursorAdapter(view.getContext(), null,1);
        studentListView.setAdapter(mCursorLoader);

        studentListView.setOnItemClickListener((adapterView, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), EditorActivity.class);

            Uri currentUri= ContentUris.withAppendedId(Contract.StudentEntry.CONTENT_STUDENT_URI,id);

            intent.setData(currentUri);

            intent.putExtra("Access",1);
            startActivity(intent);
        });

        LoaderManager.getInstance(this).initLoader(Student_Loader,null,this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                Contract.StudentEntry._ID,
                Contract.StudentEntry.COLUMN_STUDENT_NAME,
                Contract.StudentEntry.COLUMN_STUDENT_STATE,
                Contract.StudentEntry.COLUMN_STUDENT_STATUS
        };
        String selection= Contract.StudentEntry.COLUMN_STUDENT_STATUS+"=?";
        String[] selectionArgs=new String[]{String.valueOf(Contract.StudentEntry.STATUS_IDLE)};
        return new CursorLoader(getContext(), Contract.StudentEntry.CONTENT_STUDENT_URI, projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursorLoader.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorLoader.swapCursor(null);
    }
}