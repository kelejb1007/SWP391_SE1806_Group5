<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="filter-options">
    <a href="novels?filter=all&genre=${selectedGenre}" class="filter-option ${empty selectedFilter || selectedFilter == 'all' ? 'active' : ''}">All</a>
    <a href="novels?filter=popular&genre=${selectedGenre}" class="filter-option ${selectedFilter == 'popular' ? 'active' : ''}">Popular</a>
    <a href="novels?filter=rating&genre=${selectedGenre}" class="filter-option ${selectedFilter == 'rating' ? 'active' : ''}">Rating</a>
    <a href="novels?filter=time&genre=${selectedGenre}" class="filter-option ${selectedFilter == 'time' ? 'active' : ''}">Time Update</a>
</div>

