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
public class NovelSubmission {
//     submissionNID INT IDENTITY(1,1) PRIMARY KEY,
//    novelID INT NOT NULL,
//    userID INT NOT NULL,  -- Người gửi duyệt (có thể là chính tác giả hoặc người khác)
//    managerID INT ,
//    submissionDate DATETIME2 DEFAULT GETDATE(),
//    approvalDate DATETIME2 DEFAULT GETDATE(),
//    status VARCHAR(10) CHECK (status IN ('pending', 'approved', 'rejected')),
//    reasonRejected NVARCHAR(200),

    private int submissionNID;
    private int novelID;
    private int userID;
    private int managerID;
    private String submissionDate;
    private String approvalDate;
    private String status;
    private String reasonRejected;
    private String type;
    private int draftID;

    private String novelName;
    private String userName;
    private int draftName;

    public NovelSubmission() {
    }

    public NovelSubmission(int submissionNID, int novelID, int userID, int managerID, String submissionDate, String approvalDate, String status, String reasonRejected) {
        this.submissionNID = submissionNID;
        this.novelID = novelID;
        this.userID = userID;
        this.managerID = managerID;
        this.submissionDate = submissionDate;
        this.approvalDate = approvalDate;
        this.status = status;
        this.reasonRejected = reasonRejected;
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

    public int getSubmissionNID() {
        return submissionNID;
    }

    public void setSubmissionNID(int submissionNID) {
        this.submissionNID = submissionNID;
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
    
    

}
