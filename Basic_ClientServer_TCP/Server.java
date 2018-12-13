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
 * @Autor: Steven Bernal Tovar (Kaiziferr)
 *         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Server {

	public static void main(String[] args) {

		try {

			/**
			 * server socket runs on the server and listens for in coming TCP connections.
			 * Each server socket listens on a particular port on the server machine. When a
			 * client on a remote host attempts to connect to that port, the server wakes
			 * up, negotiates the connection between the client and the server, and returns
			 * a regular Socket object representing the socket between the two hosts. Once a
			 * ServerSocket has set up the connection, the server uses a regular Socket
			 * object to send data to the client
			 */

			/**
			 * A new ServerSocket is created on a particular port using a ServerSocket()
			 * con‐ structor.
			 */
			ServerSocket serverSocket = new ServerSocket(1234);

			System.out.println("The has server initialized ...");

			/**
			 * The ServerSocket listens for incoming connection attempts on that port using
			 * its accept() method. accept() blocks until a client attempts to make a
			 * connection, at which point accept() returns a Socket object connecting the
			 * client and the server.
			 */
			Socket connectionSocket = serverSocket.accept();

			/**
			 * Get input streams that communicate with the client.
			 */
			BufferedReader connectionReader = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));

			/**
			 * Get output streams that communicate with the client.
			 */
			PrintWriter connectionWriter = new PrintWriter(connectionSocket.getOutputStream(), true);

			/**
			 * Data is transmitted
			 */
			connectionWriter.println("Enter your name:");

			connectionWriter.println("good morning " + connectionReader.readLine());

			/**
			 * Input streams close the connection
			 */
			connectionWriter.close();

			/**
			 * Output streams close the connection
			 */
			connectionReader.close();

			/**
			 * Soket close the connection
			 */
			connectionSocket.close();

			/**
			 * Output streams close the connection
			 */
			serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
