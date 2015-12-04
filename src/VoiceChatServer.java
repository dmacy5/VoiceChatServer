
import java.net.*;
import java.io.*;

public class VoiceChatServer{

    static ClientThread threads[] = new ClientThread[5];

    public static void main(String[] args){
        try {
            ServerSocket listener = new ServerSocket(9999);

            while (true) {
                new ClientThread(listener.accept(), threads).start();
            }
            //listener.close();
        }
        catch(IOException e){
        }

    }
}
