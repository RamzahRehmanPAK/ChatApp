package Server;

/**
 * Created by Ramzah Rehman on 11/17/2016.
 */
public abstract class Registrar implements Runnable {
    private Server server;
    public Registrar(Server server){
        this.server=server;
    }
}
