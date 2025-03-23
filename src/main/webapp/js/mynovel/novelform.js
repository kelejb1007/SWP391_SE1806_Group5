/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const fileInput = document.getElementById('file-input');
const imagePreview = document.getElementById('image-preview');

// Thêm sự kiện change vào input file
fileInput.addEventListener('change', function (event) {
    const file = event.target.files[0]; // Lấy file được chọn

    if (file) {
        const reader = new FileReader(); // Tạo FileReader để đọc file

        // Khi file được đọc xong
        reader.onload = function (e) {
            imagePreview.src = e.target.result; // Gán URL hình ảnh vào src của img

            imagePreview.style.display = 'block'; // Hiển thị ảnh
        };
        reader.readAsDataURL(file); // Đọc file dưới dạng URL
    }
});
