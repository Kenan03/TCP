import java.io.*;
import java.net.Socket;
import java.util.*;

class listClients extends Thread{
    private acceptClient server;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String message = null;
    private String clientName;
    private File file;
    int flag = 0;
//    private Map <String, Integer> map;
    public listClients(Socket clientSocket, acceptClient server, int flag) throws IOException {
        file = new File("/home/kenan/JavaProjects/TCP/books.txt");
//        file = new File("books.txt");
        this.flag = this.flag + flag;
        this.server = server;
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientName = in.readLine();
//        map = new HashMap<>();
        //message = this.in.readLine();
        out.println("Server: Hello to the chat @" + clientName + "!  Clients in chat = " + this.flag);
        start();

    }

    public void run() {
        String msg;
        int numberBook = 0;
        while (true) {
            try {
                msg = in.readLine();
//                System.out.println(msg);
                if(msg.contains("@sendUser")) {
                    String name = null;
                    StringTokenizer st = new StringTokenizer(msg);
                    for(int i = 0; i < st.countTokens(); i++) {
                        String t = st.nextToken();
                        if (i == 1) {
                            name = t;
                        }
                    }
                    String msg1 = msg.replaceFirst("@sendUser " + name, "");
                    server.sendMessageToOneClients(name, msg1, this);
                }
                else if(msg.contains("@takeBookName")){
                    String msg1 = msg.replaceFirst("@takeBookName ", "");
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    StringBuilder stringBuilder = new StringBuilder();
                    String read;
                    while((read = br.readLine()) != null) {
                        stringBuilder.append(read + "\n");
                    }
                    String book = msg1 + "\n";
                    int indexStart;
                    int indexEnd = 0;
//                    System.out.println(stringBuilder);
                    indexStart = stringBuilder.indexOf(book);
                    if(indexStart == -1){
                        server.sendMessageToYourself("We have don't such book", this);
                    }
                    else {
                        for (int i = 0; i < stringBuilder.length(); i++) {
                            if (i >= indexStart) {
                                if ((stringBuilder.charAt(i) == '-')) {
                                    System.out.println("this book busy");
                                    server.sendMessageToYourself("this book busy", this);
                                    break;
                                } else if ((stringBuilder.charAt(i)) == '\n') {
                                    indexEnd = i;
                                    stringBuilder.insert(indexEnd, " - " + this.getClientName());
                                    server.sendMessageToYourself("Enjoy reading!", this);
//                                    if(map.get(this.clientName) != null) {
//                                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                                            if (entry.getKey().equals(this.clientName)) {
//                                                numberBook = numberBook + 1;
//                                                entry.setValue(numberBook);
//                                                break;
//                                            }
//                                        }
//                                    }
//                                    else {
//                                        numberBook = numberBook + 1;
//                                        map.put(this.clientName, (numberBook));
//                                    }
                                    break;
                                }
                            }
                        }
                    }
//                    System.out.println(stringBuilder + " " + map.toString());
                    System.out.println(stringBuilder);
                    FileWriter fw = new FileWriter(file);
                    fw.write(String.valueOf(stringBuilder));
                    fw.flush();
                    fw.close();
                }
                else if(msg.contains("@returnBookName")){
                    String msg1 = msg.replaceFirst("@returnBookName ", "");
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    StringBuilder stringBuilder = new StringBuilder();
                    String read;
                    while ((read = br.readLine()) != null) {
                        stringBuilder.append(read + "\n");
                    }
                    String book = msg1 + " - " + this.getClientName() + "\n";
                    int indexStart;
                    int indexEnd = 0;
                    String r = String.valueOf(stringBuilder);
                    indexStart = stringBuilder.indexOf(book);
                    if (r.contains(book)){
                        indexStart = stringBuilder.indexOf(book);
                        for(int i = indexStart; i < stringBuilder.length(); i++) {
                            if(stringBuilder.charAt(i) == '\n'){
                                indexEnd = i;
                                break;
                            }
                        }
                        stringBuilder.delete(indexStart + msg1.length(), indexEnd);
//                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                            if (entry.getKey().equals(this.clientName)) {
//                                numberBook = numberBook - 1;
//                                entry.setValue(numberBook);
//                                break;
//                            }
//                        }
                    }
                    else{
                        server.sendMessageToYourself("You don't have such book", this);
                    }
//                    System.out.println(stringBuilder + " " + map.toString());
                    System.out.println(stringBuilder);
                    FileWriter fw = new FileWriter(file);
                    fw.write(String.valueOf(stringBuilder));
                    fw.flush();
                    fw.close();
                }
                else if(msg.equals("@list")) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String read;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n");
                    while((read = br.readLine()) != null) {
                        if(read.contains("-")) {}
                        else {
                            stringBuilder.append(read + "\n");
                        }
                    }
                    server.sendMessageToYourself(String.valueOf(stringBuilder), this);
                }

                else if(msg.equals("@quit")) {
//                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                        if (entry.getKey().equals(this.clientName)) {
//                            if(entry.getValue() == 0){
//                                map.remove(entry.getKey());
//                            }
//                        }
//                    }
//                    System.out.println(map.toString());
                    this.flag--;
                    String mess = "Client @" + getClientName() + " out from chat :(  " + "Clients in chat = ";
                    this.server.removeClient(this);
                    this.server.sendMessageFromServer(mess);
                    this.clientSocket.close();
                    this.in.close();
                    this.out.close();
                    break;
                }
                else {
                    server.sendMessageToAllClients(msg, this);
                }
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }
    public void sendMsg(String msg) {
        try {
            out.println(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String getClientName() {
        return clientName;
    }
}




















