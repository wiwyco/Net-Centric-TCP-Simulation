//COSC 4360 Assignment 2
//Winslow Conneen
//Simulation of TCP socket methods and practices in java

import java.net.*;
import java.io.*;
import java.lang.*;

public class WConneenTCPServer 
{
	
	public static void main(String[] args) throws InterruptedException 
	{
				
		ServerSocket serverSocket;
		
		try 
		{
		
			   serverSocket = new ServerSocket(9995); //creates a socket and binds it to port 9999
			   //serverSocket = new ServerSocket(0); //creates a socket and binds it to next available port 
			   
			   while (true)
			   {
			   
				   System.out.println("TCP Server waiting for client on port " + serverSocket.getLocalPort() + "...");
				   
				   Socket connectionSocket = serverSocket.accept();  //listens for connection and 
				   										// creates a connection socket for communication
				   
				   System.out.println("Just connected server port # " + connectionSocket.getLocalSocketAddress() + " to client port # " + connectionSocket.getRemoteSocketAddress());
				   
				   DataInputStream in = new DataInputStream(connectionSocket.getInputStream()); //get incoming data in bytes from connection socket
				   DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream()); //setup a stream for outgoing bytes of data
				   
				   //System.out.println("Flag");
				   String input = in.readUTF();
				   System.out.println("Flag");
				   boolean confirmation = true;
				   
				   System.out.println("RECEIVED: from IPAddress " + 
							connectionSocket.getInetAddress() + " and from port " + connectionSocket.getPort() + " the data: \n" + input);
				   
				   //data checking method
				   for(int i = 0; i < 1000; i++)
				   {
					   if(input.charAt(i) != (char) 0xAA)
					   {
						   confirmation = false;
						   System.out.println("Error found in text, connection closed");
						   break;
					   }
				   }
					   System.out.println("No loss found in the message.");
				   
				   //write back to client
				   out.writeUTF(input);
				   
				   connectionSocket.close();  //close connection socket after this exchange
				   
				   System.out.println();
			   }
	
		} 
		catch (IOException e)
		{
				e.printStackTrace();
		}
	}

}
