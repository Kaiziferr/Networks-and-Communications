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
 * @Autor: Steven Bernal Tovar (Kaiziferr)
 *         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Client {

	public static void main(String[] args) {
		try {

			/**
			 * The socket attempts to connect to the remote host.
			 */
			Socket clientSocket = new Socket(InetAddress.getLocalHost(), 1234);

			/**
			 * Get input streams that communicate with the client.
			 */
			BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			BufferedReader clientReader = new BufferedReader(new InputStreamReader(System.in));

			/**
			 * Get output streams that communicate with the client.
			 */
			PrintWriter socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);

			PrintWriter clientWriter = new PrintWriter(System.out, true);

			System.out.println("The has client initialized ...");

			clientWriter.println(socketReader.readLine());

			socketWriter.println(clientReader.readLine());

			clientWriter.println(socketReader.readLine());

			clientWriter.close();

			clientReader.close();

			socketWriter.close();

			socketReader.close();

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
