locationList = '';
index = '';

$(document).ready(function() {
	$("#chatForm").submit(function(e){
		e.preventDefault();
		submitForm();
	});
	if('' != document.getElementById("chatRoomId").innerText && '' != document.getElementById("userId").innerText){
		$("#chatForm").show();
		$("#startForm").hide();
		refreshChat();
	}
});

function createChatRoom(){
	$.ajax({
			url:"/createChatRoom",
			type:'get',
		  	success:function(json){
				$("#message").text(json);
				$("#errorMessage").text('');
				$("#chatRoomInfoForm").show();
				$("#startForm").hide();
				var formData = jQuery.parseJSON(json);
				document.getElementById("chatId").value  = formData[0].id;
				document.getElementById("chatPassword").value = formData[0].password;
		  	},
			error:function(error){
				$("#errorMessage").text(error.responseText);
			}
		});
}

function joinChatRoom(){
	$("#chatRoomInfoForm").show();
	$("#startForm").hide();
	document.getElementById("chatId").value  = '';
	document.getElementById("chatPassword").value = '';
}

function joinChat(){
	$.ajax({
			url:"/start",
			data:"chatId="+document.getElementById("chatId").value + "&chatPassword="+document.getElementById("chatPassword").value
				 + "&user="+document.getElementById("user").value,
			type:'post',
		  	success:function(json){
				$("#message").text(json);
				$("#errorMessage").text('');
				$("#chatForm").show();
				$("#chatRoomInfoForm").hide();
				document.getElementById("chatIdshow").innerText  = document.getElementById("chatId").value;
				document.getElementById("chatPasswordshow").innerText  = document.getElementById("chatPassword").value;
		  	},
			error:function(error){
				$("#errorMessage").text(error.responseText);
			}
		});
}

function submitForm(){
	if('' != document.getElementById("chat").value){
		$.ajax({
			url:"/submitChat",
			data:"data="+document.getElementById("chat").value,
			type:'post',
		  	success:function(json){
				$("#message").text(json);
				populateFormData();
				document.getElementById("chat").value= '';
		  	},
			error:function(error){
				$("#errorMessage").text("Nothing new");
			}
		});
	}
}

function refreshChat(){
	$.ajax({
			url:"/recoverData",
			type:'get',
		  	success:function(json){
				$("#message").text(json);
				populateFormData();
		  	},
			error:function(error){
				$("#errorMessage").text("Nothing new");
			}
		});
}
function closeChat(){
	$.ajax({
			url:"/closeChat",
			type:'get',
		  	success:function(json){
				$("#chatForm").hide();
				$("#chatHistoryForm").hide();
				$("#startForm").show();
				$("#errorMessage").text('');
		  	},
			error:function(error){
				$("#errorMessage").text(error.responseText);
			}
		});
}

function addSingleRow(json){
	var today = new Date();
	var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
	
	var tbody = $("tbody");
	var tr = '<tr id = "row" name = "row">';

	tr += "<td><span id='chatRoom' name='chatRoom' value='"+ document.getElementById("chatRoom").value +"'>" + document.getElementById("chatRoom").value + "</span></td>";
	tr += "<td><span id='time' name='time' value='"+ json +"'>" + json + "</span></td>";
	tr += "<td><span id='user' name='user' value='"+ document.getElementById("user").value +"'>" + document.getElementById("user").value + "</span></td>";
	tr += "<td><span id='msg' name='msg' value='"+ document.getElementById("chat").value +"'>" + document.getElementById("chat").value + "</span></td>";
	tr += '</tr>';
	tbody.append(tr);
}

function populateFormData(){
	var formData = $("#message")[0].innerHTML;
	formData = jQuery.parseJSON(formData);
	if(formData.length != 0){
		$("#errorMessage").text('');
		$("#chatHistoryForm").show();
		
		var col = [];
	    for (var i = 0; i < formData.length; i++) {
	        for (var key in formData[i]) {
	            if (col.indexOf(key) === -1) {
	                col.push(key);
	            }
	        }
	    }
	
	    for (var i = 0; i < formData.length; i++) {
			var tbody = $("tbody");
			var tr = '<tr id = "row" name = "row">';
	
	        for (var j = 0; j < col.length; j++) {
				tr += "<td><span id='"+ col[j] +"' name='"+ col[j] +"' value='"+ formData[i][col[j]] +"'>" + formData[i][col[j]] + "</span></td>";
	        }
			tr += '</tr>';
			tbody.append(tr);
	    }
	}else{
		$("#errorMessage").text("No Data Found");
	}
	
}
