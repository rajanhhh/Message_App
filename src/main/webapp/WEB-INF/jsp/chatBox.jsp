<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
    
    <title>Message Map</title>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="/css/bootstrap.min.css">
	<script src="/js/loaderScript.js"></script>
	
    <style type="text/css">
    #errorMessage {
	    color: red;
	}
	
	body{
		margin-left: 3%!important;
	}
    </style>
  </head>
  <body id="markers-on-the-map">
  	<div id = "message" hidden= "hidden">${message}</div>
  	<div id = "chatRoomId" hidden= "hidden">${chatId}</div>
  	<div id = "chatRoomPassword" hidden= "hidden">${chatRoomPassword}</div>
  	<div id = "userId" hidden= "hidden">${userId}</div>
  	
    <div class="page-header">
        <h1>Chat-pat</h1>
    </div>
     <form class="form-horizontal" id="startForm">
   		 <input type="button" class="btn btn-primary" value="Create ChatRoom" onclick="createChatRoom()">
   		 <input type="button" class="btn btn-primary" value="Join ChatRoom" onclick="joinChatRoom()">
    </form>
    
    <form class="form-horizontal" id="chatRoomInfoForm" style="display: none;">
    	<div class="form-group">
	    	<label>ChatRoom Id</label>
	    	<input id="chatId" type="text">
	    </div>
	    <div class="form-group">
	    	<label>ChatRoom Password</label>
	    	<input id="chatPassword" type="text">
	    </div>
	    <div class="form-group">
	    	<label>User Name</label>
	    	<input id="user" type="text">
	    </div>
    	<input type="button" class="btn btn-primary" value="Join" onclick="joinChat()">
    </form>
    
    <form class="form-horizontal" id="chatForm" style="display: none;">
    <div class="">
	    	<label>Chat Room Id :</label><span id="chatIdshow"></span><br>
	    	<label>Chat Room Password :</label><span id="chatPasswordshow"></span><br>
	    </div>
	    <div class="">
	    	<label>Message</label>
	    	<input id="chat" type= "text">
	    	<input type="button" class="btn btn-primary" value="Send" onclick="submitForm()" style="margin-left: 20px;">
	    </div>
	    <input type="button" class="btn btn-light" value="Refresh" onclick="refreshChat()">
	    <input type="button" class="btn btn-dark" value="Close Chat" onclick="closeChat()">
	</form>
	
	<form id = "chatHistoryForm" style="display:none">
		
		<table id="historyTable"  class="table table-bordered">
			<tbody>
				<tr>
					<th>Time</th>
					<th>Sender</th>
					<th>Message</th>
				</tr>
			</tbody>
		</table>
	</form>
	
	<div id ="errorMessage"></div>

  </body>
</html>
