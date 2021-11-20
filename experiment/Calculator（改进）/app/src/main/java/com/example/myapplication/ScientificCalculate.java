package com.example.myapplication;

public class ScientificCalculate {
    public double triangleOperate(String input){
        String triangle=input.substring(input.length()-3,input.length());
        double data=Double.parseDouble(input.substring(0,input.length()-3));
        double result=0;
        if(triangle.equals("sin")){
            result=Math.sin(data);
        }else if (triangle.equals("cos")){
            result=Math.cos(data);
        }else if(triangle.equals("tan")){
            result=Math.tan(data);
        }

        return result;
    }

    public double powOperate(String input,int beginIndex,int endIndex){
        String firstData=input.substring(0,beginIndex);
        String secondData=input.substring(endIndex,input.length());
        double first=Double.parseDouble(firstData);
        double second=Double.parseDouble(secondData);

        return Math.pow(first,second);
    }

    public double logOperate(double data){
        return Math.log10(data);
    }

    public double lnOperate(double data){
        return Math.log(data);
    }

    public int factorialOperate(int data){
        int sum=1;

        if(data==0){
            sum*=1;
        }else {

            for (int i = 1; i <= data; i++) {//循环num

                sum *= i;//每循环一次进行乘法运算

            }
        }

        return sum;//返回阶乘的值
    }

    public double reciprocalOperate(double data){
        return 1 / data;
    }

    public double percentOperate(double data){
        return data / 100;
    }

    public double PIOperate(double data){
        return data*Math.PI;
    }
}
