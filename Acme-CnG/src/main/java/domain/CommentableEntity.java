
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public abstract class CommentableEntity extends DomainEntity {

	//Constructor ----------------------------------------------------

	public CommentableEntity() {
		super();

	}


	//Relationships ---------------------------------------------------

	private Collection<Comment>	commentsReceived;


	@Valid
	@OneToMany(mappedBy = "commentableEntity")
	public Collection<Comment> getCommentsReceived() {
		return this.commentsReceived;
	}

	public void setCommentsReceived(final Collection<Comment> commentsReceived) {
		this.commentsReceived = commentsReceived;
	}

}
