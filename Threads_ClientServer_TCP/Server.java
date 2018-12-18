package Threads_ClientServer_TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * @Autor: Steven Bernal Tovar (Kaiziferr)
 *         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Server {

	public static void main(String[] args) {

		try {

			/**
			 * El socket del servidor es creado en el puerto 1234
			 */
			ServerSocket serverSocket = new ServerSocket(1234);

			/**
			 * El servidor es inicializado
			 */
			System.out.println("The has server initialized ...");

			while (true) {
				Connection connection = new Connection(serverSocket.accept());
				connection.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Esta clase permite que un servidor cree múltiples conexiones con diferentes
	 * clientes
	 */
	static class Connection extends Thread {

		/**
		 * Socket que permite la transferencia de datos entre el servidor y el cliente
		 */
		private Socket connectionSocket;

		/**
		 * Canal de salida usada por el socket para enviar datos
		 */
		private PrintWriter writerConnection;

		/**
		 * Canal de entrada usada por el socket para leer los datos enviados por el
		 * cliente
		 */
		private BufferedReader readerConnection;

		
		public Connection(Socket socketConnection) {
			this.connectionSocket = socketConnection;
			try {
				readerConnection = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
				writerConnection = new PrintWriter(socketConnection.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			writerConnection.println("Enter your math operation in this format: x operator y ");

			try {

				while (true) {

					String[] commands = readerConnection.readLine().split(" ");
					String resultado = "wrong commands ";

					if (commands.length == 3) {
						try {

							int x = Integer.parseInt(commands[0]);

							int z = Integer.parseInt(commands[2]);

							switch (commands[1]) {
							case "+":
								resultado = (x + z) + "";
								break;
							case "*":
								resultado = (x * z) + "";
								break;
							case "-":
								resultado = (x - z) + "";
								break;
							case "/":
								resultado = (x / z) + "";
								break;
							default:
								break;
							}

							writerConnection.println("answer: " + resultado);

						} catch (Exception e) {
							// TODO: handle exception
							writerConnection.println(resultado);
						}

					} else if (commands[0].equals("exit")) {
						operationClose();
					} else {
						writerConnection.println("wrong format");
					}

				}

			} catch (IOException e) {
				operationClose();
			}

		}

		public void operationClose() {
			try {
				readerConnection.close();
				writerConnection.close();
				connectionSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
