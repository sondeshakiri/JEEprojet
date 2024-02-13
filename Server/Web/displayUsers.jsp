<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User List</title>
</head>
<body>
    <h2>User List</h2>

    <ul>
        <%
            List<User> userList = (List<User>) session.getAttribute("userList");
            for (User user : userList) {
        %>
        <li><%= "ID: " + user.getId() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword() %></li>
        <%
            }
        %>
    </ul>
</body>
</html>
