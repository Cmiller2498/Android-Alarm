package com.example.timer20;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class timerActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editDay;
    EditText editMin;
    EditText editNoti;
    TextView showMinutes;
    TextView timeUp;
    TextView min;
    TextView days;
    Button btnStart;
    Button notiButton;
    CountDownTimer countDownTimer;
    public static String alarmMessage;

    public EditText getEditNoti() {
        return editNoti;
    }

    public void setEditNoti(EditText editNoti) {
        this.editNoti = editNoti;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMin = findViewById(R.id.editMinTimer);
        editDay = findViewById(R.id.editDayTimer);
        notiButton = findViewById(R.id.notiButton);
        showMinutes = findViewById(R.id.showMin);
        timeUp = findViewById(R.id.textViewTimeUp);
        min = findViewById(R.id.text_min);
        days = findViewById(R.id.text_days);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);


        editMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String sec = editMin.getText().toString();
                if (sec != null && sec != "" && sec.length() > 0) {
                    showMinutes.setText(editMin.getText());
                    timeUp.setVisibility(View.INVISIBLE);
                    showMinutes.setText("0");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        notiButton.setOnClickListener(new View.OnClickListener(){
            EditText editNoti = findViewById(R.id.editNotiText);
            @Override
            public void onClick(View v) {
                alarmMessage = editNoti.getText().toString();
                Toast.makeText(getApplicationContext(), "Note Added to Alarm", Toast.LENGTH_SHORT).show();
            }
        });

    }




    @Override
    public void onClick(View v) {
        if (editMin.getText().toString().trim().length() != 0) {

            int startMinTime = Integer.parseInt(editMin.getText().toString()) * 60000;

            Log.i("startMinTime", startMinTime + btnStart.getText().toString());

            if (v == btnStart && startMinTime > 0) {
                if (btnStart.getText().toString().equalsIgnoreCase("START")) {
                    Log.i("startMinTime Start", btnStart.getText().toString());
                    countDownTimer = new MyCountDownTimer(startMinTime, 10);
                    btnStart.setText("STOP");
                    timeUp.setVisibility(View.INVISIBLE);
                    editMin.setEnabled(false);
                    countDownTimer.start();
                } else {
                    Log.i("startMinTime Stop", btnStart.getText().toString());
                    btnStart.setText("START");
                    editMin.setEnabled(true);
                    countDownTimer.cancel();
                }
            }
        }
    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {

            int days = (int) ((millisUntilFinished / 1000) / 3600) / 24;
            int hours = (int) (millisUntilFinished / 1000) / 3600;
            int minutes = (int) ((millisUntilFinished / 1000) % 3600) / 60;
            int seconds = (int) (millisUntilFinished / 1000) % 60;

            String timeLeftFormatted;
            if (days > 0) {
                timeLeftFormatted = String.format(Locale.getDefault(),
                        "%d:%d:%02d:%02d",days, hours, minutes, seconds);
            } else if(hours > 0){
                timeLeftFormatted = String.format(Locale.getDefault(),
                        "%02d:%02d:%02d", hours, minutes, seconds);
            }else {
                timeLeftFormatted = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, seconds);
            }

            showMinutes.setText(timeLeftFormatted);
        }
        @Override
        public void onFinish() {
            btnStart.setText("START");
            editMin.setEnabled(true);
            timeUp.setVisibility(View.VISIBLE);
        }
    }
}