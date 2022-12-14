package com.example.myquize3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView questionText;
    private RadioButton[] selectionRadioButtons = new RadioButton[4];
    private Button finalAnswerButton;
    private TextView resultText;
    private static final int questionId = R.id.question;
    private static final int[] selectionId = {R.id.rdi_1, R.id.rdi_2, R.id.rdi_3, R.id.rdi_4};
    private static final int finalAnswerId = R.id.finalAnswer;
    private static final int resultId = R.id.result;
    private Quize quize;
    private List<Quize> quizeList = new ArrayList<>();
    private int quizeIndex = 0;
    private static final int nextId = R.id.next;
    private Button nextButton;
    private String defaultResultMessage;
    private int numberOfCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAsync myAsync = new MyAsync(this);
        myAsync.execute();
        getViews();
        setListener();
        numberOfCorrect = 0;
    }

    private void getViews() {
        questionText = findViewById(questionId);
        for (int i=0; i<selectionId.length; i++) {
            selectionRadioButtons[i] = findViewById(selectionId[i]);
        }
        selectionRadioButtons[0].setChecked(true);
        finalAnswerButton = findViewById(finalAnswerId);
        resultText = findViewById(resultId);
        nextButton = findViewById(nextId);
        defaultResultMessage = resultText.getText().toString();
    }

    private void setListener() {
        finalAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean correct = false;
                for (int i=0; i<selectionRadioButtons.length; i++) {
                    if (selectionRadioButtons[i].isChecked()) {
                        if (i==quizeList.get(quizeIndex).getCorrectNumber()-1) {
                            correct = true;
                        }
                        break;
                    }
                }
                if (correct) {
                    resultText.setText("????????????");
                    numberOfCorrect++;
                } else {
                    resultText.setText("???????????????");
                }
                finalAnswerButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizeIndex<quizeList.size()-1) {
                    quizeIndex++;
                } else {
                    // ????????????
                    // ?????????????????????
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("numberOfQuize", quizeList.size());
                    intent.putExtra("numberOfCorrect", numberOfCorrect);
                    startActivity(intent);
                }
                setQuize();
                finalAnswerButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setQuize() {
        Quize quize = quizeList.get(quizeIndex);
        questionText.setText(quize.getQuestion());
        for (int i=0; i<selectionId.length; i++) {
            selectionRadioButtons[i].setText(quize.getSelections().get(i));
        }
        selectionRadioButtons[0].setChecked(true);
        resultText.setText(defaultResultMessage);
    }

    public void setData(List<Quize> quizeList) {
        this.quizeList = quizeList;
        questionText.setText(this.quizeList.get(quizeIndex).getQuestion());
        for (int i=0; i<selectionId.length; i++) {
            selectionRadioButtons[i].setText(this.quizeList.get(quizeIndex).getSelections().get(i));
        }
        selectionRadioButtons[0].setChecked(true);
        resultText.setText(defaultResultMessage);
    }
}
