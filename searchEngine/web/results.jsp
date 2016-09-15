<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Results</title>
</head>
<body>
<h1>Results . . .</h1>

<% if (request.getAttribute("qresult") == null) {%>
<h3>No data to present.</h3>
<% } else { %>
<ol>
  <% for (String itm : (String[]) request.getAttribute("qresult")) { %>
  <li><%=itm%>
  </li>
  <%}%>
</ol>
<%}%>

<h3><a href="index.jsp">Search Again</a></h3>
</body>
</html>
