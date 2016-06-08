package kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBPersonLoader implements PersonLoader{

    private final Connection connection;

    public DBPersonLoader(Connection connection) {
        this.connection = connection;
    }
    
    
    @Override
    public Person[] load() {
        
        try {
        
            return processQuery(connection.createStatement().executeQuery("SELECT * FROM people"));
        
        } catch (SQLException ex) {
            
            Logger.getLogger(DBPersonLoader.class.getName()).log(Level.SEVERE, null, ex);
            return new Person[0];
        }
        
        
    }

    private Person[] processQuery(ResultSet resultSet) throws SQLException {

        ArrayList<Person> personList = new ArrayList<>();
        
        while(resultSet.next())
            personList.add(processPerson(resultSet));
        return personList.toArray(new Person[personList.size()]);
    }

    private Person processPerson(ResultSet resultSet) throws SQLException {
      
        return new Person(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("company_name"),
                resultSet.getString("address"),
                resultSet.getString("city"),
                resultSet.getString("state"),
                new Mail(resultSet.getString("email")),
                resultSet.getString("web")
        );
    }
    
    
}
