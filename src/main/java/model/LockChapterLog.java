package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class LockChapterLog {

    private int chapterID;
    private int novelID;
    private String novelName;
    private int chapterNumber;
    private String chapterName;
    private String fileURL;
    private LocalDateTime chapterCreatedDate;
    private String chapterStatus;
    private int logID;
    private String action;
    private String lockReason;
    private Timestamp lockDate; // Giả định có thêm cột thời gian khóa trong LockChapterLog

    public LockChapterLog() {
    }

    public LockChapterLog(int chapterID, int novelID, String novelName, int chapterNumber, String chapterName,
            String fileURL, LocalDateTime chapterCreatedDate, String chapterStatus,
            int logID, String action, String lockReason, Timestamp lockDate) {
        this.chapterID = chapterID;
        this.novelID = novelID;
        this.novelName = novelName;
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.fileURL = fileURL;
        this.chapterCreatedDate = chapterCreatedDate;
        this.chapterStatus = chapterStatus;
        this.logID = logID;
        this.action = action;
        this.lockReason = lockReason;
        this.lockDate = lockDate;
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

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
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

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public Timestamp getLockDate() {
        return lockDate;
    }

    public void setLockDate(Timestamp lockDate) {
        this.lockDate = lockDate;
    }
}
