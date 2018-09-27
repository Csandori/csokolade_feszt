<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Kiadás csoportok</title>
<%@ include file="includeJsCss.jsp"%>
<script type="text/javascript"
	src="js/tablesorter/jquery.tablesorter.js"></script>
<link rel="stylesheet" type="text/css" href="css/listExpenseGroups.css">
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/FreddyFY/material-datepicker/master/dist/material-datepicker.css">
  <script type="text/javascript" src="http://momentjs.com/downloads/moment-with-locales.min.js"></script>
  <script type="text/javascript" src="https://cdn.rawgit.com/FreddyFY/material-datepicker/master/dist/material-datepicker.min.js"></script>
</head>
<body>

	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
		<%@ include file="topnav.jsp"%>
		<main class="mdl-layout__content">
		<div class="page-content">
			<jsp:include page="/listExpense" />
		</div>
		</main>
	</div>


	<div class="modal" id="modifyExpenseGroup" tabindex="-1" role="dialog"
		aria-labelledby="modifyEmployeeModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title" id="exampleModalLongTitle">Kiadás
						csoport szerkesztése</h2>
				</div>
				<div class="modal-body" id="modal-body">Betöltés...</div>
				<div class="modal-footer">
					<button type="button"
						class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect"
						data-dismiss="modal">Mégse</button>
					<button id="saveButton" type="button"
						class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
						onclick="saveModEmployee()">Mentés</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal" id="newExpenseGroup" tabindex="-1" role="dialog"
		aria-labelledby="modifyEmployeeModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title" id="exampleModalLongTitle">Kiadás
						csoport hozzáadása</h2>
				</div>
				<div class="modal-body" id="modal-body">Betöltés...</div>
				<div class="modal-footer">
					<button type="button"
						class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect"
						data-dismiss="modal">Mégse</button>
					<button id="saveButton" type="button"
						class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent"
						onclick="saveModEmployee()">Mentés</button>
				</div>
			</div>
		</div>
	</div>

</body>
<script>
	function showModify(focsoport, expenseGId) {
		var modal = $('#modifyExpenseGroup');
		Id = expenseGId;
		$.post(
				"/returnModifyExpenseGroup",
				{
					focsoport : focsoport,
					modifyId : expenseGId
				},
				function(data, status) {
					// change modal content
					modal.find('#modal-body').html(data).promise().done(
							function() {
								componentHandler.upgradeDom()
							});
					modal.modal('show');
					//Megváltoztatja a gomb eredményét
					modal.find('#saveButton').attr('onclick',
							'saveExpenseGroup("' + expenseGId + '")')
				}, 'html').fail(function(xhr, status, error) {
		});
	}

	$('#modifyExpenseGroup').on('hidden.bs.modal', function() {
		var modal = $('#modifyExpenseGroup');
		modal.find('#modal-body').html("Betöltés...");
	})

	function saveExpenseGroup(groupId) {
		var str = $('form').serialize();
		str += "&groupid=" + groupId;
		$.post("/saveExpenseGroup", str, function(data, status) {
			location.reload();
		})
	}
</script>
</html>