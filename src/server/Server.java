package server;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.*;

public class Server {

	public static void main(String[] args) {

		try {

			// Obtenemos el buffer para leer los datos introducidos por el usuario
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);

			// Pedimos al usuario el puerto en el que queremos para el registro RMI
			System.out.print("Server | RMIRegistry port number: ");
			int port = Integer.parseInt(br.readLine());

			// Iniciamos el registro RMI
			startRegistry(port);

			// Creamos la URL del registro RMI
			String registryURL = "rmi://localhost:" + port + "/server";

			// Creamos el objeto remoto que se encargara de enviar los datos a los clientes
			ServerImpl objectServer = new ServerImpl();

			// Registramos el objeto remoto en el registro RMI
			Naming.rebind(registryURL, objectServer);

			// Liberamos los recursos para poder cerrar el programa
			br.close();
			is.close();

			// Si se produce alguna excepcion, la capturamos
		} catch (Exception re) {

			// Mostramos el mensaje de error por pantalla
			System.out.println("Server | Exception: " + re);

		}

	}

	// Metodo que se encarga de iniciar el registro RMI
	private static void startRegistry(int port) throws RemoteException {

		try {

			// Intentamos obtener el registro RMI en el puerto indicado
			Registry registry = LocateRegistry.getRegistry(port);
			String[] values = registry.list();

			for(String value : values) {
				System.out.println(value);
			}

			// Si no se puede obtener el registro RMI, lo creamos
		} catch (RemoteException e) {

			// Creamos el registro RMI en el puerto indicado
			LocateRegistry.createRegistry(port);

		}

	}

}