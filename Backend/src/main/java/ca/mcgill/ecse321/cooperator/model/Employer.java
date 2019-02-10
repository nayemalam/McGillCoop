package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Employer extends User{
private String companyName;

public void setCompanyName(String value) {
this.companyName = value;
}
public String getCompanyName() {
return this.companyName;
}
private Integer employerId;

public void setEmployerId(Integer value) {
this.employerId = value;
}
public Integer getEmployerId() {
return this.employerId;
}
private String location;

public void setLocation(String value) {
this.location = value;
}
public String getLocation() {
return this.location;
}
   private Set<CoopTerm> coopTerm;
   
   @OneToMany(mappedBy="employer" )
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }
   
   }
