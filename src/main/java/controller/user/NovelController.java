package controller.user;

import DAO.GenreDAO;
import DAO.NovelDAO;
import model.Genre;
import model.Novel;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "NovelListController", urlPatterns = {"/novels"})
/**
 *
 * @author Phan Hồng Tài - CE181490
 */
public class NovelController extends HttpServlet {

    private NovelDAO novelDAO;

    @Override
    public void init() throws ServletException {
        novelDAO = new NovelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("action");
        if (view == null || view.equals("list")) {
            String searchQuery = request.getParameter("search");
            String selectedGenre = request.getParameter("genre");
            String selectedFilter = request.getParameter("filter");

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
                    + (selectedFilter != null ? "&filter=" + selectedFilter : "")) // Also include selectedFilter
                    .forward(request, response);
        } else if (view.equals("create")) {
            // Implement create functionality here
        } else if (view.equals("edit")) {
            // Implement edit functionality here
        } else if (view.equals("delete")) {
            // Implement delete functionality here
        }
    }

    private List<Novel> getNovelsByFilter(String selectedFilter, String selectedGenre) {
        return novelDAO.getNovelsSortedByFilter(selectedFilter, selectedGenre);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
