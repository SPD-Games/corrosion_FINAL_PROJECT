//TODO send equipped data
package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import corrosion.entity.player.*;
import corrosion.network.protocol.*;
import corrosion.network.*;

public class Player2Client extends Protocol{
  /**
  * Sends a player from the server to the client
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      c.out.writeInt(((ArrayList) data).size());
      for (Player p : ((ArrayList)data)){
        c.out.writeDouble(p.getXPos());
        c.out.writeDouble(p.getYPos());
        c.out.writeDouble(p.getRotaion());
      }
      //todo send equipped data
    } catch(Exception e){
      System.out.println(e);
    }
  }

  /**
  * Gets the
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      ArrayList<Player> p = new ArrayList<Player>();
      int len = in.readInt();
      for(int i = 0; i < len; ++i){
        double xPos, yPos, rotation;
        xPos = in.readDouble();
        yPos = in.readDouble();
        rotation = in.readDouble();
        p.add(new Player(xPos, yPos, rotation));
      }
      Client.setPlayers(p);
      //add a new player to draw
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
