package Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Created by Ramzah Rehman on 11/17/2016.
 */
//java.io.EOFException sender closed socket

public class Server {

    private Registrar registrar;

    public void startService(int choice) throws Exception{

        if(choice==0) {

            int port=9090;
            String IP="224.1.2.3";

            registrar = new SocketRegistrar(this,IP, port);

            Thread threadRegistrar = new Thread(registrar);

            threadRegistrar.start();

        }
        else if(choice ==1){

        }
    }
}
