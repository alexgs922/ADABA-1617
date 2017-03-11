
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class CommentableEntity extends DomainEntity {

	//Constructor ----------------------------------------------------

	public CommentableEntity() {
		super();

	}


	//Relationships ---------------------------------------------------

	private Collection<Comment>	comments;


	@Valid
	@OneToMany(mappedBy = "commentableEntity")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

}
