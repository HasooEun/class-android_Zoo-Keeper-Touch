package com.example.hasoo.zookeepertouch;

public class Position {

    private int row1, col1, row2, col2;

    Position(int row1, int col1, int row2, int col2){
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
    }

    Position(int row1, int col1){
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = -1;
        this.col2 = -1;
    }

    public int getRow1(){
        return row1;
    }

    public int getCol1() {
        return col1;
    }

    public int getRow2() {
        return row2;
    }

    public int getCol2() {
        return col2;
    }
}
