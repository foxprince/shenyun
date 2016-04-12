<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="url" required="true" rtexprvalue="true"%>
<%@ attribute name="formCommand" required="false" rtexprvalue="true"%>
<c:set var="commandName" value="${formCommand}"/>
<c:url value="${url}" var="pageUrl"/>
<c:url var="firstUrl" value="${pageUrl}&page=1" />
<c:url var="lastUrl" value="${pageUrl}&page=${totalPages}" />
<c:url var="prevUrl" value="${pageUrl}&page=${currentIndex - 1}" />
<c:url var="nextUrl" value="${pageUrl}&page=${currentIndex + 1}" />

							<div class="row-fluid center">
								<div class="span4">
								<form:form action="${pageUrl}" commandName="pageRequest" method="GET">每页:
								<form:input  path="size" class="input-short"/>
								<input type="submit" value="条"/><form:errors path="size" cssclass="error"/>
								</form:form>
								</div>
								<div class="span4" >
									共${total}条，总${totalPages}页。
								</div>
								<div class="span4">
								<form:form action="${pageUrl}" commandName="pageRequest" method="GET">到第:
								<form:input type="text"  path="page" class="input-short"></form:input>
								<input type="submit" value="页"/>
								<form:errors path="page" cssclass="error"></form:errors></form:form>
								</div>
								
							</div>
							<div class="span12 pagination pagination-centered">
							<c:choose>
					            <c:when test="${hasPrevious}">
					                <li><a href="${firstUrl}">&lt;&lt;</a></li>
					                <li><a href="${prevUrl}">&lt;</a></li>
					            </c:when>
					        </c:choose>
					        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
					            <c:choose>
					                <c:when test="${i == currentIndex}">
					                    <li class="active"><a href="${pageUrl}&page=${i}"><c:out value="${i}" /></a></li>
					                </c:when>
					                <c:otherwise>
					                    <li><a href="${pageUrl}&page=${i}"><c:out value="${i}" /></a></li>
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
