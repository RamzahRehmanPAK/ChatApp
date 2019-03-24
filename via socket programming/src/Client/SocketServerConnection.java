package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Ramzah Rehman on 11/17/2016.
 */
public class SocketServerConnection extends ServerConnection {


    public static boolean running;
    static Socket socket;

    int choice;
    public SocketServerConnection(int choice){
        this.choice=choice;
    }
    @Override
    public void send() throws Exception {


        DataOutputStream out=new DataOutputStream(socket.getOutputStream());

        while(running){

            synchronized (client.message){

                while(!client.message.isMessage){
                    client.message.wait();
                }
                client.message.isMessage=false;

            }
            out.writeUTF(client.text);
            if(client.text.equals("@@#DISCONNECT#")){
                disconnect();
            }
        }

    }

    @Override
    public void receive() throws Exception{

        try{
            DataInputStream input=new DataInputStream(socket.getInputStream());

            while(running){

                client.chatBox.append(input.readUTF() + "\n");

            }
        }
        catch (SocketException e){
            //e.printStackTrace();
        }
        catch(IOException e){
            //e.printStackTrace();
        }
        catch(Exception e){
           //e.printStackTrace();
        }
    }

    @Override
    public void register(String name) throws Exception{

        ServerSocket serverSocket=new ServerSocket(0);

        int port=serverSocket.getLocalPort();
        String IP=InetAddress.getLocalHost().toString();
        IP=IP.substring(IP.indexOf("/")+1);


        MulticastSocket multicastSocket=new MulticastSocket();

        String message=name+":"+IP+":"+port+":";

        byte data[]=message.getBytes();

        InetAddress group=InetAddress.getByName("224.1.2.3");


        DatagramPacket sendPacket=new DatagramPacket(data,data.length,group,9090);

        multicastSocket.send(sendPacket);

        socket=serverSocket.accept();
    }

    @Override
    public void disconnect() throws Exception {
        running = false;
        socket.close();
    }

    public  void run(){

        if(choice==0){
            //send;
            try{
                send();
            }
            catch (Exception e){

            }
        }
        else if(choice==1){
            //receive;
            try{
                receive();
            }
            catch (Exception e){

            }
        }
    }

}
