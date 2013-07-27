/**
 * 
 */
package Shared;

import java.io.Serializable;

/**
 * @author 16715136
 *
 */
public class Message implements Serializable{

	/**
	 * 
	 */
	private String command = "";
	private String type = "";
	private String message = "";
	//Receiver can be all or a specific name.
	private String receiver = "";
	private String sender = "";
	private String date = "";
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
}
