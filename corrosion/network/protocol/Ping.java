/**
* Michael Metzinger
* Dec 14 2018
* Initial ping message sending and receiving
*/

package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

public class Ping extends Protocol{

  /**
  * Sends an initial ping message
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      c.out.writeLong(System.currentTimeMillis());
    } catch(Exception e){
      System.out.println("Error sending ping" + e);
    }
  }

  /**
  * Gets an initial ping message
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      long n = in.readLong();
      //replies to the ping
      Protocol.send(1, n, c);
    }catch(Exception e){
      System.out.println("Error getting ping" + e);
    }
  }
}
