package ApiRest2.Persistence;

import ApiRest2.Entities.Ciudad;
import ApiRest2.Entities.Pais;
import ApiRest2.Entities.Provincia;
import ApiRest2.Entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 05/06/2017.
 */
@Repository
public class UsuarioDao extends FatherDao<Usuario>{

    @Autowired
    public UsuarioDao(@Value("${db.host}") String host, @Value("${db.port}")String port,
                      @Value("${db.name}") String dbName, @Value("${db.username}")String dbUserName,
                      @Value("${db.pw}")String dbPass){
        super(host, port, dbName, dbUserName, dbPass);
    }

    //Agregar un usuario
    public void insert(Usuario usuario){
        // Aca se manda el id_ciudad, entonces...*LEER COMENT linea 12 Usuario   //Por esto//
        try {
            PreparedStatement ps =  conn.prepareStatement("INSERT INTO usuarios (nombre," +
                    " apellido, direccion, telefono, id_ciudad, id_provincia, id_pais, " +
                    "email, username, contrasena) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)");
            ps.setString(1,usuario.getNombre());
            ps.setString(2,usuario.getApellido());
            ps.setString(3,usuario.getDireccion());
            ps.setString(4,usuario.getTelefono());
            ps.setInt(5,usuario.getCiudad().getId());
            ps.setInt(6, usuario.getProvincia().getId());
            ps.setInt(7,usuario.getPais().getId());
            ps.setString(8,usuario.getEmail());
            ps.setString(9,usuario.getUserName());
            ps.setString(10,usuario.getContrasena());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    //Mostrar un usuario, mediante id
    public Usuario getById(int id){
        String sql = "SELECT * FROM usuarios " +
                "INNER JOIN ciudades ON ciudades.id = usuarios.id_ciudad" +
                " INNER JOIN provincias ON provincias.id = usuarios.id_provincia" +
                " INNER JOIN paises ON paises.id = usuarios.id_pais " +
                " WHERE usuarios.id = ?";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                Ciudad ciudad = new Ciudad(rs.getInt("ciudades.id"),rs.getString("ciudades.nombre"));

                Provincia provincia = new Provincia(rs.getInt("provincias.id"),rs.getString("provincias.nombre"));

                Pais pais = new Pais(rs.getInt("paises.id"),rs.getString("paises.nombre"));
                Usuario usuario = new Usuario(rs.getString("nombre"),rs.getString("apellido"),
                        rs.getString("direccion"), rs.getString("telefono"),
                        ciudad, provincia, pais, rs.getString("email"),
                        rs.getString("username"), rs.getString("contrasena"));
                usuario.setId(rs.getInt("usuarios.id"));
                return usuario;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Mostrar todos los usuarios
    public List<Usuario> getAll(){
        String sql = "SELECT * FROM usuarios " +
                "INNER JOIN ciudades ON ciudades.id = usuarios.id_ciudad" +
                " INNER JOIN provincias ON provincias.id = usuarios.id_provincia" +
                " INNER JOIN paises ON paises.id = usuarios.id_pais";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Usuario> usuarios = new ArrayList<Usuario>();
            while (rs.next()){
                Ciudad ciudad = new Ciudad(rs.getInt("ciudades.id"),rs.getString("ciudades.nombre"));

                Provincia provincia = new Provincia(rs.getInt("provincias.id"),rs.getString("provincias.nombre"));

                Pais pais = new Pais(rs.getInt("paises.id"),rs.getString("paises.nombre"));

                Usuario usuario = new Usuario(rs.getString("nombre"),rs.getString("apellido"),
                        rs.getString("direccion"), rs.getString("telefono"),
                        ciudad, provincia, pais, rs.getString("email"),
                        rs.getString("username"), rs.getString("contrasena"));
                usuario.setId(rs.getInt("usuarios.id"));
                usuarios.add(usuario);
            }
            return usuarios;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Eliminar un usuario
    public void deleteUser(int id){
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
