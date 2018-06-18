package com.example.hasoo.zookeepertouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StartActivity extends AppCompatActivity {

    private final String FILE_NAME = "score.txt";
    ArrayList<ScoreRecords> arrayList;
    ScoreAdapter scoreAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 파일을 읽어 arrayList에 저장
        // arrayList를 이용하여 ScoreAdapter 생성
        // scoreAdapter를 listView의 Adapter로 설정

        // 버튼 인스턴스 생성 및 onClickListener 설정
            // intent를 생성하여 GameActivity로 이동
    }

    @Override   // gameActivity의 결과를 받아 화면에 출력
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 데이터 리프레시 참조
        // https://stackoverflow.com/questions/15422120/notifydatasetchange-not-working-from-custom-adapter
        arrayList.clear();
        arrayList.addAll(readFileAndMakeList());
        scoreAdapter.notifyDataSetChanged();
    }

    // 파일을 읽어 arrayList를 만드는 메소드
    private ArrayList<ScoreRecords> readFileAndMakeList(){

        // arrayList 생성
        // bufferedReader 생성하여 파일 읽기

        // arrayList 정렬
        Collections.sort(arrayList, new Comparator<ScoreRecords>() {
            @Override
            public int compare(ScoreRecords o1, ScoreRecords o2) { // 정렬 예제
                int i1 = Integer.parseInt(o1.getScore());
                int i2 = Integer.parseInt(o2.getScore());
                return i2 - i1;
            }
        });

        return arrayList;
    }
}
