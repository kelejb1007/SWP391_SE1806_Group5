/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Novel;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class NovelDAO {
    private final DBContext db;

    public NovelDAO() {
        db = new DBContext();
    }
    
    //Admin-------------------------------------------------------------------------------------------------------------
public List<Novel> getAllActiveNovel(String s) {
        List<Novel> list = new ArrayList<>();
        String sql = "SELECT novelID, novelName, imageUML, totalChapter, publishedDate, fullName\n" 
                +    "FROM Novel n JOIN UserAccount u ON n.UserID = u.UserID "
                +    "WHERE novelStatus = '" + s + "'";
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Novel m = new Novel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
                                    (rs.getTimestamp(5) != null? rs.getTimestamp(5).toLocalDateTime() : null), rs.getString(6));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
     //------------------------------------------------------------------------------------------------------------------
