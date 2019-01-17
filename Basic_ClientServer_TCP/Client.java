package Basic_ClientServer_TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * @Autor: Steven Bernal Tovar (Kaiziferr)
 *         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Client {

	public static void main(String[] args) {
		try {

			/**
			 * El socket intenta conectarse al host remoto. El InetAddress.getLocalHost() es
			 * donde esta alojado el servidor, en este proyecto se encuentra en el local
			 * host, en el puerto 1234.
			 */
			Socket clientSocket = new Socket(InetAddress.getLocalHost(), 1234);

			/**
			 * Se inicializa el flujo de entrada por el cual se recibe información del
			 * servidor.
			 */
			BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			/**
			 * Se inicializa el flujo de entrada por el cual se lee la información de la
			 * consola del cliente.
			 */
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(System.in));

			/**
			 * Se inicializa el flujo de salida por el cual se envia información al
			 * servidor.
			 */
			PrintWriter socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);

			/**
			 * Se inicializa el flujo de entrada por el cual se imprime la información por
			 * la consola
			 */
			PrintWriter clientWriter = new PrintWriter(System.out, true);

			/**
			 * Mensaje que indica que el servidor se está inicializando
			 */
			System.out.println("The has client initialized ...");

			/**
			 * Imprime por consola los datos recibidos
			 */
			clientWriter.println(socketReader.readLine());

			/**
			 * Se envían los datos al servidor
			 */
			socketWriter.println(clientReader.readLine());

			/**
			 * Imprime por consola los datos recibidos
			 */
			clientWriter.println(socketReader.readLine());

			/**
			 * Cierra canal impresión por consola.
			 */
			clientWriter.close();

			/**
			 * Cierra canal lectura por consola.
			 */
			clientReader.close();

			/**
			 * Cierra canal salida.
			 */
			socketWriter.close();

			/**
			 * Cierra canal entrada.
			 */
			socketReader.close();


			/**
			 * Cierra el Socket del cliente
			 */
			clientSocket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
