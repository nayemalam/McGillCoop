package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Employer extends User{
   private Set<CoopTerm> coopTerm;
   
   @OneToMany(mappedBy="employer" )
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }
   
   private int employerId;

public void setEmployerId(int value) {
    this.employerId = value;
}
public int getEmployerId() {
    return this.employerId;
}
}
