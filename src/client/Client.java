package src.client;

import src.server.ServerInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class Client {

	public static void main(String[] args) {

		try {

			// Creamos el buffer para leer los datos introducidos por el usuario
			InputStreamReader is = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);

			// Pedimos al usuario la IP o nombre del servidor al que nos queremos conectar
			System.out.print("Client | RMIRegistry host name: ");
			String address = br.readLine();

			// Pedimos al usuario el puerto del servidor al que nos queremos conectar
			System.out.print("Client | RMIRegistry port number: ");
			int port = Integer.parseInt(br.readLine());

			// Creamos la URL del registro RMI
			String registryURL = "rmi://" + address + ":" + port + "/server";

			// Obtenemos el objeto remoto del servidor
			ServerInterface serverObject = (ServerInterface) Naming.lookup(registryURL);

			// Creamos el objeto cliente que se encargara de recibir los datos del servidor
			new ClientImpl(serverObject);

		} catch (Exception e) {

			// Mostramos por pantalla el error
			System.out.println("Client | Exception: " + e.getMessage());

		}

	}

}