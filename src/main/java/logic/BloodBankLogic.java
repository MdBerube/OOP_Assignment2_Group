package logic;

import common.ValidationException;
import dal.BloodBankDAL;
import entity.BloodBank;
import entity.Person;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.Date;

/**
 * this is a test. netbeans is crap!!
 * @author Mike Berube
 */
public class BloodBankLogic extends GenericLogic<BloodBank,BloodBankDAL>{
   
    // column names same as id and HTML element names 
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRIVATELY_OWNED = "privately_owned";
    public static final String ESTABLISHED = "established";
    public static final String OWNER_ID = "owner_id";
    public static final String EMPLOYEE_COUNT = "employee_count";
    
    BloodBankLogic(){
        super( new BloodBankDAL() );
    }
    
    @Override
    public List<BloodBank> getAll(){
        return get( () -> dal().findAll() );
    }
    
    @Override
    public BloodBank getWithId( int id ){
        return get( () -> dal().findById( id ));
    }
    
    public BloodBank getBloodBankWithName( String name ){
        return get( () -> dal().findByName( name ) );
    }
     public List<BloodBank> getBloodBankWithPrivatelyOwned( boolean privatelyOwned ){
        return get( () -> dal().findByPrivatelyOwned( privatelyOwned ));
    }
     public List<BloodBank> getBloodBankWithEstablished(Date established){
        return get( () -> dal().findByEstablished( established ));
    }
      public BloodBank getBloodBanksWithOwner(int ownerId){
        return get( () -> dal().findByOwner( ownerId ));
    }
    public List<BloodBank> getBloodBanksWithEmployeeCount(int count){
        return get( () -> dal().findByEmployeeCount( count ));
    }
    
    @Override
    public BloodBank createEntity(Map<String, String[]> parameterMap){
        // no logic classes in this method
        
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null");
        BloodBank entity = new BloodBank();
        
        // generate/set bank id value
        if( parameterMap.containsKey(ID) ){
            try{
                entity.setId( Integer.parseInt( parameterMap.get( ID ) [ 0 ] ) );
            } catch( java.lang.NumberFormatException ex ) {
                throw new ValidationException( ex );      
            }
        }
        
            // extract data from the map first
            // everything in map is string so must be converted
            String privatelyOwned = parameterMap.get( PRIVATELY_OWNED )[0];
            String established = parameterMap.get( ESTABLISHED )[0];
            //Date established = convertStringToDate();
            String bankName = parameterMap.get( NAME )[0];
            String employeeCount = parameterMap.get(EMPLOYEE_COUNT)[0];
   
           
           // validate data then set in entity
           validateBoolean().accept( privatelyOwned, 1);
           validateString().accept( established, 45);
           validateString().accept( bankName, 100);
           validateString().accept( employeeCount, 45);
          
           // 5/6 attributes set.
           entity.setPrivatelyOwned( convertInt(privatelyOwned) );
           // still doesn't check if date is correct tho..
           entity.setEstablished( convertStringToDate(established ));
           entity.setName( bankName );
           entity.setEmplyeeCount( Integer.parseInt(employeeCount) );
                   
                   
                   
        return entity;
    }
      
    @Override
    public List<?> extractDataAsList(BloodBank e) {
       return Arrays.asList(e.getOwner(),e.getPrivatelyOwned(), e.getEstablished(), e.getName(), e.getEmplyeeCount(), e.getId()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getColumnNames() {
      return Arrays.asList("owner_id","privately_owned","established","name","employee_count","id"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(OWNER_ID, PRIVATELY_OWNED, ESTABLISHED, NAME, EMPLOYEE_COUNT, ID ); //To change body of generated methods, choose Tools | Templates.
    }
    
    // checks string for correct length and empty spaces/null
    public ObjIntConsumer<String> validateString( ) {
        
            ObjIntConsumer<String> validator = (value,length) -> {
            if( value == null || value.trim().isEmpty() || value.length() > length ){
                String error = "";
                if( value == null || value.trim().isEmpty() ){
                    error = "value cannot be null or empty: " + value;
                }
                if( value.length() > length ){
                    error = "string length is " + value.length() + " > " + length;
                }
                throw new ValidationException( error );
                }
            };

      return validator;
    }
    
    /*
    // use regex?
    public ObjIntConsumer<Integer> validateInt() {
        ObjIntConsumer<Integer> validator = (value, length) -> {
            // logic here
            if(value == null  ){
                String error = "ValidateInt: Int is null";
            if(value == null || ){
                error = "";
            }
              if () {  
                }
                throw new ValidationException( error );
            }
        }; // lambda end
                return validator;
    }
*/
    
    // might need to check for spacing issues in converting from string to int earlier on
    public ObjIntConsumer<String> validateBoolean() {
        ObjIntConsumer<String> validator = (value, length) -> {
            // logic here
            if (value == null ){
                 String error = "validateBoolean: value cannot be null";
                if(value != "1" || value != "0"){
                error = "validateBoolean: value must be 1 or 0";
                }
                throw new ValidationException( error );
            }
        };
                return validator;
    }
    
    /*
    // checks date for correct formatting?
    public ObjIntConsumer<Date> validateDate() {
        ObjIntConsumer<Date> validator = (value, length) -> {
            // logic here
            
        };
                return validator;
    }
*/
    
    // converts bit(int) to boolean
    public boolean convertInt(String value) {
        boolean i = false;
        if (value  == "1") {
               i = true;
           } else {
               i = false;
           }
        return i;
    }
    
}

