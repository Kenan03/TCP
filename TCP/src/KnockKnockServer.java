import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KnockKnockServer {
    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
//        int portNumber = 1233;
        acceptClient acp = new acceptClient(portNumber);
        acp.start();
    }
}
class acceptClient extends Thread{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private File file;

    private List<listClients> list;
    private BufferedReader in;
    int flag = 0;

    public acceptClient(int portNumber) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);
        list = new ArrayList<listClients>();
    }

    public void run() {
        System.out.println("Starting server");
        while(true) {
            try {
                this.clientSocket = serverSocket.accept();
                listClients book = new listClients(clientSocket, this, this.flag+1);
                this.list.add(flag, book);
//                listClients lc = new listClients(clientSocket, this, this.flag + 1);
//                this.clientlist.add(flag, lc);
                this.flag++;
            } catch (IOException e) {
                System.out.println("error1");
                throw new RuntimeException(e);
            }
        }
    }
    public void sendMessageToAllClients(String msg, listClients cl) { // для отправления сообщений от пользователя
        for (listClients o : this.list) {
            if(!o.equals(cl))
                o.sendMsg(cl.getClientName() + ": " + msg);
        }
    }
    public void sendMessageToYourself(String msg, listClients cl) {
        for (listClients o : this.list) {
            if(o.equals(cl)) {
                o.sendMsg(msg);
                break;
            }
        }
    }
    public void sendMessageToOneClients(String name, String msg1, listClients cl) { // для отправления сообщений for one пользователя
        for (listClients o : this.list) {
            if(o.getClientName().equals(name)) {
                o.sendMsg(cl.getClientName() + ": " + msg1);
            }
        }
    }
    public void removeClient(listClients client) {
        this.flag--;
        this.list.remove(client);
    }
    public void sendMessageFromServer(String msg) {
        for (listClients o : this.list) {
            o.sendMsg("Server: " + msg + list .size());
        }
    }
}



















//
//
//
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class KnockKnockServer {
//
//    private ExecutorService executorService;
//    private List<KnockKnockClient> clientList;
//    private int listeningPort;
//    private BufferedReader nameReader;
//
//    private AtomicInteger clientCount;
//    private static Lock clientListLock = new ReentrantLock();
//
//    public KnockKnockServer(int listeningPort) {
//        this.listeningPort = listeningPort;
//        this.executorService = Executors.newCachedThreadPool();
//
//        this.clientList = new ArrayList<>();
//        this.clientCount = new AtomicInteger(0);
//
//        try{
//            ServerSocket serverSocket = new ServerSocket(listeningPort);
//            System.out.println("Server is running...");
//            Socket clientSocket = null;
//
//            while (true) {
//                clientSocket = serverSocket.accept();
//
//                this.nameReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                String clientName = this.nameReader.readLine();
//
//                KnockKnockClient client = new KnockKnockClient(clientSocket, this, clientName);
//                this.clientList.add(client);
//
//                this.executorService.execute(client);
//            }
//
//        } catch (IOException exception) {
//            System.out.println("Error when creating a server socket!!!");
//            exception.printStackTrace();
//            this.executorService.shutdown();
//        }
//    }
//
//    public void sendMessageToAllClients(String msg) { // для отправления сообщений от сервака
//        String finalMsg = "Server: " + msg;
//        for (KnockKnockClient o : this.clientList) {
//            o.sendMsg(finalMsg);
//        }
//    }
//
//    public void sendMessageToAllClients(String msg, KnockKnockClient clientHandler) { // для отправления сообщений от пользователя
//        for (KnockKnockClient o : this.clientList) {
//            if(!o.equals(clientHandler))
//                o.sendMsg(clientHandler.getClientName() + ": " + msg);
//        }
//    }
//
//    public void sendMessageToOneClient(String msg, KnockKnockClient clientHandler){
//        clientHandler.sendMsg(msg);
//    }
//
//    public void sendUserCommand(String msg, String nameOfClient){
//        this.findClientHandler(nameOfClient).sendMsg(msg);
//    }
//
//    public KnockKnockClient findClientHandler(String nameOfClient){
//        for(KnockKnockClient clientHandler : this.clientList)
//            if(clientHandler.getClientName().equals(nameOfClient))
//                return clientHandler;
//        return null;
//    }
//
//    // удаляем клиента из коллекции при выходе из чата
    //    public void removeClient(KnockKnockClient client) {
    //        clientListLock.lock();
    //        try {
    //            this.clientList.remove(client);
    //        }
    //        finally {
    //            clientListLock.unlock();
    //        }
    //    }
//
//    public AtomicInteger getClientCount() {
//        return clientCount;
//    }
//
//    public List<KnockKnockClient> getClientList() {
//        clientListLock.lock();
//        try {
//            return clientList;
//        }
//        finally {
//            clientListLock.unlock();
//        }
//    }
//}
