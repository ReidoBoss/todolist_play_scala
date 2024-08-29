const username = document.getElementById("username")
const password = document.getElementById("password")
const error = document.getElementById("error")

function register(){
	const xhr = new XMLHttpRequest
	xhr.open("POST","/users",true)
	xhr.setRequestHeader("Content-Type","application/json")
	const data = {"username":username.value,"password": password.value}
	xhr.send(JSON.stringify(data))

	xhr.onload = () => {
		const res = xhr.response
		const json = JSON.parse(res)	

		username.value = ""
		password.value = ""
		error.value = json.Message
		
	}	
}

