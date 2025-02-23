/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class ReadingHistory {
    private int readingID;
    private int userID;
    private int novelID;
    private Integer chapterID; // Can be null
    
     private LocalDateTime lastReadDate;
     
     

    public ReadingHistory() {
    }

    public ReadingHistory(int readingID, int userID, int novelID, Integer chapterID, LocalDateTime lastReadDate) {
        this.readingID = readingID;
        this.userID = userID;
        this.novelID = novelID;
        this.chapterID = chapterID;
        this.lastReadDate = lastReadDate;
    }

    public int getReadingID() {
        return readingID;
    }

    public void setReadingID(int readingID) {
        this.readingID = readingID;
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

    public Integer getChapterID() {
        return chapterID;
    }

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public LocalDateTime getLastReadDate() {
        return lastReadDate;
    }

    public void setLastReadDate(LocalDateTime lastReadDate) {
        this.lastReadDate = lastReadDate;
    }
     
}
