//Gets the user info on page load
window.onload = function() {
	getUser();
}

//Gets the current user's info
function getUser() {
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			let user = JSON.parse(xhttp.responseText);
			console.log("test");
			console.log(user);
			loadFields(user);
		}
	}

	xhttp.open("GET", "http://localhost:8080/ERS/html/getUser.do", true);
	xhttp.send();
}

//Populates the the text boxes with user info
function loadFields(user) {
	document.getElementById("userId").value = user.userId;
	document.getElementById("role").value = user.roleId;
	document.getElementById("email").value = user.email;
	document.getElementById("username").value = user.userName;
	document.getElementById("firstName").value = user.firstName;
	document.getElementById("lastName").value = user.lastName;

	document.getElementById("editEmail").value = user.email;
	document.getElementById("editFirst").value = user.firstName;
	document.getElementById("editLast").value = user.lastName;
}

//If the user wants to change password, they must enter the password twice.
//This function verifies the the two passwords entered by the user are the same.
function verifyPassword() {
	let pass1 = document.getElementById("password1").value;
	let pass2 = document.getElementById("password2").value;

	if (pass1 != pass2 || pass1 == "" || pass2 == "")
		document.getElementById("submitBtn").disabled = true;
	else
		document.getElementById("submitBtn").disabled = false;
}

//Verifies that the email entered by the user is different from their original email
function verifyEmail() {
	let email1 = document.getElementById("email").value;
	let email2 = document.getElementById("editEmail").value;

	if (email1 == email2 || email2 == "")
		document.getElementById("submitBtn").disabled = true;
	else
		document.getElementById("submitBtn").disabled = false;
}

//Verifies that the first name entered by the user is different from their original first name
function verifyFirstName() {
	let first1 = document.getElementById("firstName").value;
	let first2 = document.getElementById("editFirst").value;

	if (first1 == first2 || first2 == "")
		document.getElementById("submitBtn").disabled = true;
	else
		document.getElementById("submitBtn").disabled = false;
}

//Verifies that the last name entered by the user is different from their original last name
function verifyLastName() {
	let last1 = document.getElementById("lastName").value;
	let last2 = document.getElementById("editLast").value;

	if (last1 == last2 || last2 == "")
		document.getElementById("submitBtn").disabled = true;
	else
		document.getElementById("submitBtn").disabled = false;
}