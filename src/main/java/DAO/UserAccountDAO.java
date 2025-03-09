package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ManagerAccount;
import model.UserAccount;
import utils.DBContext;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class UserAccountDAO {

    DBContext dbContext = new DBContext();

    // Xác thực user bằng username và password
    public UserAccount authenticateUser(String username, String password) {
        String sql = "SELECT * FROM UserAccount WHERE userName = ? AND password = ?";
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
    public UserAccount getUserByEmail(String email) {
        String sql = "SELECT * FROM UserAccount WHERE email = ?";
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
        String sql = "INSERT INTO UserAccount (email, fullName, role) VALUES (?, ?, 'user')";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy vai trò của user (User, Staff, Admin)
    public String getUserRole(int userId) {
        String sql = "SELECT role FROM UserAccount WHERE userID = ?";
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

    private static String hashSHA256(String input) {
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

    // Chuyển `ResultSet` thành `UserAccount`
    private UserAccount mapResultSetToUser(ResultSet rs) throws SQLException {
        UserAccount user = new UserAccount();
        user.setUserID(rs.getInt("userID"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("fullName"));
        user.setEmail(rs.getString("email"));
        user.setCreationDate(rs.getTimestamp("creationDate"));
        user.setNumberPhone(rs.getString("numberPhone"));
        user.setDateOfBirth(rs.getTimestamp("dateOfBirth"));
        user.setStatus(rs.getInt("status"));
        return user;
    }

    public static void main(String[] args) {
        String password = "123";
        System.out.println("Hash SHA-256 của password: " + hashSHA256(password));
    }

    // Khoa thêm do?n này Cho UC Rgist
    public boolean registerUser(UserAccount user) {
        String sql = "INSERT INTO UserAccount (userName, password, fullName, email, numberPhone, gender, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, hashSHA256(user.getPassword())); // Hash mật khẩu
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getNumberPhone());
            stmt.setString(6, user.getGender());
            stmt.setInt(7, user.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra user có tồn tại không (trùng username hoặc email)
    public boolean isUserExist(String username, String email) {
        String sql = "SELECT userID FROM UserAccount WHERE userName = ? OR email = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Nếu có kết quả -> username hoặc email đã tồn tại

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Khoa thêm phần này cho ViewProfile

    //LienXuanThinh
    // Lấy danh sách tất cả tài khoản
    public List<UserAccount> getAllAccounts() throws SQLException {
        List<UserAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT * FROM UserAccount";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listAccounts.add(mapResultSetToUser(rs));
            }
        }
        return listAccounts;
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<UserAccount> searchAccounts(String keyword) throws SQLException {
        List<UserAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT * FROM UserAccount WHERE userName LIKE ? OR email LIKE ? OR fullName LIKE ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listAccounts.add(mapResultSetToUser(rs));
                }
            }
        }
        return listAccounts;
    }

    // Lấy danh sách tài khoản bị khóa
    public List<UserAccount> getLockedAccounts() {
        List<UserAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT * FROM UserAccount WHERE status = 1"; // 0 = bị khóa

        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listAccounts.add(mapResultSetToUser(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listAccounts;
    }

    // Cập nhật trạng thái khóa/mở khóa tài khoản
    public void updateLockStatus(int userID, boolean newStatus) throws SQLException {
        String sql = "UPDATE UserAccount SET status = ? WHERE userID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStatus ? 1 : 0); // 1 = mở khóa, 0 = khóa
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        }
    }

    public List<UserAccount> getAccountStatistics() {
        List<UserAccount> useraccount = new ArrayList<>();
        String sql = "SELECT YEAR(creationDate) AS year, "
                + "MONTH(creationDate) AS month, "
                + "DAY(creationDate) AS day, "
                + "COUNT(userID) AS totalAccounts, "
                + "SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) AS totalActiveAccounts, "
                + "SUM(CASE WHEN MONTH(creationDate) = MONTH(GETDATE()) "
                + "AND YEAR(creationDate) = YEAR(GETDATE()) THEN 1 ELSE 0 END) AS totalAccountsThisMonth, "
                + "SUM(CASE WHEN MONTH(creationDate) = MONTH(DATEADD(MONTH, -1, GETDATE())) "
                + "AND YEAR(creationDate) = YEAR(DATEADD(MONTH, -1, GETDATE())) THEN 1 ELSE 0 END) AS totalAccountsLastMonth "
                + "FROM UserAccount "
                + "GROUP BY YEAR(creationDate), MONTH(creationDate), DAY(creationDate) "
                + "ORDER BY YEAR(creationDate) DESC, MONTH(creationDate) DESC, DAY(creationDate) DESC;";

        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UserAccount useracc = new UserAccount();
                useracc.setYear(rs.getInt("year"));
                useracc.setMonth(rs.getInt("month"));
                useracc.setDay(rs.getInt("day"));
                useracc.setTotalAccounts(rs.getInt("totalAccounts"));
                useracc.setTotalActiveAccounts(rs.getInt("totalActiveAccounts"));
                useracc.setTotalAccountsThisMonth(rs.getInt("totalAccountsThisMonth"));
                useracc.setTotalAccountsLastMonth(rs.getInt("totalAccountsLastMonth"));
                useraccount.add(useracc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return useraccount;
    }

}
