package com.example.demo;

import DAO.ClientsDataInterface;
import DAO.PetsDataRepository;
import Model.Clients;
import Model.Pets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class DemoApplicationTests {
    CommonController cc = new CommonController();
     @Autowired
    private JdbcTemplate jdbcTemplate;
     @Autowired
    private ClientsDataInterface clientInterface;
     @Autowired
     private PetsDataRepository petInterface;

	@Test
        @Ignore
	public void contextLoads() {
             Clients client = new Clients();
            client.setPetOwner("Petya");
            client.setOwnerPhone("+79012010000");
            Pets pet = new Pets();
            pet.setClient(client);
            pet.setPetAge("5");
            pet.setPetDiagnosis("сдох");
            pet.setPetName("Бобик");
            client.getHs().add(pet);
            pet.setClient(client);
clientInterface.save(client);

assertTrue(true);
	}
        @Test
        @Ignore
        public void testInterface(){
           ArrayList<Clients> cl = clientInterface.findByOwnerPhone("+78213330010");
           String name = cl.get(0).getPetOwner();
           assertEquals("Петр Морковкин", name);
        }
       @Test
        @Ignore
        public void testSearch(){
            ArrayList<Pets> pets = petInterface.findBypetNameAndPetAgeAndPetDiagnosisAndId
        (null, "13", "слепота", 214L);
            System.out.println(pets.get(0).getPetName());
            assertTrue(true);
}
      @Test
      @Ignore
        public void testSql(){
            Form form = new Form();
//            form.setPetName("блоха Наташа");
//            form.setPetDiagnosis("вывих шестой лапы");
            form.setPetAge(1);
System.out.println(form.getPetName()+" "+form.getPetAge()+" "+form.getPetDiagnosis());
         String sql = cc.findPets(form);
          System.out.println(sql);
        List<Pets> list = jdbcTemplate.query(sql, new PetsMapper());
    for (Pets row : list) {
            System.out.println(row.getPetName());
        }
    assertTrue(true);
        } 
        @Ignore
         @Test
         public void testDelSql(){
          petInterface.deleteBypetName("Венька");
                  
         
          assertTrue(true);
         }
}