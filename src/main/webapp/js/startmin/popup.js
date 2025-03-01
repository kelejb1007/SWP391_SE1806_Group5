/* global bootstrap, fetch */



function submitLock() {
    var novelID = document.getElementById('novelID').value;
    var lockReason = document.getElementById('lockReason').value;

    if (!lockReason.trim()) {
        alert("Please enter lock reason");
        return;
    }

    fetch('managenovel', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'novelID=' + encodeURIComponent(novelID) + '&reasonLock=' + encodeURIComponent(lockReason) + '%action=lock'
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Lock successfully");
                    location.reload();
                } else {
                    alert("Error: " + data.message);
                }
            })
            .catch(error => console.error('Error:', error));
}/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


