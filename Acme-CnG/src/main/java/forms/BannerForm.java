
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class BannerForm {

	// Constructors -----------------------------------------------------------

	public BannerForm() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	url;


	@URL
	@NotBlank
	@NotNull
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}
