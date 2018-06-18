package com.example.hasoo.zookeepertouch;

public class ScoreRecords {
    private String score;
    private String userName;

    ScoreRecords(String score, String userName){
        this.score = score;
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }
}
