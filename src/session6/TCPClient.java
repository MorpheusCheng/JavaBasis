package session6;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Author: cyz
 * Date: 2019/9/14
 * Description:
 */
public class TCPClient {
    private TCPStatus status;
    private int ackNumber;
    private Socket socket;

    public TCPClient(){
        this.status = TCPStatus.CLOSED;
        this.ackNumber = 0;
    }

    //这里是入口，表示建立连接，主要逻辑都在 sendMessage 和  recieveMessage。每次状态变化都打印出来信息和状态。
    public void connect(int port){
        try {
            this.socket = new Socket("127.0.0.1",port);
            status = TCPStatus.SYN_SENT;
            sendMessage(1,0,100,0,0);
            Message recv = receiveMessage();
            if (recv.SYN == 1 && recv.ACK == 1 && recv.ack == 101){
                sendMessage(0,1,recv.ack , recv.seq + 1 ,0);
                status = TCPStatus.ESTABLISHED;
            }else {
                System.out.println("连接失败！");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //这里是入口吧，表示断开连接，主要逻辑都在 sendMessage 和  recieveMessage，每次状态变化都打印出来信息和状态。
    public void disconnect(){
        try {
            sendMessage(0,0,300,0,1);
            this.status = TCPStatus.FIN_WAIT_1;
            Message message = receiveMessage();
            if (message.ACK == 1 && message.ack == 301){
                this.status = TCPStatus.FIN_WAIT_2;
                Message message1 = receiveMessage();
                if (message1.FIN == 1 && message1.ACK == 1){
                    sendMessage(0,1,message1.ack,message1.seq + 1,0);
                    this.status = TCPStatus.CLOSED;
                    socket.close();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //实现发送给 TCPServer 的握手或者挥手逻辑
    public void sendMessage(int SYN, int ACK, int seq, int ack, int FIN){
        try{
            Message message = new Message();
            message.SYN = SYN;
            message.ACK = ACK;
            message.seq = seq;
            message.ack = ack;
            message.FIN = FIN;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("client send message: " + message);
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //实现接收来自 TCPSerer 的握手或者挥手逻辑
    public Message receiveMessage(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            Message message = (Message) objectInputStream.readObject();
            System.out.println("client recv message: " + message);
            return message;
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
