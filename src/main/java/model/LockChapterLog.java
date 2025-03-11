package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class LockChapterLog {
    private int logCID;
     private int managerID;
     private int chapterID;
     private LocalDateTime datetime;
     private String action;
     private String lockReason;
     
     private String staffName;
     private String chapterName;

    public LockChapterLog() {
    }

    public LockChapterLog(int managerID, int chapterID, String action, String lockReason) {
        this.managerID = managerID;
        this.chapterID = chapterID;
        this.action = action;
        this.lockReason = lockReason;
    }

    public int getLogCID() {
        return logCID;
    }

    public void setLogCID(int logCID) {
        this.logCID = logCID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
     
     
}
