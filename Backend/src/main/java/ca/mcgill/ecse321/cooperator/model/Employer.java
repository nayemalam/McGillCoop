package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Employer extends User{
   private int employerId;

public void setEmployerId(int value) {
    this.employerId = value;
}
@Id
public int getEmployerId() {
    return this.employerId;
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
