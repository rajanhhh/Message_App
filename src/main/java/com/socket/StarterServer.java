package com.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Component;

@Component("StarterServer")
public class StarterServer {
	public static void starter() throws IOException {
		try {
			ServerSocket server=new ServerSocket(8888);
			 int counter=0;
			 System.out.println("Server Started ....");
			 while(true){
			   counter++;
			   Socket serverClient=server.accept();  //server accept the client connection request
			   System.out.println(" >> " + "Client No:" + counter + " started!");
			   
			   ClientThread sct = new ClientThread(serverClient,counter); //send  the request to a separate thread
			   sct.start();
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
