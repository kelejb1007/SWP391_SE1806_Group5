package controller.staff;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ManagerAccount;

/**
 *
 * @author Nguyen Ngoc Phat - CE180321
 */
@WebFilter("/staff/*") // Filter áp dụng cho các URL bắt đầu với "/staff/"
public class ManagerAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        ManagerAccount manager = (ManagerAccount) session.getAttribute("manager"); // Đổi từ "user" thành "manager"
        if (manager == null) {
            res.sendRedirect(req.getContextPath() + "/ManagerLogin"); // Redirect nếu chưa đăng nhập
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
