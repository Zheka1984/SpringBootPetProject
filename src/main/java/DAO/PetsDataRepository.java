/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Pets;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author nalog_ot01
 */
public interface PetsDataRepository extends JpaRepository<Pets, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO pettest (petname, petage, petdiagnosis, id) VALUES "
            + "(?1, ?2, ?3, (SELECT id FROM clienttest WHERE petowner=?4 and ownerphone=?5))", nativeQuery = true)
    public void addPetWithFK(String petname, String petage, String petdiagnosis,
            String petowner, String ownerphone);
    public ArrayList<Pets> findBypetName(String petName);
    public ArrayList<Pets> findBypetAge(String petAge);
    public ArrayList<Pets> findBypetDiagnosis(String petDiagnosis);
    @Query(value="Select * from pettest where petname=?1 and petage=?2 and petdiagnosis=?3 and "
            + "id=?4", nativeQuery = true)
    public ArrayList<Pets> findBypetNameAndPetAgeAndPetDiagnosisAndId
        (String petName, String petAge, String petDiagnosis, Long Id);
        @Transactional
  @Modifying
        @Query(value="DELETE FROM pettest T1\n" +
"    USING   pettest T2\n" +
"WHERE   T1.petid > T2.petid \n" +
"    AND T1.petdiagnosis  = T2.petdiagnosis\n" +
"    and T1.petname = T2.petname\n" +
"    and T1.petage = T2.petage", nativeQuery = true)
        public int deleteDuplicates();
        @Transactional
   @Modifying
        @Query(value = "Delete from pettest where petage=?1 and petdiagnosis=?2 and petname=&3", nativeQuery = true)
  public int deleteBypetAgeAndPetDiagnosisAndPetName(String petage, String petdiagnosis, String petname);
  @Transactional
 @Modifying
   @Query(value = "Delete from pettest where petage=?1", nativeQuery = true)
  public int deleteBypetAge(String petAge);
  @Transactional
 @Modifying
  @Query(value = "Delete from pettest where petdiagnosis=?1", nativeQuery = true)
  public int deleteBypetDiagnosis(String petDiagnosis);
  @Transactional
 @Modifying
  @Query(value = "Delete from pettest where petname=?1", nativeQuery = true)
  public int deleteBypetName(String petName);
  @Transactional
 @Modifying
  @Query(value = "Delete from pettest where petage=?1 and petdiagnosis=?2", nativeQuery = true)
  public int deleteBypetAgeAndpetDiagnosis(String petAge, String petDiagnosis);
   @Transactional
 @Modifying
  @Query(value = "Delete from pettest where petage=?1 and petname=?2", nativeQuery = true)
  public int deleteBypetAgeAndpetName(String petAge, String petName);
  @Transactional
 @Modifying
  @Query(value = "Delete from pettest where petdiagnosis=?1 and petname=?2", nativeQuery = true)
  public int deleteBypetDiagnosisAndpetName(String petDiagnosis, String petName);
  @Transactional
 @Modifying
  @Query(value = "Delete from pettest where petid=?1",  nativeQuery = true)
  public Integer deleteBypetId(int Id);
  @Query(value = "select petid from pettest where id=?1 and petage=?2 and petdiagnosis=?3 and petname=?4",  nativeQuery = true)
  public Integer extractPetId(int Id, String petage, String petdiagnosis, String petname);
 @Transactional
 @Modifying  
 @Query(value = "update pettest set petage=?1, petdiagnosis=?2, petname=?3 where petage=?4 and petdiagnosis=?5 and petname=&6" ,  nativeQuery = true)
 public Integer updateBypetAgeAndPetDiagnosisAndPetName(String newPetage, String newPetdiagnosis, String newPetname,
         String oldPetage, String oldPetdiagnosis, String oldPetname);
}
