package Server;

import java.net.*;
import java.util.*;

class ClientTable{

    ArrayList <Socket>clientSockets;
    private int nextIndex;
    private static int size;

    public ClientTable (ArrayList<Socket> clientSockets){
        this.clientSockets=clientSockets;
        size=clientSockets.size();
        nextIndex=0;
    }

    public void addAtEnd(Socket socket){

        synchronized (clientSockets){
            clientSockets.add(socket);
            size+=1;
        }
    }
    public  void remove(Socket socket){

        synchronized(clientSockets){

            for(int i=0;i<size;i++){

                if(clientSockets.get(i)==socket){

                    clientSockets.remove(i);
                    size--;
                    if(i<nextIndex){
                        nextIndex--;
                    }
                    return;
                }
            }
        }



    }
    public Socket next(){

        Socket temp=null;

        synchronized(clientSockets){

            if(nextIndex<size) {
                temp= clientSockets.get(nextIndex);
                nextIndex++;
            }
        }
        return temp;
    }
}
