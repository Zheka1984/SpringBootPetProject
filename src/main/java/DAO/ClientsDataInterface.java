package DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Model.Clients;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author nalog_ot01
 */

public interface ClientsDataInterface extends JpaRepository <Clients, Long> {
  public ArrayList<Clients> findBypetOwnerAndOwnerPhone(String petOwner, String ownerPhone);
  public ArrayList<Clients> findBypetOwner(String petOwner);
  public ArrayList<Clients> findByOwnerPhone(String ownerPhone);
@Transactional
   @Modifying
  @Query(value = "Delete from clienttest where petowner=?1 and ownerphone=?2", nativeQuery = true)
  public int deleteBypetOwnerAndOwnerPhone(String petOwner, String ownerPhone);
@Transactional
   @Modifying
  @Query(value = "Delete from clienttest where petowner=?1", nativeQuery = true)
  public int deleteBypetOwner(String petOwner);
@Transactional
 @Modifying
  @Query(value = "Delete from clienttest where ownerphone=?1", nativeQuery = true)
  public int deleteByOwnerPhone(String ownerPhone);
  @Transactional
  @Modifying
  @Query(value = "Delete from clienttest where id=?1", nativeQuery = true)
  public int deleteById(long id);
  @Transactional
  @Modifying
  @Query(value = "delete from clienttest where id not in (select max(dup.id)\n" +
"from clienttest as dup group by dup.petowner, dup.ownerphone) ", nativeQuery = true)
  public int deleteRepeated();
}
