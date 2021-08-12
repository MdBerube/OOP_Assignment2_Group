package logic;

import common.ValidationException;
import dal.DonationRecordDAL;
import entity.DonationRecord;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;

/**
 *
 * @author Ngoc Que Huong Tran
 */
public class DonationRecordLogic extends GenericLogic<DonationRecord, DonationRecordDAL> {
    
    public static final String PERSON_ID = "person_id";
    public static final String DONATION_ID = "donation_id";
    public static final String TESTED = "tested";
    public static final String ADMINSTRATOR = "adminstrator";
    public static final String HOSPITAL = "hospital";
    public static final String CREATED = "created";
    public static final String ID = "id";

    DonationRecordLogic(DonationRecordDAL dal) {
        super(dal);
    }
    
    @Override
    public List<DonationRecord> getAll() {
        return get(() -> dal().findAll());
    }

    @Override
    public DonationRecord getWithId(int id) {
        return get(() -> dal().findById(id));
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
    public DonationRecord createEntity( Map<String, String[]> parameterMap) {
        Objects.requireNonNull( parameterMap, "parameterMap cannot be null");
        DonationRecord entity = new DonationRecord();
        
        if (parameterMap.containsKey(ID)) {
            try {
                entity.setId(Integer.parseInt(parameterMap.get(ID)[0]));
            }
            catch (java.lang.NumberFormatException ex) {
                throw new ValidationException(ex);
            }
        }
        
        ObjIntConsumer< String> validator = ( value, length ) -> {
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
        
        String personId  = parameterMap.get(PERSON_ID)[0];
        String donationId = parameterMap.get(DONATION_ID)[0];
        String tested = parameterMap.get(TESTED)[0];
        String adminstrator = parameterMap.get(ADMINSTRATOR)[0];
        String hospital = parameterMap.get(HOSPITAL)[0];
        String created = parameterMap.get(CREATED)[0];
        String id = parameterMap.get(ID)[0];
        
        validator.accept(personId, 10);
        validator.accept(donationId, 10);
        validator.accept(tested, 1);
        validator.accept(adminstrator, 100);
        validator.accept(hospital, 100);
        validator.accept(created, 19);
        validator.accept(id, 10);
        
        entity.setPerson(personId);
        entity.setBloodDonation(donationId);
        entity.setTested(tested);
        entity.setAdministrator(adminstrator);
        entity.setHospital(hospital);
        entity.setCreated(created);
        entity.setId(id);
        
        return entity;
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "Person ID", "Donation ID", "Tested", "Adminstrator", "Hospital", "Create", "Record ID");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(PERSON_ID,DONATION_ID, TESTED, ADMINSTRATOR, HOSPITAL, CREATED, ID);
    }

    @Override
    public List<?> extractDataAsList(DonationRecord e) {
        return Arrays.asList(e.getPerson(), e.getBloodDonation(), e.getTested(), e.getAdministrator(), e.getHospital(), e.getCreated(), e.getId());
    }
  
}
