
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends CommentableEntity {

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}


	//Attributes ---------------------------------------------------------------------

	private String	fullName;
	private String	email;
	private String	phone;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^\\+?\\d{1,3}?[- .]?\\d+$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}


	//Relationships --------------------------------------------------------------------------------------

	private Collection<PrivateMessage>	sendedMessages;
	private Collection<PrivateMessage>	recivedMessages;
	private Collection<Comment>	commentsSent;
	private UserAccount			userAccount;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<Comment> getCommentsSent() {
		return this.commentsSent;
	}

	public void setCommentsSent(final Collection<Comment> commentsSent) {
		this.commentsSent = commentsSent;
	}

	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<PrivateMessage> getSendedMessages() {
		return this.sendedMessages;
	}

	public void setSendedMessages(final Collection<PrivateMessage> sendedMessages) {
		this.sendedMessages = sendedMessages;
	}

	@Valid
	@OneToMany(mappedBy = "recipient")
	public Collection<PrivateMessage> getRecivedMessages() {
		return this.recivedMessages;
	}

	public void setRecivedMessages(final Collection<PrivateMessage> recivedMessages) {
		this.recivedMessages = recivedMessages;
	}

}
