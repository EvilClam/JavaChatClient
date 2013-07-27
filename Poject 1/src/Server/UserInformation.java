package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class UserInformation {
	private String Username;
	private Socket ConnectionSocket;
	private HashMap<String, UserInformation> users;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	
	public String getUsername() {
		return Username;
	}
	
	public void setUsername(String username) {
		Username = username;
	}
	
	public Socket getConnectionSocket() {
		return ConnectionSocket;
	}
	
	public void setConnectionSocket(Socket connectionSocket) {
		ConnectionSocket = connectionSocket;
	}

	public HashMap<String, UserInformation> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, UserInformation> users) {
		this.users = users;
	}

	public ObjectInputStream getInFromClient() {
		return inFromClient;
	}

	public void setInFromClient(ObjectInputStream inFromClient) {
		this.inFromClient = inFromClient;
	}

	public ObjectOutputStream getOutToClient() {
		return outToClient;
	}

	public void setOutToClient(ObjectOutputStream outToClient) {
		this.outToClient = outToClient;
	}
	
	
}
