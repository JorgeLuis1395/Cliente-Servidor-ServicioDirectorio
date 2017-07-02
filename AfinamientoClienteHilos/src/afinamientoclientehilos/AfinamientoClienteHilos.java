package afinamientoclientehilos;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import encriptar.encriptacion;
public class AfinamientoClienteHilos extends Thread {
    private Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String mensaje="soy el nodo ",mensajeRecibido;
    final int puerto=4314;
    final String HOST = "localhost";
    private String numNodo;
    encriptacion encrip=new encriptacion();
    public AfinamientoClienteHilos(String nodo){
        numNodo=nodo;
    }
    
    private void GuardarArchivo(String mensaje) throws IOException{
        BufferedWriter out = null;   
        try {   
            out = new BufferedWriter(new FileWriter("salida.txt", true));   
            out.write(mensaje);
            out.newLine();
            } catch (IOException e) {   
            // error processing code   
            } finally {   
                    if (out != null) {   
                out.close();   
            }      
        }
    }
    
    public void Ejecucion(){
        try {
            cliente=new Socket(HOST,this.puerto);
            entrada=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            salida=new PrintWriter(cliente.getOutputStream(), true);
            salida.println(encrip.encriptar(mensaje+numNodo));
            if((mensajeRecibido=entrada.readLine())!=null)
                GuardarArchivo("mensaje recibido: "+mensajeRecibido+"   \t mensaje encriptado: "+encrip.encriptar(mensaje+numNodo));
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(AfinamientoClienteHilos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void run(){
        Ejecucion();
    }
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
		AfinamientoClienteHilos cliente=new AfinamientoClienteHilos(String.valueOf(i));
                cliente.start();
        }
    }
    
}
