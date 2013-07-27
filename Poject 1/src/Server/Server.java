package Server;
import java.io.*;
import java.net.*;
import java.util.HashMap;


public class Server {
	
	private HashMap<String, UserInformation> Names = new HashMap<String, UserInformation>();
	ServerSocket welcomeSocket;
	public Server()
	{
		try {
			welcomeSocket = new ServerSocket(3003);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[]args) throws Exception { 
		Server br = new Server();
		while (true) {	
			UserInformation newClient = new UserInformation();
			Socket newClientConnection = br.welcomeSocket.accept();
			newClient.setUsers(br.Names);
			newClient.setConnectionSocket(newClientConnection);
			(new ClientThread(newClient)).start();
		}
	}
}




/* BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));  

DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());  

clientSentence = inFromClient.readLine();  

System.out.println("Received: " + clientSentence);  


String filecontent="HI from server";  
//File content is cleared    

System.out.println("file : "+filecontent);  

outToClient.writeBytes(filecontent+'\n');  */

/* (new HelloThread()).start();
(new HelloThread()).start();*/