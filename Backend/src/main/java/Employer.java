import javax.persistence.OneToMany;
import java.util.Set;
import javax.persistence.Entity;

@Entity
public class Employer extends User {
	private Set<CoopTerm> coopTerm;

	@OneToMany(mappedBy = "employer")
	public Set<CoopTerm> getCoopTerm() {
		return this.coopTerm;
	}

	public void setCoopTerm(Set<CoopTerm> coopTerms) {
		this.coopTerm = coopTerms;
	}

	private String employerId;

	public void setEmployerId(String value) {
		this.employerId = value;
	}

	public String getEmployerId() {
		return this.employerId;
	}

}
