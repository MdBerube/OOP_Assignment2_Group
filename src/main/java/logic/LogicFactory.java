package logic;

import java.lang.ClassNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class LogicFactory {

    private static final String PACKAGE = "logic.";
    private static final String SUFFIX = "Logic";
  
    // main factory implementation?
    private LogicFactory() {
        
    }
    
    public static < T> T getFor( String entityName ) throws ClassNotFoundException {
        
         T newInstance = null;
         Class<T> type;
           try {
               type = (Class<T>) Class.forName(PACKAGE + entityName + SUFFIX);
               newInstance = getFor(type);
           }catch (Exception e){
               e.printStackTrace();
           }
           return newInstance; 
    }
    
    public static<T> T getFor(Class type) throws InstantiationException , IllegalAccessException,  
    IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        
        T newInstance = null;
        try{
            // getDeclared returns a constructor 
            Constructor<T> declaredConstructor = (type.getDeclaredConstructor());
            newInstance = declaredConstructor.newInstance();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return newInstance;
    }
  
}
