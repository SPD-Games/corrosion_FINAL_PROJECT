/**
* Michael Metzinger
* Dec 14 2018
* The client that connect to the server
*/

//NOTE you have died will be on the map

package corrosion.network;

import corrosion.entity.player.*;
import corrosion.entity.*;
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
  private static Client client;
  private Connection connection;
  private ArrayList<Player> players = new ArrayList<Player>();
  private ArrayList<Entity> entities = new ArrayList<Entity>();
  private ArrayList<Entity> entitiesInView = new ArrayList<Entity>();

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
    }
  };
  private Timer optimizeTimer = new Timer(1000, optimizeListener);

  public static void setPlayers(ArrayList<Player> p){
      client.players = p;
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
    Protocol.init();
    try{
      //creates a connection to the server
      connection = new Connection(ip, port);
    } catch(Exception e){
      System.out.println("Error connecting to server" + e);
    }
    client = this;
    optimizeTimer.start();
    sendTimer.start();
  }
}
