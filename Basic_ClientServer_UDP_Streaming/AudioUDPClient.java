package Basic_ClientServer_UDP_Streaming;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioUDPClient {

	/**
	 * Flujo de enrada por donde entran los datos enviados por el servidor
	 */
	private AudioInputStream audioInputStream;

	/**
	 * DatagramSocket recibe los paquetes de audio del server
	 */
	private DatagramSocket socketDatagramaClient;

	/**
	 * Una línea de datos de origen es una línea de datos en la que se pueden
	 * escribir datos. Actúa como fuente de su mezclador. Una aplicación escribe
	 * bytes de audio en una línea de datos de origen, que maneja el almacenamiento
	 * en búfer de los bytes y los entrega al mezclador.
	 */
	private SourceDataLine sourceDataLine;

	public AudioUDPClient() {
		initiateAudio();
	}

	/**
	 * crea un socket que se conecta al puerto 9786. Se crea un arreglo de bytes
	 * para mantener los datos de audio contenidos en el paquete UDP. Un bucle
	 * infinito recibirá paquetes del servidor, creará una instancia de
	 * AudioInputStream y luego llamará al método playAudio para reproducir el
	 * sonido. El paquete se crea en el siguiente código y luego se bloquea hasta
	 * que se recibe un paquete nuevo.
	 * 
	 * 
	 * Se extrae un arreglo de bytes del paquete. Se utiliza como argumento del
	 * constructor ByteArrayInputStream, que se utiliza, junto con la información de
	 * formato de audio, para crear el flujo de audio real. Esto está asociado con
	 * la instancia SourceDataLine, que se abre y se inicia.
	 * 
	 */
	private void initiateAudio() {
		try {
			socketDatagramaClient = new DatagramSocket(9786);
			byte[] audioBuffer = new byte[10000];
			while (true) {
				DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
				socketDatagramaClient.receive(packet);
				try {
					byte audioData[] = packet.getData();
					InputStream byteInputStream = new ByteArrayInputStream(audioData);
					AudioFormat audioFormat = audioFormat();
					audioInputStream = new AudioInputStream(byteInputStream, audioFormat,
							audioData.length / audioFormat.getFrameSize());
					DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
					sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
					sourceDataLine.open(audioFormat);
					sourceDataLine.start();
					playAudio();
				} catch (Exception e) {
					// Handle exceptions
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construye un formato con una codificación PCM lineal y los parámetros dados.
	 * El tamaño del cuadro se establece en el número de bytes necesarios para
	 * contener una muestra de cada canal, y la velocidad del cuadro se establece en
	 * la frecuencia de muestreo.
	 * 
	 * sampleRate - el número de muestras por segundo
	 * 
	 * sampleSizeInBits - El número de bits en cada muestra.
	 * 
	 * channels - el número de canales (1 para mono, 2 para estéreo, etc.)
	 * 
	 * signed - Indica si los datos están firmados o no firmados.
	 * 
	 * bigEndian- indica si los datos para una sola muestra se almacenan en orden de
	 * bytes big-endian ( false significa little-endian). Big endian significa que
	 * el byte más significativo de una palabra se almacena en la dirección de
	 * memoria más pequeña y el byte menos significativo en la dirección de memoria
	 * más grande. El pequeño endián invierte este orden
	 * 
	 * @return formato del audio a utilizar.
	 */
	private AudioFormat audioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return audioFormat;
	}

	/**
	 * El método playAudio se utiliza para reproducir el sonido. AudioInputStream
	 * rellena un búfer, que se escribe en la línea de datos de origen. Esto
	 * reproduce efectivamente el sonido en los altavoces del sistema.
	 */
	private void playAudio() {
		byte[] buffer = new byte[10000];
		try {
			int count;
			while ((count = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(buffer, 0, count);
				}
			}
		} catch (Exception e) {
			// Handle exceptions
		}
	}

	public static void main(String[] args) {
		new AudioUDPClient();
	}

}
