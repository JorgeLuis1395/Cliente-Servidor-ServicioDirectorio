package afinamientoservidorhilos;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import encriptar.encriptacion;
public class AfinamientoServidorHilos extends Thread {
    private Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String mensaje;
    encriptacion encrip=new encriptacion();
    public AfinamientoServidorHilos(Socket cli){
        this.cliente=cli;
    }
    
    public void Ejecucion(){
        try {
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida=new PrintWriter(cliente.getOutputStream(), true);
            if((mensaje=entrada.readLine())!=null)
                salida.println(encrip.decepcriptar(mensaje));
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(AfinamientoServidorHilos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void run(){
        Ejecucion();
    }
    /*public static void main(String[] args) {
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
    }*/
    
}
