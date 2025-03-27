/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class LockAccountLog {

    private int logAID;
    private int managerID;
    private int userID;
    private Timestamp datetime;
    private String action;
    private String lockReason;

    public LockAccountLog(int logAID, int managerID, int userID, Timestamp datetime, String action, String lockReason) {
        this.logAID = logAID;
        this.managerID = managerID;
        this.userID = userID;
        this.datetime = datetime;
        this.action = action;
        this.lockReason = lockReason;
    }

    // Getter and Setter methods
    public int getLogAID() {
        return logAID;
    }

    public void setLogAID(int logAID) {
        this.logAID = logAID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
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
}
