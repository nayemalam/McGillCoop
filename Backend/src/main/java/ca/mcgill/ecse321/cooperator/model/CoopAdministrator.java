package ca.mcgill.ecse321.cooperator.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopAdministrator extends User{
   private int coopAdminId;

public void setCoopAdminId(int value) {
    this.coopAdminId = value;
}
@Id
public int getCoopAdminId() {
    return this.coopAdminId;
}
   
   }
