import javax.persistence.Entity;

@Entity
public enum DocName {
private String finalReport;

	public void setFinalReport(String value) {
		this.finalReport = value;
	}

	public String getFinalReport() {
		return this.finalReport;
	}
}
