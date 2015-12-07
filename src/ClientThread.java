import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dmacy on 12/3/2015.
 */
public class ClientThread extends Thread{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ClientThread[] threads;
    public static boolean keepGoing;

    public ClientThread(Socket socket, ClientThread[] t){
        this.socket = socket;
        this.threads = t;

        for(int i = 0; i < threads.length; i++)
            if(threads[i] == null){
                threads[i] = this;
                break;
            }
    }

    public void run(){
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            byte[] data = new byte[1024];
            int numOfBytesRead;

            for(int i = 0; i < threads.length; i++)
                if(threads[i] == this) {
                    int ix = i+1;
                    System.out.println("User " + ix + " connected.");
                }

            while(true)
            {
                numOfBytesRead = input.read(data, 0, 1024);

                for(int i = 0; i < threads.length; i++)
                    if(threads[i] != null && threads[i] != this)
                        threads[i].output.write(data, 0, numOfBytesRead);
            }

        }
        catch(IOException e)
        {
            for(int i = 0; i < threads.length; i++)
                if(threads[i] == this) {
                    int user = i+1;
                    System.out.println("User " + user + " has disconnected.");
                    threads[i] = null;
                }
        }
    }

    public void shutdown()
    {
        try {
            input.close();
            output.close();
        } catch(IOException e){}
    }

}
