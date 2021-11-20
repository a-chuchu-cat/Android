package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ScientificCalculateActivity extends AppCompatActivity implements View.OnClickListener {

    //输入框
    private EditText editText;

    private GestureDetector detector;
    private MyGestureListenerDemo listenerDemo;

    private int beginIndex=0,endIndex=0;

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

    //sin、cos、tan
    private Button sin;
    private Button cos;
    private Button tan;

    //pow
    private Button pow;

    //log、ln
    private Button log;
    private Button ln;

    //x!、1/x
    private Button factorial;
    private Button reciprocal;

    //percent
    private Button percent;

    //π
    private Button pi;

    //清空、后退
    private Button back;
    private Button clear;

    private Button equal;

    //是否进行过计算
    private boolean isCalculated=false;

    private Validate validate;

    public void onConfigurationChanged(Configuration configuration){
        if(Configuration.ORIENTATION_LANDSCAPE==configuration.orientation){
            setContentView(R.layout.landscape_scientific_layout);
        }else{
            setContentView(R.layout.activity_scientific_calculate);
        }
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific_calculate);
        init();
    }

    public void init(){
        validate=new Validate();
        //输入框
        editText = (EditText) findViewById(R.id.editText_button);
        editText.setEnabled(false);

        listenerDemo = new MyGestureListenerDemo();
        detector = new GestureDetector(this, listenerDemo);

        //0-9
        zero = (Button) findViewById(R.id.zero_button);
        one = (Button) findViewById(R.id.one_button);
        two = (Button) findViewById(R.id.two_button);
        three = (Button) findViewById(R.id.three_button);
        four = (Button) findViewById(R.id.four_button);
        five = (Button) findViewById(R.id.five_button);
        six = (Button) findViewById(R.id.six_button);
        seven = (Button) findViewById(R.id.seven_button);
        eight = (Button) findViewById(R.id.eight_button);
        nine = (Button) findViewById(R.id.nine_button);

        //sin、cos、tan
        sin = (Button) findViewById(R.id.sin_button);
        cos = (Button) findViewById(R.id.cos_button);
        tan = (Button) findViewById(R.id.tan_button);

        //pow
        pow = (Button) findViewById(R.id.pow_button);

        //log、ln
        log = (Button) findViewById(R.id.log);
        ln = (Button) findViewById(R.id.ln);

        //x!、1/x
        factorial = (Button) findViewById(R.id.factorial);
        reciprocal = (Button) findViewById(R.id.reciprocal);

        //percent
        percent = (Button) findViewById(R.id.percent_button);

        //π
        pi = (Button) findViewById(R.id.pi);

        //清空、后退
        clear = (Button) findViewById(R.id.clear_button);
        back = (Button) findViewById(R.id.back_button);

        equal = (Button) findViewById(R.id.equal_button);

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

        sin.setOnClickListener(this);
        cos.setOnClickListener(this);
        tan.setOnClickListener(this);

        pow.setOnClickListener(this);
        log.setOnClickListener(this);
        ln.setOnClickListener(this);

        factorial.setOnClickListener(this);
        reciprocal.setOnClickListener(this);

        percent.setOnClickListener(this);
        pi.setOnClickListener(this);

        clear.setOnClickListener(this);
        back.setOnClickListener(this);

        equal.setOnClickListener(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }


    @Override
    public void onClick(View view) {

        String input = editText.getText().toString();

        if(isCalculated){
            isCalculated=false;
            input="";
        }

        switch (view.getId()) {
            case R.id.zero_button:
            case R.id.one_button:
            case R.id.two_button:
            case R.id.three_button:
            case R.id.four_button:
            case R.id.five_button:
            case R.id.six_button:
            case R.id.seven_button:
            case R.id.eight_button:
            case R.id.nine_button:
                editText.setText(input + ((Button) view).getText());//结果集
                break;

            case R.id.back_button:
                if (!validate.isEditTextEmpty(input)) {
                    editText.setText(validate.backSpace(input));
                }
                break;
            case R.id.clear_button:
                editText.setText("");
                break;

            case R.id.sin_button:
            case R.id.cos_button:
            case R.id.tan_button:
                Log.d("pow",String.valueOf(validate.isPow(input)));

                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    input=input+((Button)view).getText();
                    double result=new ScientificCalculate().triangleOperate(input);
                    editText.setText(String.valueOf(result));
                    isCalculated=true;
                }
                break;
            case R.id.pow_button:
                Log.d("pow",String.valueOf(validate.isPow(input)));
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    beginIndex=input.length();
//                    input=input+((Button)view).getText();
                    input=input+"^";
                    editText.setText(input);
//                    endIndex=beginIndex+"pow".length();
                    endIndex=beginIndex+"^".length();
                    isCalculated=false;
                }
                break;

            case R.id.log:
                Log.d("pow",String.valueOf(validate.isPow(input)));

                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    double data=Double.parseDouble(input);
                    Log.d("data",String.valueOf(data));
                    double result=new ScientificCalculate().logOperate(data);
                    input=String.valueOf(result);
                    Log.d("result",input);
                    editText.setText(input);
                    isCalculated=true;
                }
                break;

            case R.id.ln:
                Log.d("pow",String.valueOf(validate.isPow(input)));

                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    double data=Double.parseDouble(input);
                    double result=new ScientificCalculate().lnOperate(data);
                    input=String.valueOf(result);
                    editText.setText(input);
                    isCalculated=true;
                }


                break;

            case R.id.factorial:
                Log.d("pow",String.valueOf(validate.isPow(input)));
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    int data=Integer.parseInt(input);
                    int result=new ScientificCalculate().factorialOperate(data);
                    input=String.valueOf(result);
                    editText.setText(input);
                    isCalculated=true;
                }

                break;

            case R.id.reciprocal:
                Log.d("pow",String.valueOf(validate.isPow(input)));
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    double data=Double.parseDouble(input);
                    if(data!=0) {
                        double result = new ScientificCalculate().reciprocalOperate(data);
                        editText.setText(String.valueOf(result));
                        isCalculated = true;
                    }
                }
                break;

            case R.id.percent_button:
                Log.d("pow",String.valueOf(validate.isPow(input)));
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    double data=Double.parseDouble(input);
                    double result=new ScientificCalculate().percentOperate(data);
                    editText.setText(String.valueOf(result));
                    isCalculated=true;
                }
                break;

            case R.id.pi:
                Log.d("pow",String.valueOf(validate.isPow(input)));
                if(validate.isEditTextEmpty(input)){
                    double result=Math.PI;
                    editText.setText(String.valueOf(result));
                }else if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&(!validate.isPow(input))){
                    double data=Double.parseDouble(input);
                    double result=new ScientificCalculate().PIOperate(data);
                    editText.setText(String.valueOf(result));
                }
                isCalculated=true;
                break;

            case R.id.equal_button:

                Log.d("pow",String.valueOf(validate.isPow(input)));
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)){
                    if(input.substring(beginIndex,endIndex).equals("^")){
                        double result=new ScientificCalculate().powOperate(input,beginIndex,endIndex);
                        editText.setText(String.valueOf(result));
                    }else{
                        input=input+"=";
                        double result=new Evaluate().calculate(input);
                        editText.setText(String.valueOf(result));
                    }
                    isCalculated=true;
                }

                break;
        }

    }

    public class MyGestureListenerDemo extends GestureDetector.SimpleOnGestureListener {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2) {
            if (e2.getX() - e1.getX() > 0) {
                startActivity(new Intent(ScientificCalculateActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            } else {
                startActivity(new Intent(ScientificCalculateActivity.this,ScaleActivity.class));
            }
            return true;
        }
    }

}