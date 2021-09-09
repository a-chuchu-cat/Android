package com.example.activityhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button sendContentButton;
    private EditText sendEditText;
    private TextView receiveContent;

    public static final int ANOTHERACTIVITY_REQUESTCODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendContentButton=(Button) findViewById(R.id.send_content);
        sendEditText=(EditText) findViewById(R.id.send_edit_text);
        receiveContent=(TextView) findViewById(R.id.receive_content);

        Intent intent=new Intent(MainActivity.this,AnotherActivity.class);


        sendContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=sendEditText.getText().toString();
                intent.putExtra("content",content);

                startActivityForResult(intent,ANOTHERACTIVITY_REQUESTCODE);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==ANOTHERACTIVITY_REQUESTCODE){
            if(resultCode==AnotherActivity.MAINACTIVITY_RESULTCODE){
                String processData=data.getStringExtra("processData");
                receiveContent.setText(processData);
            }
        }
    }
}