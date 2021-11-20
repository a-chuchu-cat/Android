package com.example.myapplication;

public class Validate {
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

    //后退一格
    public String back(String input){
        return input.substring(0,input.length()-1);
    }

    //判断最后一位是否是right_bracket
    public boolean isRightBracket(String input){
        char lastIndex=input.charAt(input.length()-1);

        if(lastIndex==')') return true;
        else return false;
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

    public boolean isBinary(String input){
        char[] array=input.toCharArray();

        int binaryIndex=0;

        for (int i = 0; i < array.length; i++) {
            if(array[i]!='0'&&array[i]!='1') binaryIndex++;
        }

        if(binaryIndex==0) return true;
        else return false;
    }

    //判断是否包含小数点
    public boolean isDotOrNot(String input){
        char[] charArray=input.toCharArray();
        int index=0;

        for (int i = 0; i < charArray.length; i++) {
            if(charArray[i]=='.') {
                index++;
            }
        }

        if(index!=0)
            return true;
        else
            return false;
    }

    //是否是pow运算
    public boolean isPow(String input){
        char[] charArray=input.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if(charArray[i]=='^')return true;
        }

        return false;
    }

    //后退一格
    public String backSpace(String input) {
        return input.substring(0, input.length() - 1);
    }
}
