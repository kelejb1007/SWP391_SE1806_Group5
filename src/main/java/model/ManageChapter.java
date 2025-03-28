package model;

import java.util.Date;

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
    private Date publishedDate;
    private String chapterStatus;
    private boolean isLocked;
    private boolean isPending; // Thêm thuộc tính để đánh dấu chapter pending
    private Date lockDateTime;
    private String lockAction;
    private String lockReason;
    private String novelName;

    public ManageChapter() {
    }

    public ManageChapter(int chapterID, int novelID, int chapterNumber, String chapterName, String fileURL,
            Date publishedDate, String chapterStatus, boolean isLocked,
            Date lockDateTime, String lockAction, String lockReason, String novelName) {
        this.chapterID = chapterID;
        this.novelID = novelID;
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.fileURL = fileURL;
        this.publishedDate = publishedDate;
        this.chapterStatus = chapterStatus;
        this.isLocked = isLocked;
        this.isPending = false; // Giá trị mặc định
        this.lockDateTime = lockDateTime;
        this.lockAction = lockAction;
        this.lockReason = lockReason;
        this.novelName = novelName;
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
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

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean isPending) {
        this.isPending = isPending;
    }

    public Date getLockDateTime() {
        return lockDateTime;
    }

    public void setLockDateTime(Date lockDateTime) {
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

    public String getNovelName() {
        return novelName; // Sửa từ chapterName thành novelName
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    @Override
    public String toString() {
        return "ManageChapter{" +
                "chapterID=" + chapterID +
                ", novelID=" + novelID +
                ", novelName='" + novelName + '\'' +
                ", chapterNumber=" + chapterNumber +
                ", chapterName='" + chapterName + '\'' +
                ", fileURL='" + fileURL + '\'' +
                ", publishedDate=" + publishedDate +
                ", chapterStatus='" + chapterStatus + '\'' +
                ", isLocked=" + isLocked +
                ", isPending=" + isPending +
                ", lockDateTime=" + lockDateTime +
                ", lockAction='" + lockAction + '\'' +
                ", lockReason='" + lockReason + '\'' +
                '}';
    }
}