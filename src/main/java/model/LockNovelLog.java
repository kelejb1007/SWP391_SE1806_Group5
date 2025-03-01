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
public class LockNovelLog {
     private int logNID;
     private int managerID;
     private int novelID;
     private LocalDateTime datetime;
     private String action;
     private String lockReason;
     
     // o bang khac
     private String staffName;
     private String novelName ;

    public LockNovelLog() {
    }

    public LockNovelLog(int managerID, int novelID, String action, String lockReason) {
        this.managerID = managerID;
        this.novelID = novelID;
        this.action = action;
        this.lockReason = lockReason;
    }

    public int getLogNID() {
        return logNID;
    }

    public void setLogNID(int logNID) {
        this.logNID = logNID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public LocalDateTime getPublishedDate() {
        return datetime;
    }

    public void setPublishedDate(LocalDateTime datetime) {
        this.datetime = datetime;
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

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

   
}
