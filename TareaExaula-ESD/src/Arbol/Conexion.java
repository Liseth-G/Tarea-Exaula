package Arbol;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
    private static Connection con;
    
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user="root";
    private static final String pass="";
    private static final String url="jdbc:mysql://localhost:3306/arbolesbd";
    // Funcion que va conectarse a la bd de mysql
    public void conector() {
        // Reseteamos a null la conexion a la bd
        con=null;
        try{
            Class.forName(driver);
            // Nos conectamos a la bd
            con= (Connection) DriverManager.getConnection(url, user, pass);
            // Si la conexion fue exitosa mostramos un mensaje de conexion exitosa
            if (con!=null){
                JOptionPane.showMessageDialog(null,"Conexion establecida");
            }
        }// Si la conexion NO fue exitosa mostramos un mensaje de error
        catch (ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error de conexion " + e);
        }
    }
    
    public Statement createStatement() throws SQLException {
        try{
            return con.createStatement();
        }catch(SQLException e){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return con.prepareStatement(sql);
    }
    
}
