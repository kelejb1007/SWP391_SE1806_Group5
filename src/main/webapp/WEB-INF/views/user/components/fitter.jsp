<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="filter-options">
    <c:set var="currentGenres" value="${selectedGenres != null ? String.join(',', selectedGenres) : ''}"/>
    
    <a href="<c:url value='/novels'>
                <c:param name='filter' value='all'/>
                <c:if test='${not empty currentGenres}'>
                    <c:param name='genre' value='${currentGenres}'/>
                </c:if>
            </c:url>" 
       class="filter-option ${selectedFilter == 'all' ? 'active' : ''}">All</a>
       
    <a href="<c:url value='/novels'>
                <c:param name='filter' value='popular'/>
                <c:if test='${not empty currentGenres}'>
                    <c:param name='genre' value='${currentGenres}'/>
                </c:if>
            </c:url>" 
       class="filter-option ${selectedFilter == 'popular' ? 'active' : ''}">Popular</a>

    <a href="<c:url value='/novels'>
                <c:param name='filter' value='rating'/>
                <c:if test='${not empty currentGenres}'>
                    <c:param name='genre' value='${currentGenres}'/>
                </c:if>
            </c:url>" 
       class="filter-option ${selectedFilter == 'rating' ? 'active' : ''}">Rating</a>

    <a href="<c:url value='/novels'>
                <c:param name='filter' value='time'/>
                <c:if test='${not empty currentGenres}'>
                    <c:param name='genre' value='${currentGenres}'/>
                </c:if>
            </c:url>" 
       class="filter-option ${selectedFilter == 'time' ? 'active' : ''}">Time Update</a>
</div>