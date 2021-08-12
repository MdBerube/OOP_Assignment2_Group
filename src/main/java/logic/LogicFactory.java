package logic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class LogicFactory {

    private static final String PACKAGE = "logic.";
    private static final String SUFFIX = "Logic";

    private LogicFactory() {
    }

    //TODO this code is not complete, it is just here for sake of programe working. need to be changed ocmpletely
    public static < T> T getFor( String entityName ) {
        //this casting wont be needed.
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
