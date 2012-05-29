package de.fhworms.tawk;

/**
 * 
 * @author frank
 * 
 */
public class ListItem {

	private String message, author, subject;

	/**
	 * This constructor requires three Strings.
	 * 
	 * @param msg
	 *            the message text
	 * @param user
	 *            the author of this message
	 * @param subj
	 *            the subject line(s)
	 */
	public ListItem(String msg, String user, String subj) {
		setMessage(msg);
		setAuthor(user);
		setSubject(subj);
	}

	public String getMessage() {
		return (message==null)?"":message;
	}

	public void setMessage(String message) {
		this.message = (message==null)?"":message;
	}

	public void setSubject(String subj) {
		this.subject = (subj==null)?"":subj;
	}

	public String getSubject() {
		return (subject==null)?"":subject;
	}

	public String getAuthor() {
		return (author==null)?"":author;
	}

	public void setAuthor(String author) {
		this.author = (author==null)?"":author;
	}

}