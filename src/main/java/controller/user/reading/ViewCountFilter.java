package controller.user.reading;

import DAO.ViewingDAO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@WebFilter("/novel-detail")
public class ViewCountFilter implements Filter {
    
    private ViewingDAO viewingDAO;
    private static final int VIEW_TIMEOUT_MINUTES = 30;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo viewingDAO trong init()
        viewingDAO = new ViewingDAO();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String novelIdParam = httpRequest.getParameter("id");

        if (novelIdParam != null && !novelIdParam.isEmpty()) {
            try {
                int novelId = Integer.parseInt(novelIdParam);
                
                // Key để lưu thời gian view cuối trong session
                String lastViewKey = "last_view_" + novelId;
                LocalDateTime lastViewTime = (LocalDateTime) session.getAttribute(lastViewKey);
                LocalDateTime currentTime = LocalDateTime.now();
                
                // Chỉ đếm view nếu chưa có view trước đó hoặc đã qua timeout
                if (lastViewTime == null || 
                    Duration.between(lastViewTime, currentTime).toMinutes() >= VIEW_TIMEOUT_MINUTES) {
                    
                    viewingDAO.createViewing(novelId);
                    session.setAttribute(lastViewKey, currentTime);
                }
                
                // Lấy tổng view để hiển thị
                int views = viewingDAO.getViewsCount(novelId);
                request.setAttribute("views", views);
            } catch (NumberFormatException e) {
                System.err.println("Invalid novel ID: " + novelIdParam);
            }
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Clean up nếu cần
    }
}

    