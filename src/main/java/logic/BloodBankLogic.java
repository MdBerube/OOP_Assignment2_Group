package logic;

/**
 *
 * @author Mike Berube
 */
public class BloodBankLogic {
    
    // should be constants?
    public String OWNER_ID = "owner_id";
    public String PRIVATELY_OWNED = "privately_owned";
    public String ESTABLISHED = "established";
    public String NAME = "name";
    public String EMPLOYEE_COUNT = "employee_count";
    public String ID = "id";
    
    private BloodBankLogic(){
        
    }
    public List<BloodBank> getAll(){
        return null;
    }
    public BloodBank getWithId(int id){
        return null;
    }
    public List<BloodBank> getBloodBankWithName(String name){
        return null;
    }
     public List<BloodBank> getBloodBankWithPrivatelyOwned(boolean privatelyOwned){
        return null;
    }
     public List<BloodBank> getBloodBankWithEstablished(Date established){
        return null;
    }
      public BloodBank getBloodBanksWithOwner(int ownerId){
        return null;
    }
    public List<BloodBank> getBloodBanksWithEmployeeCount(int count){
        return null;
    }
    public BloodBank createEntity(Map<String, String[]> parameterMap){
        return null;
    }
    public List<String> getColumnNames(){
        return null;
    }
    public List<String> getColumnCodes(){
        return null;
    }
    public List<?> extractDateAsList(BloodBank e){
        return null;
    }
}

