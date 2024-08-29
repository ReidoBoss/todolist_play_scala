const username = document.getElementById("username")
const password = document.getElementById("password")
const error = document.getElementById("error")


function login(){

	const xhr = new XMLHttpRequest
	xhr.open("POST","/users/auth",true)
	xhr.setRequestHeader("Content-Type","application/json;charset=UTF-8")

	const data = {"username":username.value,"password": password.value}
	xhr.send(JSON.stringify(data))

	xhr.onload = () => {
		res = JSON.parse(xhr.response).Message
		if(res ===	'Success'){
			window.location = "/home"
		}
		else{
			error.value = res
		}


	}


}