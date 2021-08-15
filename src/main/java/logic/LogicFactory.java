package logic;

import java.lang.ClassNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
* @author Michael Berube
*/
public abstract class LogicFactory {

    private static final String PACKAGE = "logic.";
    private static final String SUFFIX = "Logic";
  
    private LogicFactory() {
    }
    
    public static < T> T getFor( String entityName )  {
        
         T newInstance = null;
         Class<T> type;
           try {
               type = (Class<T>) Class.forName(PACKAGE + entityName + SUFFIX);
               newInstance = getFor(type);
           }catch (ClassNotFoundException e){
               e.printStackTrace();
           }
           return newInstance; 
    }
    
    public static<T> T getFor(Class type)  {
        
        T newInstance = null;
        try{
            // getDeclared returns a constructor 
            Constructor<T> declaredConstructor = (type.getDeclaredConstructor());
            newInstance = declaredConstructor.newInstance();
        }catch (InstantiationException | IllegalAccessException | IllegalArgumentException 
        | InvocationTargetException | NoSuchMethodException | SecurityException e ){
            e.printStackTrace();
        }
        return newInstance;
    }
  
}