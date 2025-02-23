package controller.user;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
public class UserAuthFilter extends HttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String originalURI = req.getRequestURI();

        // Nếu là trang đọc chương và người dùng chưa đăng nhập
        if (isRestrictedChapter(originalURI) && (session == null || session.getAttribute("user") == null)) {
            session = req.getSession(true);
            session.setAttribute("redirectURL", originalURI);
            res.sendRedirect(req.getContextPath() + "/UserLogin");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isRestrictedChapter(String url) {
        Pattern pattern = Pattern.compile(".*/readChapter\\?id=(\\d+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            int chapterId = Integer.parseInt(matcher.group(1));
            return chapterId > 3; // Chương 4 trở lên cần đăng nhập
        }
        return false;
    }

    private boolean isAllowedChapter(String url) {
        // Kiểm tra nếu URL là đọc chương (giả sử URL có dạng /readChapter?id=4)
        Pattern pattern = Pattern.compile(".*/readChapter\\?id=(\\d+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            int chapterId = Integer.parseInt(matcher.group(1)); // Lấy số chương
            return chapterId <= 3; // Chỉ cho phép đọc từ chương 1 đến 3
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
