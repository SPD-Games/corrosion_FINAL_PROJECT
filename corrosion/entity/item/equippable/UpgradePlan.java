/** Micheal Metzinger, Edward Pei
  * Jan 7 2019
  */

package corrosion.entity.item.equippable;

//Imports
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import corrosion.network.*;
import corrosion.entity.building.wall.*;
import corrosion.entity.projectile.*;
import corrosion.Sprite;
import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.entity.building.*;
import corrosion.input.*;
import corrosion.HitDetection;


public class UpgradePlan extends Equippable{
  /**
  * attack Method
  * @param player using upgrade plan
  */
  public void attackOff(Player player){}

  /**
  * sprite method for upgrade plan
  */
  public void fromServer(){
    sprite = new Sprite(icon, state, sprites, new int[]{0});
  }

  //sprite variables
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][3];
  private static final int WOOD = 1;
  private static final int STONE = 2;
  private static final int METAL = 3;
  private int placeState = 0;

  /**
  * Initializes the player class
  */
  public static void init(){
    try{
      for (int i = 0; i < 3; ++i){
        sprites[0][i] = ImageIO.read(UpgradePlan.class.getResourceAsStream("/sprites/upgradeplan/upgradePlan" + (i+1) + ".png"));
      }
      icon = ImageIO.read(UpgradePlan.class.getResourceAsStream("/sprites/upgradeplan/icon.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading UpgradePlan Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
  * Method to return icon
  * @return icon icon
  */
  public BufferedImage getIcon(){
    return icon;
  }

  /**
  * return info Method
  * @return info
  */
  public String getInfo(){
    return "";
  }  /**
   * Main Constructor
  */
  public UpgradePlan(){
    this(new int[]{0,0});
  }

  /**
  * constuctor
  * @param state of plan
  */
  public UpgradePlan(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{0,0}));
    stackable = false;
  }

  /**
  * Constuctor
  * @param x position
  * @param y position
  * @param r rotation applied
  * @param id id number associated with the Equippable
  */
  public UpgradePlan(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,0}, sprites, new int[]{0,0});
  }


  /**
  * Draws a crossbow equipped to the player
  * @param g the graphics context
  */
  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.translate(-14, -60);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * attack method
  * @param p the pointer position on the screen relative to the player
  * @param player using plan
  */
  public void attack(Point p, Player player){
    p = Mouse.getPointOnMap(p);
    Ellipse2D point = new Ellipse2D.Double(p.x-2.5, p.y-2.5, 5, 5);
    ArrayList<Entity> entities = Client.getEntities();
    //Hit detection for entities and players
    for (int i = 0; i < entities.size(); ++i){
      Entity e = entities.get(i);
      if (e instanceof Wall){
        if (HitDetection.hit(point, ((Wall)e).getBuildingHitBox())){
          ((Wall) e).upgrade(placeState+1);
          return;
        }
      }
    }
    for (int i = 0; i < entities.size(); ++i){
      Entity e = entities.get(i);
      if (e instanceof Building){
        if (HitDetection.hit(point, ((Building)e).getBuildingHitBox())){
          ((Building) e).upgrade(placeState+1);
          return;
        }
      }
    }
  }

  /**
  * Attack method number 2
  * @param p the pointer position on the screen relative to the player
  * @param player using plan
  */
  public void attack2(Point p, Player player){
    placeState = (placeState + 1) % 3;
    sprite.setState(0, placeState);
  }

  /**
  *reload method
  */
  public void reload(){
    return;
  }
}
