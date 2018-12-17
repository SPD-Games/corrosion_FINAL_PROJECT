/**
* Michael Metzinger
* Dec 14 2018
* The client that connect to the server
*/

package corrosion.network;

import corrosion.entity.player.*;
import corrosion.network.protocol.*;
import corrosion.network.Connection;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;

public class Client{
  private static Client client;
  private Connection connection;
  private ArrayList<Player> players = new ArrayList<Player>();
  /**
  * Gets the client
  * @return the client
  */
  public static Client getClient(){
    return client;
  }

  public static void setPlayers(ArrayList<Player> p){
      client.players = p;
  }

  public static void getPlayers(){
    return client.players;
  }

  public static send(int protocol, Object data){
    Protocol.send(protocol, data, client.connection);
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
}
