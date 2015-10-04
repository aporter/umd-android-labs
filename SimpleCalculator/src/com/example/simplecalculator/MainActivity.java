package com.example.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private TextView display;
    private double num1 = 0;  // num1 is used to store the number before the operator
    private String operator = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        display = (TextView) findViewById(R.id.editText1);
        display.setText("0");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onClick(View v) {
        Button button = (Button) v;
        String text = display.getText().toString(),
        input = button.getText().toString();
        Double result = 0.0;
        
        // This part handles the operators buttons
        if (input.equals("+") || input.equals("-") || input.equals("x") || input.equals("÷")) {
            if (input.equals("+"))
                operator = "+";
            else if (input.equals("-"))
                operator = "-";
            else if (input.equals("x"))
                operator = "x";
            else if (input.equals("÷"))
                operator = "÷";
            
            num1 = Double.parseDouble(text);
            display.setText(" ");
        }
        
        // landscape mode operators part
        if (input.equals("x²") || input.equals("x³") || input.equals("√") || input.equals("π") ||
            input.equals("sin(x)") || input.equals("cos(x)") || input.equals("tan(x)") || input.equals("e")
            || input.equals("%") || input.equals("ln") || input.equals("log10")) {
            
            num1 = Double.parseDouble(text);
            
            if (input.equals("x²"))
                result = num1 * num1;
            else if (input.equals("x³"))
                result = num1 * num1 * num1;
            else if (input.equals("√"))
                result = Math.sqrt(num1);
            else if (input.equals("π"))
                result = Math.PI;
            else if (input.equals("sin(x)"))
                result = Math.sin(num1);
            else if (input.equals("cos(x)"))
                result = Math.cos(num1);
            else if (input.equals("tan(x)"))
                result = Math.tan(num1);
            else if (input.equals("e"))
                result = Math.E;
            else if (input.equals("%"))
                result = num1 / 100;
            else if (input.equals("ln"))
                result = Math.log(num1);
            else if (input.equals("log10"))
                result = Math.log10(num1);
            
            display.setText(Double.toString(result));
        }
        
        // This part calculates the result  (the equal button)
        else if (input.equals("=")) {
            if (operator.equals("+"))
                result = num1 + Double.parseDouble(text);
            else if (operator.equals("-"))
                result = num1 - Double.parseDouble(text);
            else if (operator.equals("x"))
                result = num1 * Double.parseDouble(text);
            else if (operator.equals("÷"))
                result = num1 / Double.parseDouble(text);
            
            display.setText(Double.toString(result));
        }
        
        // This part handles the numeric inputs 1 ~ 9
        else {
            if (text.equals("0") || text.equals(" "))
                display.setText(input);
            else
                display.append(input);
        }
        
        // Clear button
        if (input.equals("Clear") || input.equals("C")) {
            num1 = 0;
            operator = "";
            display.setText("0");
        }
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
