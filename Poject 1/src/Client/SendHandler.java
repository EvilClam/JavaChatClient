package Client;

import java.io.IOException;

import Shared.Message;

public class SendHandler
{	
	private Client clientRefrence;
	public SendHandler(Client in)
	{
		clientRefrence = in;
	}

	public void sendMessage(Message msg)
	{
		try {
			System.out.println("Send :"+msg.getMessage());
			clientRefrence.getOutToClient().writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
