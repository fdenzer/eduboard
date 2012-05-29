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

public final class User extends SoapObject {
	private String fullName;
	private String imageURL;
	private String shortName;

	private UserID userID;

	public User() {
		super("", "");
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageURL() {
		return this.imageURL;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setUserID(UserID userID) {
		this.userID = userID;
	}

	public UserID getUserID() {
		return this.userID;
	}

	public int getPropertyCount() {
		return 4;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return fullName;
		case 1:
			return imageURL;
		case 2:
			return shortName;
		case 3:
			return userID;
		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			fullName = (__obj != null) ? __obj.toString() : null;
			break;
		case 1:
			imageURL = (__obj != null) ? __obj.toString() : null;
			break;
		case 2:
			shortName = (__obj != null) ? __obj.toString() : null;
			break;
		case 3:
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
			__info.name = "fullName";
			__info.type = String.class;
			break;
		case 1:
			__info.name = "imageURL";
			__info.type = String.class;
			break;
		case 2:
			__info.name = "shortName";
			__info.type = String.class;
			break;
		case 3:
			__info.name = "userID";
			__info.type = UserID.class;
			break;
		}
	}

}
