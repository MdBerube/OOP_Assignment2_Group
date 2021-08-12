package logic;

import common.ValidationException;
import dal.AccountDAL;
import dal.DonationRecordDAL;
import entity.Account;
import entity.DonationRecord;

import java.util.*;
import java.util.function.ObjIntConsumer;

/**
 *
 * @author Ngoc Que Huong Tran
 */
public class DonationRecordLogic extends GenericLogic<DonationRecord, DonationRecordDAL> {

    public static final String PERSON_ID = "person_id";
    public static final String DONATION_ID = "donation_id";
    public static final String TESTED = "tested";
    public static final String ADMINISTRATOR = "administrator";
    public static final String HOSPITAL = "hospital";
    public static final String CREATED = "created";
    public static final String ID = "id";

    DonationRecordLogic() {
        super( new DonationRecordDAL() );
    }

    @Override
    public List<DonationRecord> getAll() {
        return get( () -> dal().findAll() );
    }

    @Override
    public DonationRecord getWithId( int id ) {
        return get( () -> dal().findById( id ) );
    }

    public List<DonationRecord> getDonationRecordWithTested(boolean tested) {
        return get(() ->dal().findByTested(tested));
    }
    
    public List<DonationRecord> getDonationRecordWithAdministrator(String administrator) {
        return get(() -> dal().findByAdministrator(administrator));
    }
    public  List<DonationRecord> getDonationRecordWithHospital(String username) {
        return get(() -> dal().findByHospital(username)); 
    }
    
    public List<DonationRecord> getDonationRecordsWithCreated(Date created) {
        return get(() -> dal().findByCreated(created));
    }
    
    public  List<DonationRecord> getDonationRecordsWithPerson(int person) {
        return get(() -> dal().findByPerson(person));
    }
    
    public  List<DonationRecord> getDonationRecordsWithDonation(int donationId) {
        return get(() -> dal().findByDonation(donationId));
    } 

    @Override
    public DonationRecord createEntity( Map<String, String[]> parameterMap ) {

        Objects.requireNonNull( parameterMap, "parameterMap cannot be null" );

        DonationRecord entity = new DonationRecord();

        //ID is generated, so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        //the only time that we will have id is for update behaviour.
        if( parameterMap.containsKey( ID ) ){
            try {
                entity.setId( Integer.parseInt( parameterMap.get( ID )[ 0 ] ) );
            } catch( NumberFormatException ex ) {
                throw new ValidationException( ex );
            }
        }
        

        Integer personId = Integer.valueOf( parameterMap.get( PERSON_ID )[ 0 ]);
        Integer donationId = Integer.valueOf(parameterMap.get( DONATION_ID )[ 0 ]);
        Boolean tested = Boolean.valueOf(parameterMap.get( TESTED )[ 0 ]);
        String administrator = parameterMap.get( ADMINISTRATOR )[ 0 ];
        String hospital = parameterMap.get( HOSPITAL )[ 0 ];

        entity.setAdministrator(administrator);
        entity.setId(donationId);
        entity.setCreated(new Date());
        entity.setHospital(hospital);
        entity.setTested(tested);
        entity.setId(personId);
        
        
        
        return entity;
    }


   @Override
    public List<String> getColumnNames() {
        return Arrays.asList( "Record ID", "Person ID", "Donation ID", "Tested", "Adminstrator", "Hospital", "Create");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID,PERSON_ID,DONATION_ID, TESTED, ADMINISTRATOR, HOSPITAL, CREATED);
    }

    @Override
    public List<?> extractDataAsList(DonationRecord e) {
        return Arrays.asList( e.getId(), e.getPerson(), e.getBloodDonation(), e.getTested(), e.getAdministrator(), e.getHospital(), e.getCreated());
    }
}
