<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="novel-buttons">
    <button
        id="favoriteButton"
        class="favorite-btn ${not empty favorite ? 'active' : ''}"
        data-novel-id="${novel.novelID}"
    >
        <i class="fas fa-heart"></i>
        <span class="fav-text">
            <c:choose>
                <c:when test="${not empty favorite}">
                    Remove from Favorite
                </c:when>
                <c:otherwise>
                    Add to Favorite
                </c:otherwise>
            </c:choose>
        </span>
    </button>
</div>
