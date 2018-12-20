/**
* Michael Metzinger
* Dec 14 2018
* The client that connect to the server
*/

//NOTE you have died will be on the map

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

  public static Connection getConnection(){
    return client.connection;
  }

  public static void addProjectile(Projectile p){
    client.projectiles.add(p);
  }

  public static ArrayList<Projectile> getProjectiles(){
    return client.projectiles;
  }

  public static void removeProjetile(Projectile p){
    client.projectiles.remove(p);
  }

  public static void setPing(long p){
    ping = p;
  }

  public static long getPing(){
    return ping;
  }

  public static void setPlayer(Player p){
    int n = client.players.indexOf(p);
    if (n == -1){
      client.players.add(p);
    } else {
      client.players.set(n, p);
    }
  }

  public static void setId(long id){
    synchronized(client.idLock){
      client.id = id;
    }
  }

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
    public void actionPerformed(ActionEvent arg0) {
      send(2, MainPlayer.getMainPlayer());
    }
  };
  private Timer sendTimer = new Timer(1000/64, sendLoopListener);

  private ActionListener optimizeListener = new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent arg0) {
      optimizeEntities();
      Protocol.send(0, null, connection);
    }
  };
  private Timer optimizeTimer = new Timer(1000, optimizeListener);

  public static void setPlayers(ArrayList<Player> p){
      client.players = p;
  }

  public static void removePlayer(Player p){
      client.players.remove(p);
  }

  public static ArrayList<Player> getPlayers(){
    return client.players;
  }

  public static ArrayList<Entity> getEntities(){
    return client.entitiesInView;
  }

  public static void addEntity(Entity entity){
    client.entitiesInView.add(entity);
    client.entities.add(entity);
  }

  public static void addEntity(Entity entity, int protocol){
    addEntity(entity);
    send(protocol, entity);
  }

  public static void removeEntity(Entity entity){
    client.entitiesInView.remove(entity);
    client.entities.remove(entity);
  }

  public static void optimizeEntities(){
    ArrayList<Entity> visable = new ArrayList<Entity>();
    double xPos = MainPlayer.getMainPlayer().getXPos();
    double yPos = MainPlayer.getMainPlayer().getYPos();

    for(Entity e: client.entities){
      if ((e.getXPos() - xPos)*(e.getXPos() - xPos) + (e.getYPos() - yPos)*(e.getYPos() - yPos) < 100000000){
        visable.add(e);
      }
    }

    client.entitiesInView = visable;
  }

  public static void send(int protocol, Object data){
    Protocol.send(protocol, data, client.connection);
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

    long startTime = System.currentTimeMillis();
    long timeElapsed = 0l;
    while(id == -1){
      timeElapsed = System.currentTimeMillis() - startTime;
      if (timeElapsed > 2000){
        //Error
        System.out.println("Failed to connect to server.");
        System.exit(-1);
      }
      try{
        Thread.sleep(10);
      } catch(Exception e){}
    }

    MainPlayer.spawn(0,0,getId());
    optimizeTimer.start();
    sendTimer.start();
    System.out.println("DONE");
  }
}
