package com.example.widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView screen;
    //first number
    private String firstNum = "";
    //operator
    private String operator = "";
    //second number
    private String secondNum = "";
    //result
    private String result = "";
    private String showResult = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);
        findViewById(R.id.btn_clean).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_device).setOnClickListener(this);
        findViewById(R.id.btn_cross).setOnClickListener(this);
        findViewById(R.id.btn_C).setOnClickListener(this);
        findViewById(R.id.btn_D).setOnClickListener(this);
        findViewById(R.id.btn_E).setOnClickListener(this);
        findViewById(R.id.btn_F).setOnClickListener(this);
        findViewById(R.id.btn_DEC).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_A).setOnClickListener(this);
        findViewById(R.id.btn_B).setOnClickListener(this);
        findViewById(R.id.btn_BIN).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String inputText;
        if (view.getId() == R.id.btn_DEC) {
            inputText = "DEC";
        } else if (view.getId() == R.id.btn_BIN) {
            inputText = "BIN";
        } else {
            inputText = ((TextView) view).getText().toString();
        }
        switch (view.getId()) {
            case R.id.btn_clean:
                clean();
                break;
            case R.id.btn_delete:
                delete();
                break;

            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_cross:
            case R.id.btn_device:
                operator = inputText;
                refreshText(showResult.toUpperCase() + operator);
                break;
            case R.id.btn_equal:
                int calculate_result = calculateFour();
                refreshOperate(convertToHex(calculate_result));
                refreshText(showResult.toUpperCase() + "=" + result.toUpperCase());
                break;

            case R.id.btn_DEC:
                String decResult = hex2dec(firstNum);
                refreshOperate(decResult);
                refreshText(showResult.toUpperCase() + " to DEC is " + result.toUpperCase());
                break;
            case R.id.btn_BIN:
                String binResult = hex2bin(firstNum);
                refreshOperate(binResult);
                refreshText(showResult.toUpperCase() + " to BIN is " + result.toUpperCase());
                break;
            default:
                if (result.length() > 0 && operator.equals("")) {
                    clean();
                }
                if (operator.equals("")) {
                    firstNum = firstNum + inputText;
                } else {
                    secondNum = secondNum + inputText;
                }
                if (showResult.equals("0")) {
                    refreshText(inputText);
                } else {
                    refreshText(showResult + inputText);
                }
                break;
        }
    }

    private void delete() {
        String screenText = screen.getText().toString();
        StringBuilder delete_result = new StringBuilder(screenText);
        if (screenText.length() - 1>=0){
            StringBuilder showDelete = delete_result.delete(screenText.length() - 1, screenText.length());
            showResult = String.valueOf(showDelete);
            screen.setText(showResult);
        }else {
            clean();
        }
    }

    @SuppressLint("SetTextI18n")
    private int calculateFour() {
        switch (operator) {
            case "+":
                return (convertToInt(firstNum) + convertToInt(secondNum));
            case "-":
                return (convertToInt(firstNum) - convertToInt(secondNum));
            case "×":
                return (convertToInt(firstNum) * convertToInt(secondNum));
            case "÷":
                if (convertToInt(secondNum)==0){
                    return 0;
                }else return (convertToInt(firstNum) / convertToInt(secondNum));
            default:
                return convertToInt(firstNum);
        }
    }

    private void clean() {
        refreshText("");
        refreshOperate("");
    }

    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";

    }

    private void refreshText(String text) {
        showResult = text;
        screen.setText(showResult.toUpperCase());

    }

    int convertToInt(String s) {
        try {
            if (s.equals("")){
                return 0;
            }else return Integer.parseInt(s, 16);
        }catch (Exception e){
            clean();
            return 0;
        }
    }

    String convertToHex(int i) {
        return Integer.toHexString(i);
    }

    String hex2dec(String hexString) {
        try {
            if (hexString.length()>20){
                clean();
                return "";
            }
            BigInteger sInt = new BigInteger(hexString, 16);
            return sInt.toString(10);
        }catch (Exception e){
            clean();
            return "";
        }
    }

    String hex2bin(String hexString) {
        try {
            if (hexString.length()>20){
                clean();
                return "";
            }
            BigInteger sInt = new BigInteger(hexString, 16);
            String result = sInt.toString(2);
            return new StringBuilder(result).reverse().toString();

        }catch (Exception e){
            clean();
            return "";
        }

    }
}