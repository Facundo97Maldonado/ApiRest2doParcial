package ApiRest2.Persistence;


import com.mysql.jdbc.Connection;
import org.springframework.stereotype.Repository;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Facundo on 07/06/2017.
 */
@Repository
public abstract class FatherDao<T> {


    protected Connection conn;

    public abstract T getById(int id);

    public abstract List<T> getAll();

    public abstract void insert(T value);

    public FatherDao(){

    }

    public FatherDao(String host, String port, String dbName, String dbUserName, String dbPass) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = (Connection) DriverManager.getConnection
                    ("jdbc:mysql://" + host + ":" + port + "/" + dbName,  dbUserName, dbPass);
        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
