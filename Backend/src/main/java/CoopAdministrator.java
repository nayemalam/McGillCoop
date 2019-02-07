import javax.persistence.ManyToMany;
import java.util.Set;
import javax.persistence.Entity;

@Entity
public class CoopAdministrator extends User {
	private String administratorID;

	private void setAdministratorID(String value) {
		this.administratorID = value;
	}

	private String getAdministratorID() {
		return this.administratorID;
	}

	private Set<Student> student;

	@ManyToMany(mappedBy = "coopAdministrator")
	public Set<Student> getStudent() {
		return this.student;
	}

	public void setStudent(Set<Student> students) {
		this.student = students;
	}

}
