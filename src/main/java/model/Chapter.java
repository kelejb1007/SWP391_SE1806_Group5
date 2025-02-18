/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class Chapter {
      private int chapterID;
    private int novelID;
    private int chapterNumber;
    private String chapterName;
    private String fileURL;
    private LocalDateTime chapterCreatedDate;
    private String chapterStatus; // Thêm trường này
    private String timeString;
     public Chapter() {
    }

    public Chapter(int chapterID, int novelID, int chapterNumber, String chapterName, String fileURL, LocalDateTime chapterCreatedDate, String chapterStatus) {
        this.chapterID = chapterID;
        this.novelID = novelID;
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.fileURL = fileURL;
        this.chapterCreatedDate = chapterCreatedDate;
        this.chapterStatus = chapterStatus;
    }

    // Getters và Setters cho tất cả các thuộc tính
    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public LocalDateTime getChapterCreatedDate() {
        return chapterCreatedDate;
    }

    public void setChapterCreatedDate(LocalDateTime chapterCreatedDate) {
        this.chapterCreatedDate = chapterCreatedDate;
    }

    public String getChapterStatus() {
        return chapterStatus;
    }

    public void setChapterStatus(String chapterStatus) {
        this.chapterStatus = chapterStatus;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }


    @Override
    public String toString() {
        return "Chapter{" +
                "chapterID=" + chapterID +
                ", novelID=" + novelID +
                ", chapterNumber=" + chapterNumber +
                ", chapterName='" + chapterName + '\'' +
                ", fileURL='" + fileURL + '\'' +
                ", chapterCreatedDate=" + chapterCreatedDate +
                ", chapterStatus='" + chapterStatus + '\'' +
                ", timeString='" + timeString + '\'' +
                '}';
    }

}
