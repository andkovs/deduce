package dao;

/**
 * Created by Andrey on 01.08.2017.
 */
public class ContextName{

    private static String contextName = null;

    public static String getName(){
        return contextName;
    }

    public static void setName(String name){
       contextName = name;
    }
}
