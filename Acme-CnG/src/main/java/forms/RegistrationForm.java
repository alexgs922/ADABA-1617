
package forms;

import javax.persistence.Column;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import utilities.validators.PasswordMatches;

@PasswordMatches
public class RegistrationForm {

	// Constructors -----------------------------------------------------------

	public RegistrationForm() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	username;
	private String	password;
	private String	passwordCheck;

	private boolean	termsOfUse;

	private String	fullName;
	private String	email;
	private String	phone;

	@NotNull
	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	@Size(min = 5, max = 32)
	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	@AssertTrue
	public boolean getTermsOfUse() {
		return termsOfUse;
	}

	public void setTermsOfUse(boolean termsOfUse) {
		this.termsOfUse = termsOfUse;
	}

	@NotBlank
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		this.fullName = name;
	}


	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^\\+?\\d{1,3}?[- .]?\\d+$")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
