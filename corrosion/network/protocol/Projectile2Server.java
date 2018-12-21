/**
* Michael Metzinger
* Dec 20 2018
*
*/

package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

public class Projectile2Server extends Protocol{

  /**
  * Sends an projectile to the server
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{

    } catch(Exception e){
      System.out.println("Error sending projectile" + e);
    }
  }

  /**
  * Gets an projectile message from the client
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      System.out.println("New Projectile");
    }catch(Exception e){
      System.out.println("Error getting projectile" + e);
    }
  }
}
