from requests import post,get
from colored import fg, bg, attr
import threading
import time

url = "https://modeler-backend.azurewebsites.net/"
datos='{"username":"johann.paez@mail.escuelaing.edu.co","password":"Prueba123@"}'
headers1 ={"Content-Type":"application/json"}
cont = 0
listaPeticionesIniciales = []
listaNPeticiones = []
peticionesFinalestotales = 0
errores = 0

class hilo(threading.Thread) :
    def __init__(self, num, peticionesIniciales, peticiones, function):
        self.num = num
        self.peticionesIniciales = peticionesIniciales
        self.peticiones = peticiones
        self.function = function
        threading.Thread.__init__(self, target=function, args=(num, peticionesIniciales, peticiones))
        
def promedio(lista):
    return round(sum(lista) / len(lista), 5)

def getModeler(numHilo, peticionesIniciales, peticiones):
    global peticionesFinalestotales, errores
    for i in range(peticiones):
        #print("Iteración " + str(i) + " del hilo " + str(numHilo) + " ") 
        ini =  time.time()
        z = post("https://modeler-backend.azurewebsites.net/" + "user/login",data = '{"username":"johann.paez@mail.escuelaing.edu.co","password":"Prueba123@"}', headers = {"Content-Type":"application/json"})
        fin1 = time.time()
        token = ""
        try:
            token = z.json()["token"]    
            getStatusCode = get("https://modeler-backend.azurewebsites.net/" + "projectapi/JOHANN_PAEZ/project/MODELER/version/1/modelo",headers={"Authorization":token}).status_code
            if (getStatusCode == 401):
                getStatusCode = 500
        except:
            getStatusCode = 500
        fin2 = time.time()
        time1 = round(fin1 - ini, 3)
        time2 = round(fin2 - ini, 3)
        if len(listaPeticionesIniciales) < peticionesIniciales:
            listaPeticionesIniciales.append(time2)
        listaNPeticiones.append(time2)        
        peticionesFinalestotales += 1
        respuesta = "Petición " + str(peticionesFinalestotales) + ":  " + str(getStatusCode) + ", " + str(time2) + " s"
        if getStatusCode != 202:
            errores += 1
            print (fg(196) + str(respuesta))
        else:
            print (fg(46) + str(respuesta))
        #print(str(numHilo) + ". Tiempo de respuesta TOKEN: ", time1)
        #print(str(numHilo) + ". Tiempo de respuesta Modelo: ", time2)                

def main():
    cont  = 0
    numHilos = 50
    numPeticionesPorHilo = 10
    listaHilos = []
    peticionesIniciales = 10
    for i in range(numHilos):
        miHilo = hilo(i + 1, peticionesIniciales, numPeticionesPorHilo, getModeler)
        listaHilos.append(miHilo)
        miHilo.start()
    for i in range(len(listaHilos)):
        listaHilos[i].join()        

    petTotales = numHilos * numPeticionesPorHilo
    print()
    print(fg(27) + "El número de hilos fue: " + str(numHilos))
    print("El número de peticiones por hilo fue de: " + str(numPeticionesPorHilo))
    print("Promedio " + str(peticionesIniciales) + " peticiones: " + str(promedio(listaPeticionesIniciales)) + " s " )
    print("Promedio " + str(petTotales) + " peticiones: " + str(promedio(listaNPeticiones)) + " s " )
    print("El máximo tiempo de respuesta fue: " + str(max(listaNPeticiones)) + " s " )
    print("El mínimo tiempo de respuesta fue: " + str(min(listaNPeticiones)) + " s " )
    print("Número de peticiones totales: " + str(petTotales))
    print("Número de peticiones con éxito: " + str(petTotales - errores))
    print("Número de peticiones fallidas: " + str(errores))    
    print("Termino!")
main()
