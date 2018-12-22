//sends class
//use ObjectOutputStream to send the object -- must implement serialized
package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;
import corrosion.entity.*;

class Entity2Server extends Protocol {
  /**
  * Sends data
  * @param data data to send
  * @param c the connection to send to
  */
  public void send(Object data, Connection c){
    try{
      ObjectOutputStream out = new ObjectOutputStream(c.out);
      out.writeObject(data);
    }catch(Exception e){
      System.out.println(e);
    }
  }

  /**
  * Gets data
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  public void get(DataInputStream in, Connection c){
    try{
      ObjectInputStream oin = new ObjectInputStream(in);
      Entity o = (Entity) oin.readObject();
      System.out.println(o);
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
