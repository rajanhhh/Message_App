package com.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.springframework.stereotype.Component;

@Component("StarterClient")
public class StarterClient {
	Socket socket;
	
	public String start() throws Exception {
		try{
			Socket serverClient=new Socket("127.0.0.1",8888);
			socket = serverClient;
			return "success";
		}catch(Exception e){
			System.out.println(e);
			return "error";
		}
	}
	
	public boolean sendData(String data) {
		try {
			DataInputStream inStream=new DataInputStream(socket.getInputStream());
			DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String clientMessage="",serverMessage="";
			//while(!clientMessage.equals("bye")){
				//System.out.println("Enter number :");
			clientMessage=data;
			outStream.writeUTF(clientMessage);
			outStream.flush();
			//serverMessage=inStream.readUTF();
			//System.out.println(serverMessage);
			//}
//			outStream.close();
//			outStream.close();
//			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String recoverData() {
		String clientMessage="",serverMessage="";
		try {
			DataInputStream inStream=new DataInputStream(socket.getInputStream());
			DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			
			//while(!clientMessage.equals("bye")){
				//System.out.println("Enter number :");
//			clientMessage=data;
//			outStream.writeUTF(clientMessage);
//			outStream.flush();
			serverMessage=inStream.readUTF();
			System.out.println(serverMessage);
//			}
//			outStream.close();
//			outStream.close();
//			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serverMessage;
	}
	
}