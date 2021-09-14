package com.example.pictureuridemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICTURE=1;
    private static final int REQUEST_TAKE_PHOTO_AND_SAVE=2;
    private Uri uri;

    private ImageView picture;
    private ImageView pictureSave;

    private Button takePhoto;
    private Button takePhotoAndSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picture=(ImageView) findViewById(R.id.picture);
        pictureSave=(ImageView) findViewById(R.id.picture_save);

        takePhoto=(Button) findViewById(R.id.take_photo);
        takePhotoAndSave=(Button) findViewById(R.id.take_photo_and_save);

        takePhoto.setOnClickListener(view ->{
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                dispatchPictureIntent();
            }
        });

        takePhotoAndSave.setOnClickListener(view ->{
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                onButtonTakePhotoAndSave();
            }
        });

    }

    //创建图片地址uri，用于保存拍照后的照片
    public Uri createImageUri(){
        String status= Environment.getExternalStorageState();

        if(status.equals(Environment.MEDIA_MOUNTED)){
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new ContentValues());
        }else{
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI,new ContentValues());
        }
    }

    /**
     * 拍照并保存
     */
    public void onButtonTakePhotoAndSave(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Ensure that there is a camera activity to handle the intent
        if(intent.resolveActivity(getPackageManager())!=null){
            //Create the file where photo should go
            Uri photoUri=createImageUri();
            uri=photoUri;

            if(photoUri!=null){
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent,REQUEST_TAKE_PHOTO_AND_SAVE);
            }

        }
    }

    private void dispatchPictureIntent(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,REQUEST_IMAGE_PICTURE);
        }
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==REQUEST_IMAGE_PICTURE&&resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Bitmap bitmap=(Bitmap) bundle.get("data");
            picture.setImageBitmap(bitmap);
        }else if(resultCode==RESULT_OK){
            pictureSave.setImageURI(uri);
        }
    }
}