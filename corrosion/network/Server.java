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
  private ArrayList<Entity> activeEntities = new ArrayList<Entity>();
  private ArrayList<Entity> newEntities = new ArrayList<Entity>();
  private ArrayList<Entity> disposeEntities = new ArrayList<Entity>();
  private ArrayList<Player> players = new ArrayList<Player>();

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
      for (Connection client : clients){
        Protocol.send(3, players, client);
      }
    }
  };
  private Timer sendTimer = new Timer(1000/64, sendLoopListener);
  /**
  * Main Constructor
  * @param port the port which to run the server socket on
  */
  public Server(int port){
    Protocol.init();
    try{
      //creates a new server socket
      socket = new ServerSocket(port);
    } catch(IOException e){
      System.out.println("Error creating server: " + e);
      System.exit(-1);
    }
    //listens for new clients
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
      } catch(IOException e){}
      if (newClient != null){
        //Send log message
        System.out.println("New Client: " + newClient);
        //adds the client connection to clients
        clients.add(new Connection(newClient));
        players.add(new Player(0,0,0));
      }
    }
  }

  /**
  * Starts a new server
  */
  public static void main(String[] args){
    server = new Server(1234);
  }
}
