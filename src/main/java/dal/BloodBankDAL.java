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
public class BloodBankDAL extends GenericDAL {
    
    // constructor for object
    public BloodBankDAL(){
        super(BloodBank.class);
    }
    
    public List<BloodBank> findAll(){
         return findResults( "BloodBank.findAll", null );
    }
    
    // not sure if cast is correct here?
    @Override
    public BloodBank findById(int bankId){
        Map<String, Object> map = new HashMap<>();
        map.put( "bank_id", bankId );
        return (BloodBank) findResult("BloodBank.findByBankId", map );
    }
    // not sure if cast is correct here?
    public BloodBank findByName(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put( "name", name);
        return (BloodBank) findResult("BloodBank.findByName", map );
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
    // not sure if cast should be here?
    public BloodBank findByOwner(int ownerId) {
        Map<String, Object> map = new HashMap<>();
        map.put( "owner", ownerId);
        return (BloodBank) findResult("BloodBank.findByOwner", map );
    }
    
    // bonus stuff 
    // public List<BloodBank> findContaining(String search) {
        // Map<String, Object> map = new HashMap<>();
      //   map.put( "established", search);
       //  return findResults("BloodBank.findByEstablished", map );
    // }
    
}
