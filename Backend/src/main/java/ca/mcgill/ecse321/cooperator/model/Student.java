package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Student extends User{
   private CoopAdministrator coopAdministrator;
   
   @ManyToOne(optional=false)
   public CoopAdministrator getCoopAdministrator() {
      return this.coopAdministrator;
   }
   
   public void setCoopAdministrator(CoopAdministrator coopAdministrator) {
      this.coopAdministrator = coopAdministrator;
   }
   
   private Statistics statistics;
   
   @OneToOne(mappedBy="student" , cascade={CascadeType.ALL}, optional=false)
   public Statistics getStatistics() {
      return this.statistics;
   }
   
   public void setStatistics(Statistics statistics) {
      this.statistics = statistics;
   }
   
   private Set<CoopTerm> coopTerm;
   
   @OneToMany(mappedBy="student" , cascade={CascadeType.ALL})
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }
   
   private int studenId;

public void setStudenId(int value) {
    this.studenId = value;
}
public int getStudenId() {
    return this.studenId;
}
}
