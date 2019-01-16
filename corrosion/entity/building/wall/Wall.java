/**
* Michael Metzinger
* Jan 13 2019
* A wall that can be placed
*/

package corrosion.entity.building.wall;

import corrosion.entity.building.*;
import corrosion.entity.player.*;
import corrosion.input.Mouse;
import corrosion.Sprite;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import corrosion.entity.*;
import corrosion.HitDetection;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import corrosion.Inventory;
import corrosion.entity.item.*;

public class Wall extends Building {
  private static BufferedImage[][] sprites = new BufferedImage[1][4];
  protected transient Sprite sprite;
  protected Path2D[] placingHitBoxs = new Path2D[4];
  protected Path2D hitBox;
  protected Path2D buildingHitBox;
  protected boolean placeable = false;
  protected int[] state;

  /**
  * Loads all the sprites
  */
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(Wall.class.getResourceAsStream("/sprites/wall/twigWall.png"));
      sprites[0][1] = ImageIO.read(Wall.class.getResourceAsStream("/sprites/wall/woodWall.png"));
      sprites[0][2] = ImageIO.read(Wall.class.getResourceAsStream("/sprites/wall/stoneWall.png"));
      sprites[0][3] = ImageIO.read(Wall.class.getResourceAsStream("/sprites/wall/metalWall.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading wall Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * Sets the state when getting from server
  */
  public void fromServer(){
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  /**
  * Blank Constructor
  */
  public Wall(){
    this(0, 0, 0);
  }

  /**
  * Main Constructor
  * @param xPos the x position of the Wall
  * @param yPos the y position of the Wall
  * @param rotation the rotation of the Wall
  */
  public Wall(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    setZIndex(2);
    state = new int[]{0,0};
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  /**
  * Draws the Wall
  * @param g the graphics context to Use
  * @param t the time since last frame
  */
  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Draws the preview of the wall about to be placed
  * @param g the graphics context to Use
  * @param p the player that is placing the wall
  */
  public void drawPreview(Graphics g, Player p){
    placeable = false;
    //get mouse corrdinates and enitiies
    Point2D pointOnMap = Mouse.getPointOnMap();
    Point mousePos = Mouse.getPosition();
    ArrayList<Entity> entities = Client.getEntities();

    //checks if on foundation
    boolean onFoundation = false;
    //iterates through all entities
    for (int i = 0; i < entities.size(); ++i){
      //checks if the entity is a foundation(Square or Triangle)
      if (entities.get(i) instanceof Square){
        //checks if the mouse is on the foundation
        AffineTransform newTransform = ((Square)entities.get(i)).checkPlacingHitBoxesWall(pointOnMap);
        //if so
        if (newTransform != null){
          //use the translation given by the foundation
          transform = newTransform;
          onFoundation = true;
          break;
        }
      }else if (entities.get(i) instanceof Triangle){
        //checks if the mouse is on the foundation
        AffineTransform newTransform = ((Triangle)entities.get(i)).checkPlacingHitBoxesWall(pointOnMap);
        //if so
        if (newTransform != null){
          //use the translation given by the foundation
          transform = newTransform;
          onFoundation = true;
          break;
        }
      }
    }

    //dont draw if it is not on a foundation
    if(!onFoundation){return;}

    //create the hitbox
    buildingHitBox = new Path2D.Double();
    buildingHitBox.moveTo(15, 0);
    buildingHitBox.lineTo(235, 0);
    buildingHitBox.lineTo(235, 10);
    buildingHitBox.lineTo(15, 10);
    buildingHitBox.transform(transform);

    //iterat through all entities
    for (int i = 0; i < entities.size(); ++i){
      //check if the entity is a Wall
      if (entities.get(i) instanceof Wall){
        Shape otherHitBox = ((Wall)entities.get(i)).getBuildingHitBox();
        //check if another wall is placed in the same spot
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          //if so dont draw
          return;
        }
      }
    }
    //draw the wall
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
    placeable = true;
  }

  /**
  * Gets the building hit box of the Wall
  * @return the building hit box of the Wall
  */
  public Shape getBuildingHitBox(){
    return buildingHitBox;
  }

  /**
  * Gets the hitbox of the Wall
  * @return the hitbox of the Wall
  */
  @Override
  public Shape getHitBox(){
    return hitBox;
  }

  /**
  * Upgrades the Wall
  * @param level the level to upgrade the wall to
  */
  public void upgrade(int level){
    Inventory i = MainPlayer.getMainPlayer().getInvetory();
    Item uses;
    //checks what to upgrade the wall to
    if (level == WOOD){
      //check if the player has the resourses to place the wall
      uses = new Wood(100);
      if (!i.removeItem(uses)){return;}
      //upgrade
      hp = 30*25;
    } else if (level == STONE){
      //check if the player has the resourses to place the wall
      uses = new Stone(100);
      if (!i.removeItem(uses)){return;}
      //upgrade
      hp = 120*25;
    } else if (level == METAL){
      //check if the player has the resourses to place the wall
      uses = new Metal(100);
      if (!i.removeItem(uses)){return;}
      //upgrade
      hp = 240*25;
    }

    //send to server
    state = new int[]{0,level};
    sprite.setState(0, level);
    Protocol.send(8, this, Client.getConnection());
  }

  /**
  * Places the Wall
  * @return if the placement was succsesful
  */
  public boolean place(){
    //checks if the wall is placeable from the preview
    if(!placeable){return false;}
    ArrayList<Entity> entities = Client.getEntities();

    //creates the bulding hit box
    buildingHitBox = new Path2D.Double();
    buildingHitBox.moveTo(15, 0);
    buildingHitBox.lineTo(235, 0);
    buildingHitBox.lineTo(235, 10);
    buildingHitBox.lineTo(15, 10);
    buildingHitBox.transform(transform);

    //creates the hitbox
    hitBox = new Path2D.Double();
    hitBox.moveTo(-5, 0);
    hitBox.lineTo(255, 0);
    hitBox.lineTo(255, 10);
    hitBox.lineTo(-5, 10);
    hitBox.transform(transform);

    //sets the position of the wall
    xPos = buildingHitBox.getCurrentPoint().getX();
    yPos = buildingHitBox.getCurrentPoint().getY();


    //check if the wall is on foundation
    boolean onFoundation = false;
    //iterates through all the entities
    for (int i = 0; i < entities.size(); ++i){
      //checks if the wall is on foundation(Square or Triangle)
      if (entities.get(i) instanceof Square){
        Shape otherHitBox  = ((Square)entities.get(i)).getBuildingHitBox();
        //check if the wall is on the foundation
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          onFoundation = true;
          break;
        }
      }else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        //check if the wall is on the foundation
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          onFoundation = true;
          break;
        }
      }
    }

    //if the wall is not on the foundation cancel the placement
    if(!onFoundation){return false;}

    //iterate throught all the entities
    for (int i = 0; i < entities.size(); ++i){
      //check if the entity is a wall
      if (entities.get(i) instanceof Wall){
        Shape otherHitBox = ((Wall)entities.get(i)).getBuildingHitBox();
        //check if the wall is placed on another wall
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          //if so cancel the placement
          return false;
        }
      }
    }

    //check if the player has the available resourses
    if(!MainPlayer.getMainPlayer().getInvetory().removeItem(new Wood(25))){return false;}

    //send to server
    id = Client.getId();
    Client.addEntity(this);
    Protocol.send(8, this, Client.getConnection());
    return true;
  }
}
