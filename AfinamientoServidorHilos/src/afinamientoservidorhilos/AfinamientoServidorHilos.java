package afinamientoservidorhilos;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import encriptar.encriptacion;
public class AfinamientoServidorHilos extends Thread {
    private Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String mensajeEntrada="",Usuario,nombre;
    encriptacion encrip=new encriptacion();
    ConexionBaseDatos conecBaseDatos=new ConexionBaseDatos();
    public AfinamientoServidorHilos(Socket cli){
        this.cliente=cli;
    }
    
    private boolean VerUsuario(){
        int i=0;
        String Usuario[]={"","",""},verificarNombre="";
        StringTokenizer tokens=new StringTokenizer(encrip.decepcriptar(mensajeEntrada), "\n");
        while(tokens.hasMoreTokens()){
            if(i>1)
                Usuario[i]+=tokens.nextToken();
            else{
                Usuario[i++]=tokens.nextToken();
            }
        }
        conecBaseDatos.Coneccion();
        verificarNombre=conecBaseDatos.SelectNombreContrasena(Usuario[0],Usuario[1]);
        conecBaseDatos.cerrarConeccion();
        this.Usuario=Usuario[0];
        if(!verificarNombre.equals("")){
            nombre=verificarNombre;
            return true;
        }else
            return false;
    }
    
    public void Ejecucion(){
        try {
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida=new PrintWriter(cliente.getOutputStream(), true);
            if((mensajeEntrada=entrada.readLine())!=null)
                if(VerUsuario())
                    salida.println(encrip.encriptar("Usuario : "+Usuario+" con nombre: "+nombre));
                else
                    salida.println(encrip.encriptar("Usuario : "+Usuario+" incorrecto"));
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(AfinamientoServidorHilos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void run(){
        Ejecucion();
    }
    public static void main(String[] args) {
        try {
            final int puerto=4314;
            ServerSocket servidor=new ServerSocket(puerto);
            for(int i=0;i<100;i++){
                Socket cliente=new Socket();
                cliente=servidor.accept();
                AfinamientoServidorHilos server=new AfinamientoServidorHilos(cliente);
                server.start();
            }
            servidor.close();
        } catch (IOException ex) {
            Logger.getLogger(AfinamientoServidorHilos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
