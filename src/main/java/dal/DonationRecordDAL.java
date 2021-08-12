package dal;

import entity.DonationRecord;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationRecordDAL extends GenericDAL<DonationRecord> {
    DonationRecordDAL() {
        super(DonationRecord.class);
    }

    /*
    find all DonationRecords
    */
    @Override
    public List<DonationRecord> findAll() {
        return findResults( "DonationRecord.findAll", null );
    }

    /*
    find DonationRecord with id field of DonationRecord table
    */
    @Override
    public DonationRecord findById(int recordId) {
        Map<String, Object> map = new HashMap<>();
        map.put( "recordId", recordId );
        return findResult( "DonationRecord.findByRecordId", map );
    }

    /*
    find DonationRecord with tested field of DonationRecord table
    */
    public  List<DonationRecord> findByTested(boolean tested ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "tested", tested );
        return findResults( "DonationRecord.findByTested", map );
    }
    /*
    find DonationRecord with administrator field of DonationRecord table
    */
    public  List<DonationRecord> findByAdministrator( String administrator ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "administrator", administrator );
        return findResults( "DonationRecord.findByAdministrator", map );
    }
    /*
    find DonationRecord with hospital field of DonationRecord table
    */
    public  List<DonationRecord> findByHospital( String hospital ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "hospital", hospital );
        return findResults( "DonationRecord.findByHospital", map );
    }
    /*
    find DonationRecord with person_id field of DonationRecord table
    */
    public List<DonationRecord> findByPerson( int personId ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "personId", personId );
        return findResults( "DonationRecord.findByPerson", map );
    }
    /*
    find DonationRecord with donation_id field of DonationRecord table
    */
    public List<DonationRecord> findByDonation( int donationId ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "donationId", donationId );
        return findResults( "DonationRecord.findByDonation", map );
    }
    /*
    find DonationRecord with created field of DonationRecord table
    */
    public  List<DonationRecord> findByCreated( Date created ) {
        Map<String, Object> map = new HashMap<>();
        map.put( "created", created );
        return findResults( "DonationRecord.findByCreated", map );
    }

}

