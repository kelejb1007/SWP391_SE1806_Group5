<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <!-- Overlay Background -->
    <div class="overlay" id="overlay">
        <!-- Login Form -->
        <div class="login-form" id="loginForm">
            <button class="close-btn" id="closeBtn">×</button>
            <h2>Login</h2>
            <form action="processLogin" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn">Login</button>
            </form>
        </div>
    </div>