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
        //getQuizes();
        getViews();
        //setQuize();
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
                    resultText.setText("正解です");
                    numberOfCorrect++;
                } else {
                    resultText.setText("間違いです");
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
                    // 最終問題
                    // 結果画面に遷移
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
/*
    private void getQuizes() {
        quizeList.add(new Quize("中国の首都はどこか", new String[]{"台北", "北京", "上海", "西安"}, 2));
        quizeList.add(new Quize("中国の人口の9割以上を占める民族は何民族か", new String[]{"唐民族", "アイヌ民族", "満州民族", "漢民族"}, 4));
        quizeList.add(new Quize("中国の華北平原を流れる運河はどれか", new String[]{"ナイル川", "長江", "ガンジス川", "黄河"}, 4));
        quizeList.add(new Quize("チベットから中国にかけて広がる世界最大の山脈は何山脈か", new String[]{"アンデス山脈", "アルプス山脈", "ヒマラヤ山脈", "ロッキー山脈"}, 3));
        quizeList.add(new Quize("中国が人口抑制のために行っている政策は何か", new String[]{"一人っ子政策", "5か年計画", "改革開放", "人口改革"}, 1));
        quizeList.add(new Quize("韓国の首都はどこか", new String[]{"ソウル", "インチョン", "ピョンヤン", "プサン"}, 1));
        quizeList.add(new Quize("『東南アジア連合』の略称を何というか", new String[]{"OPEC", "WTO", "APEC", "ASEAN"}, 4));
        quizeList.add(new Quize("世界第2位の人口がある国はどこか", new String[]{"インド", "インドネシア", "中国", "ロシア"}, 1));
        quizeList.add(new Quize("『アジア太平洋経済協力』の略称を何というか", new String[]{"PKO", "APEC", "OPEC", "OECD"}, 2));
        quizeList.add(new Quize("安価な労働力を使って単一作物を大量に栽培する大規模農園のことを何というか", new String[]{"プランバレー", "プランテーション", "モノカルチャー", "単一作園"}, 2));
    }
*/
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
