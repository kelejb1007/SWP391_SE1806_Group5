<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="novel-buttons">
<c:choose>
    <c:when test="${not empty user}">
        <c:set var="isFavorite" value="${not empty favorite}" scope="request"/>
        <button 
            class="favorite-btn ${isFavorite ? 'active' : ''}"
            data-novel-id="${novel.novelID}"
            onclick="toggleFavorite(${novel.novelID})"
            >
            <i class="fas fa-heart"></i>
            <span class="fav-text">${isFavorite ? 'Remove from Favorite' : 'Add to Favorite'}</span>
        </button>
    </c:when>
    <c:otherwise>
      <button 
    class="favorite-btn ${isFavorite ? 'active' : ''}"
    data-novel-id="${novel.novelID}"
    onclick="toggleFavorite(${novel.novelID})">
    <i class="fas fa-heart"></i><span class="fav-text">Add to Favorite</span>
</button>

    </c:otherwise>
</c:choose> </div>