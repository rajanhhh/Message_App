package com.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.socket.StarterClient;


@Controller
@Component("MyController")
public class ViewController {

	@Autowired
	StarterClient starterClient;
	
	@RequestMapping(value = "/chat", method=RequestMethod.GET)
	public String map(Model model) {
		try {
			return "chatBox";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "chatBox";
	}
	
	@RequestMapping(value = "/start", method=RequestMethod.GET)
	public String startClient(Model model) {
		try {
			starterClient.start();
			return "textResponse";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/submitChat", method=RequestMethod.GET)
	public String submitChat(Model model, String data) {
		try {
			starterClient.sendData(data);
			return "textResponse";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
	
	@RequestMapping(value = "/recoverData", method=RequestMethod.GET)
	public String recoverData(Model model, String data) {
		try {
			String str = starterClient.recoverData();
			model.addAttribute("message", str);
			return "textResponse";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "textResponse";
	}
}
