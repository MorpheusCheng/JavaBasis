package session6;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: cyz
 * Date: 2019/9/14
 * Description:
 */
public class TCPServer {
    private TCPStatus status;
    private int ackNumber;

    private ServerSocket serverSocket;
    private Socket socket;
    private  static final int DEFAULT_PORT = 6666;
    private int port;

    public int getPort(){
        return port;
    }

    public TCPServer(){
        init(DEFAULT_PORT);
    }
    private void init(int port) {
        try{
            this.port = port;
            this.serverSocket = new ServerSocket(port);
            this.socket = serverSocket.accept();
            this.ackNumber = 0;
            listen();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //表示服务端准备好可以接收 TCP 连接了
    public void listen(){
        this.status = TCPStatus.LISTEN;
        System.out.println("server start to listen");
        try{
            Thread thread = new Thread(() -> {
                while (true){
                    Message recv = receiveMessage();
                    if (recv.SYN == 1){
                        this.status = TCPStatus.SYN_RCVD;
                        sendMessage(1,1,200,recv.seq + 1, 0);
                    }else if (recv.FIN == 1){
                        sendMessage(0,1 , 400 , recv.seq + 1 , 0);
                        this.status = TCPStatus.CLOSE_WAIT;
                        try {
                            Thread.sleep(3000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        sendMessage(0,1,500,recv.seq + 1 ,1);
                        Message message = receiveMessage();
                        if (message.ACK == 1){
                            this.status = TCPStatus.CLOSED;
                            try {
                                socket.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }else {

                    }
                }
            } );
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //实现发送给 TCPClient 的握手或者挥手逻辑
    public void sendMessage(int SYN , int ACK , int seq , int ack , int FIN){
        try{
            Message message = new Message();
            message.SYN = SYN;
            message.ACK = ACK;
            message.seq = seq;
            message.ack = ack;
            message.FIN = FIN;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("server send message: " + message);
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //实现接收来自 TCPClient 的握手或者挥手逻辑
    public Message receiveMessage(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            Message message = (Message) objectInputStream.readObject();
            System.out.println("server recv message: " + message);
            return message;
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
