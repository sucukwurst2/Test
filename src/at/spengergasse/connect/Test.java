package at.spengergasse.connect;

import java.io.IOException;
import java.net.UnknownHostException;

public class Test {
	public static void main(String[]args) {
		    Client client = new Client();
		    try {
				client.startConnection("127.0.0.1", 6666);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				String response = client.sendMessage("hello server");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   // assertEquals("hello client", response);
		}
}
