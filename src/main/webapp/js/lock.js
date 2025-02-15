
    document.addEventListener('DOMContentLoaded', function () {
        // Chặn menu chuột phải
        document.addEventListener('contextmenu', event => event.preventDefault());

        // Chặn tổ hợp phím sao chép
        document.addEventListener('keydown', event => {
            if (event.ctrlKey && (event.key === 'c' || event.key === 'x' || event.key === 'u' || event.key === 'a')) {
                event.preventDefault();
            }
            if (event.metaKey && (event.key === 'c' || event.key === 'x' || event.key === 'a')) { // Chặn trên macOS
                event.preventDefault();
            }
        });

        // Chặn bôi đen nội dung chương
        document.addEventListener('selectstart', event => event.preventDefault());

        // Chặn kéo thả
        document.addEventListener('dragstart', event => event.preventDefault());
    });

