package ca.mcgill.ecse321.cooperator.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CooperatorSystem{
   private Set<User> user;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<User> getUser() {
      return this.user;
   }
   
   public void setUser(Set<User> users) {
      this.user = users;
   }
   
   private Set<CoopTerm> coopTerm;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }
   
   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }
   
   private Set<Document> document;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Document> getDocument() {
      return this.document;
   }
   
   public void setDocument(Set<Document> documents) {
      this.document = documents;
   }
   
   private int systemId;

public void setSystemId(int value) {
    this.systemId = value;
}
@Id
public int getSystemId() {
    return this.systemId;
}
}
