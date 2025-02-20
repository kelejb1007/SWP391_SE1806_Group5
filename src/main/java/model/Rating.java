/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class Rating {
    private int ratingID;
    private int userID;
    private int novelID;
    private int score;

    public Rating() {
    }

    public Rating(int ratingID, int userID, int novelID, int score) {
        this.ratingID = ratingID;
        this.userID = userID;
        this.novelID = novelID;
        this.score = score;
    }

    public int getRatingID() {
        return ratingID;
    }

    public void setRatingID(int ratingID) {
        this.ratingID = ratingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
