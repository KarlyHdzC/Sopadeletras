from servidor.Socket import *
import threading

class SocketConexion(threading.Thread):
    
     def __init__(self,host,puerto):

        self.host=host
        self.puerto=puerto
        #protocolos
        self.sc=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        threading.Thread.__init__(self)
        self.stoprequest = threading.Event()
        self.condicion=True
        self.lista_sockets=[]
        self.iniciar_configuracion()

     def iniciar_configuracion(self):
        address=(self.host,self.puerto)
        self.sc.bind(address)
        self.sc.listen(5)

     #obtiene un objeto de tipo socket con la conexion establecida
     def esperar_conexion(self):
        try:
            socket_servidor, datos_cliente = self.sc.accept() 
            return Socket(socket_servidor,self,datos_cliente)
        except socket.error as msg:
            print("Socket Error: %s" % msg)


     #metodo del hilo que controla        
     def run(self):
        while self.condicion:
            #print ('esperando conexion '+self.nombre)
            socket_conectado=self.esperar_conexion()
            
            #inicia el socket, para empieze a leer y escribir
            socket_conectado.start()

            #agrega el socket a una lista para tener todas las conexiones
            self.lista_sockets.append(socket_conectado)


     #metodo que elimina un socket de la lista
     def eliminar_socket(self,sc):
        #print("-->"+sc.imprimir())
        ubicacion = self.lista_sockets.index(sc)
        del self.lista_sockets[ubicacion]
  
     def detener(self):
        self.condicion=False

