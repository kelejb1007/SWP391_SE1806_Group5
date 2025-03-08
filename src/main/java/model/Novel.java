/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private List<String> genreNames;
    private int ratingCount;
    private int chapterID;   // ID chương cuối cùng đã đọc
    private String lastChapterName; // Tên chương cuối cùng đã đọc
     private int lastChapterNumber; 
     private int Process;
     private LocalDateTime lastReadDate;
     private LocalDateTime  latestChapterDate;
    
    private String lockReason;
    private LocalDateTime datetime ;
    private String staffName;

    public Novel() {
    }

    public Novel(String novelName, int userID, String imageURL, String novelDescription, int totalChapter, String novelStatus) {
        this.novelName = novelName;
        this.userID = userID;
        this.imageURL = imageURL;
        this.novelDescription = novelDescription;
        this.totalChapter = totalChapter;
        this.novelStatus = novelStatus;
    }
    

    public Novel(int novelID, String novelName, String imageURL, int totalChapter, LocalDateTime publishedDate, String author, double averageRating, int viewCount) {
        this.novelID = novelID;
        this.novelName = novelName;
        this.imageURL = imageURL;
        this.totalChapter = totalChapter;
        this.publishedDate = publishedDate;
        this.author = author;
        this.averageRating = averageRating;
        this.viewCount = viewCount;
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
    
    //---------------------------------------------------------------------
    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public int getProcess() {
        return Process;
    }

    public void setProcess(int Process) {
        this.Process = Process;
    }

    public LocalDateTime getLatestChapterDate() {
        return latestChapterDate;
    }

    public void setLatestChapterDate(LocalDateTime latestChapterDate) {
        this.latestChapterDate = latestChapterDate;
    }

    
   

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public int getLastChapterNumber() {
        return lastChapterNumber;
    }

    public void setLastChapterNumber(int lastChapterNumber) {
        this.lastChapterNumber = lastChapterNumber;
    }

    public LocalDateTime getLastReadDate() {
        return lastReadDate;
    }

    public void setLastReadDate(LocalDateTime lastReadDate) {
        this.lastReadDate = lastReadDate;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
   
    public List<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
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


    

   // của Novel
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

    
     public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }
    public String getLastReadDateFormatted() {
    if (lastReadDate == null) {
        return "N/A"; // Trả về N/A nếu không có dữ liệu
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    return lastReadDate.format(formatter);
}
}
