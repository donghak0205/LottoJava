package fastcampus.aop.part2.aop_part2_chapter02_java;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainActivity extends AppCompatActivity {

    //NumberPicker
    private NumberPicker numberPicker;

    //Button
    private Button clearButton;
    private Button runButton;
    private Button addButton;

    //textViewList
    private ArrayList<TextView> textViewList;

    private HashSet<Integer> pickNumber;
    private boolean didRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberPicker = findViewById(R.id.numberPicker);
        clearButton = findViewById(R.id.clearButton);
        runButton = findViewById(R.id.runButton);
        addButton = findViewById(R.id.addButton);

        pickNumber = new HashSet<>();

        //numberPicker init value Setting
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(45);

        textViewList = new ArrayList<TextView>(
                List.of(findViewById(R.id.firstNum),
                        findViewById(R.id.secondNum),
                        findViewById(R.id.thirdNum),
                        findViewById(R.id.fourthNum),
                        findViewById(R.id.fifthNum),
                        findViewById(R.id.sixthNum)
                        )
        );

        initAddButton();
        initClearButton();
        initRunButton();
    }

    private void initAddButton() {
        addButton.setOnClickListener(b -> {
            int getNumber = numberPicker.getValue();

            if(didRun){
                Toast.makeText(this, "초기화를 해주세요!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pickNumber.size() >= 5) {
                Toast.makeText(this, "이미 5개가 선택되어있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(pickNumber.contains(getNumber)){
                Toast.makeText(this, "이미 선택한 번호 입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            TextView textView = textViewList.get(pickNumber.size());
            textView.setText(Integer.toString(getNumber));
            textView.setVisibility(TextView.VISIBLE);
            setBackground(textView,getNumber);
            pickNumber.add(Integer.valueOf(getNumber));
        });
    }

    private void initClearButton() {
        clearButton.setOnClickListener(b->{
            didRun = false;
            pickNumber.clear();
            textViewList.forEach(t->{
                t.setVisibility(TextView.GONE);
            });
        });

    }
    private void initRunButton() {
        runButton.setOnClickListener(rb -> {
            didRun = true;
            ArrayList<Integer> randomNumList = getRandomNumber();

            randomNumList.forEach(a->{
                TextView tv = textViewList.get(randomNumList.indexOf(a));
                tv.setText(a.toString());
                tv.setVisibility(TextView.VISIBLE);
                setBackground(tv, a.intValue());
            });
        });
    }

    private ArrayList<Integer>  getRandomNumber() {
        Random ran = new Random();
        HashSet<Integer> set = new HashSet<>();

        while(set.size() < 6-pickNumber.size()){
            set.add(Integer.valueOf(ran.nextInt(45) + 1));
        }

        set.addAll(pickNumber);

        ArrayList<Integer> arrList = new ArrayList<>(set);
        Collections.sort(arrList);

        return arrList;
    }

    private void setBackground(TextView tv, int i) {
        if(i<10){
            tv.setBackground(ContextCompat.getDrawable(this,R.drawable.cicrle_blue));
        } else if(i<20){
            tv.setBackground(ContextCompat.getDrawable(this,R.drawable.cicrle_gray));
        } else if(i<30){
            tv.setBackground(ContextCompat.getDrawable(this,R.drawable.cicrle_green));
        } else if(i<40){
            tv.setBackground(ContextCompat.getDrawable(this,R.drawable.cicrle_red));
        } else {
            tv.setBackground(ContextCompat.getDrawable(this,R.drawable.cicrle_yello));
        }
    }
}