package kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Kata4 {

    public static void main(String[] args) throws SQLException{
        
        Connection conection = createConnection("people.db");
        PersonLoader personLoader = new DBPersonLoader(conection);
        HistogramBuilder<Person> builder = new HistogramBuilder<>(personLoader.load());
        new ConsoleHistogramViewer<String>().show(builder.build(new AttributeExtractor<Person,String>() {

            @Override
            public String extract(Person person) {
                return person.getMail().getDomain();
            }
        }));
    }

    private static Connection createConnection(String adbpath) throws SQLException {
        DriverManager.registerDriver(new org.sqlite.JDBC());
        return DriverManager.getConnection("jdbc:sqlite:" + adbpath);
    }
    
}
