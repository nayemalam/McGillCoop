package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class CooperatorSystem {
   private Set<SystemUser> user;

   @OneToMany(cascade = { CascadeType.ALL })
   public Set<SystemUser> getUser() {
      return this.user;
   }

   public void setUser(Set<SystemUser> users) {
      this.user = users;
   }

   private Set<CoopTerm> coopTerm;

   @OneToMany(cascade = { CascadeType.ALL })
   public Set<CoopTerm> getCoopTerm() {
      return this.coopTerm;
   }

   public void setCoopTerm(Set<CoopTerm> coopTerms) {
      this.coopTerm = coopTerms;
   }

   private Set<Document> document;

   @OneToMany(cascade = { CascadeType.ALL })
   public Set<Document> getDocument() {
      return this.document;
   }

   public void setDocument(Set<Document> documents) {
      this.document = documents;
   }

   private Integer systemId;

   public void setSystemId(Integer value) {
      this.systemId = value;
   }

   @Id
   public Integer getSystemId() {
      return this.systemId;
   }

   private Integer currentUserCount=0;

   public void setCurrentUserCount(Integer value) {
      this.currentUserCount = value;
   }

   public Integer getCurrentUserCount() {
      return this.currentUserCount;
   }

}
