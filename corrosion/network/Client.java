/**
* Michael Metzinger
* Dec 14 2018
* The client that connect to the server
*/

package corrosion.network;

import corrosion.network.protocol.*;
import corrosion.network.Connection;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;

public class Client{
  private static Client client;
  private Connection connection;

  /**
  * Gets the client
  * @return the client
  */
  public static Client getClient(){
    return client;
  }

  /**
  * Main Constructor
  * @param ip the ip of the server to connect to
  * @param port the port of the server to connect to
  */
  public Client(String ip, int port){
    Protocol.init();
    try{
      //creates a connection to the server
      connection = new Connection(ip, port);
    } catch(Exception e){
      System.out.println(e);
    }
    client = this;
  }

  public static void main(String[] args)throws Exception{
    Client c = new Client("127.0.0.1", 1234);
    while(true){
      Thread.sleep(1000);
      Protocol.send(0, null, c.connection);
    }
  }
}
