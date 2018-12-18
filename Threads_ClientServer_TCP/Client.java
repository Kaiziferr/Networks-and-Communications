package Threads_ClientServer_TCP;

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

			Socket clientSocket = new Socket(InetAddress.getLocalHost(), 1234);

			BufferedReader readerSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			BufferedReader readerClient = new BufferedReader(new InputStreamReader(System.in));

			PrintWriter writerSocket = new PrintWriter(clientSocket.getOutputStream(), true);

			PrintWriter writerClient = new PrintWriter(System.out, true);

			System.out.println("The has client initialized ...");

			writerClient.println(readerSocket.readLine());

			boolean closeOperation = false;
			while (!closeOperation) {
				String comando = readerClient.readLine();

				if (comando.equals("exit")) {
					closeOperation = true;
					writerSocket.println(comando);
					writerClient.println("successful disconnection");
				} else {
					writerSocket.println(comando);

					writerClient.println(readerSocket.readLine());
				}

			}

			readerClient.close();
			readerSocket.close();
			writerSocket.close();
			writerClient.close();
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
