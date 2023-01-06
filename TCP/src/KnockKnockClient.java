import java.net.*;
import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


public class KnockKnockClient {

    public static void main(String[] args) throws IOException {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
//        String hostName = "127.0.0.1";
//        int portNumber = 1233;
        Scanner console = new Scanner(System.in);
        String clientName = "";
        String choose;
        System.out.println("Hello! This application is a chat of more people");
        System.out.print("1. Set user name: '@name Kenan'\n" + "2. Send message to one person: '@sendUser '\n" + "3. Exit: '@quit'\nSelect action: ");
        choose = console.nextLine();
        switch (choose) {
            case "@hello":
                Random random = new Random();
                int number = random.nextInt(99) + 1;
                clientName = "User_" + number;
                break;
            case "@quit":
                System.out.println("Thanks for contacting!");
                System.exit(1);
                break;
            default:
                StringTokenizer stringTokenizer = new StringTokenizer(choose);
                if (Objects.equals(stringTokenizer.nextToken(), "@name")) {
                    clientName = stringTokenizer.nextToken();
                } else {
                    System.out.println("Incorrect input!");
                    System.exit(1);
                }
                break;
        }
        Socket kkSocket = new Socket(hostName, portNumber);
        Write write = new Write(kkSocket, clientName);
        Read read = new Read(kkSocket);
        PrintWriter nameSender = new PrintWriter(kkSocket.getOutputStream(), true);
        nameSender.println(clientName);
        write.start();
        read.start();

    }
}

class Write extends Thread{
    private Socket clientSocket;
    PrintWriter out;
    BufferedReader stdIn;
    private String clientName;

    String fromUser = null;
    public Write(Socket clientSocket, String clientName) throws IOException {
        this.clientName = clientName;
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            while (true) {
                String message = this.stdIn.readLine();
                this.out.println(message);
                if (message.equals("@quit")) {
                    System.out.println("Good Bye " + this.clientName);
                    this.out.close();
                    this.stdIn.close();
                    System.exit(1);
                }
            }
        }
        catch (IOException exception){
            System.out.println("Sending message error!");
            System.exit(1);
        }
    }
}
class Read extends Thread{
    private Socket clientSocket;
    private BufferedReader in;
    String fromServer;

    public Read(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    public void run() {
        try {
            while (true){
            fromServer = null;
                if((fromServer = in.readLine()) != null){
                    System.out.println(fromServer);
                }
            }
        }
        catch (IOException exception) {
            System.out.println("Receive message from server error! Server crashed:(");
            System.exit(1);
        }
    }
}

















































//class WriteForLibrary extends Thread{
//    Socket clientSocket;
//    PrintWriter printWriter;
//    BufferedReader bufferedReader;
//    String clientName;
//    public WriteForLibrary(Socket clientSocket, String clientName) throws IOException {
//        this.clientSocket = clientSocket;
//        this.clientName = clientName;
//        this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
//        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//    }
//
//    public void run(){
//        try {
//            while (true){
//                String in = this.bufferedReader.readLine();
//                this.printWriter.println(in);
//                if(in.equals("@quit")){
//                    System.out.println("Good bye " + clientName);
//                    this.printWriter.close();
//                    this.bufferedReader.close();
//                    System.exit(1);
//                }
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
//class ReadForLibrary extends Thread{
//    Socket clientSocket;
//    BufferedReader in;
//    String fromServer;
//    public ReadForLibrary(Socket clientSocket){
//        this.clientSocket = clientSocket;
//        in = new BufferedReader(new InputStreamReader(System.in));
//    }
//
//    public void run(){
//        try {
//            while (true){
//            fromServer = null;
//                if((fromServer = in.readLine()) != null){
//                    System.out.println(fromServer);
//                }
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}





//System.out.println("Hello! This application is a chat of more people");
//        System.out.print("1. Set user name: '@name Kenan'\n" + "2. Start a chatting with default name: '@hello'\n" + "3. Library: '@library'\n" + "4. Exit: '@quit'\nSelect action: ");
//
//switch (choose) {
//
//        }
//
//
//case "@hello":
//        Random random = new Random();
//        int number = random.nextInt(99) + 1;
//        clientName = "User_" + number;
//        break;
//        case "@quit":
//        System.out.println("Thanks for contacting!");
//        System.exit(1);
//        case "@library":
//        flag = 1;
//        break;
//default:
//        StringTokenizer stringTokenizer = new StringTokenizer(choose);
//        if (Objects.equals(stringTokenizer.nextToken(), "@name")) {
//        clientName = stringTokenizer.nextToken();
//        } else {
//        System.out.println("Incorrect input!");
//        System.exit(1);
//        }
//        break;











//
//System.out.println("Hello! This is the best Library. Write your name: exp: '@name Kenan'");
//        choose = console.nextLine();
//        StringTokenizer stringTokenizer = new StringTokenizer(choose);
//        if (Objects.equals(stringTokenizer.nextToken(), "@name")) {
//        clientName = stringTokenizer.nextToken();
//        } else {
//        System.out.println("Incorrect input!");
//        System.exit(1);
//        }
//        System.out.print("1. To take book: '@takeBookName nameBook'\n" + "2. To return book: '@returnBookName nameBook'\n" + "3. Book's list: '@list'\n" + "4. Exit: '@quit'\nSelect action: ");










