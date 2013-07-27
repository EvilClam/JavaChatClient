package Client;

import java.io.IOException;

import Shared.Message;

public class ReceiveHandler extends Thread
{
	private Client clientRefrence;
	
	public ReceiveHandler(Client in)
	{
		clientRefrence = in;
	}

	public void run()
	{
		while (true) {
			try {
				Message inMessage = (Message)clientRefrence.getInFromClient().readObject();
				System.out.println( inMessage.getSender() + " : " + inMessage.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
