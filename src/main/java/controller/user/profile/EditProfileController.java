/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.profile;

import DAO.UserAccountDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.UserAccount;
/**
 * @author KHOA
 */
@WebServlet(name = "EditProfileController", urlPatterns = {"/editProfile"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class EditProfileController extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d+$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String newUsername = request.getParameter("userName");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String numberPhone = request.getParameter("numberPhone");
        String gender = request.getParameter("gender");

        // Email phai dung format
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            request.setAttribute("error", "Email must be in the format @gmail.com.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
            return;
        }

        // Phone number khong co ki tu chu
        if (!PHONE_PATTERN.matcher(numberPhone).matches()) {
            request.setAttribute("error", "Phone number must contain only digits.");
            request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
            return;
        }

        Part filePart = request.getPart("avatar");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String imageUrl = user.getImageUML();
        if (!fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            imageUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
        }

        UserAccountDAO dao = new UserAccountDAO();
        dao.updateUserProfile(user.getUserName(), newUsername, fullName, email, numberPhone, gender, imageUrl);

        // Update session
        user.setUserName(newUsername);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setNumberPhone(numberPhone);
        user.setGender(gender);
        user.setImageUML(imageUrl);

        session.setAttribute("user", user);
        request.setAttribute("message", "Profile updated successfully!");
        request.getRequestDispatcher("/WEB-INF/views/user/components/EditProfile.jsp").forward(request, response);
    }
}

