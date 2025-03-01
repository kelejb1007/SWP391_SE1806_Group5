package controller.user;

import DAO.NovelDAO;
import model.Novel;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "NovelListController", urlPatterns = {"/novels"})
public class NovelController extends HttpServlet {

    private NovelDAO novelDAO;

    @Override
    public void init() throws ServletException {
        novelDAO = new NovelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        switch (action) {
            case "list":
                viewNovelList(request, response);
                break;
            case "create":
                //createNovel(request, response);  // Chưa implement
                break;
            case "edit":
                //editNovel(request, response);    // Chưa implement
                break;
            case "delete":
                //deleteNovel(request, response);  // Chưa implement
                break;
            default:
                viewNovelList(request, response); // Handle unknown actions
                break;
        }
    }


    private void viewNovelList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchQuery = request.getParameter("search");
        String selectedGenre = request.getParameter("genre");
        String selectedFilter = request.getParameter("filter");

        // **VALIDATION: Kiểm tra searchQuery**
        if (searchQuery != null) {
            searchQuery = searchQuery.trim(); // Loại bỏ khoảng trắng thừa

            if (searchQuery.isEmpty()) {
                // Nếu searchQuery rỗng sau khi trim, xem như không có tìm kiếm
                searchQuery = null;
            } else if (searchQuery.length() > 100) {
                // Xử lý lỗi: độ dài vượt quá giới hạn
                request.setAttribute("errorMessage", "Search keyword is too long.");
                request.getRequestDispatcher("/WEB-INF/views/user/reading/novelList.jsp").forward(request, response);
                return; // Dừng xử lý tiếp
            } else if (searchQuery.length() < 3 && searchQuery.length() > 0) {
                request.setAttribute("errorMessage", "Search keyword is too short.");
                request.getRequestDispatcher("/WEB-INF/views/user/reading/novelList.jsp").forward(request, response);
                return;
            } else {
                // **SANIZATION (quan trọng):**  Loại bỏ các ký tự đặc biệt có thể gây hại
                searchQuery = sanitizeSearchQuery(searchQuery);
            }
        }

        List<Novel> novels;

        // Ưu tiên tìm kiếm trước
        if (searchQuery != null && !searchQuery.isEmpty()) {
            novels = novelDAO.searchNovels(searchQuery);
        } else {
            // 1. Lọc theo filter trước
            novels = getNovelsByFilter(selectedFilter, selectedGenre); // Pass selectedGenre here

            // 2. Nếu có genre, tiếp tục lọc theo genre
            if (selectedGenre != null && !selectedGenre.isEmpty()) {
                novels = novelDAO.filterNovelsByGenre(novels, selectedGenre);
            }
        }

        // Gán danh sách vào request
        request.setAttribute("novels", novels);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("selectedFilter", selectedFilter);
        request.setAttribute("selectedGenre", selectedGenre); // Lưu để hiển thị trên giao diện

        // Forward đến trang hiển thị danh sách novels
        String target = "/WEB-INF/views/user/reading/novelList.jsp";
        request.getRequestDispatcher("/getGenre?target=" + target
                + (selectedGenre != null ? "&genre=" + selectedGenre : "")
                + (selectedFilter != null ? "&filter=" + selectedFilter : ""))
                .forward(request, response);

    }


    private List<Novel> getNovelsByFilter(String selectedFilter, String selectedGenre) {
        return novelDAO.getNovelsSortedByFilter(selectedFilter, selectedGenre);
    }

    // **SANIZATION METHOD:**
    private String sanitizeSearchQuery(String query) {
        // CHỈ GIỮ LẠI CHỮ CÁI, SỐ, KHOẢNG TRẮNG, DẤU CHẤM, DẤU PHẨY, GẠCH NGANG
        return query.replaceAll("[^a-zA-Z0-9\\s.,-]", "");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chưa có logic post
    }
}