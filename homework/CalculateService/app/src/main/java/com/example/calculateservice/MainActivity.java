package com.example.calculateservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG="tag";
    private ServiceConnection connection;
    private CalculateService service;

    private Button start;
    private Button end;

    //输入框
    private EditText editText;

    //数字0-9
    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;

    //运算符：+、-、*、/
    private Button add;
    private Button sub;
    private Button mul;
    private Button div;

    private Button equal;

    //清空、后退
    private Button back;
    private Button clear;

    //是否进行过运算
    private boolean isCalculated=false;

    //小数点
    private Button dot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        start=(Button)findViewById(R.id.start_service);
//        end=(Button)findViewById(R.id.end_service);
//
//        start.setOnClickListener(this);
//        end.setOnClickListener(this);

        //输入框
        editText = (EditText) findViewById(R.id.editText);

        //0-9
        zero = (Button) findViewById(R.id.zero);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);

        //运算符：+、-、*、/
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        mul = (Button) findViewById(R.id.mul);
        div = (Button) findViewById(R.id.div);

        equal = (Button) findViewById(R.id.equal);

        //小数点
        dot = (Button) findViewById(R.id.dot);

        //清空、后退
        clear = (Button) findViewById(R.id.clear);
        back = (Button) findViewById(R.id.back);

        //添加点击事件
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);

        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);
        equal.setOnClickListener(this);

        clear.setOnClickListener(this);
        back.setOnClickListener(this);

        dot.setOnClickListener(this);

        connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.w(TAG,"onServiceConnected");
                service=((CalculateService.LocalBinder)iBinder).getService();
                Toast.makeText(MainActivity.this, "service:"+service, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.w(TAG,"onServiceDisconnected");
            }
        };
    }

    @Override
    public void onClick(View view) {
        //获取文本内容
        String input=editText.getText().toString();
        if (isCalculated){
            input="";
            isCalculated=false;
        }
        switch(view.getId()){
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
                editText.setText(input + ((Button) view).getText());//结果集
                break;

            case R.id.dot:
                if(!isEditTextEmpty(input)&&isNumber(input)&&!isDot(input)){
                    editText.setText(input+((Button)view).getText());
                }
                break;

            case R.id.add:
            case R.id.sub:
            case R.id.mul:
            case R.id.div:
                if(!isEditTextEmpty(input)&&(isOperator(input))){
                    input=back(input);
                }
                if(!isEditTextEmpty(input)&&isNumber(input)){
                    editText.setText(input+((Button)view).getText());
                }
                break;
            case R.id.equal:
                if(!isEditTextEmpty(input)&&isNumber(input)&&service!=null){
                    input=input+"=";
                    double result=service.calculate(input);
                    editText.setText(String.valueOf(result));
                    isCalculated=true;
                }
                break;
            case R.id.back:
                if(!isEditTextEmpty(input)){
                    editText.setText(this.back(input));
                }
                break;
            case R.id.clear:
                editText.setText("");
                break;

//            case R.id.start_service:
//                Intent intent=new Intent(MainActivity.this,CalculateService.class);
//                bindService(intent, connection, Service.BIND_AUTO_CREATE);
//                Toast.makeText(this, "service:"+service, Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.end_service:
//                if (service!=null){
//                    unbindService(connection);
//                }
//                break;
        }
    }

    //判断EditText中内容是否为空
    public boolean isEditTextEmpty(String input){
        return input.equals("");
    }

    //判断EditText中内容的最后一位是否数字
    public boolean isNumber(String input){
        char lastIndex=input.charAt(input.length()-1);
        if(lastIndex>='0'&&lastIndex<='9'){
            return true;
        }else{
            return false;
        }
    }

    //判断是否包含小数点
    public boolean isDot(String input){
        char[] charArray=input.toCharArray();
        int index=0;

        for (int i = 0; i < charArray.length; i++) {
            if(charArray[i]=='.') {
                index++;
            }
            if(charArray[i]=='+'||charArray[i]=='-'||charArray[i]=='*'||charArray[i]=='/'){
                index=0;
            }
        }

        if(index!=0)
            return true;
        else
            return false;
    }

    //判断EditText最后一位是否是运算符
    public boolean isOperator(String input){
        char lastIndex=input.charAt(input.length()-1);

        if(lastIndex=='+'||lastIndex=='-'||lastIndex=='*'||lastIndex=='/'){
            return true;
        }else {
            return false;
        }

    }

    //后退一格
    public String back(String input){
        return input.substring(0,input.length()-1);
    }

    //启动服务
    public void onButtonStartService(View view){
        Intent intent=new Intent(MainActivity.this,CalculateService.class);
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

    public void onButtonEndService(View view){
        if (service!=null){
            unbindService(connection);
        }
    }


}