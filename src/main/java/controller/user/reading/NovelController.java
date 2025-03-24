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
import java.util.ArrayList;
import java.util.Arrays;

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

        if ("search".equals(action)) {
            searchNovelList(request, response);
            return;
        }
        if ((genre == null || genre.trim().isEmpty()) && (filter == null || filter.trim().isEmpty())) {
            viewAllNovel(request, response);
        } else {
            viewNovelList(request, response);
        }
    }

    private void searchNovelList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchQuery = request.getParameter("kw");
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
            } else if (searchQuery.length() < 1) {
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
    String[] selectedGenreArray = request.getParameterValues("genre");
    String selectedFilter = request.getParameter("filter");
    
    if (selectedFilter == null || selectedFilter.trim().isEmpty()) {
        selectedFilter = "all";
    }

    List<String> selectedGenres = new ArrayList<>();
    if (selectedGenreArray != null && selectedGenreArray.length > 0) {
        for (String genre : selectedGenreArray) {
            if (genre != null && !genre.trim().isEmpty()) {
                // Tách chuỗi nếu chứa dấu phẩy
                String[] splitGenres = genre.split(",");
                for (String splitGenre : splitGenres) {
                    String trimmedGenre = splitGenre.trim();
                    if (!trimmedGenre.isEmpty()) {
                        selectedGenres.add(trimmedGenre);
                    }
                }
            }
        }
    }

    
    List<Novel> novels = novelDAO.getNovelsSortedByFilter(selectedFilter, selectedGenres);
    

    request.setAttribute("novels", novels);
    request.setAttribute("selectedFilter", selectedFilter);
    request.setAttribute("selectedGenres", selectedGenres);

    String target = "/WEB-INF/views/user/reading/novelList.jsp";
    String genreParam = (selectedGenres != null && !selectedGenres.isEmpty()) 
            ? String.join(",", selectedGenres) : "";
    System.out.println("Forwarding to: /getGenre?target=" + target + "&genre=" + genreParam + "&filter=" + selectedFilter);
    request.getRequestDispatcher("/getGenre?target=" + target 
            + "&genre=" + genreParam
            + "&filter=" + selectedFilter)
            .forward(request, response);
}
    
//    private List<Novel> getNovelsByFilter(String selectedFilter, String selectedGenre) {
//        // Lọc theo filter trước
//        return novelDAO.getNovelsSortedByFilter(selectedFilter, selectedGenre);
//    }
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
