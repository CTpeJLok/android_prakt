package com.example.prakt_11_14;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private boolean isFlash;

    private CameraManager cameraManager;
    private String cameraId;

    private Button flashBtn;
    private Button webBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        try {
            // Получаем ID камеры с функцией вспышки
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        flashBtn = (Button) findViewById(R.id.flash);
        flashBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

        webBtn = (Button) findViewById(R.id.go_web);
        webBtn.setVisibility(View.INVISIBLE);
    }

    public void call(View view) {
        Intent make = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
        startActivity(make);
    }

    public void yandex(View view) {
        Intent make = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ya.ru"));
        startActivity(make);
    }

    public void email(View view) {
        Intent make = new Intent(Intent.ACTION_SEND);
//        make.setType("text/*");
        make.setType("message/rfc822");
        make.putExtra(Intent.EXTRA_EMAIL, new String[] {"pi21-4@yandex.ru"});
        make.putExtra(Intent.EXTRA_SUBJECT, "Тестовое сообщение");
        make.putExtra(Intent.EXTRA_TEXT, "Тестовое сообщение");
        startActivity(Intent.createChooser(make, "Написать группе"));
    }

    public void vk(View view) {
        Intent make = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "https://play.google.com/store/apps/details?id=com.my.mygamesapp"
        ));
        startActivity(make);
    }

    public void flash(View view) {
        boolean hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH
        );

        if (hasFlash) {
            if (isFlash) {
                try {
                    cameraManager.setTorchMode(cameraId, false);

                    flashBtn.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

                    isFlash = false;

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Вспышка выключена",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } catch (CameraAccessException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Ошибка",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    e.printStackTrace();
                }
            } else {
                try {
                    cameraManager.setTorchMode(cameraId, true);

                    flashBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

                    isFlash = true;

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Вспышка включена",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } catch (CameraAccessException e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Ошибка",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    e.printStackTrace();
                }
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "У вас нет вспышки",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void inetCheck(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE
        );

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы подключены к сети",
                    Toast.LENGTH_SHORT);
            toast.show();

            webBtn.setVisibility(View.VISIBLE);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы не подключены к сети",
                    Toast.LENGTH_SHORT);
            toast.show();

            webBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void goWeb(View view) {
        Intent intent = new Intent(MainActivity.this, WebView.class);
        startActivity(intent);
    }
}