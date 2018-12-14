
package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

public class PingReceive extends Protocol{
  public void send(Object data, Connection c){
    try{
    c.out.writeLong((Long)data);
  }catch(Exception e){}
  }

  public void get(DataInputStream in, Connection c){
    try{
    System.out.println("Ping: " + (System.currentTimeMillis() - in.readLong()));
    }catch(Exception e){}
  }
}
