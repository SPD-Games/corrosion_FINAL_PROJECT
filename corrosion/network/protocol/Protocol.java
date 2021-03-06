/**
* Michael Metzinger
* Dec 20 2018
* Abstract Protocol that handles all sending and recieving of data
*/

package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

abstract public class Protocol{
  private static Protocol[] protocols;
  /**
  * Loads the protocols
  */
  public static void init(){
    protocols = new Protocol[14];
    protocols[0] = new Ping();
    protocols[1] = new Ping2();
    protocols[2] = new Player2Server();
    protocols[3] = new Player2Client();
    protocols[4] = new InitId2Client();
    protocols[5] = new RemovePlayer2Client();
    protocols[6] = new Projectile2Server();
    protocols[7] = new Projectile2Client();
    protocols[8] = new Entity2Server();
    protocols[9] = new Entity2Client();
    protocols[10] = new HitPlayer2Server();
    protocols[11] = new HitPlayer2Client();
    protocols[12] = new RemoveEntity2Server();
    protocols[13] = new RemoveEntity2Client();
  }

  /**
  * Sends data to a connection
  *@param protocol the protocol to use
  *@param data the data to sends
  *@param c the connection to send data to
  */
  public static void send(int protocol, Object data, Connection c){
    //start new Thread
    class Send implements Runnable{
      public void run(){
        _send(protocol, data, c);
      }
    }
    new Thread(new Send()).start();
  }

  /**
  * Sends data to a connection
  *@param protocol the protocol to use
  *@param data the data to sends
  *@param c the connection to send data to
  */
  private static void _send(int protocol, Object data, Connection c){
    synchronized(c.out){
      try{
        //send the protocol number
        c.out.writeInt(protocol);
        //sends the data
        protocols[protocol].send(data, c);
      }catch(Exception e){
        //TODO check if pipe is broken then disconnect the client
        System.out.println("Error sending data to " + c.socket + " " + e);
      }
    }
  }

  /**
  * Reads data from a connection
  * @param protocol the protocol to use
  * @param in the input to read from
  * @param c the connection that sent the data
  */
  public static void get(int protocol, DataInputStream in, Connection c){
    protocols[protocol].get(in, c);
  }

  /**
  * Gets a protocol
  * @param i the protocol index
  */
  public static Protocol getProtocol(int i){
    return protocols[i];
  }

  /**
  * Sends data
  * @param data data to send
  * @param c the connection to send to
  */
  abstract public void send(Object data, Connection c);

  /**
  * Gets data
  * @param in the stream to listen on
  * @param c the connection to send to
  */
  abstract public void get(DataInputStream in, Connection c);
}
