package corrosion.network;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;

public class Connection{
  public Socket socket;
  public DataOutputStream out;

  public Connection(Socket socket){
    this.socket = socket;
    try{
    new Thread(new Listener(this)).start();
    out = new DataOutputStream(socket.getOutputStream());
    }catch(Exception e){
      System.out.println(e);
    }
  }

  public Connection(String ip, int port) throws Exception{
    this(new Socket(ip, port));
  }

  //same as in sever TODO move into own file
  class Listener implements Runnable{
    private Connection connection;
    private DataInputStream in;
    public Listener(Connection c) throws Exception{
      connection = c;
      in = new DataInputStream(c.socket.getInputStream());
    }

    public void run(){
      try{
        while(true){
          int protolNumber = in.readInt();
          Protocol.get(protolNumber, in, connection);
        }
      }catch(Exception e){}
    }
  }
}
