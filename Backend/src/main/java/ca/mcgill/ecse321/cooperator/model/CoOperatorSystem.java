package ca.mcgill.ecse321.cooperator.model;

import Document;

import javax.persistence.OneToOne;
import CoopTerm;

import javax.persistence.Entity;
import User;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class CoOperatorSystem {
	private Set<Document> document;

	@OneToMany(mappedBy = "coOperatorSystem", cascade = { CascadeType.ALL })
	public Set<Document> getDocument() {
		return this.document;
	}

	public void setDocument(Set<Document> documents) {
		this.document = documents;
	}

	private Set<CoopTerm> coopTerm;

	@OneToMany(mappedBy = "coOperatorSystem", cascade = { CascadeType.ALL })
	public Set<CoopTerm> getCoopTerm() {
		return this.coopTerm;
	}

	public void setCoopTerm(Set<CoopTerm> coopTerms) {
		this.coopTerm = coopTerms;
	}

	private Set<User> user;

	@OneToMany(mappedBy = "coOperatorSystem", cascade = { CascadeType.ALL })
	public Set<User> getUser() {
		return this.user;
	}

	public void setUser(Set<User> users) {
		this.user = users;
	}

}
