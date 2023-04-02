package com.shak.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    AppCompatEditText edtMsg;
    AppCompatButton sendMsgBtn;
    private static final String CHANNEL_ID = "MessageChannelId";
    private static final int NOTIFICATION_ID = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendMsgBtn = findViewById(R.id.sendMsgBtn);
        edtMsg = findViewById(R.id.edtMsg);

        sendMsgBtn.setOnClickListener(view -> {
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.large_icon, null);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap largeImageBitmap = (Objects.requireNonNull(bitmapDrawable)).getBitmap();

            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification;
            String message;
            if (!Objects.requireNonNull(edtMsg.getText()).toString().equals("")) {
                message = edtMsg.getText().toString();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notification = new Notification.Builder(this)
                            .setLargeIcon(largeImageBitmap)
                            .setSmallIcon(R.drawable.notification_app)
                            .setContentText(message)
                            .setSubText("You got a new message")
                            .setChannelId(CHANNEL_ID)
                            .build();
                    nManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
                } else {
                    notification = new Notification.Builder(this)
                            .setLargeIcon(largeImageBitmap)
                            .setSmallIcon(R.drawable.notification_app)
                            .setContentText(message)
                            .setSubText("You got a new message")
                            .build();
                }

                nManager.notify(NOTIFICATION_ID, notification);
                edtMsg.setText("");
            } else {
                Toast.makeText(this, "Please Enter Some Message", Toast.LENGTH_LONG).show();
            }
        });

    }
}