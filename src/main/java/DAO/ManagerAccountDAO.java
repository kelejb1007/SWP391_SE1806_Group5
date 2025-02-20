package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import model.ManagerAccount;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class ManagerAccountDAO {

    DBContext dbContext = new DBContext();

    // Xác thực user bằng username và password
    public ManagerAccount authenticateUser(String username, String password) {
        String sql = "SELECT * FROM ManagerAccount WHERE userName = ? AND password = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hashSHA256(password));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy user bằng email (dùng cho Google Login)
    public ManagerAccount getUserByEmail(String email) {
        String sql = "SELECT * FROM ManagerAccount WHERE email = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tạo user mới từ Google
    public void createUserFromGoogle(String email, String fullName) {
        String sql = "INSERT INTO ManagerAccount (email, fullName, role) VALUES (?, ?, 'user')";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy vai trò của user (Staff, Admin)
    public String getUserRole(int userId) {
        String sql = "SELECT role FROM ManagerAccount WHERE ManagerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "user";
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Chuyển `ResultSet` thành `ManagerAccount`
    private ManagerAccount mapResultSetToUser(ResultSet rs) throws SQLException {
        ManagerAccount user = new ManagerAccount();
        user.setManagerID(rs.getInt("managerID"));
        user.setUsername(rs.getString("userName"));
        user.setFullName(rs.getString("fullName"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        return user;
    }
}