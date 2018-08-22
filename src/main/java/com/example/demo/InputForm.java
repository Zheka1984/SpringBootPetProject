/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 *
 * @author nalog_ot01
 */

public class InputForm {
    @Pattern(regexp = "^[А-Яа-яЁё\\s-*]+$", message = "Введите имя русскими буквами")
    private String petOwner;
    @Pattern(regexp = "^\\+?\\d{1}[-]?\\d{3}[-]?\\d{3}[-]?\\d{2}[-]?\\d{2}$", message = "Телефон должен состоять из 11 цифр")
    private String ownerPhone;
    @Pattern(regexp = "^[А-Яа-яЁё\\s-*]*$", message = "Введите имя русскими буквами")
    private String petName1;
    @Min(1)
    private Integer petAge1 = null;
    @Pattern(regexp = "^[А-Яа-яЁё\\s-*]*$", message = "Введите имя русскими буквами")
    private String petName2;
    @Min(1)
    private Integer petAge2 = null;
    @Pattern(regexp = "^[А-Яа-яЁё\\s-*]*$", message = "Введите имя русскими буквами")
    private String petName3;
    @Min(1)
    private Integer petAge3 = null;
    
    private String petDiagnosis1;
    
    private String petDiagnosis2;
    
    private String petDiagnosis3;

    public String getPetDiagnosis1() {
        return petDiagnosis1;
    }

    public void setPetDiagnosis1(String petDiagnosis1) {
        this.petDiagnosis1 = petDiagnosis1;
    }

    public String getPetDiagnosis2() {
        return petDiagnosis2;
    }

    public void setPetDiagnosis2(String petDiagnosis2) {
        this.petDiagnosis2 = petDiagnosis2;
    }

    public String getPetDiagnosis3() {
        return petDiagnosis3;
    }

    public void setPetDiagnosis3(String petDiagnosis3) {
        this.petDiagnosis3 = petDiagnosis3;
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

    public String getPetName1() {
        return petName1;
    }

    public void setPetName1(String petName1) {
        this.petName1 = petName1;
    }

    public Integer getPetAge1() {
        return petAge1;
    }

    public void setPetAge1(Integer petAge1) {
        this.petAge1 = petAge1;
    }

    public String getPetName2() {
        return petName2;
    }

    public void setPetName2(String petName2) {
        this.petName2 = petName2;
    }

    public Integer getPetAge2() {
        return petAge2;
    }

    public void setPetAge2(Integer petAge2) {
        this.petAge2 = petAge2;
    }

    public String getPetName3() {
        return petName3;
    }

    public void setPetName3(String petName3) {
        this.petName3 = petName3;
    }

    public Integer getPetAge3() {
        return petAge3;
    }

    public void setPetAge3(Integer petAge3) {
        this.petAge3 = petAge3;
    }
}
