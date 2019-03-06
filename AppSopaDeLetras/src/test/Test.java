/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import socket.SocketCliente;

/**
 *
 * @author Carlos Sánchez
 */
public class Test implements Runnable
{
   private static List<String> lines=new ArrayList<String>();

  // public static void main(String []args)
  //  {@Override
    public void run() {
        
        FileInputStream fis;
        FileOutputStream fos = null;
        FileReader lector  = null;
        BufferedReader br = null;
        SocketCliente socketCliente=new SocketCliente("localhost",1234);
        DataInputStream dis = null;
        socketCliente.conectar();
        
        System.out.println("Cliente conectado");
//       while(true)
       // {    
            /* System.out.println("rsp: "+socketCliente.leer());*/
             
            String ruta  = "Palabras.txt";
           System.out.println("Requested File: ");
            try {
                File file = new File(ruta);
                // Create new file if it does not exist
                // Then request the file from server
                if(!file.exists()){
                    file.createNewFile();
                    System.out.println("Created New File: ");
                }
                //fos = new FileOutputStream(file);
                //send(filename);
               lector = new FileReader(ruta);
               br = new BufferedReader(lector);
                // Get content in bytes and write to a file
                byte[] buffer = new byte[100];

                dis = new DataInputStream(socketCliente.getSc().getInputStream());
                long tam=130; 
                int n=0,m=0;
                DataOutputStream dos=new DataOutputStream(new FileOutputStream(ruta));
                while(m<tam){
                    n=dis.read(buffer);
                   m=m+n;
                    System.out.println(m+"-"+n);
                    dos.write(buffer,0,n);
                    dos.flush();
                    
                }
                /*for(int counter=0; (counter = dis.read(buffer, 0, buffer.length)) >= 0;)
                {
                        fos.write(buffer, 0, counter);
                        System.out.println("cont"+counter);
                }*/
                //dos.close();
                //dis.close();
                //fos.flush();
                //fos.close();
                

            } catch (IOException e) {
                e.printStackTrace();
            }
            
            /**/
            //List<String> lines=new ArrayList<String>();
            //dvidir palabras
           try{
                String linea =  br.readLine();
                   //contador
                int contador = 0;
                while(linea != null){
                    String[] values = linea.split(",");
                    
                    //recorremos el arrar de string
                    for (int i = 0; i<values.length; i++) {
                        
                        System.out.println(values[i]);
                        String palabra = values[i]; 
                        String resultado = "";
                        int zz,azar;
                        for (zz=palabra.length();zz>=1;zz--){
                            azar = (int)(Math.random()* zz+1) ;
                            resultado = resultado + palabra.substring(azar-1,azar);
                            palabra =  palabra.substring(0,azar-1)+palabra.substring(azar,zz);
                        }
                        System.out.println(resultado);
                        lines.add(resultado);
                    }
                    contador++;
                    linea = br.readLine();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            /**/
             while(true)
            {
                String msj=leer("Ingrese el mensaje a enviar:");
                socketCliente.salida(msj);
                if(msj.equals("EXIT"))
                    break;
                else                     
                    System.out.println("rsp: "+socketCliente.leer());
            }
           /* String msj=leer("Ingrese el mensaje a enviar:");
            socketCliente.salida(msj);
            if(msj.equals("EXIT"))
                break;
            else                     
                System.out.println("rsp: "+socketCliente.leer());*/
        //}
        
        
        
    }
      
    public static List<String> getLines(){
        return lines;
  
    }
    public static String leer(String mensaje)
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println(mensaje);
        return scanner.nextLine();
    }
 
         

    
    }
    

