 function toggleDropdown(listId, titleElement) {
        var list = document.getElementById(listId);
        var icon = titleElement.querySelector('i');
        var isOpen = list.style.display === "block";

        if (isOpen) {
            list.style.display = "none";
            icon.classList.remove('fa-caret-up');
            icon.classList.add('fa-caret-down');
            localStorage.setItem('categoryListOpen', 'false');
        } else {
            list.style.display = "block";
            icon.classList.remove('fa-caret-down');
            icon.classList.add('fa-caret-up');
            localStorage.setItem('categoryListOpen', 'true');
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        var list = document.getElementById("categoryList");
        var titleElement = document.querySelector(".sidebar-title");
        var icon = titleElement.querySelector('i');
        var isOpen = localStorage.getItem('categoryListOpen') === 'true';

        list.style.display = isOpen ? "block" : "none";
        icon.classList.toggle('fa-caret-up', isOpen);
        icon.classList.toggle('fa-caret-down', !isOpen);
    });