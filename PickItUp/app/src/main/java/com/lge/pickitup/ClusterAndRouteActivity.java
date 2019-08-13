package com.lge.pickitup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClusterAndRouteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "ClusterAndRouteActivity";
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mSdf;
    private String mOldDateStr;

    private int mCourierNumber;
    private TextView mTextCourierNumber;
    private TextView mTextCourierDate;
    private Button mBtnClusterAndRoute;
    private View.OnTouchListener mTouchListner;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_and_route);

        mTextCourierNumber = findViewById(R.id.text_courier_number);
        mTextCourierDate = findViewById(R.id.text_courier_date2);
        mBtnClusterAndRoute = findViewById(R.id.btn_process_cluster_and_route);

        mTouchListner = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setBackgroundColor(0xFFE91E63);
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.setBackgroundColor(0xFF42A5F5);
                }
                return false;
            }
        };
        mBtnClusterAndRoute.setOnTouchListener(mTouchListner);
        mBtnClusterAndRoute.setOnClickListener(this);
        mTextCourierDate.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        String dateStr;
        mOldDateStr = mTextCourierDate.getText().toString();

        if (b != null) {
            dateStr = b.getString(Utils.KEY_DB_DATE);
            mCourierNumber = Integer.parseInt(b.getString(Utils.KEY_COURIER_NUMBER));
        } else {
            dateStr = Utils.getTodayDateStr();
            mCourierNumber = -1;
        }

        mTextCourierDate.setText(dateStr);
        if (mCourierNumber == -1) {
            mTextCourierNumber.setText(getString(R.string.default_courier_number));
        } else {
            mTextCourierNumber.setText(Integer.toString(mCourierNumber));
        }

        mSdf = new SimpleDateFormat("yyyy-MM-dd");


        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mTextCourierDate.setText(mSdf.format(newDate.getTime()));
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_process_cluster_and_route:
                Log.i(LOG_TAG, "Process Clustering & Routing");
                process_cluster_and_route(
                        mTextCourierDate.getText().toString(), mCourierNumber);
                break;
            case R.id.text_courier_date2:
                mDatePickerDialog.show();
                break;
        }
    }

    private void process_cluster_and_route(String date, int clusterNum) {
        TmsWASConnector conn = TmsWASConnector.getInstance();
        conn.getCluster(0);
    }
}
