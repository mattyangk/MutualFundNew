
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<c:forEach items="${errors}" var="error">
	<p style="font-size:medium; color:red">${error}</p>
</c:forEach>

<c:forEach items="${successes}" var="success">
	<p style="font-size:medium; color:green;">${success}</p>
</c:forEach>