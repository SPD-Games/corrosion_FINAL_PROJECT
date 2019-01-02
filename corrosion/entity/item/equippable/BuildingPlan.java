/** Micheal Metzinger, Edward Pei
  * December 24 2018
  */

package corrosion.entity.item.equippable;

import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

import corrosion.entity.projectile.*;
import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.entity.building.*;

public class BuildingPlan extends Equippable{
  //TODO move all images and draw handling in Usable
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][4];;
  private static final int SQUARE = 0;
  private static final int WALL = 1;
  private static final int DOOR_FRAME = 2;
  private static final int TRIANGLE = 0;
  private int placeState = 0;
  private Building preview = new Square();

  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 1 + ".png"));
      sprites[0][1] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 2 + ".png"));
      sprites[0][2] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 3 + ".png"));
      sprites[0][3] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 4 + ".png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading BuildingPlan Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
   * Main Constructor
  */
  public BuildingPlan(){
    this(new int[]{0,0});
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public BuildingPlan(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{0,0}));

  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public BuildingPlan(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,0}, sprites, new int[]{0,0});
  }

  private void resetPreview(){
    if (placeState == SQUARE){
      preview = new Square();
    } else if (placeState == WALL){
      //preview = new Wall();
    } else if (placeState == DOOR_FRAME){
      //preview = new DoorFrame();
    } else if (placeState == TRIANGLE){
      //preview = new Triangle();
    }
  }

  /**
  * Draws a crossbow equipped to the player
  * @param g the graphics context
  */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.translate(-14, -60);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
    if (player == MainPlayer.getMainPlayer()){
      preview.drawPreview(g, player);
    }
  }

  public void draw(Graphics g, long t){
    transform.setToTranslation(xPos -50, yPos -50);
    ((Graphics2D)(g)).drawImage(sprite.getIcon(), transform, null);
  }

  /**
  *
  * @param p the pointer position on the screen relative to the player
  */
  public void attack(Point p, Player player){
    preview.place();
    resetPreview();
  }

  /**
  *
  * @param p the pointer position on the screen relative to the player
  */
  public void attack2(Point p, Player player){
    placeState = (placeState + 1) % 4;
    sprite.setState(0, placeState);
    resetPreview();
  }

  /**
  *
  */
  public void reload(){
    return;
  }
}
