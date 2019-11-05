import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class UDPClient {

    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        int port = 65456;
        Scanner in = new Scanner(System.in);

        while(true) {
            String mess = in.nextLine();
            byte[] queryBuff = mess.getBytes();
            DatagramPacket query = new DatagramPacket(queryBuff, queryBuff.length, address, port);

            DatagramSocket socket = new DatagramSocket();

            socket.send(query);

            //System.out.println("Me: " + mess);

            byte[] buff = new byte[UDP.MAX_DATAGRAM_SIZE];
            DatagramPacket packet = new DatagramPacket(buff, buff.length);

            socket.receive(packet);

            String str = new String(packet.getData(), 0, packet.getLength()).trim();

            System.out.println("Server: " + str);

            socket.close();
        }
    }

}