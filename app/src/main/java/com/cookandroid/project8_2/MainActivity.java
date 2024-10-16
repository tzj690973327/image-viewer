package com.cookandroid.project8_2;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btnPrev,btnNext;
    int curNum=0;
    File[] imageFiles=new File[0];
    String imageFname;
    myPictureView myPictureView;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 이미지 뷰어");

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_MEDIA_IMAGES}, MODE_PRIVATE);

        btnPrev=findViewById(R.id.btnPrev);
        btnNext=findViewById(R.id.btnNext);
        myPictureView = findViewById(R.id.myPictureView1);

        File[] allFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures").listFiles();
        for(int i = 0; i< Objects.requireNonNull(allFiles).length; i++){
            if(allFiles[i].isFile()) {
                imageFiles = Arrays.copyOf(imageFiles,imageFiles.length+1);
                imageFiles[imageFiles.length-1]=allFiles[i];
            }
        }

        if (imageFiles.length > 0) {
            imageFname = imageFiles[curNum].toString();
            myPictureView.imagePath = imageFname;
        } else {
            Toast.makeText(getApplicationContext(),"No images found",Toast.LENGTH_SHORT).show();
        }


        btnPrev.setOnClickListener(view -> {
            if(curNum<=0) {
                Toast.makeText(getApplicationContext(),"첫번째 그림입니다",Toast.LENGTH_SHORT).show();
            } else {
                curNum--;
                imageFname=imageFiles[curNum].toString();
                myPictureView.imagePath =imageFname;
                myPictureView.invalidate();
            }
        });

        btnNext.setOnClickListener(view -> {
            if(curNum>= imageFiles.length-1) {
                Toast.makeText(getApplicationContext(),"마지막 그림입니다",Toast.LENGTH_SHORT).show();
            } else {
                curNum++;
                imageFname=imageFiles[curNum].toString();
                myPictureView.imagePath =imageFname;
                myPictureView.invalidate();
            }
        });
    }
}
