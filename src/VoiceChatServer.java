
import java.net.*;
import java.io.*;

public class VoiceChatServer{

    static ClientThread threads[] = new ClientThread[5];

    public static void main(String[] args){
        try {
            ServerSocket listener = new ServerSocket(8888);
            System.out.println("Press any key to exit...");

            while (true) {
                new ClientThread(listener.accept(), threads).start();
            }
        }
        catch(IOException e){
        }

    }
}
