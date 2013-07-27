package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

import Shared.Message;

public class ClientThread extends Thread {
	private UserInformation currentUser;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private Message msg;
	private boolean pass = true;
	public ClientThread(UserInformation input) {
		currentUser = input;
		try {
			inFromClient = new ObjectInputStream(currentUser.getConnectionSocket().getInputStream());
			outToClient = new ObjectOutputStream(currentUser.getConnectionSocket().getOutputStream());
			msg = new Message();
			input.setInFromClient(inFromClient);
			input.setOutToClient(outToClient);
			authenticate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    public void run() 
    {	
    	if (pass) {
    		try {
    			while (!msg.getType().equals("logout"))
    			{
    				msg = (Message)inFromClient.readObject();
    				send(msg);
    				System.out.println(msg.getMessage());
    			}
    			msg.setCommand("send_all");
    			msg.setType("logout");
    			msg.setSender(currentUser.getUsername());
    			send(msg);
    			System.out.println(msg.getMessage());
    			currentUser.getUsers().remove(currentUser.getUsername());
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			/*e.printStackTrace();*/
    			msg.setCommand("send_all");
    			msg.setType("logout");
    			msg.setSender(currentUser.getUsername());
    			send(msg);
    			System.out.println(msg.getMessage());
    			synchronized (currentUser.getUsers()) {
    				currentUser.getUsers().remove(currentUser.getUsername());
    			}	
    		} catch (ClassNotFoundException e) {
    			/*e.printStackTrace();*/
    			msg.setCommand("send_all");
    			msg.setType("logout");
    			msg.setSender(currentUser.getUsername());
    			send(msg);
    			System.out.println(msg.getMessage());
    			synchronized (currentUser.getUsers()) {
    				currentUser.getUsers().remove(currentUser.getUsername());
    			}
    		}
    	}
    }
    
    private void send(Message input) 
    {
    	HashMap<String, UserInformation> temp = currentUser.getUsers();
    	synchronized (temp) {
    	if (input.getCommand().equals("\\w")) {
    		try {
    			if (temp.containsKey(input.getReceiver())) {
    				synchronized (temp.get(input.getReceiver()).getOutToClient()) {
    					temp.get(input.getReceiver()).getOutToClient().writeObject(input);
					}
    			} else {
    				msg.setType("Fail");
					msg.setMessage("Username does not exist.");//Use error codes?
					outToClient.writeObject(msg);
    			}
    			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else if (input.getCommand().equals("send_all")) {
    		try {
    			Set<String> temp2 = temp.keySet();
    			for (String y : temp2) {
    				if (!y.equals(currentUser.getUsername())) {
    					synchronized (temp.get(y).getOutToClient()) {
    						System.out.println(currentUser.getUsername() + " sent this message : " + input.getMessage());
    						temp.get(y).getOutToClient().writeObject(input);
    					}	
    				}
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
    	} else if (input.getCommand().equals("\\l")) {
    		try {
    			Set<String> temp2 = temp.keySet();
    			String names = temp2.toString();
    			names = names.substring(1, names.length()-1);
    			synchronized (currentUser.getOutToClient()) {
					System.out.println(currentUser.getUsername() + " requests this message : " + names);
					input.setSender("Server");
					input.setCommand("\\l");
					input.setMessage(names);
					currentUser.getOutToClient().writeObject(input);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	}
    }
    
    private void authenticate()
    {
    	boolean isValid = false;
    	String username = "";
    	
    	while (!isValid) { 
    		synchronized (currentUser.getUsers()) {
    			try {
    				msg = (Message)inFromClient.readObject();
    				username = msg.getMessage();
    				if (!msg.getType().equals("login")) {
    					isValid = true;
        				pass = false;
        				break;
    				}
    				if (!currentUser.getUsers().containsKey(username)) {
    					currentUser.setUsername(username);
    					isValid = true;
    					msg.setType("Success");
    					msg.setMessage("You are now logged in as : " + username);
    					outToClient.writeObject(msg);
    					currentUser.getUsers().put(username, currentUser);
    				} else {
    					msg.setType("Fail");
    					msg.setMessage("Username already in use");//Use error codes?
    					outToClient.writeObject(msg);
    				}
    			} catch (IOException e) {
    				isValid = true;
    				pass = false;
    			} catch (ClassNotFoundException e) {
    				isValid = true;
    				pass = false;
    			}		
			}
    	}
    }
}