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

/**
 *@author M. Brandt 
 */
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public final class TawkRequest {
	private final static String SOAPURL = "http://unit4.ztt.fh-worms.de:8080/tawk/services/TawkServiceService";
	public final static String NAMESPACE = "http://tawk.fhworms.de";
	public final static String KEY = "TawkService";

	private TawkRequest() {
	}

	/**
	 * Gets the user object for a given user ID
	 * 
	 * @param sessionToken
	 *            the current session token
	 * @param userID
	 *            the user ID to look up
	 * @return the user object
	 * @throws Exception
	 */
	public static User getUser(SessionID sessionToken, UserID userID)
			throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "getUser");
		object.addProperty("sId", sessionToken);
		object.addProperty("userID", userID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/getUser", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		SoapObject ret = (SoapObject) envelope.getResponse();
		User response = new User();
		for (int i = 0; i < ret.getPropertyCount(); i++) {
			response.setProperty(i, ret.getProperty(i));
		}

		return response;
	}

	/**
	 * Logout current user
	 * 
	 * @param sessionToken
	 *            the current session token
	 * @throws Exception
	 */
	public static void logout(SessionID sessionToken) throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "logout");
		object.addProperty("sessionToken", sessionToken);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/logout", envelope);
	}

	/**
	 * Gets the user object for the logged-in user
	 * 
	 * @param sessionToken
	 *            the current session token
	 * @return the user object
	 * @throws Exception
	 */
	public static User getCurrentUser(SessionID sessionToken) throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "getCurrentUser");
		object.addProperty("sId", sessionToken);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/getCurrentUser", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		SoapObject ret = (SoapObject) envelope.getResponse();
		User response = new User();
		for (int i = 0; i < ret.getPropertyCount(); i++) {
			response.setProperty(i, ret.getProperty(i));
		}

		return response;
	}

	/**
	 * Find users
	 * 
	 * @param sessionToken
	 *            the current session token
	 * @param search
	 *            the search string
	 * @return an array of user ids
	 * @throws Exception
	 */
	public static UserID[] findUser(SessionID sessionToken, String search)
			throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "findUser");
		object.addProperty("sessionToken", sessionToken);
		object.addProperty("regEx", search);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/findUser", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		/*
		 * ksoap2 return a SoapObject on 1 result and a List for multiple
		 * results.
		 */
		if (envelope.getResponse().getClass().toString()
				.equals(SoapObject.class.toString())) {
			SoapObject ret = (SoapObject) envelope.getResponse();

			UserID[] response = new UserID[1];
			response[0] = new UserID();

			for (int i = 0; i < ret.getPropertyCount(); i++) {
				response[0].setProperty(i, ret.getProperty(i));
			}

			return response;
		} else {
			@SuppressWarnings("rawtypes")
			List list = (List) envelope.getResponse();

			if (list == null)
				return null;

			UserID[] response = new UserID[list.size()];
			int i = 0;
			for (Object obj : list) {
				SoapObject soapObject = (SoapObject) obj;
				response[i] = new UserID();
				for (int j = 0; j < soapObject.getPropertyCount(); j++) {
					response[i].setProperty(j, soapObject.getProperty(j));
				}
				i++;
			}

			return response;
		}
	}

	/**
	 * Gets the message IDS for all messages
	 * 
	 * @param sessionID
	 *            the current session ID
	 * @return array of message IDs
	 * @throws Exception
	 */
	public static String[] getMessageIDs(SessionID sessionID) throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "getMessageIDs");
		object.addProperty("sessionID", sessionID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/getMessageIDs", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		/*
		 * ksoap2 return a primitive (String) on 1 result and a List for
		 * multiple results.
		 */
		if (envelope.getResponse().getClass().toString()
				.equals(SoapPrimitive.class.toString())) {

			String[] response = new String[1];
			response[0] = envelope.getResponse().toString();

			return response;
		} else {
			@SuppressWarnings("rawtypes")
			List list = (List) envelope.getResponse();

			if (list == null)
				return null;

			String[] response = new String[list.size()];
			int i = 0;
			for (Object message : list) {
				response[i++] = (message != null) ? message.toString() : null;
			}

			return response;
		}
	}

	/**
	 * Get messages which are older than the given message
	 * 
	 * @param sessionID
	 *            the current session ID
	 * @param lastKnownMessageID
	 *            the ID of the last known message
	 * @return array of message IDs
	 * @throws Exception
	 */
	public static String[] getMoreMessageIDs(SessionID sessionID,
			String lastKnownMessageID) throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "getMoreMessageIDs");
		object.addProperty("sessionID", sessionID);
		object.addProperty("lastKnownMessageID", lastKnownMessageID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/getMoreMessageIDs", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		/*
		 * ksoap2 return a primitive (String) on 1 result and a List for
		 * multiple results.
		 */
		if (envelope.getResponse().getClass().toString()
				.equals(SoapPrimitive.class.toString())) {

			String[] response = new String[1];
			response[0] = envelope.getResponse().toString();

			return response;
		} else {
			@SuppressWarnings("rawtypes")
			List list = (List) envelope.getResponse();

			if (list == null)
				return null;

			String[] response = new String[list.size()];
			int i = 0;
			for (Object message : list) {
				response[i++] = (message != null) ? message.toString() : null;
			}

			return response;
		}
	}

	/**
	 * Check if newer messages are available
	 * 
	 * @param sessionID
	 *            the current session ID
	 * @param latestMessageID
	 *            the last known message ID
	 * @return boolean expression if newer messages are available or not
	 * @throws Exception
	 */
	public static boolean checkForNewerMessages(SessionID sessionID,
			MessageID latestMessageID) throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "checkForNewerMessages");
		object.addProperty("sessionID", sessionID);
		object.addProperty("latestMessageID", latestMessageID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/checkForNewerMessages", envelope);

		return envelope.getResponse().toString().equals("true");
	}

	/**
	 * Gets the message object for the given message ID
	 * 
	 * @param sessionId
	 *            the current session ID
	 * @param messageID
	 *            the message ID of the message to get
	 * @return the message object for the given message ID
	 * @throws Exception
	 */
	public static Message getMessage(SessionID sessionId, MessageID messageID)
			throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "getMessage");
		object.addProperty("sessionId", sessionId);
		object.addProperty("messageID", messageID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/getMessage", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		SoapObject ret = (SoapObject) envelope.getResponse();
		Message response = new Message();
		for (int i = 0; i < ret.getPropertyCount(); i++) {
			response.setProperty(i, ret.getProperty(i));
		}

		return response;
	}

	/**
	 * Sends a message to the server
	 * 
	 * @param sessionId
	 *            the current session token
	 * @param message
	 *            the message to send
	 * @return the message id of the new message
	 * @throws Exception
	 */
	public static MessageID sendMessage(SessionID sessionId, Message message)
			throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "sendMessage");
		object.addProperty("sessionId", sessionId);
		object.addProperty("message", message);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/sendMessage", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		SoapObject ret = (SoapObject) envelope.getResponse();
		MessageID response = new MessageID();
		response.setMessageID(ret.getPropertyAsString(0));

		return response;
	}

	/**
	 * Authenticate user (log in)
	 * 
	 * @param username
	 *            the user's name
	 * @param password
	 *            the user's password
	 * @return the session id for the given user
	 * @throws Exception
	 */
	public static SessionID authenticate(String username, String password)
			throws Exception {
		SoapObject object = new SoapObject(NAMESPACE, "authenticate");
		object.addProperty("username", username);
		object.addProperty("password", password);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(object);

		HttpTransportSE transport = new HttpTransportSE(SOAPURL);
		transport.call(NAMESPACE + "/authenticate", envelope);

		if (envelope.bodyIn == null)
			return null;

		if (envelope.getResponse() == null)
			return null;

		SoapObject ret = (SoapObject) envelope.getResponse();
		SessionID response = new SessionID();
		for (int i = 0; i < ret.getPropertyCount(); i++) {
			response.setProperty(i, ret.getProperty(i));
		}

		return response;
	}

}
