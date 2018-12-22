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
import corrosion.entity.projectile.*;

public class Projectile2Server extends Protocol{

  /**
  * Sends an projectile to the server
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      Projectile p = (Projectile) data;
      String className = p.getClass().toString().substring(34);
      c.out.writeInt(className.length());
      c.out.writeChars(className);
      c.out.writeDouble(p.getXPos());
      c.out.writeDouble(p.getYPos());
      c.out.writeDouble(p.getXVel());
      c.out.writeDouble(p.getYVel());
      c.out.writeDouble(p.getRotaion());
      c.out.writeLong(p.getId());
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
      int classLength = in.readInt();
      String className = "corrosion.entity.projectile.";
      for (int i = 0; i < classLength; ++i){
        className += in.readChar();
      }
      Class[] cl  = {double.class, double.class, double.class, double.class, double.class, long.class};
      Projectile p = (Projectile)Class.forName(className).getConstructor(cl).newInstance(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readLong());
      Server.forwardProjectiles(p, c);
    }catch(Exception e){
      System.out.println("Error getting projectile" + e);
    }
  }
}
