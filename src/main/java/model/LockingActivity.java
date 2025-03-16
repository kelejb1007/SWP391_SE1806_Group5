/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

public class LockingActivity {
    private int LogNID;
    private int novelID;
    private String novelName;
    private int managerID;
    private String managerName;
    private String action;
    private LocalDateTime datetime;
    private String LockReason;

    // Constructor with all fields
    public LockingActivity(int LogNID, int novelID, String novelName, int managerID, String managerName, String action, LocalDateTime datetime, String LockReason) {
        this.LogNID = LogNID;
        this.novelID = novelID;
        this.novelName = novelName;
        this.managerID = managerID;
        this.managerName = managerName;
        this.action = action;
        this.datetime = datetime;
        this.LockReason = LockReason;
    }

    // Constructor without activityID (for insertion)
    public LockingActivity(int novelID, int managerID, String action, LocalDateTime datetime, String LockReason) {
        this.novelID = novelID;
        this.managerID = managerID;
        this.action = action;
        this.datetime = datetime;
        this.LockReason = LockReason;
    }

    public int getLogNID() {
        return LogNID;
    }

    public void setLogNID(int LogNID) {
        this.LogNID = LogNID;
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

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getLockReason() {
        return LockReason;
    }

    public void setLockReason(String LockReason) {
        this.LockReason = LockReason;
    }

    @Override
    public String toString() {
        return "LockingActivity{" +
                "LogNID=" + LogNID +
                ", novelID=" + novelID +
                ", novelName='" + novelName + '\'' +
                ", managerID=" + managerID +
                ", managerName='" + managerName + '\'' +
                ", action='" + action + '\'' +
                ", datetime=" + datetime +
                ", LockReason='" + LockReason + '\'' +
                '}';
    }
}
