package controller.user.reading;

import DAO.NovelDAO;
import DAO.RatingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Rating;
import model.UserAccount;

/**
 * @author Phan Hồng Tài - CE181490
 */
@WebServlet(name = "RatingController", urlPatterns = {"/rating"})
public class RatingController extends HttpServlet {

    private RatingDAO ratingDAO;
    private NovelDAO novelDAO;

    @Override
    public void init() throws ServletException {
        ratingDAO = new RatingDAO();
        novelDAO = new NovelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) session.getAttribute("user");

        System.out.println("RatingController: User from session = " + user);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("not_logged_in");
            System.out.println("RatingController: User not logged in.");
            return;
        }

        String novelIdParam = request.getParameter("novelId");
        String scoreParam = request.getParameter("score");

        System.out.println("RatingController: novelIdParam = " + novelIdParam + ", scoreParam = " + scoreParam);

        if (novelIdParam == null || novelIdParam.isEmpty() || scoreParam == null || scoreParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("missing_parameters");
            System.out.println("RatingController: Missing parameters.");
            return;
        }

        int novelId;
        int score;

        try {
            novelId = Integer.parseInt(novelIdParam);
            score = Integer.parseInt(scoreParam);
            System.out.println("RatingController: novelId = " + novelId + ", score = " + score);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("invalid_parameters");
            System.out.println("RatingController: Invalid parameters - NumberFormatException: " + e.getMessage());
            return;
        }

        if (score < 1 || score > 5) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("invalid_score");
            System.out.println("RatingController: Invalid score.");
            return;
        }

        boolean success = false; // Initialize success to false
        double averageRating = 0.0;
        int ratingCount = 0;
        try {
            success = updateOrAddRate(user, novelId, score);
            System.out.println("RatingController: updateOrAddRate success = " + success);

            if (success) {
                // Calculate the average rating after successfully adding/updating the rating
                averageRating = ratingDAO.calculateAverageRating(novelId);
               ratingCount = ratingDAO.getRatingCount(novelId);
                
                System.out.println("RatingController: averageRating = " + averageRating + ", ratingCount = " + ratingCount);
            }
        } catch (Exception e) {
            System.err.println("RatingController: Exception in updateOrAddRate: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace
        }

        if (success) {
            String responseString = String.format("%.2f,%d,%d", averageRating, ratingCount, score);
            out.print(responseString);
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("RatingController: Response = " + responseString);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("rating_failed");
            System.out.println("RatingController: Rating failed.");
        }
    }

    private boolean updateOrAddRate(UserAccount user, int novelId, int score) {
        Rating existingRate = ratingDAO.getRateByNovelIdAndUserId(novelId, user.getUserID());
        boolean success = false;  // Initialize success to false
        System.out.println("RatingController: updateOrAddRate - novelId = " + novelId + ", user = " + user.getUserID() + ", score = " + score);

        try {
            if (existingRate == null) {
                System.out.println("RatingController: No existing rate found, adding new rate");
                Rating newRate = new Rating();
                newRate.setUserID(user.getUserID());
                newRate.setNovelID(novelId);
                newRate.setScore(score);
                success = ratingDAO.addRate(newRate);
                System.out.println("RatingController: Added new rate, success = " + success);
            } else {
                System.out.println("RatingController: Existing rate found, updating rate");
                existingRate.setScore(score);
                success = ratingDAO.updateRate(existingRate);
                System.out.println("RatingController: Updated existing rate, success = " + success);
            }
        } catch (Exception e) {
            System.err.println("RatingController: Exception in updateOrAddRate: " + e.getMessage());
            e.printStackTrace();  // Print the stack trace
        }
        return success;
    }

}