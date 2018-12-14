
package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

public class PingSend extends Protocol{
  public void send(Object data, Connection c){
    try{
    c.out.writeLong(System.currentTimeMillis());
      }catch(Exception e){}
  }

  public void get(DataInputStream in, Connection c){
    try{
      long n = in.readLong();
      Protocol.send(1, new Long(n), c);
    }catch(Exception e){}
  }
}
