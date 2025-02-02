package com.example.hw_3_akylbek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private Button btnOpenResult;
    private Double first, second;
    private Boolean isOperationClick;
    private String currentOperation = "";
    private ActivityMainBinding binding;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textView = binding.textView;

        binding.textView.setText("0");

        btnOpenResult = findViewById(R.id.btn_open_result);
        btnOpenResult.setVisibility(View.GONE);

        findViewById(R.id.btn_open_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });{
            btnOpenResult.setOnClickListener(v -> {
                String result = textView.getText().toString();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            });

        }

        //EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.text_view);
    }

    public void onNamberClick(View view) {
        String text = ((MaterialButton) view).getText().toString();
        btnOpenResult.setVisibility(View.GONE);
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
            btnOpenResult.setVisibility(View.VISIBLE);

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
