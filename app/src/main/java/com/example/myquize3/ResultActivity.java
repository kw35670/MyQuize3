package com.example.myquize3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView resultText;
    private Button moveTopButton;
    private int resultId = R.id.result;
    private int moveTopId = R.id.moveTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setViews();
        setListeners();
        Intent intent = getIntent();
        int numberOfQuize = intent.getIntExtra("numberOfQuize", 0);
        int numberOfCorrect = intent.getIntExtra("numberOfCorrect", 0);
        String resultMessage = numberOfQuize+"問中"+numberOfCorrect+"問正解です";
        resultText.setText(resultMessage);
    }

    private void setViews() {
        resultText = findViewById(resultId);
        moveTopButton = findViewById(moveTopId);
    }

    private void setListeners() {
        moveTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}