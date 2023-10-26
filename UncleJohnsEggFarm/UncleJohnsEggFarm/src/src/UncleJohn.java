package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
//Uncle John is waiting for the eggs
public class UncleJohn extends Thread {
	
	private ServerSocket _serverSocket;
	private Scanner _scanner;
	
	public UncleJohn(String ipAddress, int port) throws Exception
	{
		_serverSocket = new ServerSocket(port);
	}
	
	@Override
	public void run()
	{
		System.out.println("Uncle John is waiting for the eggs");
		while(true)
		{
			try {
				listen();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
    private void listen() throws Exception {
        String data = null;
        Socket client = _serverSocket.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        
        System.out.println("\r\nNew connection from " + clientAddress);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));        
        while ( (data = in.readLine()) != null ) {
            System.out.println("Egg from " + clientAddress + ": " + data + " was recepted by Uncle John.");
        }
    }
    
    public InetAddress getSocketAddress() {
        return this._serverSocket.getInetAddress();
    }
    
    public int getPort() {
        return this._serverSocket.getLocalPort();
    }
}
