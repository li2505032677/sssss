<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="toAdd">添加</a>
<a href="list?orders=desc&cpage=${cpage }">售出比例降序排序</a>

<table>
	<tr>
		<td>编号</td>
		<td>商品名称</td>
		<td>价格</td>
		<td>总量</td>
		<td>售出</td>
		<td>售出比例</td>
	</tr>
	<c:forEach items="${goodsList }" var="goods" varStatus="var">
	<tr>
		<td>${var.count}</td>
		<td>${goods.gname }</td>
		<td>${goods.price }</td>
		<td>${goods.gcount }</td>
		<td>${goods.saleCount }</td>
		<td>
			<fmt:formatNumber pattern="##.00" value="${goods.saleCount/goods.gcount*100 }"></fmt:formatNumber>%</td>
	</tr>
	</c:forEach>

	<tr>
		<td colspan="5">
			<a href="list?cpage=1">首页</a>
			<a href="list?cpage=${cpage-1 }">上一页</a>
			<a href="list?cpage=${cpage+1 }">下一页</a>
			<a href="list?cpage=${pages }">尾页</a>
		</td>
	</tr>
</table>
</body>
</html>