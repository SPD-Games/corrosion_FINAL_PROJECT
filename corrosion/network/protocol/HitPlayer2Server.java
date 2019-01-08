package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import corrosion.network.protocol.*;
import corrosion.network.*;
import corrosion.entity.player.*;

public class HitPlayer2Server extends Protocol{
  /**
  * Sends hit message to server
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      ArrayList d = (ArrayList)data;
      c.out.writeLong((Long)d.get(0));
      c.out.writeInt((Integer)d.get(1));
    } catch(Exception e){
      System.out.println("Error sending hit: " + e);
    }
  }

  /**
  * Gets hit message from client
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      long id = in.readLong();
      int damage = in.readInt();
      Server.hitPlayer(id, damage);
    }catch(Exception e){
      System.out.println("Error recieving hit: " + e);
    }
  }
}
