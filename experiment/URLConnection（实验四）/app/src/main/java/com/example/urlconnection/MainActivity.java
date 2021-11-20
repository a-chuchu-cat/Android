package com.example.urlconnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 申请权限的集合，同时要在AndroidManifest.xml中申请，Android 6以上需要动态申请权限
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权限
    List<String> mPermissionList = new ArrayList<>();
    //显示图片窗体
    private ImageView showImg;

    private static final int REQUEST_IMAGE_PICTURE=1;
    private Button takePhoto;
    private Button upload;
    private ImageView image;
    private TextView emotions;

    String imageFilePath="data/data/com.example.urlconnection/01.png";

    //图片路径
    private String path;
    //
    File f;

    TextView textView;

    //接收的情感
    String emotion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        image = findViewById(R.id.picture);
        takePhoto = findViewById(R.id.take_photo);
        upload = findViewById(R.id.upload);
        emotions = findViewById(R.id.emotion);

        initPhotoError();

        takePhoto.setOnClickListener(view -> {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                dispatchPictureIntent();
                upload.setEnabled(true);
            }
        });

        //6.0获取多个权限
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            //PERMISSION_GRANTED:Permission check result: this is returned by checkPermission if the permission has been granted to the given package.
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        upload.setOnClickListener(view -> {
            //未授予的权限为空，表示都授予了
            if (mPermissionList.isEmpty()) {
                f = new File(imageFilePath);
                //读取根目录下的一张图片
                boolean fileExist = fileIsExists(imageFilePath);
                if (fileExist) {

                    try {
                        //上传图片
                        emotion = ImageUpload.run(f);
                        emotions.setText(emotion);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//            readImg(showImg);
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                //requestPermissions:Requests permissions to be granted to this application.
                // These permissions must be requested in your manifest, they should not be granted to your app,
                // and they should have protection level dangerous, regardless whether they are declared by the platform or a third-party app.
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            }
        });
    }
    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    //界面显示图片
//    public void readImg(View view) {
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        showImg.setImageBitmap(bitmap);
//    }
    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //Callback for the result from requesting permissions. This method is invoked for every call on requestPermissions(String[], int).
    //Note: It is possible that the permissions request interaction with the user is interrupted. In this case you will receive empty permissions
    // and results arrays which should be treated as a cancellation.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        Toast.makeText(MainActivity.this,"权限未申请",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void dispatchPictureIntent(){
        //设置图片的保存路径，作为全局变量
//        imageFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.png";
//        File temp=new File(imageFilePath);
//        Uri uri=Uri.fromFile(temp);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,REQUEST_IMAGE_PICTURE);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_IMAGE_PICTURE&&resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Bitmap bitmap=(Bitmap) bundle.get("data");

            File save=new File(imageFilePath);

            try{
                if(! save.exists()){
                    System.out.println(save.createNewFile());
                }

                FileOutputStream out=new FileOutputStream(save);
                bitmap.compress(Bitmap.CompressFormat.PNG,60,out);
                out.flush();
                out.close();

            }catch (Exception e){
                e.printStackTrace();
            }

            Bitmap bitmap1=BitmapFactory.decodeFile(imageFilePath);
            image.setImageBitmap(bitmap1);
        }
    }
}