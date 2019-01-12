/** Micheal Metzinger, Edward Pei
  * Jan 7 2019
  */
package corrosion.input.bind;

import corrosion.entity.item.Item;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.geom.*;
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
import corrosion.network.protocol.*;

import corrosion.entity.player.*;
import corrosion.entity.building.wall.*;
import java.awt.geom.AffineTransform;


public class Use extends Bindable{
  public void pressed(KeyEvent event){
    Shape playerHitBox = new Rectangle2D.Double(MainPlayer.getMainPlayer().getXPos()-10,MainPlayer.getMainPlayer().getYPos()-150,20,150);
    AffineTransform tx = new AffineTransform();
    tx.rotate(MainPlayer.getMainPlayer().getRotation(),MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos());
    playerHitBox = tx.createTransformedShape(playerHitBox);

    ArrayList<Entity> entities = Client.getEntities();
    for (int iEntities = 0; iEntities < entities.size(); ++iEntities){
      Entity e = entities.get(iEntities);
      if (e instanceof DoorFrame){
        Shape s = ((DoorFrame)e).getBuildingHitBox();
        if (HitDetection.hit(s, playerHitBox)){
          ((DoorFrame)e).open();
          Protocol.send(8, e, Client.getConnection());
          return;
        }
      } else if (e instanceof Item){
        Shape s = ((Item) e).getPickUpHitBox();
        if(HitDetection.hit(s, playerHitBox)){
          if(MainPlayer.getMainPlayer().getInvetory().addItem(((Item) e))){
            Client.removeEntity(e);
            Protocol.send(12, e, Client.getConnection());
            return;
          }
        }
      }
    }

  }
  public void released(KeyEvent e){}
  public void typed(){}
}
