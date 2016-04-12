<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="formName" required="true" rtexprvalue="true" description="no need to add a '#'"%>
<%@ attribute name="validateUrl" required="true" rtexprvalue="true" %>
<spring:url value="${validateUrl}" var="processedValidateUrl" />
<script type="text/javascript">
			function collectFormData(fields) {
				var data = {};
				for (var i = 0; i < fields.length; i++) {
					var $item = $(fields[i]);
					data[$item.attr('name')] = $item.val(); 
				}
				return data;
			}
				
			$(document).ready(function() {
				var $form = $('#${formName}');
				$form.bind('submit', function(e) {
					// Ajax validation
					var $inputs = $form.find('input');
					var data = collectFormData($inputs);
					
					$.post('${processedValidateUrl}', data, function(response) {
						$form.find('.control-group').removeClass('error');
						$form.find('.help-inline').empty();
						$form.find('.alert').remove();
						
						if (response.status == 'FAIL') {
							for (var i = 0; i < response.errorMessageList.length; i++) {
								var item = response.errorMessageList[i];
								var $controlGroup = $('#' + item.fieldName);
								$controlGroup.addClass('error');
								$controlGroup.find('.help-inline').html(item.message);
							}
						} else {
							$form.unbind('submit');
							$form.submit();
						}
					}, 'json');
					
					e.preventDefault();
					return false;
				});
			});
		</script>