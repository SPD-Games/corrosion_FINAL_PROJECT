/**
* Michael Metzinger
* Dec 14 2018
* Handles all the socket sending and recieving
*/
package corrosion.network;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;

public class Connection{
  public Socket socket;
  public DataOutputStream out;
  public long id;
  private boolean isServer;

  /**
  * Secondary constructor
  * @param socket the conneted socket to send and receive data
  */
  public Connection(Socket socket){
    this(socket, -1);
  }

  /**
  * Main constructor
  * @param socket the conneted socket to send and receive data
  * @param id the id of the socket to use
  */
  public Connection(Socket socket, long id){
    isServer = true;
    this.id = id;
    this.socket = socket;
    try{
      //listens for new data
      new Thread(new Listener(this)).start();
      //creates a output stream
      out = new DataOutputStream(socket.getOutputStream());
    } catch(Exception e){
      System.out.println("Error creating new socket" + e);
    }
  }

  /**
  * Secondary constructor
  * @param ip the ip of a server to connect to
  * @param port the port of a server to connect to
  */
  public Connection(String ip, int port) throws Exception{
    this(new Socket(ip, port));
    isServer = false;
  }

  class Listener implements Runnable{
    private Connection connection;
    private DataInputStream in;

    /**
    * Main Constructor
    * @param c the connection to listen on
    */
    public Listener(Connection c) throws Exception{
      connection = c;
      //creats a new input stream
      in = new DataInputStream(c.socket.getInputStream());
    }

    /**
    * Listener loop
    */
    public void run(){
      int protolNumber = -1;
      try{
        //continues listening for incoming Protocols
        while(true){
          //gets the protocol number
          protolNumber = in.readInt();
          //runs the protocol data handler
          Protocol.get(protolNumber, in, connection);
        }
      }catch(Exception e){
        if (connection.isServer){
          Server.closeConnection(connection);
        } else {
          //main menu
          System.out.println("Error listening for data" + e + protolNumber);
        }
      }
    }
  }
}
