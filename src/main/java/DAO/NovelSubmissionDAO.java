/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBContext;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class NovelSubmissionDAO {

    private final DBContext db;

    public NovelSubmissionDAO() {
        db = new DBContext();
    }

    public boolean addNovelSubmission(int novelID, int userID) {
        String sql = "INSERT INTO NovelSubmission (novelID, userID, status)\n"
                   + "VALUES (?, ?, 'pending')";
        Connection connection;
        PreparedStatement statement;
        int n;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, novelID);
            statement.setInt(2, userID);
            n = statement.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(NovelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

}
