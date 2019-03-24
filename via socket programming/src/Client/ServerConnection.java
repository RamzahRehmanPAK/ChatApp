package Client;

import java.net.Socket;

/**
 * Created by Ramzah Rehman on 11/17/2016.
 */
public abstract class ServerConnection extends Thread {

    static Client client;

    public abstract void send() throws Exception;
    public abstract void receive()throws Exception;
    public abstract void register(String Name) throws Exception;
    public abstract void disconnect() throws Exception;
}
