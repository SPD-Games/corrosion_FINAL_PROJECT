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
import corrosion.entity.player.*;


public class RemovePlayer2Client extends Protocol{

  /**
  * Sends an initial id
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      c.out.writeLong(((Player)data).getId());
    } catch(Exception e){
      System.out.println("Error sending init id" + e);
    }
  }

  /**
  * Gets an initial id
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      long id = in.readLong();
      //replies to the ping
      Client.removePlayer(new Player(0,0,0,id));
    }catch(Exception e){
      System.out.println("Error getting init id" + e);
    }
  }
}
