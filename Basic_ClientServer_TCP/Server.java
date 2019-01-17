package Basic_ClientServer_TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * @Descripción: El socket del servidor se ejecuta en el servidor y escucha las
 *               próximas conexiones TCP. El socket del servidor escucha en el
 *               puerto 1234 cuando un cliente en un host remoto intenta
 *               conectarse a ese puerto, el servidor se despierta, negocia la
 *               conexión entre el cliente y el servidor y devuelve un objeto
 *               Socket normal que representa el socket entre los dos hosts. Una
 *               vez que un ServerSocket ha configurado la conexión, el servidor
 *               utiliza un objeto el Socket normal para enviar datos al
 *               cliente. Este programa consiste en que un cliente envia su
 *               nombre, para que el servidor lo salude.
 * @Autor: Steven Bernal Tovar (Kaiziferr)
 *         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Server {

	public static void main(String[] args) {

		try {

			/**
			 * Se crea un nuevo ServerSocket en el puerto 1234.
			 */
			ServerSocket serverSocket = new ServerSocket(1234);

			/**
			 * Mensaje que indica que el servidor se está iniciando.
			 */
			System.out.println("The has server initialized ...");

			/**
			 * El ServerSocket escucha los intentos de conexión entrantes en el puerto 1234
			 * utilizando el método accept(). Este metodo accept() bloquea hasta que un
			 * cliente intenta establecer una conexión al servidor. Cuando el servidor
			 * acepte la petición, retornara un objet Socket que permitirá la conexión entre
			 * el cliente y el servidor.
			 */
			Socket connectionSocket = serverSocket.accept();

			/**
			 * Se inicializa el flujo de entrada por el cual se recibe información del
			 * cliente.
			 */
			BufferedReader connectionReader = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));

			/**
			 * Se inicializa el flujo de salida por el cual se envia información al cliente.
			 */
			PrintWriter connectionWriter = new PrintWriter(connectionSocket.getOutputStream(), true);

			/**
			 * Datos enviando.
			 */
			connectionWriter.println("Enter your name:");

			/**
			 * connectionReader.readLine() lee el nombre enviado por el cliente
			 * connectionWriter.println envia un saludo al cliente.
			 */
			connectionWriter.println("good morning " + connectionReader.readLine());
 
			/**
			 * Cierra canal salida.
			 */
			connectionWriter.close();

			/**
			 * Cierra canal de entrada.
			 */
			connectionReader.close();

			/**
			 * Cierra el Socket que permitía la conexión entre el servidor y el cliente.
			 */
			connectionSocket.close();

			/**
			 * El servidor cierra sus operaciones, ya no estará en modo escucha.
			 */
			serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
