<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<head>
    <meta charset="utf-8">
    <title>YongDiary</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Favicon -->
    <link href="assets/img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;500&family=Roboto:wght@500;700;900&display=swap" rel="stylesheet"> 

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/assets/lib/animate/animate.min.css" rel="stylesheet">
    <link href="/assets/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="/assets/lib/lightbox/css/lightbox.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/assets/css/style.css" rel="stylesheet">
   	<style type="text/css">
	
	   	
 	   	html, body{height: 100%} 
	    #wrapper{
	        height:auto;
 	        min-height: 100%; 
	        padding-bottom: 200px;
		}
   	</style> 
    
</head>
<body>
<div id="wrapper">
    <!-- Spinner Start -->
    <div id="spinner" class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
        <div class="spinner-grow text-primary" style="width: 3rem; height: 3rem;" role="status">
            <span class="sr-only">Loading...</span>
        </div>
    </div>
    <!-- Spinner End -->


    <!-- Topbar Start -->
    <div class="container-fluid bg-light p-0">
        <div class="row gx-0 d-none d-lg-flex">
            <div class="col-lg-7 px-5 text-start">
                <div class="h-100 d-inline-flex align-items-center py-3 me-4">
                    <small class="fa fa-map-marker-alt text-primary me-2"></small>
                    <small>123 Street, New York, USA</small>
                </div>
                <div class="h-100 d-inline-flex align-items-center py-3">
                    <small class="far fa-clock text-primary me-2"></small>
                    <small>Mon - Fri : 09.00 AM - 09.00 PM</small>
                </div>
            </div>
            <div class="col-lg-5 px-5 text-end">
                <div class="h-100 d-inline-flex align-items-center py-3 me-4">
                    <small class="fa fa-phone-alt text-primary me-2"></small>
                    <small>+012 345 6789</small>	
                </div>
                <div class="h-100 d-inline-flex align-items-center">
                <c:set var="authentication" value="${pageContext.request.userPrincipal}" />
           	 	  <c:choose>
	           	 	  <c:when test="${empty member}">
                    	  <a class="btn btn-sm-square bg-white text-primary me-1" href="/user/loginPage"><i class="bi bi-door-open"></i></a>
                   	  </c:when>
                   	  <c:otherwise>
                   	      <h6 class="mb-0" style="margin-right: 15px;">${member.memId}</h6>
                    	  <a class="btn btn-sm-square bg-white text-primary me-1" href="/logout"><i class="bi bi-door-closed"></i></a>
                      </c:otherwise>
                   </c:choose>
<!--                     <a class="btn btn-sm-square bg-white text-primary me-1" href=""><i class="fab fa-linkedin-in"></i></a> -->
<!--                     <a class="btn btn-sm-square bg-white text-primary me-0" href=""><i class="fab fa-instagram"></i></a> -->
                </div>
            </div>
        </div>
    </div>
    <!-- Topbar End -->


    <!-- Navbar Start -->
    <nav class="navbar navbar-expand-lg bg-white navbar-light sticky-top p-0">
        <a href="/" class="navbar-brand d-flex align-items-center px-4 px-lg-5">
            <h2 class="m-0" style="color: #04ef09;">오</h2>
            <h5 class="m-0">늘의</h5>
            <h2 class="m-0" style="color: #04ef09;">이</h2>
         	<h5 class="m-0">웃</h5>
        </a>
        <button type="button" class="navbar-toggler me-4" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <div class="navbar-nav ms-auto p-4 p-lg-0">
                <a href="/" class="nav-item nav-link">Home</a>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Map</a>
                    <div class="dropdown-menu fade-up m-0">
                        <a href="/map/mapSearch" class="dropdown-item">Map</a>
                        <a href="/map/myMapList" class="dropdown-item">myMap</a>
<!--                         <a href="team.html" class="dropdown-item">Our Team</a> -->
<!--                         <a href="testimonial.html" class="dropdown-item">Testimonial</a> -->
                        <a href="404.html" class="dropdown-item">404 Page</a>
                    </div>
                </div>
                <div class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Chatting</a>
                    <div class="dropdown-menu fade-up m-0">
                        <a href="/chating" class="dropdown-item">Chat</a>
                        <a href="/map/myMapList" class="dropdown-item">myMap</a>
<!--                         <a href="team.html" class="dropdown-item">Our Team</a> -->
<!--                         <a href="testimonial.html" class="dropdown-item">Testimonial</a> -->
                        <a href="404.html" class="dropdown-item">404 Page</a>
                    </div>
                </div>
                <a href="contact.html" class="nav-item nav-link">Contact</a>
            </div>
            <a href="" class="btn btn-primary py-4 px-lg-5 d-none d-lg-block">Show Calendar<i class="fa fa-arrow-right ms-3"></i></a>
        </div>
    </nav>
    <!-- Navbar End -->
<div class="row g-0 justify-content-center" id="">