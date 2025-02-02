package com.example.hw_3_akylbek;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.widget.SeekBar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.hw_3_akylbek.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    private boolean isLiked = false;
    private ImageButton likeButton;
    private ActivityResultBinding binding;
    private TextView textView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        TextView textViewResult = findViewById(R.id.text_view_result);

        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dropdown_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner1.setAdapter(adapter);

        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    Toast.makeText(ResultActivity.this, "Вы выбрали: " + selectedItem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        binding.seekBar1.setOnSeekBarChangeListener(seekBarChangeListener);
        binding.seekBar2.setOnSeekBarChangeListener(seekBarChangeListener);

        binding.btnNext.setOnClickListener(view -> {
            finishAffinity();
        });

        likeButton = findViewById(R.id.likeButton);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLiked = !isLiked;
                likeButton.setImageResource(isLiked ? R.drawable.ic_like_on : R.drawable.ic_like_off);
            }
        });

        String result = getIntent().getStringExtra("result");
        if (result != null && !result.isEmpty()){
            textViewResult.setText(result);
        } else {
        textViewResult.setText("No result");}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}