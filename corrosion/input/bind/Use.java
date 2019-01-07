/** Micheal Metzinger, Edward Pei
  * Jan 7 2019
  */
package corrosion.input.bind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
import corrosion.Drawing;
import corrosion.entity.item.equippable.*;
import corrosion.entity.player.Player;
import corrosion.input.*;
import java.awt.geom.Ellipse2D;
import corrosion.HitDetection;
import corrosion.entity.projectile.*;
import corrosion.entity.*;
import corrosion.network.*;
import corrosion.entity.player.*;
import corrosion.entity.building.wall.*;

public class Use extends Bindable{
  public void pressed(KeyEvent event){
    Shape playerHitBox = new Rectangle2D.Double(MainPlayer.getMainPlayer().getXPos()-10,MainPlayer.getMainPlayer().getYPos(),20,150);

    ArrayList<Entity> entities = Client.getEntities();
    for (int iEntities = 0; iEntities < entities.size(); ++iEntities){
      Entity e = entities.get(iEntities);
      if (e instanceof DoorFrame){
        Shape s = ((DoorFrame)e).getBuildingHitBox();
        if (HitDetection.hit(s, playerHitBox)){
          ((DoorFrame)e).open();
          Protocol.send(8,e,Client.getConnection());
          return;
        }
      }
    }

  }
  public void released(KeyEvent e){}
  public void typed(){}
}
