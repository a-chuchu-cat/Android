package com.example.contactdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ContentResolver resolver;
    private Button all;
    private ListView allListview;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver=getContentResolver();
        all=(Button) findViewById(R.id.button_all);
        message=(TextView)findViewById(R.id.message);

//        String msg="";
//        Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
//        while(cursor.moveToNext()){
//            int columnIndex=cursor.getColumnIndex(ContactsContract.Contacts._ID);
//            String id=cursor.getString(columnIndex);
//            msg=msg+"id:"+id;
//            columnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//            String name=cursor.getString(columnIndex);
//            msg=msg+"  name:"+name;
//
//            String selection= ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?";
//            String[] selectionArgs={id};
//            Cursor phoneNumbers=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,selection,selectionArgs,null);
//            while(phoneNumbers.moveToNext()){
//                columnIndex=phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                String number=phoneNumbers.getString(columnIndex);
//                msg=msg+"  phoneNumber:"+number;
//            }
//            msg=msg+"\n";
//            Log.e("msg",msg);
//
//        }
//
//        message.setText(msg);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg="";
                Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
                while(cursor.moveToNext()){
                    int columnIndex=cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String id=cursor.getString(columnIndex);
                    msg=msg+"id:"+id;
                    columnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    String name=cursor.getString(columnIndex);
                    msg=msg+"  name:"+name;

                    String selection= ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?";
                    String[] selectionArgs={id};
                    Cursor phoneNumbers=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,selection,selectionArgs,null);
                    while(phoneNumbers.moveToNext()){
                        columnIndex=phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number=phoneNumbers.getString(columnIndex);
                        msg=msg+"  phoneNumber:"+number;
                    }
                    msg=msg+"\n";

                }

                message.setText(msg);
            }
        });
    }
}