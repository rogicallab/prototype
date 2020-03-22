package com.example.notification4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmManager am;
    private PendingIntent pending;
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = this.findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                // 10sec
                calendar.add(Calendar.SECOND, 10);

                Intent intent = new Intent(getApplicationContext(), AlarmNotification.class);
                intent.putExtra("RequestCode",requestCode);

                pending = PendingIntent.getBroadcast(getApplicationContext(),requestCode,intent,0);

                // アラームをセット
                am = (AlarmManager)getSystemService(ALARM_SERVICE);

                if(am != null){
                    am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending);
                    // アラームが設定されたことを表示
                    Toast.makeText(getApplicationContext(),"alarm start",Toast.LENGTH_SHORT).show();

                    Log.d("debug", "start");
                }

            }
        });

        Button buttonCancel = findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indent = new Intent(getApplicationContext(), AlarmNotification.class);
                PendingIntent pending = PendingIntent.getBroadcast(
                        getApplicationContext(), requestCode, indent, 0);

                // アラームを解除する
                AlarmManager am = (AlarmManager)MainActivity.this.
                        getSystemService(ALARM_SERVICE);
                if (am != null) {
                    am.cancel(pending);
                    Toast.makeText(getApplicationContext(),
                            "alarm cancel", Toast.LENGTH_SHORT).show();
                    Log.d("debug", "cancel");
                }
                else{
                    Log.d("debug", "null");
                }
            }
        });

    }
}
