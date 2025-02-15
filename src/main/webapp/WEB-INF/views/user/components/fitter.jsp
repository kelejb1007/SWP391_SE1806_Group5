<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="filter-options">
    <a href="novels?filter=all" class="filter-option ${empty selectedFilter || selectedFilter == 'all' ? 'active' : ''}">All</a>
    <a href="novels?filter=popular" class="filter-option ${selectedFilter == 'popular' ? 'active' : ''}">Popular</a>
    <a href="novels?filter=rating" class="filter-option ${selectedFilter == 'rating' ? 'active' : ''}">Rating</a>
    <a href="novels?filter=time" class="filter-option ${selectedFilter == 'time' ? 'active' : ''}">Time Update</a>
</div>

