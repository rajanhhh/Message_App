package com.DBchat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bean.ChatRoom;
import com.bean.History;



@Controller
@Component("ViewController")
public class ViewController {
	
	@Autowired
	DBController DBController;

	@RequestMapping(value = "/chat", method=RequestMethod.GET)
	public String map(HttpSession session,Model model) {
		try {
			if(!StringUtils.isEmpty(session.getAttribute("user")) && !StringUtils.isEmpty(session.getAttribute("chatId"))) {
				model.addAttribute("chatId", session.getAttribute("chatId").toString());
				model.addAttribute("userId", session.getAttribute("user").toString());
				model.addAttribute("chatPassword", session.getAttribute("chatPassword").toString());
			}
			return "chatBox";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "chatBox";
	}
	
	@RequestMapping(value = "/start", method=RequestMethod.POST)
	public String startClient(HttpSession session,HttpServletResponse response,Model model, String chatId, String chatPassword, String user) {
		try {
			ArrayList<ChatRoom> arrayList = null;
			if(!StringUtils.isEmpty(chatId) && !StringUtils.isEmpty(chatPassword)) {
				arrayList = DBController.validatePassword(chatId, chatPassword);
			}
			if(arrayList.size() != 0) {
				session.setAttribute("user", user);
				session.setAttribute("chatId", chatId);
				session.setAttribute("chatPassword", chatPassword);
				response.setStatus(200);
			}else {
				model.addAttribute("message", "Could not join due to incorrect credentials");
				response.setStatus(400);
			}
			return "textResponse";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("message", "error");
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/submitChat", method=RequestMethod.POST)
	public String submitChat(HttpSession session,Model model, String data,HttpServletResponse response) {
		boolean isSuccess = false;
		LocalDateTime now = LocalDateTime.now();
		try {
			if(!StringUtils.isEmpty(session.getAttribute("user")) && !StringUtils.isEmpty(session.getAttribute("chatId"))) {
				isSuccess = DBController.addChat(session.getAttribute("chatId").toString(), session.getAttribute("user").toString(), data,now.toString());
			}
			if(isSuccess) {
				//model.addAttribute("message", now);
				return recoverData(session,model,response);
			}else {
				model.addAttribute("message", "Failure");
				response.setStatus(400);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/recoverData", method=RequestMethod.GET)
	public String recoverData(HttpSession session,Model model,HttpServletResponse response) {
		LocalDateTime now = LocalDateTime.now();
		try {
			ArrayList<History> arrayList = null;
			if(!StringUtils.isEmpty(session.getAttribute("chatId"))) {
				if(!StringUtils.isEmpty(session.getAttribute("lastRefreshTime")))
					arrayList = DBController.fetch(session.getAttribute("chatId").toString(), session.getAttribute("lastRefreshTime").toString());
				else
					arrayList = DBController.fetch(session.getAttribute("chatId").toString(), null);
			}
			session.setAttribute("lastRefreshTime", now.toString());
			if(arrayList.size() != 0) {
				ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(arrayList);
				 model.addAttribute("message", jsonStr);
				response.setStatus(200);
			}else {
				model.addAttribute("message", "No Record Found");
				response.setStatus(400);
			}
			return "textResponse";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/createChatRoom", method=RequestMethod.GET)
	public String createChatRoom(HttpSession session,Model model,HttpServletResponse response) {
		try {
			LocalDateTime now = LocalDateTime.now();
			Random rnd = new Random();
		    int number = rnd.nextInt(999999);
		    String password = String.format("%06d", number);
		     
		    boolean isChatRoomCreated = DBController.createRoom(password, now.toString());
		    
		    ArrayList<ChatRoom> arrayList = null;
			if(isChatRoomCreated) {
				arrayList = DBController.getChatRoomId(password, now.toString());
			}
		    
			if(arrayList.size() == 1) {
				ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(arrayList);
				 model.addAttribute("message", jsonStr);
				response.setStatus(200);
			}else if(arrayList.size() > 1){
				model.addAttribute("message", "More than one chatRoom Found");
				response.setStatus(400);
			}else {
				model.addAttribute("message", "Error occured");
				response.setStatus(400);
			}
			
			return "textResponse";
		} catch (Exception e) {
			response.setStatus(400);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/closeChat", method=RequestMethod.GET)
	public String closeChat(HttpSession session,Model model,HttpServletResponse response) {
		try {
			session.removeAttribute("user");
			session.removeAttribute("chatId");
			session.removeAttribute("chatPassword");
			session.removeAttribute("lastRefreshTime");
			model.addAttribute("message", "Success");
			return "textResponse";
		} catch (Exception e) {
			response.setStatus(400);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
}
