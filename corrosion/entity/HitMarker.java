package corrosion.entity;
import corrosion.entity.Entity;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import java.awt.*;

public class HitMarker extends Entity{
  int fade = 255;
  int time = 1000;
  String msg;

  /**
  * constructor for the hit marker
  * @param xPos x position
  * @param yPos y position
  * @param msg the mesage
  */
  public HitMarker(double xPos, double yPos, String msg){
    super(xPos,yPos,0,Client.getId());
    this.msg = msg;
    setZIndex(10);
  }

  /**
  * Draws the player to the Window
  * @param g the graphics context
  * @param t time since last frame
  */
  public void draw(Graphics g, long t){
    time -= (int)t;
    if (time <= 0){
      //remove
      Client.removeEntity(this);
      Protocol.send(12, this, Client.getConnection());
      return;
    }else if(time <= 255){
      g.setColor(new Color(255,255,255,time));
    } else {
      g.setColor(new Color(255,255,255,255));
    }
    // wite the amount of damage done
    g.drawString(msg, (int)xPos, (int)yPos);
  }
}
