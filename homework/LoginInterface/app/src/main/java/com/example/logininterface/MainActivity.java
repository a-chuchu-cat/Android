package com.example.logininterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView information;
    private Button login;
    private EditText userNameEdit,userPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        information=findViewById(R.id.information);
        login=findViewById(R.id.login);
        userNameEdit=findViewById(R.id.username_edit);
        userPasswordEdit=findViewById(R.id.user_password_edit);

        login.setOnClickListener(view -> {
            String userName=userNameEdit.getText().toString();
            String userPassword=userPasswordEdit.getText().toString();

            if(userName.equals("abc")&&userPassword.equals("123")) information.setText("登录成功");
            else information.setText("登陆失败");
        });


    }
}