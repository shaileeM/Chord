package chord;

import java.net.*;   // Contains Socket classes
import java.io.*;    // Contains Input/Output classes
import java.nio.file.*;

class prog2{
    public static void main(String argv[]) throws IOException {
        ServerSocket s = new ServerSocket(8800);

        System.out.println("Server waiting for client on port " + s.getLocalPort());
        int count = 0;
        do {
            count = count + 1;
            Socket connected = s.accept();
            new clientThread(connected, count).start();
        } while (true);

    }
}

class clientThread extends Thread {

    Socket myclientSocket = null;
    int mycount;
    DataInputStream is = null;
    PrintStream os = null;


    public clientThread(Socket clientSocket, int count) {
        this.myclientSocket = clientSocket;
        this.mycount = count;
    }

    public void run() {
        try {

            System.out.println("New connection accepted " + myclientSocket.getInetAddress() + ": " + myclientSocket.getPort());

            BufferedReader reader;
            BufferedWriter writer;

            reader = new BufferedReader(new InputStreamReader(myclientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(myclientSocket.getOutputStream()));

            String data;
            String testdirectory = "file1.txt\nfile2.txt\nfile3.txt";
            do{
                data = reader.readLine();
                System.out.println("Received from " +mycount + ":" + data);
                if (data.equals("LIST")) {
                    writer.write(mycount + "\n"+"150 - Transfer Initiated."+"\n"+
                            "DATA " +returnDirectoryList().getBytes().length + "\n" +
                            returnDirectoryList() + "\r\n");
                } else {
                    writer.write("Server Echos to " + mycount + ":"+ data + "\n"+"This is a new line."+"\r\n");
                }
                writer.newLine();
                writer.flush();

            }while (data.equals("QUIT") == false);

            myclientSocket.close();
            System.exit(0);
        } catch (IOException ex) {
        }
    }
    private String returnDirectoryList()
    {
        String files = "";
        Path dir = Paths.get(".");
        try {
            DirectoryStream<Path> stream =
                    Files.newDirectoryStream(dir);
            for (Path file: stream) {
                files = files + file.getFileName() +"\n";
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println("returnDirectoryList "+x.toString());
        }
        return files;
    }
}