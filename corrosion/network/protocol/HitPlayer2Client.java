package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import corrosion.network.protocol.*;
import corrosion.network.*;
import corrosion.entity.player.*;

public class HitPlayer2Client extends Protocol{
  /**
  * Sends hit message to client
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      c.out.writeInt((Integer)data);
    } catch(Exception e){
      System.out.println("Error sending hit: " + e);
    }
  }

  /**
  * Gets hit message from server
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      int damage = in.readInt();
      MainPlayer.getMainPlayer().hit(damage);
    }catch(Exception e){
      System.out.println("Error recieving hit: " + e);
    }
  }
}
