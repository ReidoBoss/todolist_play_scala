function logout(){

	const xhr = new XMLHttpRequest
	xhr.open("POST","/logout",true)
	xhr.setRequestHeader("Content-Type","application/json")

	xhr.send()
	window.location.href = "/"

}