//COSC 4360 Assignment 2
//Winslow Conneen
//Simulation of TCP socket methods and practices in java

import java.net.*;
import java.text.DecimalFormat;
import java.io.*;
import javax.swing.JOptionPane;

public class WConneenTCPClient 
{

	public static void main(String[] args) throws InterruptedException, SocketTimeoutException
	{
		DecimalFormat df = new DecimalFormat("0.00");	//Create decimal format for results
		
		String serverName = "localhost";		//create host name
		//String serverName = "192.168.1.135";
		int port = 9995;			//Set port number
		
		byte ping_byte = (byte)0xAA; //set byte equivalent to '10101010'
		
		byte [] message = new byte [1000]; //create and fill a byte array for byte
		
		for ( int i = 0; i < 1000; i++)
		{
			message[i] = ping_byte;
		}
		
		String message_S = new String(message);		//convert byte array to string
		
		int totalRTT = 0;
		
		for(int x = 0; x < 10; x++)
		{
			try 
			{	
				System.out.println("Connecting to " + serverName + " on port " + port);
				
				Socket clientSocket = new Socket(serverName, port);  //create socket for connecting to server
				
				clientSocket.setSoTimeout(1000);		//set timeout to one second
				
				System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
				
				OutputStream outToServer = clientSocket.getOutputStream();  //stream of bytes
				
				DataOutputStream out = new DataOutputStream(outToServer);
				
				System.out.println("TCP Client says: ");
	
				long startTime = System.nanoTime();		//begin recording time
				
				out.writeUTF(message_S);		//write message to server
				
				InputStream inFromServer = clientSocket.getInputStream();  //stream of bytes
				
				DataInputStream in = new DataInputStream(inFromServer);		
				
				String response = in.readUTF();			//recieve string from server
				long endTime = System.nanoTime();		//record time
	
				clientSocket.close();		//close socket
				
				boolean confirmation = true;
				
				System.out.println("Recieved message from server:\n" + response);
				
				//Method to check byte array
				for(int i = 0; i < 1000; i++)
				{
					   if(response.charAt(i) != (char) 0xAA)
					   {
						   confirmation = false;
						   System.out.println("Error found in text, connection closed");
						   break;
					   }
				}
				   
				if(confirmation)
				{
					int RTT = (int) (endTime - startTime);
					System.out.println("No loss found in the message. \n"
							+ "Round Trip Time: " + RTT + "ms");
					totalRTT += RTT;
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		double avgRTT = totalRTT/10;
		
		//format and print results
		System.out.println("Average Round Trip Time: " + df.format(avgRTT/1000000) + "ms\nAverage Data Rate: " + df.format((16/(avgRTT/1000000))) +  "Mbps");
	}
	
}
