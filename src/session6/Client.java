package session6;

/**
 * Author: cyz
 * Date: 2019/9/14
 * Description:
 */
public class Client {
    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        client.connect(6666);
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        client.disconnect();
    }
}
