package logic;

import common.EMFactory;
import common.TomcatStartUp;
import common.ValidationException;
import entity.Account;
import entity.Person;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author chamd
 */
public class PersonLogicTest {
    
    private PersonLogic logic;
    private Person expectedEntity;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    
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

        logic = LogicFactory.getFor( "Person" );
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

        Person entity = new Person();
        entity.setFirstName("Chamali");
        entity.setLastName("Gorakanage");
        entity.setPhone("1111111111");
        entity.setAddress("1385 Woodroffe Ave");
        entity.setBirth(format.parse("2021-07-21 15:16:46.0"));

        //add an account to hibernate, account is now managed.
        //we use merge instead of add so we can get the updated generated ID.
        expectedEntity = em.merge( entity );
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
        List<Person> list = logic.getAll();
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
    private void assertPersonEquals( Person expected, Person actual ) {
        //assert all field to guarantee they are the same
        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getFirstName(), actual.getFirstName() );
        assertEquals( expected.getLastName(), actual.getLastName() );
        assertEquals( expected.getPhone(), actual.getPhone() );
        assertEquals( expected.getAddress(), actual.getAddress() );
        assertEquals( expected.getBirth(), actual.getBirth() );
    }
    
    @Test
    final void testGetWithId() {
        //using the id of test account get another account from logic
        Person returnedPerson = logic.getWithId( expectedEntity.getId() );

        //the two accounts (testAcounts and returnedAccounts) must be the same
        assertPersonEquals( expectedEntity, returnedPerson );
    }
    
    @Test
    final void testGetPersonWithPhone() {
         int foundFull = 0;
        //using the id of test account get another account from logic
        List<Person> returnedPeople = logic.getPersonWithPhone( expectedEntity.getPhone() );
        for(Person person: returnedPeople) {
            assertEquals(expectedEntity.getPhone(), person.getPhone());
             //exactly one account must be the same
            if( person.getId().equals( expectedEntity.getId() ) ){
                assertPersonEquals( expectedEntity, person );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetPersonWithFirstName() {
         int foundFull = 0;
        //using the id of test account get another account from logic
        List<Person> returnedPeople = logic.getPersonWithFirstName( expectedEntity.getFirstName() );
        for(Person person: returnedPeople) {
            assertEquals(expectedEntity.getFirstName(), person.getFirstName());
             //exactly one account must be the same
            if( person.getId().equals( expectedEntity.getId() ) ){
                assertPersonEquals( expectedEntity, person );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetPersonWithLastName() {
         int foundFull = 0;
        //using the id of test account get another account from logic
        List<Person> returnedPeople = logic.getPersonWithLastName( expectedEntity.getLastName() );
        for(Person person: returnedPeople) {
            assertEquals(expectedEntity.getLastName(), person.getLastName());
             //exactly one account must be the same
            if( person.getId().equals( expectedEntity.getId() ) ){
                assertPersonEquals( expectedEntity, person );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetPersonWithAddress() {
         int foundFull = 0;
        //using the id of test account get another account from logic
        List<Person> returnedPeople = logic.getPersonWithAddress( expectedEntity.getAddress() );
        for(Person person: returnedPeople) {
            assertEquals(expectedEntity.getAddress(), person.getAddress());
             //exactly one account must be the same
            if( person.getId().equals( expectedEntity.getId() ) ){
                assertPersonEquals( expectedEntity, person );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testGetPersonWithBirth() {
         int foundFull = 0;
        //using the id of test account get another account from logic
        List<Person> returnedPeople = logic.getPersonWithBirth( expectedEntity.getBirth() );
        for(Person person: returnedPeople) {
            assertEquals(expectedEntity.getBirth(), person.getBirth());
             //exactly one account must be the same
            if( person.getId().equals( expectedEntity.getId() ) ){
                assertPersonEquals( expectedEntity, person );
                foundFull++;
            }
        }
        assertEquals( 1, foundFull, "if zero means not found, if more than one means duplicate" );
    }
    
    @Test
    final void testCreateEntity() {
        Map<String, String[]> sampleMap = new HashMap<>();
        sampleMap.put( PersonLogic.ID, new String[]{ Integer.toString(expectedEntity.getId()) } );
        sampleMap.put( PersonLogic.FIRST_NAME, new String[]{ expectedEntity.getFirstName() } );
        sampleMap.put( PersonLogic.LAST_NAME, new String[]{ expectedEntity.getLastName() } );
        sampleMap.put( PersonLogic.PHONE, new String[]{ expectedEntity.getPhone() } );
        sampleMap.put( PersonLogic.ADDRESS, new String[]{ expectedEntity.getAddress() } );
        sampleMap.put( PersonLogic.BIRTH, new String[]{ logic.convertDateToString(expectedEntity.getBirth()) } );

        Person returnedPeople = logic.createEntity(sampleMap);
        assertPersonEquals(expectedEntity, returnedPeople);    
    }
    
    @Test
    final void testCreateEntityNullAndEmptyValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( PersonLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( PersonLogic.FIRST_NAME, new String[]{ expectedEntity.getFirstName() } );
            map.put( PersonLogic.LAST_NAME, new String[]{ expectedEntity.getLastName() } );
            map.put( PersonLogic.PHONE, new String[]{ expectedEntity.getPhone() } );
            map.put( PersonLogic.ADDRESS, new String[]{ expectedEntity.getAddress() } );
            map.put( PersonLogic.BIRTH, new String[]{ expectedEntity.getBirth().toString() } );
        };

        //idealy every test should be in its own method
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.ID, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.ID, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.FIRST_NAME, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.FIRST_NAME, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.LAST_NAME, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.LAST_NAME, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.PHONE, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.PHONE, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.ADDRESS, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.ADDRESS, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.BIRTH, null );
        assertThrows( NullPointerException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.BIRTH, new String[]{} );
        assertThrows( IndexOutOfBoundsException.class, () -> logic.createEntity( sampleMap ) );
    }
    
    @Test
    final void testCreateEntityBadLengthValues() {
        Map<String, String[]> sampleMap = new HashMap<>();
        Consumer<Map<String, String[]>> fillMap = ( Map<String, String[]> map ) -> {
            map.clear();
            map.put( PersonLogic.ID, new String[]{ Integer.toString( expectedEntity.getId() ) } );
            map.put( PersonLogic.FIRST_NAME, new String[]{ expectedEntity.getFirstName() } );
            map.put( PersonLogic.LAST_NAME, new String[]{ expectedEntity.getLastName() } );
            map.put( PersonLogic.PHONE, new String[]{ expectedEntity.getPhone() } );
            map.put( PersonLogic.ADDRESS, new String[]{ expectedEntity.getAddress() } );
            map.put( PersonLogic.BIRTH, new String[]{ expectedEntity.getBirth().toString() } );
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
        sampleMap.replace( PersonLogic.ID, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.ID, new String[]{ "12b" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );

        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.FIRST_NAME, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.FIRST_NAME, new String[]{ generateString.apply( 51 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.LAST_NAME, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.LAST_NAME, new String[]{ generateString.apply( 51 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.PHONE, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.PHONE, new String[]{ generateString.apply( 16 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.ADDRESS, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.ADDRESS, new String[]{ generateString.apply( 101 ) } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        
        fillMap.accept( sampleMap );
        sampleMap.replace( PersonLogic.BIRTH, new String[]{ "" } );
        assertThrows( ValidationException.class, () -> logic.createEntity( sampleMap ) );
        sampleMap.replace( PersonLogic.BIRTH, new String[]{ generateString.apply( 46 ) } );
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
        sampleMap.put( PersonLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( PersonLogic.FIRST_NAME, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( PersonLogic.LAST_NAME, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( PersonLogic.PHONE, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( PersonLogic.ADDRESS, new String[]{ generateString.apply( 1 ) } );
        sampleMap.put( PersonLogic.BIRTH, new String[]{ logic.convertDateToString(new Date(1)) } );

        //idealy every test should be in its own method
        Person returnedPerson = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( PersonLogic.ID )[ 0 ] ), returnedPerson.getId() );
        assertEquals( sampleMap.get( PersonLogic.FIRST_NAME )[ 0 ], returnedPerson.getFirstName() );
        assertEquals( sampleMap.get( PersonLogic.LAST_NAME )[ 0 ], returnedPerson.getLastName() );
        assertEquals( sampleMap.get( PersonLogic.PHONE )[ 0 ], returnedPerson.getPhone() );
        assertEquals( sampleMap.get( PersonLogic.ADDRESS )[ 0 ], returnedPerson.getAddress() );
        assertEquals( sampleMap.get( PersonLogic.BIRTH )[ 0 ], logic.convertDateToString(returnedPerson.getBirth()) );
        
        sampleMap = new HashMap<>();
        sampleMap.put( PersonLogic.ID, new String[]{ Integer.toString( 1 ) } );
        sampleMap.put( PersonLogic.FIRST_NAME, new String[]{ generateString.apply( 50 ) } );
        sampleMap.put( PersonLogic.LAST_NAME, new String[]{ generateString.apply( 50 ) } );
        sampleMap.put( PersonLogic.PHONE, new String[]{ generateString.apply( 15 ) } );
        sampleMap.put( PersonLogic.ADDRESS, new String[]{ generateString.apply( 100 ) } );
        sampleMap.put( PersonLogic.BIRTH, new String[]{ logic.convertDateToString(new Date(45)) } );

        //idealy every test should be in its own method
        returnedPerson = logic.createEntity( sampleMap );
        assertEquals( Integer.parseInt( sampleMap.get( PersonLogic.ID )[ 0 ] ), returnedPerson.getId() );
        assertEquals( sampleMap.get( PersonLogic.FIRST_NAME )[ 0 ], returnedPerson.getFirstName() );
        assertEquals( sampleMap.get( PersonLogic.LAST_NAME )[ 0 ], returnedPerson.getLastName() );
        assertEquals( sampleMap.get( PersonLogic.PHONE )[ 0 ], returnedPerson.getPhone() );
        assertEquals( sampleMap.get( PersonLogic.ADDRESS )[ 0 ], returnedPerson.getAddress() );
        assertEquals( sampleMap.get( PersonLogic.BIRTH )[ 0 ], logic.convertDateToString(returnedPerson.getBirth()) );
    }
    
    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();
        assertEquals( Arrays.asList( "ID", "FirstName", "LastName", "Phone", "Address", "Birth" ), list );
    }

    @Test
    final void testGetColumnCodes() {
        List<String> list = logic.getColumnCodes();
        assertEquals( Arrays.asList( PersonLogic.ID, PersonLogic.FIRST_NAME, PersonLogic.LAST_NAME, PersonLogic.PHONE, PersonLogic.ADDRESS, PersonLogic.BIRTH), list );
    }

    @Test
    final void testExtractDataAsList() {
        List<?> list = logic.extractDataAsList( expectedEntity );
        assertEquals( expectedEntity.getId(), list.get( 0 ) );
        assertEquals( expectedEntity.getFirstName(), list.get( 1 ) );
        assertEquals( expectedEntity.getLastName(), list.get( 2 ) );
        assertEquals( expectedEntity.getPhone(), list.get( 3 ) );
        assertEquals( expectedEntity.getAddress(), list.get( 4 ) );
        assertEquals( expectedEntity.getBirth(), list.get( 5 ) );
    }
}
