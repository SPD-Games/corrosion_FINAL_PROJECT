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
import java.io.File;
import java.util.Scanner;
import corrosion.entity.Entity;
import corrosion.entity.projectile.*;
import corrosion.entity.player.*;
import corrosion.network.protocol.*;
import corrosion.network.Connection;
import corrosion.entity.*;
import java.util.Random;

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

  /**
  * add an entity
  * @param e the entity to add
  */
  public static void addEntity(Entity e){
    //add a  entity that needs to be sent to other players
    server.newEntities.add(e);
    synchronized (server.activeEntities){
      //add to active entities
      int i = server.activeEntities.indexOf(e);
      //checks if entity is not already in the list
      if (i == -1){
        //add the entity
        server.activeEntities.add(e);
      } else {
        //change the active entity
        server.activeEntities.set(i, e);
      }
    }
  }

  /**
  * remove an entity
  * @param e the entity to add
  */
  public static void removeEntity(Entity e){
    //removes the entity from new entities
    synchronized(server.newEntities){
      server.newEntities.remove(e);
    }
    //removes the entity from active entities
    synchronized(server.activeEntities){
      server.activeEntities.remove(e);
    }
    //add the entity to be in the list that send out to all clients informing the removal of the entity
    synchronized(server.disposeEntities){
      server.disposeEntities.add(e);
    }
    class Wait extends Thread{
      Entity entity;
      /**
      * Main Constructor
      */
      public Wait(Entity entity){
        this.entity = entity;
      }
      /**
      * Waits for 5 minutes then respawns
      */
      public void run(){
        try{
          //wait 5 minutes
          Thread.sleep(5*60*1000);
        } catch (Exception e){

        }
        //respawn
        addEntity(entity);
      }
    }

    //checks if the entity being removed is a respawnable item
    if (e instanceof Barrel){
      e = new Barrel(e.getXPos(),e.getYPos(),e.getRotation(),e.getId());
    } else if (e instanceof Crate){
      e = new Crate(e.getXPos(),e.getYPos(),e.getRotation(),e.getId());
    } else if (e instanceof Tree){
      e = new Tree(e.getXPos(),e.getYPos(),e.getRotation(),e.getId());
    } else if (e instanceof Rock){
      e = new Rock(e.getXPos(),e.getYPos(),e.getRotation(),e.getId());
    } else {
      return;
    }
    //respawns the entity in 5 minutes
    new Wait(e).start();
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

  /**
  * hit the player
  * @param id the id of player/item
  * @param damage the damage that is dealt
  */
  public static void hitPlayer(long id, int damage){
    ArrayList out = new ArrayList();
    //iterates through all players
    for (int iClient = 0; iClient < server.clients.size(); ++iClient){
      Connection c = server.clients.get(iClient);
      //checks if the id of the damaged player is the same as the client
      if (c.id == id){
        //send to client
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
      //iterates through all new intities
      while (newEntities.size() != 0){
        //iterates through all clients
          for (int iClient = 0; iClient < clients.size(); ++ iClient){
            //send data to client
            Connection c = clients.get(iClient);
            Entity e = newEntities.get(0);
            Protocol.send(9, e, c);
        }
        //remove entity from list
        newEntities.remove(0);
      }
      //iterates through all entities
      while (disposeEntities.size() != 0){
          //iterates through all clients
          for (int iClient = 0; iClient < clients.size(); ++ iClient){
            //sends data to all clients
            Connection c = clients.get(iClient);
            Entity e = disposeEntities.get(0);
            Protocol.send(13, e, c);
          }
          //remove entity from list
          disposeEntities.remove(0);
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

  /**
  * hit the player
  * @param p the id of player/item
  * @param from the damage that is dealt
  */
  public static void forwardProjectiles(Projectile p, Connection from){
    //iterates through all clients
    for (int i = 0; i < server.clients.size(); ++i){
      Connection c = server.clients.get(i);
      //check if the client isnt the one originaly sending the projectile
      if (c.id != from.id){
        //sent the projectile
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
    loadEntities();
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
    //send client their id
    Protocol.send(4, c.id, c);
    //send client all the active entities
    for (int iEntity = 0; iEntity < activeEntities.size(); ++iEntity){
      Protocol.send(9,activeEntities.get(iEntity),c);
    }
  }

  /**
  * load entities like crates and barrels trees/rocks
  */
  public void loadEntities(){
    try {
      // load the crate data
      File file = new File("data/Crates.txt");
      Scanner input = new Scanner(file);
      double xPos, yPos;
      //loop through file
      while (input.hasNextLine()){
        //read input
        xPos = input.nextDouble();
        yPos = input.nextDouble();
        //create new crate
        addEntity(new Crate(xPos, yPos, 0, getId()));
      }
      input.close();
    } catch(Exception e){
      if (!(e instanceof java.util.NoSuchElementException)){
        System.out.println("Error loading crates: " + e);
        System.exit(-1);
      }
    }
    try {
      //load the barrel data
      File file = new File("data/Barrels.txt");
      Scanner input = new Scanner(file);
      double xPos, yPos;
      //loop through file
      while (input.hasNextLine()){
        //read data
        xPos = input.nextDouble();
        yPos = input.nextDouble();
        //create new barrel
        addEntity(new Barrel(xPos, yPos, 0, getId()));
      }
      input.close();
    } catch(Exception e){
      if (!(e instanceof java.util.NoSuchElementException)){
        System.out.println("Error loading Barrels: " + e);
        System.exit(-1);
      }
    }
    try {
      //load the tree and rock data
      File file = new File("data/TreesRocks.txt");
      Scanner input = new Scanner(file);
      double xPos, yPos;
      //loop through file
      while (input.hasNextLine()){
        //read data
        xPos = input.nextDouble();
        yPos = input.nextDouble();
        //determine if it sould be a rock or a tree (10% rock, 90% tree)
        Random rand = new Random();
        int n = rand.nextInt(100);
        if (n > 10){
          //add new tree
          addEntity(new Tree(xPos, yPos, 0, getId()));
        } else {
          //add new rock
          addEntity(new Rock(xPos, yPos, 0, getId()));
        }
      }
      input.close();
    } catch(Exception e){
      if (!(e instanceof java.util.NoSuchElementException)){
        System.out.println("Error loading TreesRocks: " + e);
        System.exit(-1);
      }
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
