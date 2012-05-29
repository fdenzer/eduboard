//Copyright (c) 2011, Markus Brandt
//All rights reserved.
//
//Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
//
//Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
//Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
//Neither the name of the FH Worms nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package de.fhworms.tawk;

import java.util.Hashtable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public final class Message extends SoapObject {
	private String message;
	private MessageID messageID;
	private UserID receiver;
	private String subject;
	private UserID userID;

	public Message() {
		super("", "");
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessageID(MessageID messageID) {
		this.messageID = messageID;
	}

	public MessageID getMessageID() {
		return this.messageID;
	}

	public void setReceiver(UserID receiver) {
		this.receiver = receiver;
	}

	public UserID getReceiver() {
		return this.receiver;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setUserID(UserID userID) {
		this.userID = userID;
	}

	public UserID getUserID() {
		return this.userID;
	}

	public int getPropertyCount() {
		return 5;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return message;
		case 1:
			return messageID;
		case 2:
			return receiver;
		case 3:
			return subject;
		case 4:
			return userID;
		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			message = (__obj != null) ? __obj.toString() : null;
			break;
		case 1:
			messageID = null;
			if (__obj != null) {
				messageID = new MessageID();
				for (int i = 0; i < ((SoapObject) __obj).getPropertyCount(); i++) {
					messageID.setProperty(i,
							((SoapObject) __obj).getProperty(i));
				}
			}
			break;
		case 2:
			receiver = null;
			if (__obj != null) {
				receiver = new UserID();
				for (int i = 0; i < ((SoapObject) __obj).getPropertyCount(); i++) {
					receiver.setProperty(i, ((SoapObject) __obj).getProperty(i));
				}
			}
			break;
		case 3:
			subject = (__obj != null) ? __obj.toString() : null;
			break;
		case 4:
			userID = null;
			if (__obj != null) {
				userID = new UserID();
				for (int i = 0; i < ((SoapObject) __obj).getPropertyCount(); i++) {
					userID.setProperty(i, ((SoapObject) __obj).getProperty(i));
				}
			}
			break;
		}
	}

	public void getPropertyInfo(int __index,
			@SuppressWarnings("rawtypes") Hashtable __table, PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "message";
			__info.type = String.class;
			break;
		case 1:
			__info.name = "messageID";
			__info.type = MessageID.class;
			break;
		case 2:
			__info.name = "receiver";
			__info.type = UserID.class;
			break;
		case 3:
			__info.name = "subject";
			__info.type = String.class;
			break;
		case 4:
			__info.name = "userID";
			__info.type = UserID.class;
			break;
		}
	}

}
