/**
* Michael Metzinger
* Dec 14 2018
* The main server that handles sending and recieving data from clients
*/

package corrosion.network;

import java.util.ArrayList;
import java.net.*;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.network.protocol.*;
import corrosion.network.Connection;

public class Server{
  private static Server server;

  private ServerSocket socket;
  private ArrayList<Connection> clients = new ArrayList<Connection>();
  private ArrayList<Player> players = new ArrayList<Player>();
  private ArrayList<Entity> activeEntities = new ArrayList<Entity>();
  private ArrayList<Entity> newEntities = new ArrayList<Entity>();
  private ArrayList<Entity> disposeEntities = new ArrayList<Entity>();
  private long id = 0;
  private final Object idLock = new Object();
  private long nextPlayerId = 1000000000000l;
  private final Object nextPlayerIdLock = new Object();

  public long getId(){
    synchronized(idLock){
      return id++;
    }
  }

  public long getnextPlayerId(){
    synchronized(nextPlayerIdLock){
      long out = nextPlayerId;
      nextPlayerId += 1000000000000l;
      return out;
    }
  }

  //send data loop
  private ActionListener sendLoopListener = new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent arg0) {
      /*
      while (newEntities.size() != 0){
        for (Connection c : clients){
          Protocol.send();
        }
      }*/
      for (int iClient = 0; iClient < clients.size(); ++ iClient){
        for (int iPlayer = 0; iPlayer < players.size(); ++ iPlayer){
          Connection c = clients.get(iClient);
          Player p = players.get(iPlayer);

          if (c.id != p.getId()){
            Protocol.send(3, p, c);
          }
        }
      }
    }
  };
  private Timer sendTimer = new Timer(1000/32, sendLoopListener);

  public static void setPlayer(Player p){
    int n = server.players.indexOf(p);
    if (n == -1){
      server.players.add(p);
    } else {
      server.players.set(n, p);
    }
  }

  public static ArrayList<Player> getPlayers(){
    return server.players;
  }

  public static void removePlayer(Player p){
    server.players.remove(p);
    for (int iClient = 0; iClient < server.clients.size(); ++ iClient){
      Protocol.send(5, p, server.clients.get(iClient));
    }
  }

  public static void closeConnection(Connection c){
    System.out.println("Client Disconnected" + c.socket);
    try{
      c.socket.close();
    }catch(Exception e){}
    removePlayer(new Player(0,0,0,c.id));
    server.clients.remove(c);
  }

  /**
  * Main Constructor
  * @param port the port which to run the server socket on
  */
  public Server(int port){
    server = this;
    Protocol.init();
    try{
      //creates a new server socket
      socket = new ServerSocket(port);
    } catch(IOException e){
      System.out.println("Error creating server: " + e);
      System.exit(-1);
    }
    //listens for new clients
    sendTimer.start();
    newClientListener();
  }

  /**
  * Listens for new clients to connect and adds them to the client list
  */
  public void newClientListener(){
    //loops until server quits
    while(true){
      Socket newClient = null;
      try{
        //wait till a new client connects
        newClient = socket.accept();
      } catch(IOException e){System.out.println("Error adding new client" + e);}
      if (newClient != null){
        //Send log message
        System.out.println("New Client: " + newClient);
        //adds the client connection to clients
        long id = getnextPlayerId();
        Connection c = new Connection(newClient, id);
        clients.add(c);

        //run in new thread after a sleep
        Protocol.send(4, id, c);
      }
    }
  }

  /**
  * Starts a new server
  */
  public static void main(String[] args){
    new Server(1234);
  }
}
