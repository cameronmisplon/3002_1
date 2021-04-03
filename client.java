import java.net.*;
import java.io.*;
import java.util.*;

public class client{
	public static void main(String[] args){
		int port = 57234;
		byte[] message;
		String echo;
		String ip = " ";
		String connectionType;
		Console input = System.console();
		
		System.out.println("Welcome to the chat service Facebook wishes they made");
		connectionType = input.readLine("Are you on the same LAN as server: ( Yes(Y) or No(N) ) \n");
		if (connectionType.equals("Yes") || connectionType.equals("Y")) {
			ip = "127.0.1.1";
			port = 32517;
		}
		else if (connectionType.equals("No") || connectionType.equals("N")) {
			ip = "105.185.168.28";
		}
		try(Socket tcpsocket = new Socket(InetAddress.getByName("105.185.168.28"),57234))
		{
			InputStream tcpinput = tcpsocket.getInputStream();
			BufferedReader tcpreader = new BufferedReader(new InputStreamReader(tcpinput));
			System.out.println(tcpreader.readLine());
			InetAddress serveraddress = tcpsocket.getInetAddress();
			DatagramSocket udpsocket = new DatagramSocket();
			String text;
			tcpsocket.close();
			do{
				text = input.readLine("Enter text: ");
				message = text.getBytes();
				DatagramPacket packet = new DatagramPacket(message, message.length, serveraddress,port);
				udpsocket.send(packet);
				packet = new DatagramPacket(message,message.length);
				udpsocket.receive(packet);
				echo = new String(packet.getData(), 0, packet.getLength());
				System.out.println(echo);
			}
			while (!echo.equals("bye"));
			udpsocket.close();
		}
		catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
