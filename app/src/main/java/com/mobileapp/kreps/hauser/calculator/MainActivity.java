package com.mobileapp.kreps.hauser.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity {
    private int[] numerals = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
    private int[] operands = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};
    private TextView displayView;
    private boolean numeralFlag;
    private boolean error;
    private boolean dotFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.displayView = (TextView) findViewById(R.id.displayView);
        setupNumerals();
        setupOperands();
    }

    private void setupNumerals() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (error) {
                    displayView.setText(button.getText());
                    error = false;
                } else {
                    displayView.append(button.getText());
                }
                numeralFlag = true;
            }
        };
        for (int id : numerals) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setupOperands() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numeralFlag && !error) {
                    Button button = (Button) v;
                    displayView.append(button.getText());
                    numeralFlag = false;
                    dotFlag = false;
                }
            }
        };
        for (int id : operands) {
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numeralFlag && !error && !dotFlag) {
                    displayView.append(".");
                    numeralFlag = false;
                    dotFlag = true;
                }
            }
        });
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView.setText("");
                numeralFlag = false;
                error = false;
                dotFlag = false;
            }
        });
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalPressed();
            }
        });
    }

    private void equalPressed() {
        if (numeralFlag && !error) {
            String txt = displayView.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                displayView.setText(Double.toString(result));
                dotFlag = true; // Result contains a dot
            } catch (ArithmeticException ex) {
                displayView.setText("Error");
                error = true;
                numeralFlag = false;
            }
        }
    }
}