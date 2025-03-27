package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ManagerAccount;
import utils.DBContext;
import java.util.ArrayList;
import java.util.List;

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
        user.setCanLock(rs.getBoolean("canLock"));
        user.setCanApprove(rs.getBoolean("canApprove"));
        return user;
    }

    /**
     * Khoa thêm cho UC ManageStaff Lấy danh sách tài khoản theo vai trò.
     *
     * @param role Vai trò cần lấy (ví dụ: "Staff").
     * @return Danh sách tài khoản phù hợp.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> getAccountsByRole(String role) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role  "
                + "FROM ManagerAccount WHERE role = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    //LienXuanThinh
    // Lấy danh sách tất cả tài khoản
    public List<ManagerAccount> getAllAccounts() throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role, status "
                + "FROM ManagerAccount";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ManagerAccount account = mapResultSetToAccount(rs);
                listAccounts.add(account);
            }
        }
        return listAccounts;
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<ManagerAccount> searchAccounts(String keyword) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role, status "
                + "FROM ManagerAccount "
                + "WHERE username LIKE ? OR email LIKE ? OR fullName LIKE ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    /**
     * Tìm kiếm tài khoản nhân viên theo từ khóa.
     *
     * @param role Vai trò nhân viên ("Staff").
     * @param keyword Từ khóa tìm kiếm.
     * @return Danh sách tài khoản nhân viên phù hợp.
     * @throws SQLException Nếu có lỗi truy vấn.
     */
    public List<ManagerAccount> searchAccountsByRole(String role, String keyword) throws SQLException {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role "
                + "FROM ManagerAccount "
                + "WHERE role = ? AND (username LIKE ? OR email LIKE ? OR fullName LIKE ?)";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, role);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);

            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ManagerAccount account = mapResultSetToAccount(rs);
                    listAccounts.add(account);
                }
            }
        }
        return listAccounts;
    }

    // Lấy danh sách tài khoản bị khóa
    public List<ManagerAccount> getLockedAccounts() {
        List<ManagerAccount> listAccounts = new ArrayList<>();
        String sql = "SELECT managerID, username, password, creationDate, fullName, email, numberPhone, gender, canLock, canApprove, role, status "
                + "FROM ManagerAccount WHERE status = 0"; // Chỉ lấy tài khoản bị khóa

        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listAccounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listAccounts;
    }

    // Cập nhật trạng thái khóa/mở khóa tài khoản
    public void updateLockStatus(int managerID, boolean newStatus) throws SQLException {
        String sql = "UPDATE ManagerAccount SET status = ? WHERE managerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStatus ? 1 : 0); // 1 = Mở khoá, 0 = Khoá
            stmt.setInt(2, managerID);
            stmt.executeUpdate();
        }
    }

    private ManagerAccount mapResultSetToAccount(ResultSet rs) throws SQLException {
        ManagerAccount account = new ManagerAccount();
        account.setManagerID(rs.getInt("managerID"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setCreationDate(rs.getTimestamp("creationDate"));
        account.setFullName(rs.getString("fullName"));
        account.setEmail(rs.getString("email"));
        account.setNumberPhone(rs.getString("numberPhone"));
        account.setGender(rs.getString("gender"));
        account.setCanLock(rs.getBoolean("canLock"));
        account.setCanApprove(rs.getBoolean("canApprove"));
        account.setRole(rs.getString("role"));

//        account.setStatus(rs.getInt("status"));
        return account;
    }
    //Khoa thêm cho Register Staff

    public boolean isUserExist(String username, String email) {
        String sql = "SELECT managerID FROM ManagerAccount WHERE userName = ? OR email = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();

            // Nếu có kết quả, tức là đã có tài khoản với username hoặc email này
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(ManagerAccount newAccount) {
        String sql = "INSERT INTO ManagerAccount (username, password, fullName, email, numberPhone, gender, role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newAccount.getUsername());
            stmt.setString(2, hashSHA256(newAccount.getPassword())); // Hash mật khẩu
            stmt.setString(3, newAccount.getFullName());
            stmt.setString(4, newAccount.getEmail());
            stmt.setString(5, newAccount.getNumberPhone());
            stmt.setString(6, newAccount.getGender());
            stmt.setString(7, newAccount.getRole());  // Đây là "Staff" mặc định nếu chưa thay đổi

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;  // Trả về true nếu có ít nhất 1 dòng được chèn vào
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //DetailStaff
    public ManagerAccount getAccountById(int managerID) throws SQLException {
        ManagerAccount account = null;
        String sql = "SELECT * FROM ManagerAccount WHERE managerID = ?";

        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    account = new ManagerAccount();
                    account.setManagerID(rs.getInt("managerID"));
                    account.setUsername(rs.getString("username"));
                    account.setFullName(rs.getString("fullName"));
                    account.setEmail(rs.getString("email"));
                    account.setNumberPhone(rs.getString("numberPhone"));
                    account.setGender(rs.getString("gender"));
                    account.setRole(rs.getString("role"));
                    account.setCreationDate(rs.getDate("creationDate"));
                    account.setStatus(rs.getInt("status"));
                }
            }
        }
        return account;
    }
    // VO CHI TAI THEM CODE

    public List<ManagerAccount> getAllManagers() {
        List<ManagerAccount> accounts = new ArrayList<>();
        String sql = "SELECT managerID, username, email, role, canLock, canApprove FROM ManagerAccount";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ManagerAccount acc = new ManagerAccount();
                acc.setManagerID(rs.getInt("managerID"));
                acc.setUsername(rs.getString("username"));
                acc.setEmail(rs.getString("email"));
                acc.setRole(rs.getString("role"));
                acc.setCanLock(rs.getBoolean("canLock"));
                acc.setCanApprove(rs.getBoolean("canApprove"));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean updatePermissions(int managerID, boolean canLock, boolean canApprove, String role) throws SQLException {
        String sql = "UPDATE ManagerAccount SET canLock = ?, canApprove = ?, role = ? WHERE managerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, canLock);
            stmt.setBoolean(2, canApprove);
            stmt.setString(3, role);
            stmt.setInt(4, managerID);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
        }
    }

    public static void main(String[] args) {
        // Giả sử bạn đã có dbContext (Database Connection)
        ManagerAccountDAO accountDAO = new ManagerAccountDAO();

        // Test với dữ liệu mẫu
        int managerID = 2;  // Giả sử admin có ID = 1
        boolean canLock = true;
        boolean canApprove = false;
        String role = "Admin"; // Hoặc "Moderator", "Staff" nếu có các vai trò khác

        try {
            boolean result = accountDAO.updatePermissions(managerID, canLock, canApprove, role);
            if (result) {
                System.out.println("✅ Cập nhật quyền thành công!");
            } else {
                System.out.println("❌ Cập nhật quyền thất bại!");
            }
        } catch (SQLException e) {
            System.out.println("⚠ Lỗi SQL: " + e.getMessage());
        }

    }

    public boolean changeAdminPassword(int managerID, String oldPassword, String newPassword) {
        if (!isValidPassword(newPassword)) {
            System.out.println("Invalid password! Please enter a strong password, no spaces.");
            return false;
        }
        String checkPasswordSQL = "SELECT password FROM ManagerAccount WHERE managerID = ? AND role = 'Admin'";
        String updatePasswordSQL = "UPDATE ManagerAccount SET password = ? WHERE managerID = ? AND role = 'Admin'";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement checkStmt = conn.prepareStatement(checkPasswordSQL)) {

            checkStmt.setInt(1, managerID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (!storedHashedPassword.equals(hashSHA256(oldPassword))) {
                    System.out.println("Old password is incorrect.");
                    return false;
                }
            } else {
                System.out.println("Admin not found.");
                return false;
            }
            // Nếu mật khẩu cũ đúng, thực hiện cập nhật mật khẩu mới
            try ( PreparedStatement updateStmt = conn.prepareStatement(updatePasswordSQL)) {
                updateStmt.setString(1, hashSHA256(newPassword));
                updateStmt.setInt(2, managerID);

                int rowsUpdated = updateStmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
// Kiểm tra mật khẩu có hợp lệ không

    private boolean isValidPassword(String password) {
        return password != null
                && // Không được null
                !password.contains(" ")
                && // Không chứa khoảng trắng
                password.length() >= 8
                && // Ít nhất 8 ký tự
                password.matches(".*[A-Z].*")
                && // Có ít nhất một chữ in hoa
                password.matches(".*\\d.*");      // Có ít nhất một số
    }

    //EditlStaff
    public void updateAccount(int managerID, String username, String fullName, String email, String numberPhone, String gender) throws SQLException {
        String query = "UPDATE ManagerAccount SET username = ?, fullName = ?, email = ?, numberPhone = ?, gender = ? WHERE managerID = ?";

        try ( Connection conn = dbContext.getConnection();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, fullName);
            pstmt.setString(3, email);
            pstmt.setString(4, numberPhone);
            pstmt.setString(5, gender);
            pstmt.setInt(6, managerID);

            pstmt.executeUpdate();
        }
    }

    public boolean isUserExist(String username, String email, int excludeManagerID) throws SQLException {
        String query = "SELECT COUNT(*) FROM ManagerAccount WHERE (userName = ? OR email = ?) AND managerID != ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setInt(3, excludeManagerID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu có kết quả, nghĩa là username hoặc email đã tồn tại
                }
            }
        }
        return false;
    }
}
