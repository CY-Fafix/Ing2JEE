package fr.cytech.profil;


import java.sql.Date;

import fr.cytech.utilisateur.Utilisateur;
import jakarta.persistence.*;

@Entity
public class Profil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//@Temporal(TemporalType.DATE)
    private Date dateDeNaissance;
	private String bio;
	@OneToOne
	private Utilisateur utilisateur;
	
    public Profil() {}

    public Profil(Date dateDeNaissance, String bio) {
        this.dateDeNaissance = dateDeNaissance;
        this.bio = bio;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

	
}
