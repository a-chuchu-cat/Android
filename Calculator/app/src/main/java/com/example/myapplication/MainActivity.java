package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //输入框
    private EditText editText;

    private GestureDetector detector;
    private MyGestureListener listener;

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

    //是否存在left_bracket
    private boolean isExistLeftBracket=false;

    //left_bracket的数量
    private int countOnLeftBracket=0;

    //小数点
    private Button dot;

    //左括号、右括号
    private Button left_bracket;
    private Button right_bracket;

    private Button help;

    public void onConfigurationChanged(Configuration configuration){
        super.onConfigurationChanged(configuration);
        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.landscape_layout);
        }else{
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //输入框
        editText = (EditText) findViewById(R.id.editText);
        editText.setEnabled(false);

        listener=new MyGestureListener();
        detector=new GestureDetector(this,listener);

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

        //左括号、右括号
        left_bracket=(Button)findViewById(R.id.left_bracket);
        right_bracket=(Button)findViewById(R.id.right_bracket);

        help=(Button)findViewById(R.id.help);

        help.setOnClickListener(this);

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

        left_bracket.setOnClickListener(this);
        right_bracket.setOnClickListener(this);

        //Inflate the menu
        help.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
            getMenuInflater().inflate(R.menu.button_menu,contextMenu);
        });
    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    //判断EditText中内容是否为空
    public boolean isEditTextEmpty(String input){
        return input.equals("");
    }

    //判断right_bracket前一位是否为left_bracket
    public boolean forwardRightBracket(String input){
        if(!input.equals("")){
            char lastIndex=input.charAt(input.length()-1);
            if(lastIndex=='('){
                return true;
            }else{
                return false;
            }
        }else {
            return true;
        }
    }

    //判断EditText中内容的最后一位是否数字
    public boolean isNumber(String input){
        if (!input.equals("")) {
            char lastIndex=input.charAt(input.length()-1);
            if(lastIndex>='0'&&lastIndex<='9'){
                return true;
            }else{
                return false;
            }
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

    //判断最后一位是否是小数点
    public boolean isLastDot(String input){
        if(!input.equals("")) {
            char lastIndex = input.charAt(input.length() - 1);

            if (lastIndex == '.') return true;
            else return false;
        }else{
            return false;
        }
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

    //判断最后一位是否是right_bracket
    public boolean isRightBracket(String input){
        char lastIndex=input.charAt(input.length()-1);

        if(lastIndex==')') return true;
        else return false;
    }

    //统计left_bracket的数量
    public void countOnLeftBracket(String input){
        char[] array=input.toCharArray();

        for (int i = 0; i < array.length; i++) {
            if(array[i]=='('){
                countOnLeftBracket++;
            }
        }
    }


    //后退一格
    public String back(String input){
        return input.substring(0,input.length()-1);
    }

    //left_bracket和right_bracket是否匹配
    public boolean match(String input){
        if(!input.equals("")){
            char[] array=input.toCharArray();
            int left_bracket_num=0;
            int right_bracket_num=0;

            for (int i = 0; i < array.length; i++) {
                if(array[i]=='('){
                    left_bracket_num++;
                }else if (array[i]==')'){
                    right_bracket_num++;
                }
            }

            if(left_bracket_num==right_bracket_num){
                return true;
            }else {
                return false;
            }
        }else{
            return true;
        }
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
                if(!isEditTextEmpty(input)&&(isNumber(input)||isRightBracket(input))){
                    editText.setText(input+((Button)view).getText());
                }
                break;
            case R.id.equal:
                if(!isEditTextEmpty(input)&&(isNumber(input)||isRightBracket(input))&&match(input)){
                    input=input+"=";
                    double result=new Evaluate().calculate(input);
                    editText.setText(String.valueOf(result));
                    isCalculated=true;
                }
                break;
            case R.id.back:
                if(!isEditTextEmpty(input)){
                    if(isRightBracket(input)){
                        countOnLeftBracket=countOnLeftBracket+1;
                        isExistLeftBracket=true;
                    }
                    editText.setText(this.back(input));
                }
                break;
            case R.id.clear:
                editText.setText("");
                break;

            case R.id.left_bracket:
                if(!isLastDot(input)&&!isNumber(input)){
                    editText.setText(input+((Button)view).getText());
                    isExistLeftBracket=true;
                }
                if(isEditTextEmpty(input)){
                    editText.setText(input+((Button)view).getText());
                    isExistLeftBracket=true;
                }
                break;

            case R.id.right_bracket:
                if(!isLastDot(input)&&isExistLeftBracket&&!isEditTextEmpty(input)&&!forwardRightBracket(input)&&(!isOperator(input))){
                    countOnLeftBracket(input);
                    editText.setText(input+((Button)view).getText());
                    countOnLeftBracket=countOnLeftBracket-1;
                    if(countOnLeftBracket==0){
                        isExistLeftBracket=false;
                    }
                }
                break;
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onFling(MotionEvent e1,MotionEvent e2,float v1,float v2){
            if(e1.getX()-e2.getX()>0){
                startActivity(new Intent(MainActivity.this,ScientificCalculateActivity.class));
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.help_item:
                Toast.makeText(MainActivity.this, "这是帮助文档", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_item:
                break;
        }
        return true;
    }

}