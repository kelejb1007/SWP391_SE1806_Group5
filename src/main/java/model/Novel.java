/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class Novel {
    private int novelID;
    private String novelName;
    private String author;
    private String imageURL;
    private String novelDescription;
    private int totalChapter;
    private String novelStatus;
    private Date publishedDate;

    public Novel() {
    }

    public Novel(int novelID, String novelName, String author, String imageURL, String novelDescription, int totalChapter, String novelStatus, Date publishedDate) {
        this.novelID = novelID;
        this.novelName = novelName;
        this.author = author;
        this.imageURL = imageURL;
        this.novelDescription = novelDescription;
        this.totalChapter = totalChapter;
        this.novelStatus = novelStatus;
        this.publishedDate = publishedDate;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    
}
