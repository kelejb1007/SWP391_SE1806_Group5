/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author $ LienXuanThinh - CE182117
 */
public class Notification {

    private int id;
    private int userID;
    private int novelID;
    private String chapterName;
    private Timestamp createdAt;
    private boolean isRead;
    private String novelName;

    public Notification() {
        // Constructor mặc định
    }

    public Notification(int userID, int novelID, String chapterName, boolean isRead,String novelName) {
        this.userID = userID;
        this.novelID = novelID;
        this.chapterName = chapterName;
        this.isRead = isRead;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.novelName = novelName;

    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

}
