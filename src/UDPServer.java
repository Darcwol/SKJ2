import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;


public class UDPServer {
    private DatagramSocket server;
    private Scanner in;


    public UDPServer() throws SocketException  {
        initializeServer();
    }

    private void initializeServer() throws SocketException {
        server = new DatagramSocket();
        System.out.println("Server listens on: " + server.getLocalPort());
        in = new Scanner(System.in);
    }

    private void service() throws IOException {
        byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
        final DatagramPacket datagram = new DatagramPacket(buff, buff.length);

        server.receive(datagram);

        new Thread(() ->  {
            String n = new String(datagram.getData(), 0, datagram.getLength());
            System.out.println("Client: " + n);
            String response = in.nextLine();
            byte[] respBuff = response.getBytes();
            int clientPort = datagram.getPort();
            InetAddress clientAddress = datagram.getAddress();
            DatagramPacket resp = new DatagramPacket(respBuff, respBuff.length, clientAddress, clientPort);
            try {
                server.send(resp);
                //System.out.println("Me: " + response);
            } catch (IOException e) {
                // do nothing
            }
        }).start();
    }

    public void listen() {
        while(true) {
            try {
                service();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    public static void main(String[] args) {
        try {
            new UDPServer().listen();
        } catch (SocketException e) {
            System.out.println("Could not set up the server");
        }
    }

}