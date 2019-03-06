/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class SocketCliente
{
    private String host;
    private int puerto;
    private Socket sc;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private FileInputStream fis;
    private FileOutputStream fos = null;

    
    public SocketCliente(String host, int puerto)
    {
        this.host=host;
        this.puerto=puerto;
    }
    
    /**
     * Conexion con el servidor
     */
    public void conectar()
    {
        try {
            sc=new Socket(host,puerto);
            System.out.println("conexion establecida");
            entrada=new DataInputStream(sc.getInputStream());
            salida=new DataOutputStream(sc.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconectar()
    {
        try {
            sc.close();
            entrada.close();
            salida.close();
            } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que me permite leer una mensaje enviado por el socket
     * @return 
     */
    public String leer()
    {
        try {
            //return entrada.readUTF();
            byte[] bs = new byte[1024];
            entrada.read(bs);
            String respuesta = new String(bs,"UTF-8");
            return respuesta;
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Me permite enviar un mensaje por el socket
     * @param mensaje 
     */
    public boolean salida(String mensaje)
    {
        try {
            byte[] rs=mensaje.getBytes();
            salida.write(rs,0,rs.length);
            return true;
        } catch (IOException ex) {
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Socket getSc() {
        return sc;
    }
}
