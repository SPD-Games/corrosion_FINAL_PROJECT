/**
* Michael Metzinger
* Dec 20 2018
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
import corrosion.entity.projectile.*;
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

  public static void addEntity(Entity e){
    server.newEntities.add(e);
    synchronized (server.activeEntities){
      int i = server.activeEntities.indexOf(e);
      if (i == -1){
        server.activeEntities.add(e);
      } else {
        server.activeEntities.set(i, e);
      }
    }
  }

  /**
  * Gets the next id for server generated entities
  * @return the next usable id for server generated entities
  */
  public long getId(){
    synchronized(idLock){
      return id++;
    }
  }

  /**
  * Gets the next id range for connected clients to use
  * @return the next id range for connected clients to use
  */
  public long getnextPlayerId(){
    synchronized(nextPlayerIdLock){
      long out = nextPlayerId;
      nextPlayerId += 1000000000000l;
      return out;
    }
  }

  public static void hitPlayer(long id, int damage){
    ArrayList out = new ArrayList();
    for (int iClient = 0; iClient < server.clients.size(); ++iClient){
      Connection c = server.clients.get(iClient);
      if (c.id == id){
        Protocol.send(11, damage, c);
        return;
      }
    }

  }

  //send data loop
  private ActionListener sendLoopListener = new ActionListener(){
    @Override
    /**
    * Send all new data to the server
    */
    public void actionPerformed(ActionEvent arg0) {

      while (newEntities.size() != 0){
          for (int iClient = 0; iClient < clients.size(); ++ iClient){
            Connection c = clients.get(iClient);
            Entity e = newEntities.get(0);
            if (c.id != e.getId() - (e.getId()%1000000000000l)){
              Protocol.send(9, e, c);
            }
        }
        newEntities.remove(0);
      }

      //get new player list and clears it
      ArrayList<Player> players = getPlayers();
      server.players = new ArrayList<Player>();
      //send all new player data to all connected clients
      for (int iClient = 0; iClient < clients.size(); ++ iClient){
        for (int iPlayer = 0; iPlayer < players.size(); ++ iPlayer){
          Connection c = clients.get(iClient);
          Player p = players.get(iPlayer);
          //check if sending a player is not own by the client
          if (c.id != p.getId()){
            //sends the player to the client
            Protocol.send(3, p, c);
          }
        }
      }
    }
  };
  //sends new data to clients 64 times a second
  private Timer sendTimer = new Timer(1000/64, sendLoopListener);

  public static void forwardProjectiles(Projectile p, Connection from){
    for (int i = 0; i < server.clients.size(); ++i){
      Connection c = server.clients.get(i);
      if (c.id != from.id){
        Protocol.send(7,p,c);
      }
    }
  }

  /**
  * Adds a new player to be queued to sent
  * @param p new player to add to the queue
  */
  public static void setPlayer(Player p){
    //TODO binary insert
    //checks if it is already in the server
    int n = server.players.indexOf(p);
    if (n == -1){
      //not it - add it to the queue
      server.players.add(p);
    } else {
      //is in - replace it
      server.players.set(n, p);
    }
  }

  /**
  * Gets the queue of players ready to be sent to clients
  * @return a list of players
  */
  public static ArrayList<Player> getPlayers(){
    return server.players;
  }

  /**
  * Removes a player from the server and messages it to all clients
  * @param p the player to remove
  */
  public static void removePlayer(Player p){
    //removes the player from the list
    server.players.remove(p);
    //send a message stating that the player has been removed to all connect clients
    for (int iClient = 0; iClient < server.clients.size(); ++ iClient){
      Protocol.send(5, p, server.clients.get(iClient));
    }
  }

  /**
  * Handles a disconnected client and informs all other clients that it has been removed
  * @param c a client connection that has disconnected
  */
  public static void closeConnection(Connection c){
    //messages that it disconnect
    System.out.println("Client Disconnected" + c.socket);
    try{
      //closes the connection
      c.socket.close();
    }catch(Exception e){}
    //removes the player and informs all other clients that it has disconnected
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
  * Send all current data to the new client
  * @param c the connection to send the data to
  */
  public void newClient(Connection c){
    Protocol.send(4, c.id, c);

    for (int iEntity = 0; i < activeEntities.size(); ++iEntity){
      Protocol.send(9,activeEntities.get(iEntity),c);
    }
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

        //sends all server data to the new client
        class NewClient implements Runnable{
          public void run(){
            newClient(c);
          }
        }
        new Thread(new NewClient()).start();
      }
    }
  }

  /**
  * Starts a new server
  * @param args command line arguments
  */
  public static void main(String[] args){
    new Server(1234);
  }
}
