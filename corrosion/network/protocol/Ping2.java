/**
* Michael Metzinger
* Dec 14 2018
* Secondary ping message sending and receiving
*/

package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

public class Ping2 extends Protocol{

  /**
  * Sends the reply ping message
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      c.out.writeLong((Long)data);
    } catch(Exception e){
      System.out.println("Error sending ping" + e);
    }
  }

  /**
  * Gets the reply ping message
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      //displays the ping
      System.out.println("Ping: " + (System.currentTimeMillis() - in.readLong()));
    }catch(Exception e){
      System.out.println("Error getting ping" + e);
    }
  }
}
