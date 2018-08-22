/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

/**
 *
 * @author Админ
 */
public class Form {
    public String petOwner = null;
    private String ownerPhone = null;
private String petName=null;
private Integer petAge = null;
private String petDiagnosis=null;
private Integer petId = null;
public boolean isPetWillChanged = false;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getPetAge() {
        return petAge;
    }

    public void setPetAge(Integer petAge) {
        this.petAge = petAge;
    }

    public String getPetDiagnosis() {
        return petDiagnosis;
    }

    public void setPetDiagnosis(String petDiagnosis) {
        this.petDiagnosis = petDiagnosis;
    }

    public String getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public boolean isIsPetWillChanged() {
        return isPetWillChanged;
    }

    public void setIsPetWillChanged(boolean isPetWillChanged) {
        this.isPetWillChanged = isPetWillChanged;
    }


    }
    
    

