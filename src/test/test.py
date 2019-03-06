from servidor.SocketConexion import *
import sys

if __name__ == "__main__":
    #crear socket
    socketConexion=SocketConexion("localhost",1234)
    condicion=True
    while (condicion):
        print("1.- Iniciar Servidor")
        print("2.- Parar Servidor")
        print("3.- Salir")

        valor=input()
        print("                                                            ")

        if valor=="1":
            socketConexion.start()
            print('Servidor Iniciado...')
        elif valor=="2":
            socketConexion.detener;
            print('Servidor Detenido...')
        elif valor=="3":
            print('Ejecucion Terminada');
            sys.exit(0)
            
        
    