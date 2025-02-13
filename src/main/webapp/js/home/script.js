// Ví dụ script
document.addEventListener("DOMContentLoaded", () => {
    console.log("Page loaded successfully!");
    // Thêm các hành động khác nếu cần
});


// Lấy các phần tử
const overlay = document.getElementById("overlay");
const closeBtn = document.getElementById("closeBtn");

// Hiển thị form login (gọi hàm này từ trang homepage)
function showLoginForm() {
    overlay.style.display = "flex"; // Hiển thị overlay
}

// Đóng form login
closeBtn.addEventListener("click", () => {
    overlay.style.display = "none";
});

// Đảm bảo overlay cũng đóng khi người dùng click ngoài form
overlay.addEventListener("click", (e) => {
    if (e.target === overlay) {
        overlay.style.display = "none";
    }
});




