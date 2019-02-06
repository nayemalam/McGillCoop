import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import java.util.Set;
import javax.persistence.Entity;

@Entity
public class Student extends User {
	private String mcGillId;

	public void setMcGillId(String value) {
		this.mcGillId = value;
	}

	public String getMcGillId() {
		return this.mcGillId;
	}

	private Set<CoopTerm> coopTerm;

	@OneToMany(mappedBy = "student", cascade = { CascadeType.ALL })
	public Set<CoopTerm> getCoopTerm() {
		return this.coopTerm;
	}

	public void setCoopTerm(Set<CoopTerm> coopTerms) {
		this.coopTerm = coopTerms;
	}

	private Statistics statistics;

	@OneToOne(mappedBy = "student", cascade = { CascadeType.ALL }, optional = false)
	public Statistics getStatistics() {
		return this.statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	private Set<CoopAdministrator> coopAdministrator;

	@ManyToMany
	public Set<CoopAdministrator> getCoopAdministrator() {
		return this.coopAdministrator;
	}

	public void setCoopAdministrator(Set<CoopAdministrator> coopAdministrators) {
		this.coopAdministrator = coopAdministrators;
	}

}
