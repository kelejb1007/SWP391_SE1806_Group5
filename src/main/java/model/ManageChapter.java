package model;

import java.time.LocalDateTime;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class ManageChapter {

    private int chapterID;
    private int novelID;
    private int chapterNumber;
    private String chapterName;
    private String fileURL;
    private LocalDateTime publishedDate;  // Đổi từ chapterCreatedDate
    private String chapterStatus;
    private boolean isLocked;

    // Thêm các thuộc tính liên quan đến khóa chương
    private LocalDateTime lockDateTime;
    private String lockAction;
    private String lockReason;

    public ManageChapter() {
    }

    public ManageChapter(int chapterID, int novelID, int chapterNumber, String chapterName, String fileURL,
            LocalDateTime publishedDate, String chapterStatus, boolean isLocked,
            LocalDateTime lockDateTime, String lockAction, String lockReason) {
        this.chapterID = chapterID;
        this.novelID = novelID;
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.fileURL = fileURL;
        this.publishedDate = publishedDate;
        this.chapterStatus = chapterStatus;
        this.isLocked = isLocked;
        this.lockDateTime = lockDateTime;
        this.lockAction = lockAction;
        this.lockReason = lockReason;
    }

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

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getChapterStatus() {
        return chapterStatus;
    }

    public void setChapterStatus(String chapterStatus) {
        this.chapterStatus = chapterStatus;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public LocalDateTime getLockDateTime() {
        return lockDateTime;
    }

    public void setLockDateTime(LocalDateTime lockDateTime) {
        this.lockDateTime = lockDateTime;
    }

    public String getLockAction() {
        return lockAction;
    }

    public void setLockAction(String lockAction) {
        this.lockAction = lockAction;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    @Override
    public String toString() {
        return "ManageChapter{"
                + "chapterID=" + chapterID
                + ", novelID=" + novelID
                + ", chapterNumber=" + chapterNumber
                + ", chapterName='" + chapterName + '\''
                + ", fileURL='" + fileURL + '\''
                + ", publishedDate=" + publishedDate
                + ", chapterStatus='" + chapterStatus + '\''
                + ", isLocked=" + isLocked
                + ", lockDateTime=" + lockDateTime
                + ", lockAction='" + lockAction + '\''
                + ", lockReason='" + lockReason + '\''
                + '}';
    }
}
