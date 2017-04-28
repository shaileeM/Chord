package chord;


import java.net.*;   // Contains Socket classes
import java.io.*;    // Contains Input/Output classes
import java.nio.CharBuffer;

public class prog1
{


    public static void main(String args[])
    {


        try{
//
            Socket client=new Socket("172.20.73.234", 8888);
            System.out.println("Connected to server " + client.getInetAddress()
                    + ": " + client.getPort());
            System.out.println("local port is " + client.getLocalPort());

            BufferedReader kbreader;
            BufferedWriter writer;
            BufferedReader reader;

            kbreader = new BufferedReader(new InputStreamReader(System.in));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String data = "", datakb, line=null;

            do{
                System.out.print("Text to the server? ");
                datakb = kbreader.readLine();
                writer.write(datakb);
                writer.newLine();
                writer.flush();

                System.out.print("Received from the Server:  ");
                line = reader.readLine();
                while (line.equals("")==false)
                {
                    System.out.println(line);
                    line = reader.readLine();
                }
            } while (datakb.trim().equals("QUIT")==false);
            client.close();
            System.exit(0);

        }catch(Exception e){
            System.err.println("Exception: " + e.toString());
        }
    }
}
