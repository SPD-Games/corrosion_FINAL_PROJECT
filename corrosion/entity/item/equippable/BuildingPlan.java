
//Michael Metzinger, Edward Pei, Henry Lim
//Jan 15, 2019
//Build Plan class
package corrosion.entity.item.equippable;

//imports
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

import corrosion.entity.building.wall.*;
import corrosion.entity.projectile.*;
import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.entity.building.*;

public class BuildingPlan extends Equippable{

  //Variables
  //Icon image
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][4];;
  //Building shape types
  private static final int SQUARE = 0;
  private static final int WALL = 1;
  private static final int DOOR_FRAME = 2;
  private static final int TRIANGLE = 3;
  private int placeState = 0;
  private Building preview = new Square();

  /**
  *Method not in use for BuildPlan class
  *@param player player class
  */
  public void attackOff(Player player){}

  /**
  *Sprite method for BuildPlan
  */
  public void fromServer(){
    sprite = new Sprite(icon, new int[]{0,0}, sprites, new int[]{0,0});
    preview = new Square();
  }

  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      //Image for the four types of build shape modes
      sprites[0][0] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 1 + ".png"));
      sprites[0][1] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 2 + ".png"));
      sprites[0][2] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 3 + ".png"));
      sprites[0][3] = ImageIO.read(new File("sprites/buildingplan/BuildingPlan" + 4 + ".png"));
      //Load icon image
      icon =  ImageIO.read(new File("sprites/buildingplan/icon.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading BuildingPlan Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
  *Method to return icon
  */
  public BufferedImage getIcon(){
    return icon;
  }
  /**
  * Return info of class
  */
  public String getInfo(){
    return "";
  }

  /**
   * Constructor
  */
  public BuildingPlan(){
    //Evoke constuctor
    this(new int[]{0,0});
  }

  /**
   * Constructor
   * @param state state of sprite
  */
  public BuildingPlan(int[] state){
    //Evoke consctructor in Equippable
    super(new Sprite(icon, state, sprites, new int[]{0,0}));
    //Set boolean to flase
    stackable = false;
  }

  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with the Equippable
  */
  public BuildingPlan(double xPos, double yPos, double rotation, long id){
    //Evoke consctructor in equippable
    super(xPos,yPos,rotation, id);
    //Instantiate with new sprite
    this.sprite = new Sprite(icon, new int[]{0,0}, sprites, new int[]{0,0});
  }

 /**
 * Set strucure placed to correspond structure on hand
 */
  private void resetPreview(){
    if (placeState == SQUARE){
      preview = new Square();
    } else if (placeState == WALL){
      preview = new Wall();
    } else if (placeState == DOOR_FRAME){
      preview = new DoorFrame();
    } else if (placeState == TRIANGLE){
      preview = new Triangle();
    }
  }

  /**
  * Draws a BuildPlan equipped to the player
  * @param g the graphics context
  */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    //Shift image
    transform.translate(-14, -60);
    //Draw image
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);

    //If class equiped to main player...
    //Draw preview of placing item
    if (player == MainPlayer.getMainPlayer()){
      if (preview == null){return;}
      preview.drawPreview(g, player);
    }
  }

  /**
  * Place material method
  * @param p the pointer position on the screen relative to the player
  * @param player player using BuildPlan
  */
  public void attack(Point p, Player player){
    preview.place();
    //Reset
    resetPreview();
  }

  /**
  * 2nd place material method
  * @param p the pointer position on the screen relative to the player
  * @param player player using BuildPlan
  */
  public void attack2(Point p, Player player){
    placeState = (placeState + 1) % 4;
    sprite.setState(0, placeState);
    //Reset
    resetPreview();
  }

  /**
  * Reload method
  */
  public void reload(){
    return;
  }
}
