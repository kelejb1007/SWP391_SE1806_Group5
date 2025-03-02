/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tai vo 
 */
import java.util.Date;

public class Comment {
    private int commentID;
    private int userID;
    private int novelID;
    private String content;
    private Date commentDate;
    private String username; // Thêm thuộc tính username

    // Constructor mặc định
    public Comment() {
    }

    // Constructor đầy đủ
    public Comment(int commentID, int userID, int novelID, String content, Date commentDate, String username) {
        this.commentID = commentID;
        this.userID = userID;
        this.novelID = novelID;
        this.content = content;
        this.commentDate = commentDate;
        this.username = username;
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

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

        @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", userID=" + userID +
                ", novelID=" + novelID +
                ", content='" + content + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }
}
