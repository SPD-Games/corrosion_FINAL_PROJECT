//TODO send equipped data
package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

import corrosion.entity.player.*;
import corrosion.network.protocol.*;
import corrosion.network.*;
import corrosion.entity.Entity;
import corrosion.entity.item.equippable.Equippable;

public class Player2Client extends Protocol{
  /**
  * Sends a player from the server to the client
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      Player p = (Player)data;
      c.out.writeLong(p.getId());
      c.out.writeDouble(p.getXPos());
      c.out.writeDouble(p.getYPos());
      c.out.writeDouble(p.getRotaion());

      Equippable equipped = p.getEquipped();
      if (equipped == null){
        c.out.writeInt(0);
        return;
      }
      int[] state = equipped.sprite.getState();
      String className = equipped.getClass().toString().substring(39);

      c.out.writeInt(className.length());
      c.out.writeChars(className);
      c.out.writeInt(state[0]);
      c.out.writeInt(state[1]);

      //corrosion.entity.item.equippable."""BLAH"""

      //todo send equipped data
    } catch(Exception e){
      System.out.println("Server Sends Players" +e);
    }
  }

  /**
  * Gets the
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      long id = in.readLong();
      double xPos, yPos, rotation;
      xPos = in.readDouble();
      yPos = in.readDouble();
      rotation = in.readDouble();
      Player p = new Player(xPos, yPos, rotation, id);

      int classLength = in.readInt();
      if (classLength != 0){
        String className = "corrosion.entity.item.equippable.";
        for (int i = 0; i < classLength; ++i){
          className += in.readChar();
        }
        int[] state = {in.readInt(), in.readInt()};
        Class[] cl  = {Player.class, int[].class};
        Equippable q = (Equippable)Class.forName(className).getConstructor(cl).newInstance(p, state);
        p.setEquipped(q);
      }
      Client.setPlayer(p);
      //add a new player to send to others
    }catch(Exception e){
      System.out.println("Client Receive Players" +e);
    }
  }
}
