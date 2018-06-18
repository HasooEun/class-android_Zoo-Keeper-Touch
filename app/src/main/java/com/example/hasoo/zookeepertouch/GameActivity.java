package com.example.hasoo.zookeepertouch;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // definition
    private final int BOARD_SIZE = 8;
    private final int EMPTY_BLOCK = -1;
    private final int NUM_OF_IMGS = 7;
    private final int TIME_LIMIT = 100;
    private final String FILE_NAME = "score.txt";

    // objects
    int[] imgs = null, scores = null;
    int[][] board = null;
    ImageView[][] imageViews = null;
    TextView[] textViews = null;
    boolean isIdleState = true;
    int preRow = EMPTY_BLOCK, preCol = EMPTY_BLOCK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialize variables
        board = new int[BOARD_SIZE][BOARD_SIZE];
        imgs = new int[NUM_OF_IMGS];
        scores = new int[NUM_OF_IMGS];
        textViews = new TextView[NUM_OF_IMGS];
        imageViews = new ImageView[BOARD_SIZE][BOARD_SIZE];

        for(int i=0; i<BOARD_SIZE; i++){
            imageViews[i] = new ImageView[BOARD_SIZE];
        }
        initVariables(board, imgs, imageViews);

        boolean need_to_check = true;
        while(need_to_check){

            // positions to be removed
            ArrayList<Position> arrayList = new ArrayList<>();
            // make random board
            fillBoard(board);
            // check the random board
            need_to_check = checkBoard(board, arrayList);
            // remove selected blocks
            removeBlocks(board, arrayList, null, null);
            // cascade blocks
            cascadeBlocks(board);
        }

        // print board
        fillImageViews(board, imgs, imageViews);

        // start timer
        final AsyncTimer timer = new AsyncTimer();
        timer.execute();

        // set button to work
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel(true);
                gameFinished();
            }
        });
    }

    // initialize variables with call-by-reference
    private void initVariables(int[][] board, int[] imgs, ImageView[][] imageViews){

        for(int i=0; i<BOARD_SIZE; i++){
            for(int j=0; j<BOARD_SIZE; j++){
                board[i][j] = EMPTY_BLOCK;
            }
        }
        
        for(int i=0; i<NUM_OF_IMGS; i++){
            scores[i] = 0;
        }

        imgs[0] = R.drawable.img1;
        imgs[1] = R.drawable.img2;
        imgs[2] = R.drawable.img3;
        imgs[3] = R.drawable.img4;
        imgs[4] = R.drawable.img5;
        imgs[5] = R.drawable.img6;
        imgs[6] = R.drawable.img7;

        textViews[0] = findViewById(R.id.textView00);
        textViews[1] = findViewById(R.id.textView01);
        textViews[2] = findViewById(R.id.textView02);
        textViews[3] = findViewById(R.id.textView03);
        textViews[4] = findViewById(R.id.textView04);
        textViews[5] = findViewById(R.id.textView05);
        textViews[6] = findViewById(R.id.textView06);

        imageViews[0][0] = findViewById(R.id.imageView00);
        imageViews[0][1] = findViewById(R.id.imageView01);
        imageViews[0][2] = findViewById(R.id.imageView02);
        imageViews[0][3] = findViewById(R.id.imageView03);
        imageViews[0][4] = findViewById(R.id.imageView04);
        imageViews[0][5] = findViewById(R.id.imageView05);
        imageViews[0][6] = findViewById(R.id.imageView06);
        imageViews[0][7] = findViewById(R.id.imageView07);

        imageViews[1][0] = findViewById(R.id.imageView10);
        imageViews[1][1] = findViewById(R.id.imageView11);
        imageViews[1][2] = findViewById(R.id.imageView12);
        imageViews[1][3] = findViewById(R.id.imageView13);
        imageViews[1][4] = findViewById(R.id.imageView14);
        imageViews[1][5] = findViewById(R.id.imageView15);
        imageViews[1][6] = findViewById(R.id.imageView16);
        imageViews[1][7] = findViewById(R.id.imageView17);

        imageViews[2][0] = findViewById(R.id.imageView20);
        imageViews[2][1] = findViewById(R.id.imageView21);
        imageViews[2][2] = findViewById(R.id.imageView22);
        imageViews[2][3] = findViewById(R.id.imageView23);
        imageViews[2][4] = findViewById(R.id.imageView24);
        imageViews[2][5] = findViewById(R.id.imageView25);
        imageViews[2][6] = findViewById(R.id.imageView26);
        imageViews[2][7] = findViewById(R.id.imageView27);

        imageViews[3][0] = findViewById(R.id.imageView30);
        imageViews[3][1] = findViewById(R.id.imageView31);
        imageViews[3][2] = findViewById(R.id.imageView32);
        imageViews[3][3] = findViewById(R.id.imageView33);
        imageViews[3][4] = findViewById(R.id.imageView34);
        imageViews[3][5] = findViewById(R.id.imageView35);
        imageViews[3][6] = findViewById(R.id.imageView36);
        imageViews[3][7] = findViewById(R.id.imageView37);

        imageViews[4][0] = findViewById(R.id.imageView40);
        imageViews[4][1] = findViewById(R.id.imageView41);
        imageViews[4][2] = findViewById(R.id.imageView42);
        imageViews[4][3] = findViewById(R.id.imageView43);
        imageViews[4][4] = findViewById(R.id.imageView44);
        imageViews[4][5] = findViewById(R.id.imageView45);
        imageViews[4][6] = findViewById(R.id.imageView46);
        imageViews[4][7] = findViewById(R.id.imageView47);

        imageViews[5][0] = findViewById(R.id.imageView50);
        imageViews[5][1] = findViewById(R.id.imageView51);
        imageViews[5][2] = findViewById(R.id.imageView52);
        imageViews[5][3] = findViewById(R.id.imageView53);
        imageViews[5][4] = findViewById(R.id.imageView54);
        imageViews[5][5] = findViewById(R.id.imageView55);
        imageViews[5][6] = findViewById(R.id.imageView56);
        imageViews[5][7] = findViewById(R.id.imageView57);

        imageViews[6][0] = findViewById(R.id.imageView60);
        imageViews[6][1] = findViewById(R.id.imageView61);
        imageViews[6][2] = findViewById(R.id.imageView62);
        imageViews[6][3] = findViewById(R.id.imageView63);
        imageViews[6][4] = findViewById(R.id.imageView64);
        imageViews[6][5] = findViewById(R.id.imageView65);
        imageViews[6][6] = findViewById(R.id.imageView66);
        imageViews[6][7] = findViewById(R.id.imageView67);

        imageViews[7][0] = findViewById(R.id.imageView70);
        imageViews[7][1] = findViewById(R.id.imageView71);
        imageViews[7][2] = findViewById(R.id.imageView72);
        imageViews[7][3] = findViewById(R.id.imageView73);
        imageViews[7][4] = findViewById(R.id.imageView74);
        imageViews[7][5] = findViewById(R.id.imageView75);
        imageViews[7][6] = findViewById(R.id.imageView76);
        imageViews[7][7] = findViewById(R.id.imageView77);
    }

    // 랜덤 메소드를 이용하여 비어있는 블록을 채워줌
    private void fillBoard(int[][] board){
    }

    // 현재 지울 수 있는 블록이 있는지 검사
    private boolean checkBoard(int[][] board, ArrayList<Position> arrayList) {

        // 좌에서 우로 검사
            // 동일한 블록이 3개 이상 연결되어 있는지 검사
                // true: 지울 블록 리스트에 집어 넣음
                // 지울 수 있다고 체크
        // 위에서 아래로 검사
            // 동일한 블록이 3개 이상 연결되어 있는지 검사
                // true: 지울 블록 리스트에 집어 넣음
                // 지울 수 있다고 체크

        //지울 수 있는지 여부를 반환
        return true;
    }

    // 동일한 블록이 몇개나 나열되어 있는지 검사하는 메소드
    private int findHammingDistance(int[][] board, int row, int col, boolean left_to_right){

        // left_to_right가 true인 경우 좌에서 우로 검사
        // left_to_right가 false인 경우 위에서 아래로 검사
            // 현재 이미지와 다음 이미지가 같은 같은 것이라면 hammingDistance++
        // hamming distance를 반환

        return 0;
    }

    // 지우면서 점수 계산
    private void removeBlocks(int[][] board, ArrayList<Position> arrayList, int[] scores, ImageView[][] imageViews){

        // arrayList에 있는 목록에 대하여 블록을 제거
        // 제거하면서 해당 이미지에 점수를 계산
        // 지운 블록은 EMPTY_BLOCK으로 처리
    }

    // 지워진 블록의 자리를 위의 블록으로 대체
    private void cascadeBlocks(int[][] board){
        // bottom-up approach를 이용하여 하나씩 위에서 내림
        // 위의 블록이 모두 empty거나 제일 위까지 올라간 경우 종료
    }

    // 게임 보드의 image resource를 imgs[board[i][j]]로 설정
    private void fillImageViews(int[][] board, int[] imgs, ImageView[][] imageViews){
    }

    // 점수를 게임 상단에 출력
    private void updateScore(int[] scores, TextView[] textViews){
    }

    // 이미지뷰 이름에서 현재 위치를 찾음
    private Position getPosition(View v){
        String viewId = v.getResources().getResourceName(v.getId());
        int row = Character.digit(viewId.charAt(viewId.length()-2), 10);
        int col = Character.digit(viewId.charAt(viewId.length()-1), 10);
        return new Position(row, col, -1, -1);
    }

    // 특정 이미지를 터치한 경우, 터치 표시를 하거나 표시를 지움
    private void setTouched(int row, int col, boolean set_to_touched){
    }

    // 보드 상의 두 값을 바꿈
    private void swap(int[][] board, Position pos){
        int temp = board[pos.getRow1()][pos.getCol1()];
        board[pos.getRow1()][pos.getCol1()] = board[pos.getRow2()][pos.getCol2()];
        board[pos.getRow2()][pos.getCol2()] = temp;
    }

    // 이미지를 터치했을 때 처리하는 메소드
    public void onTouchImage(View v){

        // 현재 터치한 이미지의 위치를 가져옴

        // 이전에 터치해놓은 이미지가 없다면
        if(isIdleState){
            // 현재 이미지 위치를 preRow, preCol에 저장
            // preRow, preCol을 터치했다고 표시
        } else {    // 이전에 터치해놓은 이미지가 있다면 (위치를 바꾸려 한다면)
            // 새로 터치한 이미지와 이전 이미지가 인접해 있다면
                // 일단 두 이미지를 교체
                // 지울 수 있는 목록을 담을 arrayList 생성
                // 보드를 검사했을 때 더 이상 지울 것이 없을 때 까지 아래 작업 반복
                    // 블록을 지우면서 점수 계산
                    // 비어있는 블록을 위의 블록으로 채움
                    // 비어있는 자리를 랜덤 함수로 만든 블록으로 채움
                    // 계산된 결과에 맞추어 실제 이미지를 출력
                    // arrayList 비우기
                // 검사했을 때 지울 것이 하나도 없다면
                    // 두 이미지를 원래대로 다시 교체
            // 터치마크 제거
        }
    }

    // 전체 점수와 이름을 파일에 기록
    private void saveScoreAndFinish(int totalScore, String userName){

        // buffered writer를 생성하여 파일에 기록
        // intent를 생성하여 startActivity로 이동
    }

    // 게임이 종료되었을 때 처리할 메소드
    private void gameFinished(){

        // 전체 점수 계산
        // 다이얼로그 생성
        // 게임 결과를 다이얼로그에 출력
        // 닫기 메소드에 이름을 비운 경우로 처리, 전체 점수와 사용자 이름을 파일에 기록
        // 확인 메소드에 이름을 받아 처리, 전체 점수와 사용자 이름을 파일에 기록
    }

    // 시간을 출력할 타이머 스레드
    @SuppressLint("StaticFieldLeak")
    class AsyncTimer extends AsyncTask<Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}