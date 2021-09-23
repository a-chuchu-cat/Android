package com.example.myapplication;

public class Convert {

    public double convertCentimeterToDecimeter(String input){
        return Double.parseDouble(input)/10;
    }

    public double convertCentimeterToMeter(String input){
        return Double.parseDouble(input)/100;
    }

    public double convertDecimeterToCentimeter(String input){
        return Double.parseDouble(input)*10;
    }

    public double convertDecimeterToMeter(String input){
        return Double.parseDouble(input)/10;
    }

    public double convertMeterToCentimeter(String input){
        return Double.parseDouble(input)*100;
    }

    public double convertMeterToDecimeter(String input){
        return Double.parseDouble(input)*10;
    }
}
