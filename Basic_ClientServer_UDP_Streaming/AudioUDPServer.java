package Basic_ClientServer_UDP_Streaming;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * @Descripción: Un servidor UDP capturará el sonido del micrófono y lo enviará
 *               a un cliente. El cliente UDP recibirá el audio y lo reproducirá
 *               en los altavoces del sistema. El audio se envia en paqetes al
 *               cliente. El cliente recibirá estos paquetes y los usará para
 *               reconstituir un flujo.
 * 
 * 
 * @Autor: Steven Bernal Tovar (Kaiziferr) Basado ejemplo de Richard M Reese
 *         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class AudioUDPServer {

	private final static byte AUDIO_BUFFER[] = new byte[10000];

	/**
	 * Origen del audio.
	 */
	private TargetDataLine targetDataLine;

	/**
	 * Datagrama de servidor, por el cual enviara información
	 */
	private DatagramSocket socketDatagrama;

	public AudioUDPServer() {
		setupAudio();
		broadcastAudio();
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
	 * Inicializar el audio y un método broadcastAudio para enviar este audio a un
	 * cliente
	 * 
	 * 
	 * La clase DataLine.info utiliza la información de formato de audio para crear
	 * una línea que represente el audio. Construye el objeto de información de una
	 * línea de datos a partir de la información especificada, que incluye un único
	 * formato de audio.
	 * 
	 * El método getLine de la clase AudioSystem devuelve una línea de datos que
	 * corresponde a un micrófono. La línea se abre y se inicia.
	 * 
	 */
	private void setupAudio() {
		try {
			AudioFormat audioFormat = audioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * El método broadcastAudio crea los paquetes UDP. Se crea un socket utilizando
	 * el puerto 8000 y se crea una instancia de InetAddress para la máquina actual.
	 * 
	 * Se introduce un bucle infinito en el que el método de lectura llena el array
	 * audioBuffer y devuelve el número de bytes leídos. Para recuentos superiores a
	 * 0, se crea un nuevo paquete utilizando el búfer y se envía al cliente que
	 * está escuchando en el puerto 9786
	 * 
	 * La TargetDataLineinterfaz proporciona un método para leer los datos
	 * capturados desde el búfer de la línea de datos de destino.
	 * 
	 */
	private void broadcastAudio() {
		try {
			socketDatagrama = new DatagramSocket(8000);
			InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
			while (true) {
				int count = targetDataLine.read(AUDIO_BUFFER, 0, AUDIO_BUFFER.length);
				if (count > 0) {
					DatagramPacket packet = new DatagramPacket(AUDIO_BUFFER, AUDIO_BUFFER.length, inetAddress, 9786);
					socketDatagrama.send(packet);
				}
			}
		} catch (Exception ex) {
			// Handle exceptions
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AudioUDPServer();
	}

}
