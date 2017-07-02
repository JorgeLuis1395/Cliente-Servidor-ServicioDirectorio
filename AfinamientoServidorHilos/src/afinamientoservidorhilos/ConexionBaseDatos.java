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
            System.out.println("Conexion exitosa");
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
            Logger.getLogger(ConexionBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public boolean SelectNombreContrasena(String nombre, String contrasena){
        try {
            String consulta="select NOMBREPERSONA from PERSONA where USUARIO='"+nombre+"' and CONTRASENA='"+contrasena+"';";
            result=stamt.executeQuery(consulta);
            while(result.next()){
                System.out.println(result.getString(1));
                return true;
            }
            System.out.println("Usuario o contrasena incorrecto");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
    
    public static void main(String[] args) {
        ConexionBaseDatos base=new ConexionBaseDatos();
        base.Coneccion();
        base.SelectNombreContrasena("robertToa","12345");
        base.cerrarConeccion();
    }
}
