const task = document.getElementById("task")
const due = document.getElementById("duedate")
const description = document.getElementById("description")
const currentAction = document.getElementById("currentAction")
const idTask = document.getElementById("idTask")

function chooseListUpdate(id,task,due,desc){
	changeId(id)
	changeTask(task)
	changeUpdate()
	changeDue(due)
	changeDesc(desc)
	console.log(idTask.value)
}
// i got this from stack overflow 
function wait(ms){
   var start = new Date().getTime();
   var end = start;
   while(end < start + ms) {
     end = new Date().getTime();
  }
}

function changeDesc(desc){
	description.value = desc
}
function changeId(id){
	idTask.value = id
}
function changeDue(d){
	due.value = d

}
function changeTask(t){
	task.value = t
}

function submitter(){
	if(currentAction.value == "Creating a List"){
		addList()
	}
	else{
		update()
	}
}

function changeCreate(){
		console.log(task.value)

	currentAction.value = "Creating a List"
}

function changeUpdate(){
	window.scrollTo(500, 0);
	currentAction.value = "Updating a List"
}

function hideUnhide(id,bool){
	const xhr = new XMLHttpRequest
	xhr.open("PUT",`/lists/${id}/visibility/${bool}`,true)
	xhr.setRequestHeader("Content-Type","application/json")	
	xhr.send()
	wait(1000)
	window.location.reload()	
}
function addList(){
	const xhr = new XMLHttpRequest
	xhr.open("POST","/lists",true)
	xhr.setRequestHeader("Content-Type","application/json")
	if(due.value == '' || task ==''){
		alert("Please Finish everything")
	}
	else{
		const data = {
			"description":description.value,
			"task":task.value,
			"status" : "PENDING",
			"due" :	due.value
		}

		xhr.send(JSON.stringify(data))
		xhr.onload = () => {

			const res = JSON.parse(xhr.response).Message
			if(res == 'Added'){
				wait(1000)
				window.location.reload()
			}
		}	
	}

}

function deleter(id){
	const xhr = new XMLHttpRequest
	xhr.open("DELETE","/lists/"+id,true)
	xhr.setRequestHeader("Content-Type","application/json")	
	xhr.send()
	wait(1000)

	window.location.reload()

}

function updateStatus(id,status){
	console.log("Update ni gaw")
	const xhr = new XMLHttpRequest
	xhr.open("PUT",`/lists/${id}/${status}`,true)
	xhr.setRequestHeader("Content-Type","application/json")	
	xhr.send()
	wait(1000)

	window.location.reload()
}

function logout(){

	const xhr = new XMLHttpRequest
	xhr.open("POST","/logout",true)
	xhr.setRequestHeader("Content-Type","application/json")

	xhr.send()
	wait(1000)

	window.location.href = "/"

}
function update(){
	console.log("Update ni gaw")
	const xhr = new XMLHttpRequest
	xhr.open("PUT","/lists/"+idTask.value,true)
	xhr.setRequestHeader("Content-Type","application/json")	

	if(due.value == '' || task ==''){
		alert("Please Finish everything")
	}
	else {
		const data = {
			"description":description.value,
			"task":task.value,
			"status" : "PENDING",
			"due" :	due.value
		}


		xhr.send(JSON.stringify(data))
		xhr.onload = () => {

			const res = JSON.parse(xhr.response).Message
			
		}	
		description.value = ''
		task.value = ''
		due.value =''
		wait(1000)

		window.location.reload()
	}
}

