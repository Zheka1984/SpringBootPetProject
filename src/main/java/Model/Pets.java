package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Model.Clients;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author nalog_ot01
 */
@Entity
@Table(name ="pettest")
public class Pets implements Serializable {
    
    public Pets(){
    }

    public Pets(String petName, String petAge, String petDiagnosis) {
        this.petName = petName;
        this.petAge = petAge;
        this.petDiagnosis = petDiagnosis;
    }
    
    @Id
    @Column(name = "petId", nullable=false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "petname", length = 64)
    private String petName;
    @Column(name = "petage", length = 3)
    private String petAge;
    @Column(name = "petdiagnosis", length = 150)
    private String petDiagnosis;

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clients client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetDiagnosis() {
        return petDiagnosis;
    }

    public void setPetDiagnosis(String petDiagnosis) {
        this.petDiagnosis = petDiagnosis;
    } 
}
