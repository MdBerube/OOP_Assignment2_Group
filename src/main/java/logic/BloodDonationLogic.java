
package logic;

import common.ValidationException;
import dal.BloodDonationDAL;
import entity.BloodDonation;
import entity.BloodGroup;
import entity.RhesusFactor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohammad Abou Haibeh
 */
public class BloodDonationLogic extends GenericLogic<BloodDonation, BloodDonationDAL> {
    
    public static final String BANK_ID = "bank_id";
    public static final String MILLILITERS = "milliliters";
    public static final String BLOOD_GROUP = "blood_group";
    public static final String RHESUS_FACTOR = "rhesus_factor";
    public static final String CREATED = "created";
    public static final String ID = "id";
 
    BloodDonationLogic() {
        super( new BloodDonationDAL() );
    }
    
    @Override
    public List<BloodDonation> getAll() {
        return get( () -> dal().findAll()); 
    }
    
    @Override
    public BloodDonation getWithId(int id) {
        return get( () -> dal().findById(id)); 
    }
    
    public List<BloodDonation> getBloodDonationWithMilliliters(int milliliters) {
        return get( () -> dal().findByMilliliters(milliliters));
    }
    
    public List<BloodDonation> getBloodDonationWithBloodGroup(BloodGroup bloodGroup) {
        return get( () -> dal().findByBloodGroup(bloodGroup));
    }
    
    public List<BloodDonation> getBloodDonationWithCreated(Date created) {
        return get( () -> dal().findByCreated(created));
    }
    
    public List<BloodDonation> getBloodDonationWithRhd(RhesusFactor rhd) {
        return get( () -> dal().findByRhd(rhd));
    }
    
     public List<BloodDonation> getBloodDonationWithBloodBank(int bankId) {
          return get( () -> dal().findByBloodBank(bankId));
    }
     
    @Override
    public BloodDonation createEntity(Map<String, String[]> parameterMap) {
        Objects.requireNonNull(parameterMap, "parameterMap cannot be null"); 
        
        
        BloodDonation entity = new BloodDonation();
        
   
        if (parameterMap.containsKey(ID)) {
            try {
                entity.setId(Integer.parseInt(parameterMap.get(ID)[0]));
            }
            catch (java.lang.NumberFormatException ex) {
                throw new ValidationException(ex);
            }
        }
         BloodGroup bloodGroup = BloodGroup.valueOf(parameterMap.get(BLOOD_GROUP)[0]);
        int milliliters = Integer.parseInt(parameterMap.get(MILLILITERS)[0]);
        RhesusFactor rhd = RhesusFactor.getRhesusFactor(parameterMap.get(RHESUS_FACTOR)[0]); 
        Date created = convertStringToDate(parameterMap.get(CREATED)[0]);
        
        entity.setBloodGroup(bloodGroup);
        entity.setCreated(created);
        entity.setMilliliters(milliliters);
        entity.setRhd(rhd);
        
        return entity;
    }
 
     
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList ("ID", "Milliliters", "Blood Group", "Rhesus Factor", "Bank ID", "Created");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, MILLILITERS, BLOOD_GROUP, RHESUS_FACTOR, BANK_ID, CREATED);
    }

    @Override
    public List<?> extractDataAsList(BloodDonation e) {
         return Arrays.asList(e.getId(), e.getMilliliters(), e.getBloodGroup(), e.getRhd(), e.getBloodBank(), e.getCreated());
    }
    
 
    
}