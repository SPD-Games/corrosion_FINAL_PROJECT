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
      ArrayList<Player> d = Server.getPlayers();
      synchronized(d){
        c.out.writeInt(d.size() - 1);
        //check if its a player arraylist
        int i = 0;
        for (Player p : d){
          if (i != c.id){
            c.out.writeDouble(p.getXPos());
            c.out.writeDouble(p.getYPos());
            c.out.writeDouble(p.getRotaion());

            Equippable equipped = p.getEquipped();
            if (equipped == null){
              c.out.writeInt(0);
            } else {
              int[] state = equipped.sprite.getState();
              String className = equipped.getClass().toString().substring(39);
              c.out.writeInt(className.length());
              c.out.writeChars(className);
              c.out.writeInt(state[0]);
              c.out.writeInt(state[1]);
            }
          }
          ++i;
        }
      }
      //todo send equipped data
    } catch(Exception e){
      System.out.println("Sever Sends Players" + e);
    }
  }

  /**
  * Gets the
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      //TODO ? change to a array
      ArrayList<Player> players = new ArrayList<Player>();
      int len = in.readInt();
      if (len == 0){return;}
      for(int i = 0; i < len; ++i){
        double xPos, yPos, rotation;
        xPos = in.readDouble();
        yPos = in.readDouble();
        rotation = in.readDouble();
        Player p = new Player(xPos, yPos, rotation);

        int classLength = in.readInt();
        if (classLength != 0){
          String className = "corrosion.entity.item.equippable.";
          for (int j = 0; j < classLength; ++j){
            className += in.readChar();
          }
          int[] state = {in.readInt(), in.readInt()};
          Class[] cl  = {Player.class, int[].class};
          Equippable q = (Equippable)Class.forName(className).getConstructor(cl).newInstance(p, state);
          p.setEquipped(q);
        }

        players.add(p);
      }
      Client.setPlayers(players);
      //add a new player to draw
    }catch(Exception e){
      System.out.println("Client Receive Players" + e);
    }
  }
}
