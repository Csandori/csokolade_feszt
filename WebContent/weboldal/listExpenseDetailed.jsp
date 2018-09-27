<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Kiadások</title>
<%@ include file="includeJsCss.jsp"%>
<script type="text/javascript"
	src="js/tablesorter/jquery.tablesorter.js"></script>
<link rel="stylesheet" type="text/css" href="css/listExpenseGroups.css">
</head>
<body>
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
		<%@ include file="topnav.jsp"%>
		<main class="mdl-layout__content">
		<div class="page-content">
			<jsp:include page="/expenseDetailed" />

		</div>
		</main>
	</div>


	<div class="modal" id="modifyExpense" tabindex="-1" role="dialog"
		aria-labelledby="modifyEmployeeModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title" id="exampleModalLongTitle">Kiadás
						szerkesztése</h2>
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
	<jsp:include page="/newExpenseModal" />
</body>
<script>
	function showModify(id) {
		var modal = $('#modifyExpense');
		$.post(
				"/returnModifyExpense",
				{
					id : id
				},
				function(data, status) {
					// change modal content
					modal.find('#modal-body').html(data).promise().done(
							function() {
								componentHandler.upgradeDom()
							});
					modal.modal('show');
					modal.find('#saveButton').attr('onclick',
							'saveExpense("' + id + '")')
				}, 'html').fail(function(xhr, status, error) {
		});
	}

	$('#modifyExpense').on('hidden.bs.modal', function() {
		var modal = $('#modifyExpense');
		modal.find('#modal-body').html("Betöltés...");
	})

	function saveExpense(id) {
		var str = $('form').serialize();
		if (id != 0) {
			str += "&id=" + id;
		}
		alert(str);
		$.post("/saveExpense", str, function(data, status) {
			alert(data);
			location.reload();
		})
	}

	function deleteExpense(id) {
		$.post("/deleteExpense", {
			id : id
		}, function(data, status) {
			location.reload();
		})

	}
</script>
</html>