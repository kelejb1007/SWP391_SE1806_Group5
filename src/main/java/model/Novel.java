/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class Novel {
    private int novelID;
    private String novelName;
    private int userID;
    private String imageURL;
    private String novelDescription;
    private int totalChapter;
    private String novelStatus;
    private LocalDateTime publishedDate;
    
    //Lấy từ bảng khác
    private String author;
    private double averageRating;
     private int viewCount;
     
    public Novel() {
    }

    public Novel(int novelID, String novelName, int userID, String imageURL, String novelDescription, int totalChapter, String novelStatus, LocalDateTime publishedDate) {
        this.novelID = novelID;
        this.novelName = novelName;
        this.userID = userID;
        this.imageURL = imageURL;
        this.novelDescription = novelDescription;
        this.totalChapter = totalChapter;
        this.novelStatus = novelStatus;
        this.publishedDate = publishedDate;
    }

    public Novel(int novelID, String novelName, String author,String imageURL, String novelDescription, int totalChapter, String novelStatus, LocalDateTime publishedDate) {
        this.novelID = novelID;
        this.novelName = novelName;
        this.imageURL = imageURL;
        this.novelDescription = novelDescription;
        this.totalChapter = totalChapter;
        this.novelStatus = novelStatus;
        this.publishedDate = publishedDate;
        this.author = author;
    }
    
    public Novel(int novelID, String novelName, String author,String imageURL, String novelDescription, int totalChapter, String novelStatus, double averageRating, LocalDateTime publishedDate) {
        this.novelID = novelID;
        this.novelName = novelName;
        this.imageURL = imageURL;
        this.novelDescription = novelDescription;
        this.totalChapter = totalChapter;
        this.novelStatus = novelStatus;
        this.averageRating = averageRating;
        this.publishedDate = publishedDate;
        this.author = author;
    }
    
    

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    
    
    
    
    //Của Novel
    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNovelDescription() {
        return novelDescription;
    }

    public void setNovelDescription(String novelDescription) {
        this.novelDescription = novelDescription;
    }

    public int getTotalChapter() {
        return totalChapter;
    }

    public void setTotalChapter(int totalChapter) {
        this.totalChapter = totalChapter;
    }

    public String getNovelStatus() {
        return novelStatus;
    }

    public void setNovelStatus(String novelStatus) {
        this.novelStatus = novelStatus;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate( LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    
}
