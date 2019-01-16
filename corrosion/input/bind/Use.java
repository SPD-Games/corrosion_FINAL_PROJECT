/** Micheal Metzinger, Edward Pei
  * Jan 7 2019
  * Opens doors and picks up items in range of the mainplayer
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

  /**
  * ???
  * @param e the key event
  */
  public void pressed(KeyEvent event){
    //creates a hitbox to check door opening and item pickup
    Shape playerHitBox = new Rectangle2D.Double(MainPlayer.getMainPlayer().getXPos()-10,MainPlayer.getMainPlayer().getYPos()-150,20,150);
    AffineTransform tx = new AffineTransform();
    tx.rotate(MainPlayer.getMainPlayer().getRotation(),MainPlayer.getMainPlayer().getXPos(),MainPlayer.getMainPlayer().getYPos());
    playerHitBox = tx.createTransformedShape(playerHitBox);

    //iterates through all entities
    ArrayList<Entity> entities = Client.getEntities();
    for (int iEntities = 0; iEntities < entities.size(); ++iEntities){
      Entity e = entities.get(iEntities);
      //checks if the entity is a door
      if (e instanceof DoorFrame){
        //checks if the use hitbox hits the door
        Shape s = ((DoorFrame)e).getBuildingHitBox();
        if (HitDetection.hit(s, playerHitBox)){
          //open the door and send to server
          ((DoorFrame)e).open();
          Protocol.send(8, e, Client.getConnection());
          return;
        }
      }
      //checks if the entity is a item on the ground
      else if (e instanceof Item){
        Shape s = ((Item) e).getPickUpHitBox();
        //checks if the use hitbox hits the item
        if(HitDetection.hit(s, playerHitBox)){
          //adds the item if there is room
          if(MainPlayer.getMainPlayer().getInvetory().addItem(((Item) e))){
            //sends to server
            Client.removeEntity(e);
            Protocol.send(12, e, Client.getConnection());
            return;
          }
        }
      }
    }

  }

  /**
  * method not used in this class
  */
  public void released(KeyEvent e){}

    /**
    * method not used in this class
    */
  public void typed(){}
}
