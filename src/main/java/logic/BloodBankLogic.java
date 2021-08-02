package logic;

import common.ValidationException;
import dal.BloodBankDAL;
import entity.BloodBank;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.Date;

/**
 *
 * @author Mike Berube
 */
public class BloodBankLogic extends GenericLogic<BloodBank,BloodBankDAL>{
    
    // should be constants?
    public static final String OWNER_ID = "owner_id";
    public static final String PRIVATELY_OWNED = "privately_owned";
    public static final String ESTABLISHED = "established";
    public static final String NAME = "name";
    public static final String EMPLOYEE_COUNT = "employee_count";
    public static final String ID = "id";
    
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
        
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null");
        BloodBank entity = new BloodBank();
        
        if( parameterMap.containsKey(ID) ){
            try{
                entity.setId( Integer.parseInt( parameterMap.get( ID ) [ 0 ] ) );
            } catch( java.lang.NumberFormatException ex ) {
                throw new ValidationException( ex );      
            }
        }
            
            ObjIntConsumer< String > validator = ( value, length ) -> {
                if ( value == null || value.trim().isEmpty() || value.length() > length ) {
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
            
            String displayname = null;
            if( parameterMap.containsKey(this) ){
                displayname = parameterMap.get( )[ 0 ];
                validator.accept( displayname, 45 );
            }
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
    
}

