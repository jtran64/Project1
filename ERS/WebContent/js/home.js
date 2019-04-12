//

//Resets values in createRequestModal to blank
$("#createRequestModal").on('hidden.bs.modal', function() {
	document.getElementById("amount").value = "";
	document.getElementById("dropdownMenuButton").innerHTML = "Type";
	document.getElementById("description").value = "";
});

//Loads the reimbursement table with values when the page loads
window.onload = function() {
	getReimbursements();
	getUser();
}

//https://www.w3schools.com/howto/howto_js_filter_table.asp
function showAllReq() {
	table = document.getElementById("reimbTable");
	tr = table.getElementsByTagName("tr");

	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[5];
		if (td) {
			tr[i].style.display = "";
		}
	}
}

//https://www.w3schools.com/howto/howto_js_filter_table.asp
function filterStatus(status) {
	// Declare variables
	var input, filter, table, tr, td, i, txtValue;
	input = status;
	filter = input.toUpperCase();

	if (filter == "ALL")
		showAllReq();
	else {
		table = document.getElementById("reimbTable");
		tr = table.getElementsByTagName("tr");

		// Loop through all table rows, and hide those who don't match the
		// search query
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[4];
			if (td) {
				txtValue = td.innerHTML;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}
}

//https://www.w3schools.com/howto/howto_js_filter_table.asp
function filterType(type) {
	// Declare variables
	var input, filter, table, tr, td, i, txtValue;
	input = type;
	filter = input.toUpperCase();
	console.log(filter);
	if (filter == "ALL")
		showAllReq();
	else {
		table = document.getElementById("reimbTable");
		tr = table.getElementsByTagName("tr");

		// Loop through all table rows, and hide those who don't match the
		// search query
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[5];
			if (td) {
				txtValue = td.innerHTML;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}
}

//https://www.w3schools.com/howto/howto_js_filter_table.asp
function searchAuthor() {
	// Declare variables
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("authSearch");
	filter = input.value.toUpperCase();
	table = document.getElementById("reimbTable");
	tr = table.getElementsByTagName("tr");

	// Loop through all table rows, and hide those who don't match the search
	// query
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[2];
		if (td) {
			txtValue = td.innerHTML;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

//https://www.w3schools.com/howto/howto_js_filter_table.asp
function searchResolver() {
	// Declare variables
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("resolvSearch");
	filter = input.value.toUpperCase();
	table = document.getElementById("reimbTable");
	tr = table.getElementsByTagName("tr");

	// Loop through all table rows, and hide those who don't match the search
	// query
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[3];
		if (td) {
			txtValue = td.innerHTML;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function changeType(type) {
	document.getElementById("dropdownMenuButton").innerHTML = type;
	document.getElementById("type").value = type;
}

function changeTypeFilter(type) {
	document.getElementById("typeDropDown").innerHTML = type;
	filterType(type);
}

function changeStatusFilter(status) {
	document.getElementById("statusDropDown").innerHTML = status;
	filterStatus(status);
}

function changeWelcome(username) {
	document.getElementById("welcomeUser").innerHTML = "Welcome " + username;
}

//Shows or hides the approve or deny buttons based on user role
function showHideAdmin(role) {
	if (role == "manager") {
		document.getElementById("approveBtn").style = "display: inline;";
		document.getElementById("denyBtn").style = "display: inline;";
	} else {
		document.getElementById("approveBtn").style = "display: none;";
		document.getElementById("denyBtn").style = "display: none;";
	}
}

function approveRequest() {
	approveDenyRequest("2");
}

function denyRequest() {
	approveDenyRequest("3");
}

//Approves a request
function approveDenyRequest(status) {
	let id = document.getElementById("reimbId").value;
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {

		}
	}
	xhttp.open("POST", "http://localhost:8080/ERS/html/approveDenyReq.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("reimbId=" + id + "&status=" + status);
}

//Gets user information
function getUser() {
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			let user = JSON.parse(xhttp.responseText);
			changeWelcome(user.userName);
			showHideAdmin(user.roleId);
		}
	}

	xhttp.open("GET", "http://localhost:8080/ERS/html/getUser.do", true);
	xhttp.send();
}

//Gets all reimbursements from the DB and loads them into a table
function getReimbursements() {
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			let reimbursements = JSON.parse(xhttp.responseText);
			reimbursements.forEach(addToTable);
		}
	}

	xhttp.open("GET", "http://localhost:8080/ERS/html/reimbsJSON.do", true);
	xhttp.send();
}

//Gets all the details of one reimbursement
function getReimbursementDetails(reimbId) {
	let params = "reimbId=" + reimbId;
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			let reimbursement = JSON.parse(xhttp.responseText);
			loadReimbForm(reimbursement);
		}
	}

	xhttp.open("GET", "http://localhost:8080/ERS/html/reimbJSON.do?reimbId=" + reimbId, true);
	xhttp.send(params);
}

