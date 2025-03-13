package controller.user.reading;

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
        String genre = request.getParameter("genre");
        String filter = request.getParameter("filter");

        if ((genre == null || genre.trim().isEmpty()) && (filter == null || filter.trim().isEmpty())) {
            viewAllNovel(request, response);
            return;
        }
        if (action == null) {
            action = "default"; // Gán giá trị mặc định
        }
        switch (action) {
            case "search":
                searchNovelList(request, response);
                break;

            default:
                viewNovelList(request, response); // Không có actions
                break;
        }
    }

    private void searchNovelList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchQuery = request.getParameter("search");
        //Kiểm tra dữ liệu vào trên URL
        if (searchQuery != null) {
            searchQuery = searchQuery.trim();

            if (searchQuery.isEmpty()) {
                request.setAttribute("errorMessage", "Please enter a value.");
                viewAllNovel(request, response);
                return;
            } else if (searchQuery.length() > 100) {
                request.setAttribute("errorMessage", "Search keyword is too long.");
                viewAllNovel(request, response);
                return;
            } else if (!searchQuery.matches("[a-zA-Z\\s]+")) {
                // Kiểm tra nếu searchQuery chứa ký tự không phải chữ cái hoặc khoảng trắng
                request.setAttribute("errorMessage", "Search keyword must contain only letters.");
                viewAllNovel(request, response);
                return;
            } else if (searchQuery.length() < 3) {
                request.setAttribute("errorMessage", "Search keyword is too short.");
                viewAllNovel(request, response);
                return;
            }
            //Bỏ mã độc
            searchQuery = sanitizeSearchQuery(searchQuery);
            //Danh sách tìm kiếm
            List<Novel> novels = novelDAO.searchNovels(searchQuery);

            request.setAttribute("novels", novels);

            request.setAttribute("searchQuery", searchQuery);
            String target = "/WEB-INF/views/user/reading/novelList.jsp";
            request.getRequestDispatcher("/getGenre?target=" + target)
                    .forward(request, response);
        }
    }

    private void viewAllNovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Novel> novels;
        //Danh sách novel(active)
        novels = novelDAO.getAllNovels();

        // Gán danh sách vào request
        request.setAttribute("novels", novels);

        // Forward đến trang hiển thị danh sách novels
        String target = "/WEB-INF/views/user/reading/novelList.jsp";
        request.getRequestDispatcher("/getGenre?target=" + target)
                .forward(request, response);

    }

    private void viewNovelList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selectedGenre = request.getParameter("genre");
        String selectedFilter = request.getParameter("filter");

        List<Novel> novels;

        //Danh sách novel theo bộ lọc+thể loại
        novels = getNovelsByFilter(selectedFilter, selectedGenre);

        // Gán danh sách vào request
        request.setAttribute("novels", novels);

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
        // Lọc theo filter trước
        return novelDAO.getNovelsSortedByFilter(selectedFilter, selectedGenre);
    }

    //SANIZATION METHOD
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
