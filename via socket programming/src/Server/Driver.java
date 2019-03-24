package Server;
import java.util.Scanner;

public class Driver {
    public static void main(String args [] ){

        Server server=new Server();

        Scanner scan=new Scanner(System.in);

        System.out.println("Enter 0 for socketConnection, 1 for RMI.");
        int choice =scan.nextInt();

        try {
            server.startService(choice);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
