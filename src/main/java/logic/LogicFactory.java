package logic;

public abstract class LogicFactory {

    private static final String PACKAGE = "logic.";
    private static final String SUFFIX = "Logic";

    
    private LogicFactory() {
        
    }

    //TODO this code is not complete, it is just here for sake of program working. need to be changed completely
    public static < T> T getFor( String entityName ) {
        //this casting wont be needed.
        return (T)new AccountLogic();
    }
}
