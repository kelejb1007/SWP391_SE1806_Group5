/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
 
package controller.staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ManagerLogoutController", urlPatterns = {"/ManagerLogout"})
public class ManagerLogoutController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hủy session của manager
        HttpSession session = request.getSession();
        session.invalidate();

        // Chuyển hướng về trang login của manager
        response.sendRedirect(request.getContextPath() + "/ManagerLogin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hủy session của manager
        HttpSession session = request.getSession();
        session.invalidate();

        // Chuyển hướng về trang login của manager
        response.sendRedirect(request.getContextPath() + "/ManagerLogin");
    }
}



