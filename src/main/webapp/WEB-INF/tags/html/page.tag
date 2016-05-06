<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ attribute name="url" required="true" rtexprvalue="true"%>
<%@ attribute name="formCommand" required="false" rtexprvalue="true"%>
<c:set var="commandName" value="${formCommand}"/>
<s:eval var="pageUrl" expression="T(cn.anthony.util.HttpUtil).toGetString(url,param)"  />
       

<c:url var="firstUrl" value="${pageUrl}&page=0" />
<c:url var="lastUrl" value="${pageUrl}&page=${totalPages-1}" />
<c:url var="prevUrl" value="${pageUrl}&page=${currentIndex - 1}" />
<c:url var="nextUrl" value="${pageUrl}&page=${currentIndex + 1}" />


							<div class="row">
								<div class="col-sm-4">
								<form:form action="${pageUrl}" commandName="pageRequest" method="POST">每页:
								<form:input  path="size" class="input-short"/>
								<input type="submit" value="条"/><form:errors path="size" cssclass="error"/>
								</form:form>
								</div>
								<div class="col-sm-4" >
									共${total}条，总${totalPages}页。
								</div>
								<div class="col-sm-4">
								<form:form action="${pageUrl}" commandName="pageRequest" method="POST">到第:
								<form:input type="text"  path="page" class="input-short" value="${currentIndex }"></form:input>
								<input type="submit" value="页"/>
								<form:errors path="page" cssclass="error"></form:errors></form:form>
								</div>
								
							</div>
							<div class="row text-center  pagination pagination-centered">
							<c:choose>
					            <c:when test="${hasPrevious}">
					                <li><a href="${firstUrl}">&lt;&lt;</a></li>
					                <li><a href="${prevUrl}">&lt;</a></li>
					            </c:when>
					        </c:choose>
					        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
					            <c:choose>
					                <c:when test="${i == currentIndex}">
					                    <li class="active"><a href="${pageUrl}&page=${i-1}"><c:out value="${i}" /></a></li>
					                </c:when>
					                <c:otherwise>
					                    <li><a href="${pageUrl}&page=${i-1}"><c:out value="${i}" /></a></li>
					                </c:otherwise>
					            </c:choose>
					        </c:forEach>
					        <c:choose>
					            <c:when test="${hasNext}">
					                <li><a href="${nextUrl}">&gt;</a></li>
					                <li><a href="${lastUrl}">&gt;&gt;</a></li>
					            </c:when>
					        </c:choose>			
							</div>
