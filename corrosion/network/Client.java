/**
* Michael Metzinger
* Dec 20 2018
* The client that connect to the server
*/

package corrosion.network;

import corrosion.entity.player.*;
import corrosion.entity.*;
import corrosion.entity.projectile.*;
import corrosion.network.protocol.*;
import corrosion.network.Connection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;

public class Client{
  private static long ping = 0;
  private static Client client;
  private Connection connection;
  private ArrayList<Player> players = new ArrayList<Player>();
  private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
  private ArrayList<Entity> entities = new ArrayList<Entity>();
  private ArrayList<Entity> entitiesInView = new ArrayList<Entity>();
  private long id = -1;
  private final Object idLock = new Object();

  /**
  * Gets the connection from the client
  * @return the connection from the client
  */
  public static Connection getConnection(){
    return client.connection;
  }

  /**
  * Add a new projectile
  * @param p the projectile to add
  */
  public static void addProjectile(Projectile p){
    client.projectiles.add(p);
  }

  /**
  * Gets the list of Projectiles
  * @return the list of projectiles
  */
  public static ArrayList<Projectile> getProjectiles(){
    return client.projectiles;
  }

  /**
  * Removes a projectil
  * @param p the projectile to remove
  */
  public static void removeProjetile(Projectile p){
    client.projectiles.remove(p);
  }

  /**
  * Sets the value of the ping
  * @param p value to set the ping
  */
  public static void setPing(long p){
    ping = p;
  }

  /**
  * Gets the value of the ping
  * @return the value of the ping
  */
  public static long getPing(){
    return ping;
  }

  /**
  * Sets the value of a player. If not added already creates a new Player
  * @param p the player object to set
  */
  public static void setPlayer(Player p){
    int n = client.players.indexOf(p);
    if (n == -1){
      client.players.add(p);
    } else {
      client.players.get(n).moveTo(p.getXPos(), p.getYPos());
      client.players.get(n).setRotation(p.getRotaion());
      client.players.get(n).setEquipped(p.getEquipped());
    }
  }

  /**
  * Sets the value of id for identifing entities
  * @param id the value to set id
  */
  public static void setId(long id){
    synchronized(client.idLock){
      client.id = id;
    }
  }

  /**
  * Gets a unique id
  * @return a unique id
  */
  public static long getId(){
    synchronized(client.idLock){
      return client.id++;
    }
  }
  /**
  * Gets the client
  * @return the client
  */
  public static Client getClient(){
    return client;
  }

  //send data loop
  private ActionListener sendLoopListener = new ActionListener(){
    @Override
    /**
    * Sends player location
    */
    public void actionPerformed(ActionEvent arg0) {
      Protocol.send(2, MainPlayer.getMainPlayer(), connection);
    }
  };
  //timer that sends player location 64 times a second
  private Timer sendTimer = new Timer(1000/64, sendLoopListener);

  private ActionListener optimizeListener = new ActionListener(){
    @Override
    /**
    * Updates the entities that should be rendered and gets a new ping value
    */
    public void actionPerformed(ActionEvent arg0) {
      optimizeEntities();
      //sends a new ping message
      Protocol.send(0, null, client.connection);
    }
  };
  //timer that Updates the entities that should be rendered every second
  private Timer optimizeTimer = new Timer(1000, optimizeListener);

  /**
  * Sets the list of players
  * @param p a list of players to set the list to
  */
  public static void setPlayers(ArrayList<Player> p){
      client.players = p;
  }

  /**
  * Removes a player from the List
  * @param p a player to remove from the list
  */
  public static void removePlayer(Player p){
      client.players.remove(p);
  }

  /**
  * Gets all current players
  * @return all current players
  */
  public static ArrayList<Player> getPlayers(){
    return client.players;
  }

  /**
  * Gets all current entities
  * @return all current entities
  */
  public static ArrayList<Entity> getEntities(){
    return client.entitiesInView;
  }

  /**
  * Adds a new entity
  * @param entity the entity to add
  */
  public static void addEntity(Entity entity){
    client.entitiesInView.add(entity);
    client.entities.add(entity);
  }

  /**
  * Removes a entity
  * @param entity the entity to remove
  */
  public static void removeEntity(Entity entity){
    client.entitiesInView.remove(entity);
    client.entities.remove(entity);
  }

  /**
  * Generates a list of what entities are in range of being drawn
  */
  public static void optimizeEntities(){
    ArrayList<Entity> visable = new ArrayList<Entity>();
    double xPos = MainPlayer.getMainPlayer().getXPos();
    double yPos = MainPlayer.getMainPlayer().getYPos();
    //iterates through all entities
    for(int i = 0; i < client.entities.size(); ++i){
      Entity e = client.entities.get(i);
      //checks if that entity is in possible rendering distance
      if ((e.getXPos() - xPos)*(e.getXPos() - xPos) + (e.getYPos() - yPos)*(e.getYPos() - yPos) < 100000000){
        //if so add to list
        visable.add(e);
      }
    }

    //sets the list
    client.entitiesInView = visable;
  }

  /**
  * Main Constructor
  * @param ip the ip of the server to connect to
  * @param port the port of the server to connect to
  */
  public Client(String ip, int port){
    client = this;
    Protocol.init();
    try{
      //creates a connection to the server
      connection = new Connection(ip, port);
    } catch(Exception e){
      System.out.println("Error connecting to server" + e);
    }
    //checks for server responce for 2000ms
    long startTime = System.currentTimeMillis();
    long timeElapsed = 0l;
    while(id == -1){
      timeElapsed = System.currentTimeMillis() - startTime;
      //if no responce
      if (timeElapsed > 2000){
        //server timeout error
        System.out.println("Failed to connect to server.");
        System.exit(-1);
      }
      try{
        Thread.sleep(10);
      } catch(Exception e){}
    }

    //init all client info
    MainPlayer.spawn(0,0,getId());
    optimizeTimer.start();
    sendTimer.start();
    System.out.println("DONE");
  }
}
