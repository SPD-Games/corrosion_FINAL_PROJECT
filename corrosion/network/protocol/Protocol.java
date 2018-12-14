package corrosion.network.protocol;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import corrosion.network.protocol.*;
import corrosion.network.*;

abstract public class Protocol{
  private static Protocol[] protocols;
  public static void init(){
    protocols = new Protocol[2];
    protocols[0] = new PingSend();
    protocols[1] = new PingReceive();
  }

  public static void send(int protocol, Object data, Connection c){
    //not needed?
    synchronized(c.out){
      try{
        c.out.writeInt(protocol);
        protocols[protocol].send(data, c);
      }catch(Exception e){}
    }
  }

  public static void get(int protocol, DataInputStream in, Connection c){
    protocols[protocol].get(in, c);
  }

  public static Protocol getProtocol(int i){
    return protocols[i];
  }

  abstract public void send(Object data, Connection c);
  abstract public void get(DataInputStream in, Connection c);
}
