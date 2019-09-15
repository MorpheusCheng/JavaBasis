package session6;

import java.io.Serializable;

/**
 * Author: cyz
 * Date: 2019/9/14
 * Description:
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public int SYN;
    public int ACK;
    public int FIN;
    public int ack;
    public int seq;
    @Override
    public String toString(){
        return "SYN："+SYN + " ACK：" + ACK + " FIN：" + FIN + " ack: " + ack + " seq: " + seq;
    }
}
