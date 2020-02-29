package com.phong.hocdatientrinh_handlerusingpost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText edtButton;
    Button btnVe;
    TextView txtPercent;
    LinearLayout llButton;
    LinearLayout.LayoutParams layoutParams;
    Random random = new Random();
    int n;
    int percent;
    int value;

    Handler handler = new Handler();
    Runnable foregroundThread = new Runnable() {
        @Override
        public void run() {
            //Cập nhật giao diện, truy suất mọi tài nguyên trong phần mềm: Main Thread
            txtPercent.setText(percent + "%");
            Button btn = new Button(MainActivity.this);
            btn.setText(value + "");
            btn.setLayoutParams(layoutParams);
            llButton.addView(btn);
            if (percent == 100){
                Toast.makeText(MainActivity.this,"Đã xong tiến trình",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyVe();
            }
        });
    }

    private void xuLyVe() {
        llButton.removeAllViews();
        n = Integer.parseInt(edtButton.getText().toString());
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Không truy suất đến các biến control trên giao diện:
                for (int i = 0;i < n; i++){
                    percent = i * 100/n;
                    value = random.nextInt(100);
                    //Gửi thông điệp này cho foregroundThread(Main Thread):
                    handler.post(foregroundThread);
                    SystemClock.sleep(100);
                }
                percent = 100;
                handler.post(foregroundThread);
            }
        });
        backgroundThread.start();//kích hoạt tiến trình
    }

    private void addControls() {
        edtButton = findViewById(R.id.edtButton);
        btnVe = findViewById(R.id.btnVe);
        txtPercent = findViewById(R.id.txtPercent);
        llButton = findViewById(R.id.llButton);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
