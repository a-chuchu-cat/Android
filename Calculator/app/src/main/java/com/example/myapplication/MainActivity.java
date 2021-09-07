package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        //结果
//        result=(TextView) findViewById(R.id.result);

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

    //后退一格
    public String back(String input){
        return input.substring(0,input.length()-1);
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
            case R.id.add:
            case R.id.sub:
            case R.id.mul:
            case R.id.div:
                if(!isEditTextEmpty(input)&&isNumber(input)){
                    editText.setText(input+((Button)view).getText());
                }
                break;
            case R.id.equal:
                if(!isEditTextEmpty(input)&&isNumber(input)){
                    input=input+"=";
                    double result=new Evaluate().calculate(input);
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
        }
    }
}