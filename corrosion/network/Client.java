

package corrosion.network;

import corrosion.network.protocol.*;
import corrosion.network.Connection;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;

public class Client{
  private static Client client;
  private Connection connection;

  public static Client getClient(){
    return client;
  }

  public Client(String ip, int port){
    Protocol.init();
    try{
      connection = new Connection(ip, port);
    } catch(Exception e){System.out.println(e);}
    client = this;
  }
}
