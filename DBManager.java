public class DBManager {
    private mySQL_DB myDB;

    public DBManager(String DBPath_or_something) {
        myDB = new mySQL_DB(DBPath_or_something);
    }

    public Option[] getOptions() {
        return null;
    }

    public Subject login(String username, String password) {


        return null;    //if unsuccessful login
    }
}
