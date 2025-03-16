/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tai vo 
 */
import java.time.LocalDateTime;

public class Comment {
    private int commentID;
    private int userID;
    private int novelID;
    private String content;
    private LocalDateTime commentDate;
    private String fullName; // Thêm thuộc tính username

    // Constructor mặc định
    public Comment() {
    }

    // Constructor đầy đủ
    public Comment(int commentID, int userID, int novelID, String content, LocalDateTime commentDate, String fullName) {
        this.commentID = commentID;
        this.userID = userID;
        this.novelID = novelID;
        this.content = content;
        this.commentDate = commentDate;
        this.fullName = fullName;
    }

    // Getter và Setter
    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


        @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", userID=" + userID +
                ", novelID=" + novelID +
                ", content='" + content + '\'' +
                ", commentDate=" + commentDate +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
