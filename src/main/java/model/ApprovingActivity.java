package model;

import java.time.LocalDateTime;

public class ApprovingActivity {
    private int approveID;
    private int novelID;
    private String novelName;
    private int managerID;
    private String managerName;
    private String action;
    private LocalDateTime datetime;
    private String approveReason;

    public ApprovingActivity(int approveID, int novelID, String novelName, int managerID, String managerName, String action, LocalDateTime datetime, String approveReason) {
        this.approveID = approveID;
        this.novelID = novelID;
        this.novelName = novelName;
        this.managerID = managerID;
        this.managerName = managerName;
        this.action = action;
        this.datetime = datetime;
        this.approveReason = approveReason;
    }

    public int getApproveID() {
        return approveID;
    }

    public void setApproveID(int approveID) {
        this.approveID = approveID;
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

    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason;
    }
}
