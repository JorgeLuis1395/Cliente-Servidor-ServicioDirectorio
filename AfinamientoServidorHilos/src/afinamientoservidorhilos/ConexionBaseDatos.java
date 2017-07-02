package afinamientoservidorhilos;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ConexionBaseDatos {
    private String URlConeccion="";
    private Connection coneccion=null;
    private Statement stamt=null;
    private ResultSet result=null;
    
    public void Coneccion(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            URlConeccion= "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=ClienteCompuDistri;"
                    +"integratedSecurity=true;";
            coneccion=DriverManager.getConnection(URlConeccion);
            stamt=coneccion.createStatement();
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(ConexionBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public String SelectNombreContrasena(String nombre, String contrasena){
        try {
            String consulta="select NOMBREPERSONA from PERSONA where USUARIO='"+nombre+"' and CONTRASENA='"+contrasena+"';";
            result=stamt.executeQuery(consulta);
            while(result.next()){
                return (result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    
    public void cerrarConeccion(){
        if (result != null) 
            try { result.close(); 
            } catch(Exception e) {}
        if (stamt != null) 
            try { stamt.close(); 
            } catch(Exception e) {}
        if (coneccion != null) 
            try { coneccion.close(); 
            } catch(Exception e) {}
    }
}
