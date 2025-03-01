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
        HttpSession session = req.getSession(false); // Không tạo session mới nếu chưa có

        String originalURI = req.getRequestURI();

        // Nếu là trang đọc chương hoặc đăng chapter và người dùng chưa đăng nhập
        if ((isRestrictedChapter(originalURI) || isPostChapterPage(originalURI))
                && (session == null || session.getAttribute("user") == null)) {
            res.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        chain.doFilter(request, response);
    }

    private Integer getChapterId(String url) {
        Pattern pattern = Pattern.compile(".*/readChapter\\?id=(\\d+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private boolean isRestrictedChapter(String url) {
        Integer chapterId = getChapterId(url);
        return chapterId != null && chapterId > 3; // Chương 4 trở lên cần đăng nhập
    }

    private boolean isPostChapterPage(String url) {
        Pattern pattern = Pattern.compile(".*/postChapter(\\?.*)?");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
