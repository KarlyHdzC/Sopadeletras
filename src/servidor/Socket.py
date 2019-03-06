import socket
import threading

class Socket(threading.Thread):

    def __init__(self,sc,socket_conexion,datos_cliente):
        self.datos_cliente=datos_cliente
        self.sc=sc
        self.socket_conexion=socket_conexion
        threading.Thread.__init__(self)
        self.stoprequest = threading.Event()
        #variable para definir cuando detener el hilo
        self.condicion=True

        #    self.escribir("Servidor dice Hola")
        #f = open('archivo.txt', 'rb')
        #line = f.read(1024)
        #sc.send(line)
        while(self.condicion):
            f = open('archivo.txt', 'rb')
            line = f.read(1024)

            print("Beginning File Transfer")
            while line:
                sc.send(line)
                line = f.read(100)
            f.close()
            print("Transfer Complete")
            break;

    def leer(self):
        try:
            bufSize=1024
            datos_recibidos=self.sc.recv(bufSize)
            return datos_recibidos.decode('utf-8')        
        except socket.error as msg:
            print("Socket Error: %s" % msg)
    
    def escribir(self, mensaje):
        #enviar mensaje en formato utf8
        self.sc.send(mensaje.encode('utf8'))
    
    def desconectar(self):
        #terminar socket
        self.sc.close()
    
    #imprime la informacion de conexion con el cliente
    def imprimir(self):
        print(self.datos_cliente)

    def run(self):
        while self.condicion:
            #controla si se produce un erro on el escritura
            try:
            
                trama = self.leer()
                #cuando la conexion rebiba la palabra exit termina
                if trama == "EXIT" or trama is None:
                    #desconecta y elimina el socket de la lista
                    self.socket_conexion.eliminar_socket(self)                        
                    self.desconectar()
                    self.condicion=False
                    print('cliente desconectado')
                else:
                        self.escribir("Phyton dice recibido '"+trama+"'");
            
            except socket.error as msg:
                    self.socket_conexion.eliminar_socket(self)
                    self.desconectar()