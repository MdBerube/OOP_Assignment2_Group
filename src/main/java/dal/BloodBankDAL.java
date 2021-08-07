package dal;

import entity.BloodBank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 *  BloodBank data access layer class extends genericDAL class
 * @author Mike Berube
 */
public class BloodBankDAL extends GenericDAL<BloodBank> {
    
    // constructor for object
    public BloodBankDAL(){
        super(BloodBank.class);
    }
    
    @Override
    public List<BloodBank> findAll(){
         return findResults( "BloodBank.findAll", null );
    }
   
    @Override
    public BloodBank findById(int bankId){
        Map<String, Object> map = new HashMap<>();
        map.put( "bank_id", bankId );
        return findResult("BloodBank.findByBankId", map );
    }
    
    public BloodBank findByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put( "name", name);
        return  findResult("BloodBank.findByName", map );
    }
    
    public List<BloodBank> findByPrivatelyOwned(boolean privatelyOwned) {
        Map<String, Object> map = new HashMap<>();
        map.put( "privately_owned", privatelyOwned);
        return findResults("BloodBank.findByPrivatelyOwned", map );
    }
    
    public List<BloodBank> findByEstablished(Date established) {
        Map<String, Object> map = new HashMap<>();
        map.put( "established", established);
        return findResults("BloodBank.findByEstablished", map );
        
    }
    public List<BloodBank> findByEmployeeCount(int employeeCount) {
        Map<String, Object> map = new HashMap<>();
        map.put( "emplyee_count", employeeCount);
        return findResults("BloodBank.findByEmplyeeCount", map );
    }
    
    public BloodBank findByOwner(int ownerId) {
        Map<String, Object> map = new HashMap<>();
        map.put( "owner", ownerId);
        return findResult("BloodBank.findByOwner", map );
    }
    
    // bonus stuff 
    // public List<BloodBank> findContaining(String search) {
        // Map<String, Object> map = new HashMap<>();
      //   map.put( "established", search);
       //  return findResults("BloodBank.findByEstablished", map );
    // }
    
}
