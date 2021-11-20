package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ScaleActivity extends AppCompatActivity implements View.OnClickListener{
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

    private Button dot;

    //m、dm、cm
    private Button meter;
    private Button decimeter;
    private Button centimeter;

    //binary、decimal、hex
    private Button binary;
    private Button decimal;
    private Button hex;

    //cm^3、dm^3、m^3
    private Button cubicCentimeter;
    private Button cubicDecimeter;
    private Button cubicMeter;

    //清空、后退
    private Button back;
    private Button clear;

    private Button help;

    //是否进行过运算
    private boolean isCalculated=false;

    private boolean isCentimeter=false;
    private boolean isDecimeter=false;
    private boolean isMeter=false;
    private boolean isBinary=false;
    private boolean isDecimal=false;
    private boolean isCubicCentimeter=false;
    private boolean isCubicDecimeter=false;
    private boolean isCubicMeter=false;

    private int centimeterIndex=0;
    private int decimeterIndex=0;
    private int meterIndex=0;
    private int cubicCentimeterIndex=0;
    private int cubicDecimeterIndex=0;
    private int cubicMeterIndex=0;
    private int binaryIndex=0;
    private int decimalIndex=0;

    //长度转换
    private boolean isLength=false;

    //体积转换
    private boolean isCubic=false;

    private MyGestureListenerDemo listenerDemo;
    private GestureDetector detector;

    private Validate validate;

    public void onConfigurationChanged(Configuration configuration){
        if(Configuration.ORIENTATION_LANDSCAPE==configuration.orientation){
            setContentView(R.layout.landscape_scale_layout);
        }else{
            setContentView(R.layout.activity_scale);
        }
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        init();
    }

    public void init(){
        validate=new Validate();
        //输入框
        editText = (EditText) findViewById(R.id.editText);
        editText.setEnabled(false);

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

        dot=(Button)findViewById(R.id.dot);
        clear=(Button)findViewById(R.id.clear);
        back=(Button)findViewById(R.id.back);

        //m、dm、cm
        meter=(Button) findViewById(R.id.meter);
        decimeter=(Button) findViewById(R.id.decimeter);
        centimeter=(Button) findViewById(R.id.centimeter);

        //binary、decimal、hex
        binary=(Button) findViewById(R.id.binary);
        decimal=(Button) findViewById(R.id.decimal);
        hex=(Button)findViewById(R.id.hex);

        //cm^3、dm^3、m^3
        cubicCentimeter=(Button) findViewById(R.id.cubic_centimeter);
        cubicDecimeter=(Button) findViewById(R.id.cubic_decimeter);
        cubicMeter=(Button) findViewById(R.id.cubic_meter);

        help=(Button)findViewById(R.id.help);

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

        dot.setOnClickListener(this);

        clear.setOnClickListener(this);

        back.setOnClickListener(this);

        meter.setOnClickListener(this);
        decimeter.setOnClickListener(this);
        centimeter.setOnClickListener(this);
        cubicMeter.setOnClickListener(this);
        cubicCentimeter.setOnClickListener(this);
        cubicDecimeter.setOnClickListener(this);
        cubicMeter.setOnClickListener(this);
        binary.setOnClickListener(this);
        decimal.setOnClickListener(this);
        hex.setOnClickListener(this);

        //Inflate the menu
        help.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
            getMenuInflater().inflate(R.menu.button_menu,contextMenu);
        });

        listenerDemo=new MyGestureListenerDemo();
        detector=new GestureDetector(this,listenerDemo);
    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        //获取文本内容
        String input=editText.getText().toString();
        if (isCalculated){
            input="";
            isCalculated=false;
        }

        switch(view.getId()) {
            case R.id.help:
                help.showContextMenu();
                break;
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
                if(validate.isEditTextEmpty(input)||validate.isNumber(input)|| validate.isDotOrNot(input)) {
                    if(isCalculated){
                        input="";
                    }
                    editText.setText(input + ((Button) view).getText());//结果集
                }

                break;

            case R.id.dot:
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!validate.isDotOrNot(input)){
                    editText.setText(input+((Button)view).getText());
                }
                break;

            case R.id.centimeter:
                if((!isCentimeter&&!isDecimeter&&!isMeter)&&!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!isLength&&!isCubic&&!isCalculated){
                    centimeterIndex=0;
                    int length=input.length();
                    centimeterIndex+=length;
                    editText.setText(input+((Button)view).getText());
                    isCentimeter=true;
                    isLength=true;
                }

                if(!isCentimeter&&isDecimeter&&!isMeter){
                    String number=input.substring(0,decimeterIndex);
                    double result=new Convert().convertDecimeterToCentimeter(number);
                    editText.setText(""+result+"cm");
                    isCalculated=true;
                    isCentimeter=false;
                    isDecimeter=false;
                    isLength=false;
                }

                if(!isCentimeter&&!isDecimeter&&isMeter){
                    String number=input.substring(0,meterIndex);
                    double result=new Convert().convertMeterToCentimeter(number);
                    editText.setText(""+result+"cm");
                    isCalculated=true;
                    isCentimeter=false;
                    isMeter=false;
                    isLength=false;
                }


                break;
            case R.id.decimeter:
                if((!isCentimeter&&!isDecimeter&&!isMeter)&&!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!isLength&&!isCubic&&!isCalculated){
                    decimeterIndex=0;
                    int length=input.length();
                    decimeterIndex+=length;
                    editText.setText(input+((Button)view).getText());
                    isDecimeter=true;
                    isLength=true;
                }

                if(isCentimeter&&!isDecimeter&&!isMeter){
                    String number=input.substring(0,centimeterIndex);
                    double result=new Convert().convertCentimeterToDecimeter(number);
                    editText.setText(""+result+"dm");
                    isCalculated=true;
                    isCentimeter=false;
                    isDecimeter=false;
                    isLength=false;
                }

                if(!isCentimeter&&!isDecimeter&&isMeter){
                    String number=input.substring(0,meterIndex);
                    double result=new Convert().convertMeterToDecimeter(number);
                    editText.setText(""+result+"dm");
                    isCalculated=true;
                    isDecimeter=false;
                    isMeter=false;
                    isLength=false;
                }


                break;

            case R.id.meter:
                if((!isCentimeter&&!isDecimeter&&!isMeter)&&!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!isLength&&!isCubic&&!isCalculated){
                    meterIndex=0;
                    int length=input.length();
                    meterIndex+=length;
                    editText.setText(input+((Button)view).getText());
                    isMeter=true;
                    isLength=true;
                }

                if(isCentimeter&&!isDecimeter&&!isMeter){
                    String number=input.substring(0,centimeterIndex);
                    double result=new Convert().convertCentimeterToMeter(number);
                    editText.setText(""+result+"m");
                    isCalculated=true;
                    isCentimeter=false;
                    isMeter=false;
                    isLength=false;
                }

                if(!isCentimeter&&isDecimeter&&!isMeter){
                    String number=input.substring(0,decimeterIndex);
                    double result=new Convert().convertDecimeterToMeter(number);
                    editText.setText(""+result+"m");
                    isCalculated=true;
                    isDecimeter=false;
                    isMeter=false;
                    isLength=false;
                }

                break;

            case R.id.cubic_centimeter:
                if((!isCubicCentimeter&&!isCubicDecimeter&&!isCubicMeter)&&!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!isLength&&!isCubic&&!isCalculated){
                    cubicCentimeterIndex=0;
                    int length=input.length();
                    cubicCentimeterIndex+=length;
                    editText.setText(input+((Button)view).getText());
                    isCubicCentimeter=true;
                    isCubic=true;
                }

                if(!isCubicCentimeter&&isCubicDecimeter&&!isCubicMeter){
                    String number=input.substring(0,cubicDecimeterIndex);
                    double result=Double.parseDouble(number)*1000;
                    editText.setText(""+result+"cm^3");
                    isCalculated=true;
                    isCubicCentimeter=false;
                    isCubicDecimeter=false;
                    isCubic=false;
                }

                if(!isCubicCentimeter&&!isCubicDecimeter&&isCubicMeter){
                    String number=input.substring(0,cubicMeterIndex);
                    double result=Double.parseDouble(number)*1000000;
                    editText.setText(""+result+"cm^3");
                    isCalculated=true;
                    isCubicCentimeter=false;
                    isCubicMeter=false;
                    isCubic=false;
                }
                break;

            case R.id.cubic_decimeter:
                if((!isCubicCentimeter&&!isCubicDecimeter&&!isCubicMeter)&&!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!isLength&&!isCubic&&!isCalculated){
                    cubicDecimeterIndex=0;
                    int length=input.length();
                    cubicDecimeterIndex+=length;
                    editText.setText(input+((Button)view).getText());
                    isCubicDecimeter=true;
                    isCubic=true;
                }

                if(isCubicCentimeter&&!isCubicDecimeter&&!isCubicMeter){
                    String number=input.substring(0,cubicCentimeterIndex);
                    double result=Double.parseDouble(number)/1000;
                    editText.setText(""+result+"dm^3");
                    isCalculated=true;
                    isCubicCentimeter=false;
                    isCubicDecimeter=false;
                    isCubic=false;
                }

                if(!isCubicCentimeter&&!isCubicDecimeter&&isCubicMeter){
                    String number=input.substring(0,cubicMeterIndex);
                    double result=Double.parseDouble(number)*1000;
                    editText.setText(""+result+"dm^3");
                    isCalculated=true;
                    isCubicDecimeter=false;
                    isCubicMeter=false;
                    isCubic=false;
                }
                break;

            case R.id.cubic_meter:
                if((!isCubicCentimeter&&!isCubicDecimeter&&!isCubicMeter)&&!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!isLength&&!isCubic&&!isCalculated){
                    cubicMeterIndex=0;
                    int length=input.length();
                    cubicMeterIndex+=length;
                    editText.setText(input+((Button)view).getText());
                    isCubicMeter=true;
                    isCubic=true;
                }

                if(!isCubicCentimeter&&isCubicDecimeter&&!isCubicMeter){
                    String number=input.substring(0,cubicDecimeterIndex);
                    double result=Double.parseDouble(number)/1000;
                    editText.setText(""+result+"m^3");
                    isCalculated=true;
                    isCubicCentimeter=false;
                    isCubicMeter=false;
                    isCubic=false;
                }

                if(isCubicCentimeter&&!isCubicDecimeter&&!isCubicMeter){
                    String number=input.substring(0,cubicCentimeterIndex);
                    double result=Double.parseDouble(number)/1000000;
                    editText.setText(""+result+"m^3");
                    isCalculated=true;
                    isCubicCentimeter=false;
                    isCubicMeter=false;
                    isCubic=false;
                }
                break;

            case R.id.binary:
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!validate.isDotOrNot(input)){
                    int number=Integer.parseInt(input);
                    String binary=Integer.toBinaryString(number);
                    editText.setText(binary);
                    isCalculated=true;
                }

                break;

            case R.id.decimal:
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!validate.isDotOrNot(input)&&validate.isBinary(input)){
                    String result=Integer.valueOf(input,2).toString();
                    editText.setText(result);
                    isCalculated=true;
                }
                break;

            case R.id.hex:
                if(!validate.isEditTextEmpty(input)&&validate.isNumber(input)&&!validate.isDotOrNot(input)){
                    int number=Integer.parseInt(input);
                    String result=Integer.toHexString(number);
                    editText.setText(result);
                    isCalculated=true;
                }
                break;

            case R.id.clear:
                if(!validate.isEditTextEmpty(input)) {
                    editText.setText("");
                }
                break;

            case R.id.back:
                if((!validate.isEditTextEmpty(input)&&(validate.isNumber(input)||validate.isLastDot(input)))){
                    editText.setText(validate.back(input));
                }

                if(isMeter){
                    editText.setText(input.substring(0,meterIndex));
                    isMeter=false;
                    isLength=false;
                }

                if(isDecimeter){
                    editText.setText(input.substring(0,decimeterIndex));
                    isDecimeter=false;
                    isLength=false;
                }

                if(isCentimeter){
                    editText.setText(input.substring(0,centimeterIndex));
                    isCentimeter=false;
                    isLength=false;
                }

                if(isCubicCentimeter){
                    editText.setText(input.substring(0,cubicCentimeterIndex));
                    isCubicCentimeter=false;
                    isCubic=false;
                }

                if(isCubicDecimeter){
                    editText.setText(input.substring(0,cubicDecimeterIndex));
                    isCubicDecimeter=false;
                    isCubic=false;
                }

                if(isCubicMeter){
                    editText.setText(input.substring(0,cubicMeterIndex));
                    isCubicMeter=false;
                    isCubic=false;
                }
                break;
        }
    }


    public boolean onContextItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.help_item:
                Toast.makeText(ScaleActivity.this, "这是帮助文档", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_item:
                break;
        }
        return true;
    }

    public class MyGestureListenerDemo extends GestureDetector.SimpleOnGestureListener {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2) {
            if (e2.getX() - e1.getX() > 0) {
                startActivity(new Intent(ScaleActivity.this, ScientificCalculateActivity.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
            return true;
        }
    }

}