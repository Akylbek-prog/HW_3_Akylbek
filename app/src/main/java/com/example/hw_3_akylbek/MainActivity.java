package com.example.hw_3_akylbek;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hw_3_akylbek.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Double first, second;
    private Boolean isOperationClick;
    private String currentOperation = "";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textView = binding.textView;

        binding.textView.setText("0");

        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.text_view);
    }

    public void onNamberClick(View view) {
        String text = ((MaterialButton) view).getText().toString();
        if (text.equals("AC")) {
            textView.setText("0");
            first = null;
            currentOperation = "";
        } else if (text.equals("+/-")) {
            double currentValue = Double.parseDouble(textView.getText().toString());
            textView.setText(String.valueOf(currentValue * -1));
        } else if (text.equals("%")) {
            double currentValue = Double.parseDouble(textView.getText().toString());
            textView.setText(String.valueOf(currentValue / 100));
        } else if (text.equals(".")) {
            String currentText = textView.getText().toString();
            if (!currentText.contains(".")) {
                textView.append(".");
            }
        } else if (textView.getText().toString().equals("0") || isOperationClick) {
            textView.setText(text);
        } else {
            textView.append(text);
        }
        isOperationClick = false;
    }

    public void onOperationClick(View view) {
        String text = ((MaterialButton) view).getText().toString();
        if (text.equals("=")) {
            if (first == null || currentOperation.isEmpty()) return;

            second = Double.valueOf(textView.getText().toString());
            Double result = null;

            switch (currentOperation) {
                case "+":
                    result = first + second;
                    break;
                case "-":
                    result = first - second;
                    break;
                case "x":
                    result = first * second;
                    break;
                case "/":
                    if (second != 0) {
                        result = first / second;
                    } else {
                        textView.setText("Error");
                        return;
                    }
                    break;
                default:
                    return;
            }

            if (result == result.intValue()){
                textView.setText(String.valueOf(result.intValue()));
            } else {
                textView.setText(String.valueOf(result));
            }

            first = null;
            currentOperation = "";
        } else {
            first = Double.valueOf(textView.getText().toString());
            currentOperation = text;
        }
        isOperationClick = true;
    }
}
