package DAO;

import java.nio.charset.StandardCharsets;
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
    public void createUserFromGoogle(String email, String fullName, String imageURL) {
        String sql = "INSERT INTO UserAccount (email, fullName, type, imageUML) VALUES (?, ?, 'google', ?)";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, fullName);
            stmt.setString(3, imageURL);
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
        user.setGender(rs.getString("gender"));
        user.setImageUML(rs.getString("imageUML"));
//        user.setStatus(rs.getInt("status"));
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
        String sql = "SELECT * FROM UserAccount WHERE status = 1"; // 1 = bị khóa

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
    // Khoa thêm phần này cho ViewProfile

    public UserAccount getUserByUsername(String username) {
        // Câu truy vấn SQL lấy thông tin người dùng từ bảng UserAccount
        String sql = "SELECT * FROM UserAccount WHERE userName = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);  // Set giá trị username
            ResultSet rs = stmt.executeQuery();  // Thực thi câu truy vấn

            if (rs.next()) {
                return mapResultSetToUser(rs);  // Chuyển đổi ResultSet thành đối tượng UserAccount
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu không tìm thấy user
    }

    public void updateUserImage(String username, String imageUrl) {
        String sql = "UPDATE UserAccount SET imageUML = ? WHERE userName = ?";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imageUrl);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Khoa thêm phần này cho EditProfile  
    public boolean updateUser(UserAccount user) {
        String sql = "UPDATE UserAccount SET fullName = ?, email = ?, numberPhone = ?, dateOfBirth = ?, gender = ?, imageUML = ? WHERE userID = ?";
        
        try (Connection conn = dbContext .getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getNumberPhone());
            stmt.setDate(4, new java.sql.Date(user.getDateOfBirth().getTime())); // ✅ Đúng kiểu
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getImageUML());
            stmt.setInt(7, user.getUserID());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //ChangePass
    public boolean changePassword(String username, String currentPassword, String newPassword, String confirmPassword) {
    if (!newPassword.equals(confirmPassword)) {
        return false;
    }
    String sql = "UPDATE UserAccount SET password = ? WHERE userName = ? AND password = ?";
    try (Connection conn = dbContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, hashSHA256(newPassword));
        stmt.setString(2, username);
        stmt.setString(3, hashSHA256(currentPassword));
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}




