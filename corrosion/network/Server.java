package corrosion.network;

import java.util.ArrayList;
import java.net.*;
import java.io.IOException;

import corrosion.entity.Entity;
import corrosion.network.protocol.*;
import corrosion.network.Connection;

public class Server{
  private static Server server;

  private ServerSocket socket;
  private ArrayList<Connection> clients = new ArrayList<Connection>();
  private ArrayList<Entity> activeEntities = new ArrayList<Entity>();
  private ArrayList<Entity> newEntities = new ArrayList<Entity>();
  private ArrayList<Entity> disposeEntities = new ArrayList<Entity>();

  public Server(int port){
    try{
      socket = new ServerSocket(port);
    } catch(IOException e){
      System.out.println("Error creating server: " + e);
      System.exit(-1);
    }
    newClientListener();
  }

  public void newClientListener(){
    while(true){
      Socket newClient = null;
      try{
        newClient = socket.accept();
      } catch(IOException e){}
      if (newClient != null){
        System.out.println("New Client: " + newClient);
        synchronized (clients){
          clients.add(new Connection(newClient));
        }
      }
    }
  }

  public static void main(String[] args){
    Protocol.init();
    server = new Server(1234);
  }
}