//Displays a reumbursement's details in the modal
function loadReimbForm(reimb) {
	document.getElementById("reimbId").value = reimb.reimbId;
	document.getElementById("reimbStatus").value = reimb.statusId;
	checkStatus(reimb.statusId);

	if (reimb.dateSubmitted != null)
		document.getElementById("dateSubmitted").value = reimb.dateSubmitted;
	else
		document.getElementById("dateSubmitted").value = "N/A";

	if (reimb.dateResolved != null)
		document.getElementById("dateResolved").value = reimb.dateResolved;
	else
		document.getElementById("dateResolved").value = "N/A";

	document.getElementById("reimbAuthor").value = reimb.author;

	if (reimb.resolver != null)
		document.getElementById("reimbResolver").value = reimb.resolver;
	else
		document.getElementById("reimbResolver").value = "N/A";

	document.getElementById("reimbAmount").value = "$" + reimb.reimbAmount;
	document.getElementById("reimbType").value = reimb.typeId;
	document.getElementById("reimbDescription").value = reimb.description;
	if (reimb.description != null)
		document.getElementById("reimbDescription").value = reimb.description;
	else
		document.getElementById("reimbDescription").value = "";
}

//Disables the approve or deny buttons if a request has already been approved or denied
function checkStatus(status) {
	if (status == "PENDING") {
		document.getElementById("approveBtn").disabled = false;
		document.getElementById("denyBtn").disabled = false;
	} else {
		document.getElementById("approveBtn").disabled = true;
		document.getElementById("denyBtn").disabled = true;
	}
}

//Dynamically adds rows to the reimbursement table and populates the rows with data
//from the DB
function addToTable(reimbursement, index) {
	let table = document.getElementById("reimbTable");
	let row = table.insertRow(-1);
	let cell1 = row.insertCell(0);
	let a = document.createElement("a", {
		href : "#",
		'data-toggle' : "modal",
		'data-target' : "#requestDetails"
	});
	a.href = "#";
	a.setAttribute("data-toggle", "modal");
	a.setAttribute("data-target", "#requestDetails");
	a.innerHTML = reimbursement.reimbId;
	a.onclick = function() {
		getReimbursementDetails(this.innerHTML);
	}

	cell1.appendChild(a);
	let cell2 = row.insertCell(1);
	let cell3 = row.insertCell(2);
	let cell4 = row.insertCell(3);
	let cell5 = row.insertCell(4);
	let cell6 = row.insertCell(5);

	cell2.innerHTML = reimbursement.reimbAmount;
	cell3.innerHTML = reimbursement.author;
	if (reimbursement.resolver != null)
		cell4.innerHTML = reimbursement.resolver;
	else
		cell4.innerHTML = "N/A";
	cell5.innerHTML = reimbursement.statusId;
	cell6.innerHTML = reimbursement.typeId;
}

function verifyAmount() {
	let amount = document.getElementById("amount").value;
	if(amount <= 0){
		document.getElementById("reimbSubmitBtn").disabled = true;
	}
	else
		document.getElementById("reimbSubmitBtn").disabled = false;
}

//https://www.w3schools.com/howto/howto_js_sort_table.asp
function sortTable() {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.getElementById("reimbTable");
	let sort = document.getElementById("sortAmount");
	switching = true;
	// Set the sorting direction to ascending:
	dir = "asc";
	sort.innerHTML = "Asc";
	/*
	 * Make a loop that will continue until no switching has been done:
	 */
	while (switching) {
		// Start by saying: no switching is done:
		switching = false;
		rows = table.rows;
		/*
		 * Loop through all table rows (except the first, which contains table
		 * headers):
		 */
		for (i = 1; i < (rows.length - 1); i++) {
			// Start by saying there should be no switching:
			shouldSwitch = false;
			/*
			 * Get the two elements you want to compare, one from current row
			 * and one from the next:
			 */
			x = rows[i].getElementsByTagName("TD")[1];
			y = rows[i + 1].getElementsByTagName("TD")[1];
			/*
			 * Check if the two rows should switch place, based on the
			 * direction, asc or desc:
			 */
			if (dir == "asc") {
				if (Number(x.innerHTML) > Number(y.innerHTML)) {
					shouldSwitch = true;
					break;
				}
			} else if (dir == "desc") {
				if (Number(x.innerHTML) < Number(y.innerHTML)) {
					shouldSwitch = true;
					break;
				}
			}
		}
		if (shouldSwitch) {
			/*
			 * If a switch has been marked, make the switch and mark that a
			 * switch has been done:
			 */
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
			// Each time a switch is done, increase this count by 1:
			switchcount++;
		} else {
			/*
			 * If no switching has been done AND the direction is "asc", set the
			 * direction to "desc" and run the while loop again.
			 */
			if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				sort.innerHTML = "Desc";
				switching = true;
			}
		}
	}
}