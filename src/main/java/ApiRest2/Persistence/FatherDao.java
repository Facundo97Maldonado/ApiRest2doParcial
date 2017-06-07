package ApiRest2.Persistence;


import com.mysql.jdbc.Connection;
import org.springframework.stereotype.Repository;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Facundo on 07/06/2017.
 */
@Repository
public class FatherDao {

    protected Connection conn;
    private static FatherDao instance;

    public FatherDao() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            this.conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/apiRest", "root", "ia0nonoza");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static FatherDao getInstance(){
        if(instance == null){
            instance = new FatherDao();
        }
        return instance;
    }

}
