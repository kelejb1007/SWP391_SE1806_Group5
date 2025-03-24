/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class ChapterSubmission {

    private int submissionCID;
    private int novelID;
    private int chapterID;
    private int userID;
    private int managerID;
    private String submissionDate;
    private String approvalDate;
    private String status;
    private String reasonRejected;
    private String type;
    private int draftID;

    private String chapterName;
    private String chapterNumber;
    private String novelName;
    private String userName;
    private int draftName;

    public ChapterSubmission() {
    }

    public ChapterSubmission(int submissionCID, int chapterID, int userID, int managerID, String submissionDate, String approvalDate, String status, String reasonRejected, String type) {
        this.submissionCID = submissionCID;
        this.chapterID = chapterID;
        this.userID = userID;
        this.managerID = managerID;
        this.submissionDate = submissionDate;
        this.approvalDate = approvalDate;
        this.status = status;
        this.reasonRejected = reasonRejected;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasonRejected() {
        return reasonRejected;
    }

    public void setReasonRejected(String reasonRejected) {
        this.reasonRejected = reasonRejected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDraftID() {
        return draftID;
    }

    public void setDraftID(int draftID) {
        this.draftID = draftID;
    }

    public int getSubmissionCID() {
        return submissionCID;
    }

    public void setSubmissionCID(int submissionCID) {
        this.submissionCID = submissionCID;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

}
