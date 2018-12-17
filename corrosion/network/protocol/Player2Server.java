//TODO send equipped data
package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;

public class Player2Server extends Protocol{
  /**
  * Sends the
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      c.out.writeDouble(((Entity)data).getXPos());
      c.out.writeDouble(((Entity)data).getYPos());
      c.out.writeDouble(((Entity)data).getRotaion());
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
      double xPos, yPos, rotation;
      xPos = in.readDouble();
      yPos = in.readDouble();
      rotation = in.readDouble();

      //add a new player to send to others
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
