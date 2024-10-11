package com.example.prakt_6_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd, buttonSubtract, buttonDivide, buttonMultiply, buttonUp, buttonSqrt, buttonClean;
    private TextView operation, result;
    private EditText number1, number2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonSqrt = (Button) findViewById(R.id.buttonSqrt);
        buttonClean = (Button) findViewById(R.id.buttonClean);

        operation = (TextView) findViewById(R.id.operation);
        result = (TextView) findViewById(R.id.result);

        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);

        buttonAdd.setOnClickListener(view -> calculate('+'));
        buttonSubtract.setOnClickListener(view -> calculate('-'));
        buttonDivide.setOnClickListener(view -> calculate('/'));
        buttonMultiply.setOnClickListener(view -> calculate('*'));
        buttonUp.setOnClickListener(view -> calculate('^'));
        buttonSqrt.setOnClickListener(view -> calculate('√'));
        buttonClean.setOnClickListener(view -> calculate('C'));
    }

    public void calculate(char o) {
        if (o == 'C') {
            number1.setText("");
            operation.setText("");
            number2.setText("");
            return;
        }

        float n1 = 0;
        float n2 = 0;
        float res = 0;

        try {
            n1 = Float.parseFloat(number1.getText().toString());

            if (o != '√')
                n2 = Float.parseFloat(number2.getText().toString());
        } catch (Exception e) {
            result.setText(String.valueOf(res));
            return;
        }

        operation.setText(String.valueOf(o));

        switch (o) {
            case '+':
                res = n1 + n2;
                break;
            case '-':
                res = n1 - n2;
                break;
            case '*':
                res = n1 * n2;
                break;
            case '^':
                res = (float) Math.pow(n1, n2);
                break;
            case '√':
                if (n1 < 0)
                    res = 0;
                else
                    res = (float) Math.sqrt(n1);
                break;
            case '/':
                if (n2 == 0)
                    res = 0;
                else
                    res = n1 / n2;
                break;
            default:
                break;
        }

        result.setText(String.valueOf(res));
    }
}