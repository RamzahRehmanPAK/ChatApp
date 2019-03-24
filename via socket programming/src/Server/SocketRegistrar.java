package Server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Ramzah Rehman on 11/17/2016.
 */
public class SocketRegistrar extends Registrar {

    private Server server;
    private MulticastSocket multicastSocket;
    String MulticastIP;
    private ArrayList<Socket> clientSockets;


    SocketRegistrar(Server server,String IP,int port) throws IOException{
        super(server);

        multicastSocket=new MulticastSocket(port);
        this.MulticastIP=IP;

        clientSockets=new ArrayList<>();

    }
    public void run(){

            try {

                byte [] input;

                String clientName;
                int clientPort;
                InetAddress clientIP;
                DatagramPacket receivedPacket;

                InetAddress group=InetAddress.getByName(MulticastIP);

                multicastSocket.joinGroup(group);

                Socket clientSocket;
                UserThread threadForClient;

                ClientTable table=new ClientTable(clientSockets);

                while(true){

                    input=new byte[100];
                    receivedPacket=new DatagramPacket(input,input.length);

                    multicastSocket.receive(receivedPacket);


                    String str []=new String(input).split(":");


                    clientName=str[0];
                    clientIP=InetAddress.getByName(str[1]);
                    clientPort=Integer.valueOf(str[2]);

                    try{
                        Thread.sleep(250);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                    clientSocket=new Socket(clientIP,clientPort);

                    table.addAtEnd(clientSocket);

                    threadForClient=new SocketUserThread(clientSockets,clientSocket,clientName,server);

                    threadForClient.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
