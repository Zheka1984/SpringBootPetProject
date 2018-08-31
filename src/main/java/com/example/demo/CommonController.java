/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import DAO.ClientsDataInterface;
import DAO.PetsDataRepository;
import Model.Clients;
import Model.Pets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author nalog_ot01
 */
@Controller
public class CommonController {

    @Autowired
    private ClientsDataInterface clientInterface;
    @Autowired
    private PetsDataRepository petInterface;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
     @GetMapping("/")
   public String list() {
       clientInterface.deleteRepeated();
       petInterface.deleteDuplicates();
       return "index";
   }
 @GetMapping("/enter")
  public String enter(Model model) {
      InputForm form = new InputForm();
      model.addAttribute("form", form);
       return "enter";
   }
    
    @PostMapping("/enter") 
    public String add(@ModelAttribute Clients client, @ModelAttribute("form")
            @Valid InputForm form, BindingResult bindingResult, Model model) {
String k1 = null; 
String k2 = null; 
String k3 = null;
if(form.getPetAge1()!=null)
    k1=Integer.toString(form.getPetAge1());
if(form.getPetAge2()!=null)
    k2=Integer.toString(form.getPetAge2());
if(form.getPetAge3()!=null)
    k3=Integer.toString(form.getPetAge3());
        ArrayList<Clients> clients;
        Clients clientDB;
        Pets pet1 = null;
        Pets pet2 = null;
        Pets pet3 = null; 
         if(!isFormIsEmpty(form, 1))
        pet1 = new Pets(form.getPetName1(), k1, form.getPetDiagnosis1());
          if(!isFormIsEmpty(form, 2))
        pet2 = new Pets(form.getPetName2(), Integer.toString(form.getPetAge2()), form.getPetDiagnosis2());
          if(!isFormIsEmpty(form, 3))
        pet3 = new Pets(form.getPetName3(), Integer.toString(form.getPetAge3()), form.getPetDiagnosis3());
        if (!bindingResult.hasErrors()) {
           
               //если клиент есть
               if (!isClientIsEmpty(form)){
                        if(!isFormIsEmpty(form, 1))
                        petInterface.addPetWithFK(form.getPetName1(), k1,
                                form.getPetDiagnosis1(), client.getPetOwner(), client.getOwnerPhone());
                        if(!isFormIsEmpty(form, 2))
                        petInterface.addPetWithFK(form.getPetName2(), k2,
                                form.getPetDiagnosis2(), client.getPetOwner(), client.getOwnerPhone());
                        if(!isFormIsEmpty(form, 3))
                        petInterface.addPetWithFK(form.getPetName3(), k3,
                                form.getPetDiagnosis3(), client.getPetOwner(), client.getOwnerPhone());
            }
        
        //если клиента нет
         if (isClientIsEmpty(form)){
             if(isFormIsEmpty(form, model)==0){
             if(!isFormIsEmpty(form, 1)){
            pet1.setPetName(form.getPetName1());
            pet1.setPetAge(k1);
            pet1.setPetDiagnosis(form.getPetDiagnosis1());
            pet1.setClient(client);
            client.getHs().add(pet1);}
             if(!isFormIsEmpty(form, 2)){                 
            pet2.setPetName(form.getPetName2());
            pet2.setPetAge(k2);
            pet2.setPetDiagnosis(form.getPetDiagnosis2());
            pet2.setClient(client);
            client.getHs().add(pet2);}
             if(!isFormIsEmpty(form, 3)){
            pet3.setPetName(form.getPetName3());
            pet3.setPetAge(k3);
            pet3.setPetDiagnosis(form.getPetDiagnosis3());
            pet3.setClient(client);
            client.getHs().add(pet3);}
            
            clientInterface.save(client);
         }
                    }
         return "redirect:/";
         }
        if(isFormIsEmpty(form, model)==1)
         return "enter";
    return "enter";
         }
    @GetMapping("/find")
    public String startFind(Model model){
      Form form1 = new Form();
      model.addAttribute("form1", form1);  
      return "find";  
    }
    @PostMapping("/find")
    public String find(@ModelAttribute("form1") Form form, Model model){
        String petSql = findPets(form);
        String clientSql = findClients(form);
       List<Pets> petList = jdbcTemplate.query(petSql, new PetsMapper());
       List<Clients> clientList = jdbcTemplate.query(clientSql, new ClientsMapper());
       model.addAttribute("petList", petList);
       model.addAttribute("clientList", clientList);
        return "find";
    }
    @GetMapping("/clientDel")
    public String deleteClient(Model model){
         Form form1 = new Form();
      model.addAttribute("formDel", form1);
        return "deleteClient";
    }
    @PostMapping("/clientDel")
    public String deleteClient(@ModelAttribute("formDel") Form form, Model model){
       String clientSql = findClients(form);      
        List<Clients> clientList = jdbcTemplate.query(clientSql, new ClientsMapper());
       model.addAttribute("clientList", clientList);
       model.addAttribute("client", form);
        return "deleteClient";
    }
    @PostMapping("/deleteClient")
    public String clientDeleted(@ModelAttribute("client") Form form){
        System.out.println(form.getOwnerPhone()+" "+form.getPetOwner());
        if(form.getPetOwner()!=null&form.getOwnerPhone()!=null)
            clientInterface.deleteBypetOwnerAndOwnerPhone(form.getPetOwner(), form.getOwnerPhone());
        if(form.getPetOwner()==null&form.getOwnerPhone()!=null)
            clientInterface.deleteByOwnerPhone(form.getOwnerPhone());
        if(form.getPetOwner()!=null&form.getOwnerPhone()==null)
            clientInterface.deleteBypetOwner(form.getPetOwner());
        return "redirect:/";
    }
    @GetMapping("/petDel")
    public String deletePet(Model model){
        Form form = new Form();
        Pets pet = new Pets();
      model.addAttribute("formDel", form);
      model.addAttribute("pet2", form);
      model.addAttribute("pet1", form);
      model.addAttribute("petdel", pet);
      model.addAttribute("pet", pet);
        return "deletePet"; 
    }
    @PostMapping("/petDel")
    public String deletePet(@ModelAttribute("formDel") Form form, Model model){
        String petSql = findPets(form);
        List<Pets> petList = jdbcTemplate.query(petSql, new PetsMapper());
         String clientSql = findClients(form);      
        List<Clients> clientList = jdbcTemplate.query(clientSql, new ClientsMapper());
        model.addAttribute("petList", petList);
       model.addAttribute("pet1", form);
     model.addAttribute("clientList", clientList);
        return "deletePet";
    }
    @PostMapping("/deletePet")
    public String petDeleted(@ModelAttribute("petdel") Pets pet){
        System.out.println(petInterface.extractPetId(pet.getId().intValue(), pet.getPetAge(), pet.getPetDiagnosis(), pet.getPetName()));
        petInterface.deleteBypetId(petInterface.extractPetId(pet.getId().intValue(), pet.getPetAge(), pet.getPetDiagnosis(), pet.getPetName()));
      return "redirect:/";  
    }
    @PostMapping("/deleteAllPets")
    public String deleteAllPets(@ModelAttribute("pet2") Form form){
        if(form.getPetId()==null){
            System.out.println(form.getPetName()+" "+form.getPetDiagnosis()+" "+form.getPetAge());
            System.out.println("hello");
        if(form.getPetAge()!=null&form.getPetDiagnosis()!=""&form.getPetName()!="")
            petInterface.deleteBypetAgeAndPetDiagnosisAndPetName(Integer.toString(form.getPetAge()), 
                    form.getPetDiagnosis(), form.getPetName());
        System.out.println("hello1");
        if(form.getPetAge()!=null&form.getPetDiagnosis()!=""&form.getPetName()=="")
            petInterface.deleteBypetAgeAndpetDiagnosis(Integer.toString(form.getPetAge()), form.getPetDiagnosis());
        System.out.println("hello2");
        if(form.getPetAge()!=null&form.getPetDiagnosis()==""&form.getPetName()!="")
            petInterface.deleteBypetAgeAndpetName(Integer.toString(form.getPetAge()), form.getPetName());
        System.out.println("hello3");
        if(form.getPetAge()==null&form.getPetDiagnosis()!=""&form.getPetName()!="")
            petInterface.deleteBypetDiagnosisAndpetName(form.getPetDiagnosis(), form.getPetName());
        System.out.println("hello4");
        if(form.getPetAge()!=null&form.getPetDiagnosis()==""&form.getPetName()=="")
            petInterface.deleteBypetAge(Integer.toString(form.getPetAge()));
        System.out.println("hello5");
        if(form.getPetAge()==null&form.getPetDiagnosis()!=""&form.getPetName()=="")
            petInterface.deleteBypetDiagnosis(form.getPetDiagnosis());
            System.out.println("hello6");
        if(form.getPetAge()==null&form.getPetDiagnosis()==""&form.getPetName()!="")
            petInterface.deleteBypetName(form.getPetName());
        }
        if(form.getPetId()!=null){
            petInterface.deleteById(form.getPetId().longValue());
        }
        return "redirect:/";
    }
    @GetMapping("/changePet")
    public String changePet(Model model){
        Form form = new Form();
       OldNewPets onp = new OldNewPets(); 
        model.addAttribute("pet1", form);
       model.addAttribute("pet2", form);
       model.addAttribute("petChange", onp);
       model.addAttribute("formChange", form);
       model.addAttribute("oldform", form);
       model.addAttribute("allPets", onp);
       return "changePet";
    }
    @PostMapping("/petsForChange")
    public String petsForChange(@ModelAttribute("formChange") Form form, Model model){
        OldNewPets onp = new OldNewPets();
        String petSql = findPets(form);
        List<Pets> petList = jdbcTemplate.query(petSql, new PetsMapper());
         String clientSql = findClients(form);      
        List<Clients> clientList = jdbcTemplate.query(clientSql, new ClientsMapper());
        model.addAttribute("petList", petList);
       model.addAttribute("pet1", form);
     model.addAttribute("clientList", clientList);
     model.addAttribute("allPets", onp);
     model.addAttribute("petChange", onp);
        return "changePet"; 
    }
    @PostMapping("/changeAllPets")
    public String changeAllPets(@ModelAttribute("allPets") OldNewPets form, Model model){
        String message = "нечего изменять";
       String sql = "UPDATE pettest SET ";
       String sql1 = "", sql2 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "";
       if(form.getNewPetName()!="")
           sql1 = "petname='"+form.getNewPetName()+"'";
       if(form.getNewPetName()!=""&form.getPetAge()!=null)
           sql2 = ", petage='"+form.getNewPetAge()+"'";
        if(form.getNewPetName()==""&form.getPetAge()!=null)
           sql2 = "petage='"+form.getNewPetAge()+"'";
        if(form.getNewPetDiagnosis()!=""&((form.getNewPetAge()!=null|form.getNewPetName()!="")
                |(form.getNewPetAge()!=null&form.getNewPetName()!="")))
            sql3 = ", petdiagnosis='"+form.getNewPetDiagnosis()+"'";
         if(form.getNewPetDiagnosis()!=""&form.getNewPetName()==""&form.getNewPetAge()==null)
             sql3 = "petdiagnosis='"+form.getNewPetDiagnosis()+"'";

        if(form.getPetName()!="")
            sql4 = " where petname='"+form.getPetName()+"'";
        if(form.getPetName()!=""&form.getPetAge()!=null)
            sql5 = " and petage='"+form.getPetAge()+"'";
        if(form.getPetName()==""&form.getPetAge()!=null)
            sql5 = " where petage='"+form.getPetAge()+"'";
        if(form.getPetDiagnosis()!=""&((form.getNewPetName()!=""|form.getPetAge()!=null)&(form.getPetName()!=""&
                form.getPetAge()!=null)))
           sql6 = " and petdiagnosis='"+form.getPetDiagnosis()+"'"; 
        if(form.getPetDiagnosis()!=""&form.getPetName()==""&form.getPetAge()==null)
            sql6 = " where petdiagnosis='"+form.getPetDiagnosis()+"'";
        if(form.getNewPetDiagnosis()==""&form.getNewPetName()==""&form.getNewPetAge()==null) {
            System.out.println("нахуй");
            System.out.println(sql1+sql2+sql3+sql4+sql5+sql6);
            model.addAttribute("message", message);  
            return "index";}
       sql = sql + sql1 + sql2 + sql3 + sql4 + sql5 + sql6;
       System.out.println(sql);
       jdbcTemplate.execute(sql);
       message = "изменения внесены";
       model.addAttribute("message", message);      
     return "index";
    }
    @PostMapping("/changePet")
    public String changePet(@ModelAttribute("petChange") OldNewPets form, Model model){
        String message = "животное изменено";
      String sql = "Update pettest set ";
      String sql1 = "", sql2 = "", sql3 = "", sql4 = " where petid="+petInterface.extractPetId(form.getPetId(), 
              Integer.toString(form.getPetAge()), form.getPetDiagnosis(), form.getPetName());
      if(form.getNewPetName()!=""){
          sql1 = "petname='"+form.getNewPetName()+"'";
      if(form.getNewPetAge()!=null)
          sql2 = ", petage="+form.getNewPetAge();}
      else if(form.getNewPetAge()!=null)
          sql2 = "petage="+form.getNewPetAge();
      if(form.getNewPetDiagnosis()!="")
          if((form.getNewPetName()!=""&form.getNewPetAge()!=null)||(form.getNewPetName()!=""||form.getNewPetAge()!=null))
              sql3 = ", petdiagnosis='"+form.getNewPetDiagnosis()+"'";
      if(form.getNewPetDiagnosis()!=""&&form.getNewPetName()==""&&form.getNewPetAge()!=null)
          sql3 = "petdiagnosis='"+form.getNewPetDiagnosis()+"'";
      if(form.getNewPetDiagnosis()==""&form.getNewPetName()==""&form.getNewPetAge()==null){
          message = "нечего менять";
          model.addAttribute("message", message);
        return "index";   
      }
       sql = sql + sql1 + sql2 + sql3 + sql4;
        System.out.println(sql);
       jdbcTemplate.execute(sql);
      model.addAttribute("message", message);
        return "index"; 
    }
    @GetMapping("/changeClient")
    public String changeClient(Model model){
         Form form = new Form();
       OldNewClients onc = new OldNewClients(); 
        model.addAttribute("client1", form);
       model.addAttribute("client2", form);
       model.addAttribute("clientChange", onc);
       model.addAttribute("formChange", form);
       model.addAttribute("oldform", form);
       model.addAttribute("allClients", onc);
       return "changeClient";
    }
    @PostMapping("/clientsForChange")
    public String clientsForChange(@ModelAttribute("formChange") Form form, Model model){
        OldNewClients onc = new OldNewClients();
        String petSql = findPets(form);
        List<Pets> petList = jdbcTemplate.query(petSql, new PetsMapper());
         String clientSql = findClients(form);      
        List<Clients> clientList = jdbcTemplate.query(clientSql, new ClientsMapper());
        model.addAttribute("petList", petList);
       model.addAttribute("client1", form);
     model.addAttribute("clientList", clientList);
     model.addAttribute("allClients", onc);
     model.addAttribute("clientChange", onc);
        return "changeClient"; 
    }
    @PostMapping("/changeAllClients")
    public String changeAllClients(@ModelAttribute("allClients") OldNewClients form, Model model){
         String message = "нечего изменять";
       String sql = "UPDATE clienttest SET ";
       String sql1 = "", sql2 = "", sql3 = "", sql4 = "";
       if(form.getNewOwnerPhone()!=""){
           sql1 = "ownerphone='"+form.getNewOwnerPhone()+"'";
       if(form.getNewPetOwner()!="")
           sql2 = ", petowner='"+form.getNewPetOwner()+"'";}
       if (form.getNewOwnerPhone()==""&&form.getNewPetOwner()!="")
           sql2 = "petowner='"+form.getNewPetOwner()+"'";
       if (form.getOwnerPhone()!=""){
           sql3 = " where ownerphone='"+form.getOwnerPhone()+"'";
           if(form.getPetOwner()!="")
               sql4 = " and petowner='"+form.getPetOwner()+"'";
       }
       if(form.getOwnerPhone()==""&&form.getPetOwner()!="")
           sql4 = " where petowner='"+form.getPetOwner()+"'";
       if(form.getOwnerPhone()==""&&form.getPetOwner()==""){
           model.addAttribute("message", message);
       }
        message = "клиент изменен";
        model.addAttribute("message", message);    
       sql = sql + sql1 + sql2 + sql3 + sql4;
       jdbcTemplate.execute(sql);
      model.addAttribute("message", message);
        return "index";      
    }
    @PostMapping("/changeClient")
    public String changeClient(@ModelAttribute("clientChange") OldNewClients form, Model model){
        
    }
    public boolean isClientIsEmpty(InputForm form){
        if (clientInterface.findBypetOwnerAndOwnerPhone(form.getPetOwner(), form.getOwnerPhone()).isEmpty())
                return true;
        return false;
    }
    public boolean isPetIsEmpty(Pets pet){
        if(petInterface.findBypetAge(pet.getPetAge()).isEmpty()&&petInterface.findBypetDiagnosis(pet.getPetDiagnosis()).isEmpty()
                &&petInterface.findBypetName(pet.getPetName()).isEmpty())
            return true;
        return false;
    }
    public boolean isFormIsEmpty(InputForm form, int i){
        switch(i){
            case 1:  if(!form.getPetName1().isEmpty()&form.getPetAge1()!=null&!form.getPetDiagnosis1().isEmpty())
            return false;
            break;
             case 2:  if(!form.getPetName2().isEmpty()&form.getPetAge2()!=null&!form.getPetDiagnosis2().isEmpty())
            return false; 
             break;
              case 3:  if(!form.getPetName3().isEmpty()&form.getPetAge3()!=null&!form.getPetDiagnosis3().isEmpty())
            return false; 
             break;
        }
       
        return true;  
    }
    public int isFormIsEmpty(InputForm form, Model model){
        int k = 0;
        if(form.getPetName1().isEmpty())
            k++;
        if(form.getPetAge1() == null)
            k++;
        if(form.getPetDiagnosis1().isEmpty())
            k++;
        if(k>0&&k<3){
            model.addAttribute("msg", "Надо бы заполнить все три поля для каждого зверя");
        return 1;
        }
         k = 0;
        if(form.getPetName1().isEmpty())
            k++;
        if(form.getPetAge1() == null)
            k++;
        if(form.getPetDiagnosis1().isEmpty())
            k++;
        if(k>0&&k<3){
            model.addAttribute("msg", "Надо бы заполнить все три поля для каждого зверя");
        return 1;}
         k = 0;
        if(form.getPetName1().isEmpty())
            k++;
        if(form.getPetAge1() == null)
            k++;
        if(form.getPetDiagnosis1().isEmpty())
            k++;
        if(k>0&&k<3){
            model.addAttribute("msg", "Надо бы заполнить все три поля для каждого зверя");
        return 1;
        }
        return 0;
    }
    public String findPets(Form form){        
        String query = "Select * from pettest";
        String queryPetName = "";
        String queryPetAge = "";
        String queryPetDiagnosis = "";
        if (form.getPetName()!=null){
            queryPetName = " where petname like "+"'%"+form.getPetName()+"%'";
            if(form.getPetAge()!=null){
               queryPetAge=" and petage="+"'"+form.getPetAge()+"'";}
            if(form.getPetDiagnosis()!=null){
                queryPetDiagnosis=" and petdiagnosis like "+"'%"+form.getPetDiagnosis()+"%'";}
        }
        if(form.getPetName()==null&form.getPetAge()!=null){
          queryPetAge = " where petage='"+form.getPetAge()+"'";  }
           if(form.getPetAge()!=null){
             if(form.getPetDiagnosis()!=null){
                 queryPetDiagnosis=" and petdiagnosis like "+"'%"+form.getPetDiagnosis()+"%'";}}
           if(form.getPetAge()==null&form.getPetName()==null){
             if(form.getPetDiagnosis()!=null)
            queryPetDiagnosis =  " where petdiagnosis like "+"'%"+form.getPetDiagnosis()+"%'";}
        query = query+queryPetName+queryPetAge+queryPetDiagnosis;   
       return query;
    }
    public String findClients(Form form){
      String query = "Select * from clienttest";
      String petOwner = "";
      String ownerPhone = "";
      if(form.getPetOwner()!=null)
          petOwner=" where petowner like '%"+form.getPetOwner()+"'";
      if(form.getPetOwner()!=null&form.getOwnerPhone()!=null)
          ownerPhone=" and ownerphone like '%"+form.getOwnerPhone()+"'";
      if(form.getPetOwner()==null&form.getOwnerPhone()!=null)
          ownerPhone=" where ownerphone like '%"+form.getOwnerPhone()+"'";
      return query+petOwner+ownerPhone;
    }
}
class PetsMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
      Pets pet = new Pets();
      pet.setId(rs.getLong("id"));
      pet.setPetAge(rs.getString("petage"));
      pet.setPetDiagnosis(rs.getString("petdiagnosis"));
      pet.setPetName(rs.getString("petname"));
      return pet;
    }
    
}
class ClientsMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Clients client = new Clients();
        client.setId(rs.getLong("id"));
        client.setOwnerPhone(rs.getString("ownerphone"));
        client.setPetOwner(rs.getString("petowner"));
        return client;
    }  
}