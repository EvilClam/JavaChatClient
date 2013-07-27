package Client;
import java.io.*;
import java.net.*;

import Shared.*;
public class Client {
	private String name = "0_0";
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private Socket clientSocket;
	private Message receiveMsg;
	private Message sendMsg;
	
	public Client()
	{	
	}
	
	public Client(String ip, int port)
	{
		try {
			setClientSocket(new Socket("146.232.50.251", 3003));
			setOutToClient(new ObjectOutputStream(getClientSocket().getOutputStream()));
			setInFromClient(new ObjectInputStream(getClientSocket().getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	
	public Client(String userName, String ip, int port)
	{
		try {
			setClientSocket(new Socket("146.232.50.251", 3003));
			setOutToClient(new ObjectOutputStream(getClientSocket().getOutputStream()));
			setInFromClient(new ObjectInputStream(getClientSocket().getInputStream()));
			setName(userName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	public boolean authenticate(String userName)
	{
		boolean success = false;
		SendHandler sendHandler = new SendHandler(this);
		Message msg = new Message();
		if (userName.contains(",")) {
			return success;
		}
		msg.setType("login");
		msg.setSender(userName);
		msg.setMessage(userName);
		
		try {
			sendHandler.sendMessage(msg);
			Message inMessage = (Message)getInFromClient().readObject();
			if (inMessage.getType().equals("Success")) {
				success = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
		
	public ObjectOutputStream getOutToClient() 
	{
		return outToClient;
	}
	
	public void setOutToClient(ObjectOutputStream outToClient) 
	{
		this.outToClient = outToClient;
	}
	
	public ObjectInputStream getInFromClient() 
	{
		return inFromClient;
	}
	
	public void setInFromClient(ObjectInputStream inFromClient) 
	{
		this.inFromClient = inFromClient;
	}
	
	public Socket getClientSocket() 
	{
		return clientSocket;
	}
	
	public void setClientSocket(Socket clientSocket) 
	{
		this.clientSocket = clientSocket;
	}
	
	public Message getReceiveMsg() 
	{
		return receiveMsg;
	}
	
	public void setReceiveMsg(Message receiveMsg) 
	{
		this.receiveMsg = receiveMsg;
	}
	
	public Message getSendMsg() 
	{
		return sendMsg;
	}
	
	public void setSendMsg(Message sendMsg) 
	{
		this.sendMsg = sendMsg;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public static void main(String[] args) throws Exception 
	{
		Client client = new  Client("146.232.50.251", 3003 );
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SendHandler sf = new  SendHandler(client); 
		Message msg = new Message();
		
		while (true) {
			String userName = br.readLine();
			client.setName(userName);
			if (client.authenticate(userName)) {
				break;
			}
		}
		ReceiveHandler s = new ReceiveHandler(client);
		s.start();
		while (true) {
			msg = new Message();
			msg.setCommand("send_all");
			msg.setSender(client.getName());
			String message = br.readLine();
			String[] commandCheck = message.split(" ");
			if (commandCheck[0].equals("\\w")) {
				try {
					msg.setCommand("\\w");
					msg.setReceiver(commandCheck[1]);
					msg.setMessage(commandCheck[2]);
					sf.sendMessage(msg);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Invalid command \\w receiver message");
				}
			} else if (commandCheck[0].equals("\\l")) {
				msg.setCommand("\\l");
				sf.sendMessage(msg);
			} else if (commandCheck[0].equals("\\e")){
				msg.setType("logout");
				sf.sendMessage(msg);
			} else {
				msg.setMessage(message);
				sf.sendMessage(msg);
			}
			
			
		}
	}

}
