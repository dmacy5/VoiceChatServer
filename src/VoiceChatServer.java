
import java.net.*;
import java.io.*;

public class VoiceChatServer{

    static ClientThread threads[] = new ClientThread[5];
    public static boolean keepGoing;

    public static void main(String[] args){
        try {
            ServerSocket listener = new ServerSocket(8888);
            keepGoing = true;
            System.out.println("Press any key to exit...");
            listener.setSoTimeout(10);

            while (keepGoing) {
                try {
                    new ClientThread(listener.accept(), threads).start();
                } catch(SocketTimeoutException e){

                }
                if(System.in.available() != 0) {
                    listener.close();
                    throw new IOException() ;
                }
            }
        }
        catch(IOException e){
            for(int i = 0; i < threads.length; i++)
                if(threads[i] != null) {
                   threads[i].shutdown();
                }
            System.out.println("Closing server...");
        }

    }
}
