
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dmacy on 12/3/2015.
 */
public class ClientThread extends Thread{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ClientThread[] threads;

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

            while(true)
            {
                numOfBytesRead = input.read(data, 0, 1024);

                for(int i = 0; i < threads.length; i++)
                    if(threads[i] != null && threads[i] != this)
                        output.write(data, 0, numOfBytesRead);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
