package logic;

import common.EMFactory;
import common.TomcatStartUp;
import common.ValidationException;
import entity.Account;
import entity.DonationRecord;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import javax.persistence.EntityManager;
import static logic.DonationRecordLogic.ADMINISTRATOR;
import static logic.DonationRecordLogic.CREATED;
import static logic.DonationRecordLogic.HOSPITAL;
import static logic.DonationRecordLogic.TESTED;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ngoc Que Huong Tran
 */
public class DonationRecordLogicTest {
      private DonationRecordLogic logic;
    private DonationRecord expectedEntity;

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat( "/SimpleBloodBank", "common.ServletListener", "simplebloodbank-PU-test" );
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        TomcatStartUp.stopAndDestroyTomcat();
    }

    @BeforeEach
    final void setUp() throws Exception {

        logic = LogicFactory.getFor( "Donation Record" );
        /* **********************************
         * ***********IMPORTANT**************
         * **********************************/
        //we only do this for the test.
        //always create Entity using logic.
        //we manually make the account to not rely on any logic functionality , just for testing

        //get an instance of EntityManager
        EntityManager em = EMFactory.getEMF().createEntityManager();
        //start a Transaction
        em.getTransaction().begin();

        DonationRecord entity = new DonationRecord();
        entity.setId(1);
        entity.setTested( true );
        entity.setAdministrator( "Huong" );
        entity.setHospital( "Waverly Hills Sanatorium" );
        entity.setCreated(new Date());
        

        //add an account to hibernate, account is now managed.
        //we use merge instead of add so we can get the updated generated ID.
        expectedEntity = em.merge( entity);
        //commit the changes
        em.getTransaction().commit();
        //close EntityManager
        em.close();
    }

    @AfterEach
    final void tearDown() throws Exception {
        if( expectedEntity != null ){
            logic.delete( expectedEntity );
        }
    }

    @Test
    final void testGetAll() {
        //get all the accounts from the DB
        List<DonationRecord> list = logic.getAll();
        //store the size of list, this way we know how many accounts exits in DB
        int originalSize = list.size();

        //make sure account was created successfully
        assertNotNull( expectedEntity );
        //delete the new account
        logic.delete( expectedEntity );

        //get all accounts again
        list = logic.getAll();
        //the new size of accounts must be one less
        assertEquals( originalSize - 1, list.size() );
    }

    /**
     * helper method for testing all account fields
     *
     * @param expected
     * @param actual
     */
    private void assertDonationRecordEquals( DonationRecord expected, DonationRecord actual ) {
        //assert all field to guarantee they are the same
        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getTested(), actual.getTested() );
        assertEquals( expected.getAdministrator(), actual.getAdministrator() );
        assertEquals( expected.getHospital(), actual.getHospital() );
        assertEquals( expected.getCreated(), actual.getCreated() );
    }

    @Test
    final void testGetWithId() {
        //using the id of test account get another account from logic
        DonationRecord returnedDoantionRecord = logic.getWithId( expectedEntity.getId() );

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertDonationRecordEquals( expectedEntity, returnedDoantionRecord );
    }

    @Test
    final void testGetDonationRecordWithAdministrator() {
        int foundFull = 0;
        List<DonationRecord> returnedDoantionRecord = logic.getDonationRecordWithAdministrator(expectedEntity.getAdministrator() );
        
        for( DonationRecord donationRecord: returnedDoantionRecord ) {
  
            assertEquals( expectedEntity.getAdministrator(),donationRecord.getAdministrator());
            //exactly one account must be the same
            if( donationRecord.getId().equals( expectedEntity.getId() ) ){
                assertDonationRecordEquals( expectedEntity, donationRecord );
                foundFull++;
            }
        //the two accounts (testAcounts and returnedAccounts) must be the same
       assertEquals(1, foundFull, "if zero means not found, if more than one means duplicate");
        }
    }

    @Test
    final void testGetDonationRecordWithTested() {
          int foundFull = 0;
          List<DonationRecord> returnedDoantionRecord = logic.getDonationRecordWithTested( expectedEntity.getTested() );
          for( DonationRecord donationRecord: returnedDoantionRecord ) {
  
            assertEquals( expectedEntity.getTested(),donationRecord.getTested());
            //exactly one account must be the same
            if( donationRecord.getId().equals( expectedEntity.getId() ) ){
                assertDonationRecordEquals( expectedEntity, donationRecord );
                foundFull++;
            }
        //the two accounts (testAcounts and returnedAccounts) must be the same
       assertEquals(1, foundFull, "if zero means not found, if more than one means duplicate");
        }
    }

    @Test
    final void testGetDonationRecordWithHospital() {
        int foundFull = 0;
        List<DonationRecord> returnedDoantionRecord = logic.getDonationRecordWithHospital( expectedEntity.getHospital() );
        for( DonationRecord donationRecord: returnedDoantionRecord ) {
  
            assertEquals( expectedEntity.getHospital(),donationRecord.getHospital());
            //exactly one account must be the same
            if( donationRecord.getId().equals( expectedEntity.getId() ) ){
                assertDonationRecordEquals( expectedEntity, donationRecord );
                foundFull++;
            }
        }
        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertEquals(1, foundFull, "if zero means not found, if more than one means duplicate");
    }

    @Test
    final void testGetDonationRecordsWithCreated() {
        int foundFull = 0;
        List<DonationRecord> returnedDoantionRecord = logic.getDonationRecordsWithCreated( expectedEntity.getCreated() );
        for( DonationRecord donationRecord: returnedDoantionRecord ) {    
            //all accounts must have the same password
            assertEquals( expectedEntity.getCreated(), donationRecord.getCreated() );
            //exactly one account must be the same
            if( donationRecord.getId().equals( expectedEntity.getId() ) ){
                assertDonationRecordEquals( expectedEntity, donationRecord );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }

    @Test
    final void testCreateEntity() {
                Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.ID, new String[]{ Integer.toString(expectedEntity.getId()) } );
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString(expectedEntity.getTested())  } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ expectedEntity.getAdministrator() } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ expectedEntity.getHospital() } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ logic.convertDateToString(expectedEntity.getCreated()) } );

        DonationRecord returnedDonationRecord = logic.createEntity( sampleMap );

        assertDonationRecordEquals( expectedEntity, returnedDonationRecord );
    }

    @Test
    final void testCreateEntityNullAndEmptyValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( DonationRecordLogic.ID, new String[]{ Integer.toString(expectedEntity.getId()) } );
            map.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString(expectedEntity.getTested()) } );
            map.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ expectedEntity.getAdministrator() } );
            map.put( DonationRecordLogic.HOSPITAL, new String[]{ expectedEntity.getHospital() } );
            map.put( DonationRecordLogic.CREATED, new String[]{ expectedEntity.getCreated().toString() } );
        };

        //idealy every test should be in its own method
        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ID, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ID, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.TESTED, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.TESTED, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
    }

    @Test
    final void testCreateEntityBadLengthValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( DonationRecordLogic.ID, new String[]{ Integer.toString(expectedEntity.getId()) } );
            map.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString(expectedEntity.getTested()) } );
            map.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ expectedEntity.getAdministrator() } );
            map.put( DonationRecordLogic.HOSPITAL, new String[]{ expectedEntity.getHospital() } );
            map.put( DonationRecordLogic.CREATED, new String[]{ expectedEntity.getCreated().toString() } );
        };

        IntFunction<String> generateString = ( int length ) -> {
            //https://www.baeldung.com/java-random-string#java8-alphabetic
            //from 97 inclusive to 123 exclusive
            return new Random().ints( 'a', 'z' + 1 ).limit( length )
                    .collect( StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append )
                    .toString();
        };

        //idealy every test should be in its own method
        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ID, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ID, new String[]{ "12b" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.TESTED, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.TESTED, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.ADMINISTRATOR, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.HOSPITAL, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( DonationRecordLogic.CREATED, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( DonationRecordLogic.CREATED, new String[]{ generateString.apply( 46 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
    }

    @Test
    final void testCreateEntityEdgeValues() {
        IntFunction<String> generateString = ( int length ) -> {
            //https://www.baeldung.com/java-random-string#java8-alphabetic
            return new Random().ints( 'a', 'z' + 1 ).limit( length )
                    .collect( StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append )
                    .toString();
        };

        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ generateString.apply( 1 ) } );

        //idealy every test should be in its own method
        DonationRecord returnedDonationRecord = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( AccountLogic.ID )[ 0 ] ), returnedDonationRecord.getId() );
        assertEquals( sampleMap.get( DonationRecordLogic.TESTED )[ 0 ], returnedDonationRecord.getTested() );
        assertEquals( sampleMap.get( DonationRecordLogic.ADMINISTRATOR )[ 0 ], returnedDonationRecord.getAdministrator() );
        assertEquals( sampleMap.get( DonationRecordLogic.HOSPITAL)[ 0 ], returnedDonationRecord.getHospital() );
        assertEquals( sampleMap.get( DonationRecordLogic.CREATED )[ 0 ], returnedDonationRecord.getCreated() );

        sampleMap = new HashMap<>();
        sampleMap.put( DonationRecordLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( DonationRecordLogic.TESTED, new String[]{ Boolean.toString( false ) } );
        sampleMap.put( DonationRecordLogic.ADMINISTRATOR , new String[]{ generateString.apply( 100 ) } );
        sampleMap.put( DonationRecordLogic.HOSPITAL, new String[]{ generateString.apply( 100 ) } );
        sampleMap.put( DonationRecordLogic.CREATED, new String[]{ generateString.apply( 19 ) } );

        //idealy every test should be in its own method
        returnedDonationRecord = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( DonationRecordLogic.ID )[ 0 ] ), returnedDonationRecord.getId() );
        assertEquals( sampleMap.get( DonationRecordLogic.TESTED )[ 0 ], returnedDonationRecord.getTested() );
        assertEquals( sampleMap.get( DonationRecordLogic.ADMINISTRATOR )[ 0 ], returnedDonationRecord.getAdministrator() );
        assertEquals( sampleMap.get( DonationRecordLogic.HOSPITAL )[ 0 ], returnedDonationRecord.getHospital() );
        assertEquals( sampleMap.get( DonationRecordLogic.CREATED )[ 0 ], returnedDonationRecord.getCreated() );
    }

    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        assertEquals( Arrays.asList( "ID", "TESTED", "ADMINISTRATOR", "HOSPITAL", "CREATED" ), list );
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        assertEquals( Arrays.asList( DonationRecordLogic.ID, DonationRecordLogic.TESTED, DonationRecordLogic.ADMINISTRATOR, DonationRecordLogic.HOSPITAL, DonationRecordLogic.CREATED ), list );
    }

    @Test
    final void testExtractDataAsList() {
        List<?> list = logic.extractDataAsList( expectedEntity );
        assertEquals( expectedEntity.getId(), list.get( 0 ) );
        assertEquals( expectedEntity.getTested(), list.get( 1 ) );
        assertEquals( expectedEntity.getAdministrator(), list.get( 2 ) );
        assertEquals( expectedEntity.getHospital(), list.get( 3 ) );
        assertEquals( expectedEntity.getCreated(), list.get( 4 ) );
    }
}
