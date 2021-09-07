package com.example.myapplication;

import java.util.Stack;

public class Evaluate {
    public char precede(char a,char b){
        if((a=='='&&b=='=')||(a=='(')&&b==')')
            return '=';
        else
        if(a=='('||b=='('||a=='='||((a=='+'||a=='-')&&(b=='*'||b=='/')))
            return '<';
        else
            return '>';
    }
    public double operate(double a,char b,double c)  //操作
    {
        if(b=='+')
            return a+c;
        else if(b=='-')
            return a-c;
        else   if(b=='*')
            return a*c;
        else
            return a/c;
    }
    public double calculate(String string) {

        Stack<Character> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();
        String test = string;
        char[] array = test.toCharArray();

        ops.push('=');
        for (int i = 0; array[i] != '=' || ops.peek() != '='; ) {
            char[] chars = new char[100];
            int t = 0;
            if (array[i] >= '0' && array[i] <= '9' || array[i] == '.') {
                while (((array[i] >= '0' && array[i] <= '9' )|| array[i] == '.')&&i<=array.length-2) {
                    chars[t] = array[i];
                    t++;
                    i++;
                }
                vals.push(Double.parseDouble(new String(chars)));
                chars = null;
            } else {
                switch (precede(ops.peek(), array[i])) {
                    case '<':
                        ops.push(array[i]);
                        i++;
                        break;
                    case '>':
                        double m = vals.pop();
                        double n = vals.pop();
                        char s = ops.pop();
                        vals.push(operate(n, s, m));
                        break;
                    case '=':
                        ops.pop();
                        i++;
                        break;
                }
            }
        }
        double result = vals.peek();
        return result;
    }

}



//import java.util.Stack;
//
//public class Evaluate {
//    public char precede(char a,char b){
//        if((a=='='&&b=='=')||(a=='('&&b==')')){
//            return '=';
//        }else if(a=='('||b=='('||a=='='||((a=='+'||a=='-')&&(b=='*'||b=='/'))){
//            return '<';
//        }else{
//            return '>';
//        }
//    }
//
//    public double operate(double a,char b,double c){
//        if(b=='+'){
//            return a+c;
//        }else if(b=='-'){
//            return a-c;
//        }else if(b=='*'){
//            return a*c;
//        }else{
//            return a/c;
//        }
//    }
//
//    public double calculate(String string){
//        Stack<Character> ops=new Stack<>();
//        Stack<Double> value=new Stack<>();
//
//        char[] array=string.toCharArray();
//
//        ops.push('=');
//        for(int i=0;array[i]!='='||ops.peek()!='=';){
//            char[] chars=new char[1024];
//            int t=0;
//            if(array[i]>='0'&&array[i]<='9'||array[i]=='.'){
//                while(array[i]>='0'&&array[i]<='9'||array[i]=='.'){
//                    chars[t++]=array[i++];
//                }
//                value.push(Double.parseDouble(chars.toString()));
//                chars=null;
//            }else{
//                switch(this.precede(ops.peek(),array[i])){
//                    case '<':
//                        ops.push(array[i]);
//                        i++;
//                        break;
//                    case '>':
//                        double m=value.pop();
//                        double n=value.pop();
//                        char b=ops.pop();
//                        value.push(this.operate(m,b,n));
//                        break;
//                    case '=':
//                        ops.pop();
//                        i++;
//                        break;
//                }
//            }
//        }
//        return value.peek();
//    }
//
//    public static void main(String[] args) {
//        String input="1+2*3";
//        double result=new Evaluate().calculate(input);
//        System.out.println(result);
//    }
//}
