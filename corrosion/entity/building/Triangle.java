/**
* Michael Metzinger
* Jan 13 2019
* A Triangle foundation that can have walls/doors/other foundations connected to it
*/

package corrosion.entity.building;

import corrosion.entity.building.Building;
import corrosion.entity.player.*;
import corrosion.input.Mouse;
import corrosion.Sprite;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import corrosion.network.Client;
import corrosion.entity.Entity;
import corrosion.HitDetection;
import corrosion.Inventory;
import corrosion.entity.item.*;

public class Triangle extends Building {
  private static BufferedImage[][] sprites = new BufferedImage[1][4];
  public transient Sprite sprite;
  private Path2D[] placingHitBoxs = new Path2D[3];
  private Path2D hitBox;
  private int[] state;

  /**
  * Initiates the sprites for the triangle
  */
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(Triangle.class.getResourceAsStream("/sprites/triangle/twigTriangle.png"));
      sprites[0][1] = ImageIO.read(Triangle.class.getResourceAsStream("/sprites/triangle/woodTriangle.png"));
      sprites[0][2] = ImageIO.read(Triangle.class.getResourceAsStream("/sprites/triangle/stoneTriangle.png"));
      sprites[0][3] = ImageIO.read(Triangle.class.getResourceAsStream("/sprites/triangle/metalTriangle.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading triangle Sprite: " + e);
      System.exit(-1);
    }
  }

  /**
  * Sets the state when getting from the server
  */
  public void fromServer(){
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  /**
  * Blank Constructor
  */
  public Triangle(){
    this(0, 0, 0);
  }

  /**
  * Main Counstructor
  * @param xPos the xPosition of the Triangle
  * @param yPos the y position of the triangle
  * @param rotation the rotation of the triangle
  */
  public Triangle(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    state = new int[]{0,0};
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  /**
  * Draws the Triangle
  * @param g the graphics context to Use
  * @param t the time since the last frame
  */
  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Draws the preview of the Triangle
  * @param g the graphical context to Use
  * @param p the player to draw relative to
  */
  public void drawPreview(Graphics g, Player p){
    //gets the mouse position relative to the map
    Point2D pointOnMap = Mouse.getPointOnMap();
    Point mousePos = Mouse.getPosition();

    //sets the default transform
    transform.setToTranslation(pointOnMap.getX()-125, pointOnMap.getY()-144.831216351);
    rotation = Math.atan2(mousePos.getX(), mousePos.getY());
    transform.rotate(rotation, 125, 144.831216351);

    ArrayList<Entity> entities = Client.getEntities();

    //iterates through all entities
    for (int i = 0; i < entities.size(); ++i){
      //checks for other foundations
      if (entities.get(i) instanceof Square){
        //check if trianlge should be connected to the square
        AffineTransform newTransform = ((Square)entities.get(i)).checkPlacingHitBoxesTriangle(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          break;
        }
      } else if (entities.get(i) instanceof Triangle){
        //check if trianlge should be connected to the square
        AffineTransform newTransform = ((Triangle)entities.get(i)).checkPlacingHitBoxesTriangle(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          break;
        }
      }
    }

    //creates a hitbox
    Point2D a = new Point2D.Double(2,215);
    a = transform.transform(a,null);
    Point2D b = new Point2D.Double(248,215);
    b = transform.transform(b,null);
    Point2D c = new Point2D.Double(125,1);
    c = transform.transform(c,null);
    Point2D d = new Point2D.Double(125,144.831216351);
    d = transform.transform(d,null);
    hitBox = new Path2D.Double();
    hitBox.moveTo(a.getX(), a.getY());
    hitBox.lineTo(b.getX(), b.getY());
    hitBox.lineTo(c.getX(), c.getY());
    hitBox = null;
    xPos = d.getX();
    yPos = d.getY();

    //iterates through entities
    for (int i = 0; i < entities.size(); ++i){
      //checks if the triangle overlaps with other foundations
      if (entities.get(i) instanceof Square){
        Shape otherHitBox = ((Square)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          //TODO DRAW CANNOT PLACE
          return;
        }
      } else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          //TODO DRAW CANNOT PLACE
          return;
        }
      }
    }
    //draw
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  /**
  * Gets the building hit Box
  * @return the bulding hit box
  */
  public Shape getBuildingHitBox(){
    return hitBox;
  }

  /**
  * Gets the hit Box
  * @return the hit box
  */
  @Override
  public Shape getHitBox(){return null;}

  /**
  * Upgrades the Triangle
  * @param level the level to upgrade to
  */
  public void upgrade(int level){
    Inventory i = MainPlayer.getMainPlayer().getInvetory();
    Item uses;
    //checks if the user has the available resourses
    if (level == WOOD){
      //wood
      uses = new Wood(70);
      if (!i.removeItem(uses)){return;}
      hp = 30*25;
    } else if (level == STONE){
      //stone
      uses = new Stone(70);
      if (!i.removeItem(uses)){return;}
      hp = 120*25;
    } else if (level == METAL){
      //metal
      uses = new Metal(70);
      if (!i.removeItem(uses)){return;}
      hp = 240*25;
    }
    //send to the server
    state = new int[]{0,level};
    sprite.setState(0, level);
    Protocol.send(8, this, Client.getConnection());
  }

  /**
  * Places the Triangle
  */
  public boolean place(){
    //sets up the hit box
    Point2D a = new Point2D.Double(1,216.5);
    a = transform.transform(a,null);
    Point2D b = new Point2D.Double(249,216.5);
    b = transform.transform(b,null);
    Point2D c = new Point2D.Double(125,1);
    c = transform.transform(c,null);
    Point2D d = new Point2D.Double(125,144.831216351);
    d = transform.transform(d,null);
    xPos = d.getX();
    yPos = d.getY();
    hitBox = new Path2D.Double();
    hitBox.moveTo(a.getX(), a.getY());
    hitBox.lineTo(b.getX(), b.getY());
    hitBox.lineTo(c.getX(), c.getY());

    //iterates through entities
    ArrayList<Entity> entities = Client.getEntities();
    for (int i = 0; i < entities.size(); ++i){
      //checks if entity is a foundation
      if (entities.get(i) instanceof Square){
        Shape otherHitBox = ((Square)entities.get(i)).getBuildingHitBox();
        //checks if the entity is overlaping the foundation
        if (HitDetection.hit(otherHitBox,hitBox)){
          //cancel placing
          return false;
        }
      } else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        //checks if the entity is overlaping the foundation
        if (HitDetection.hit(otherHitBox,hitBox)){
          //cancel placing
          return false;
        }
      }
    }

    //sets up the placing hitboxs
    placingHitBoxs[0] = new Path2D.Double();
    placingHitBoxs[0].moveTo(a.getX(), a.getY());
    placingHitBoxs[0].lineTo(b.getX(), b.getY());
    placingHitBoxs[0].lineTo(d.getX(), d.getY());
    placingHitBoxs[1] = new Path2D.Double();
    placingHitBoxs[1].moveTo(c.getX(), c.getY());
    placingHitBoxs[1].lineTo(b.getX(), b.getY());
    placingHitBoxs[1].lineTo(d.getX(), d.getY());
    placingHitBoxs[2] = new Path2D.Double();
    placingHitBoxs[2].moveTo(c.getX(), c.getY());
    placingHitBoxs[2].lineTo(a.getX(), a.getY());
    placingHitBoxs[2].lineTo(d.getX(), d.getY());

    //checks if the player has the resourses to build. cancel if not
    if(!MainPlayer.getMainPlayer().getInvetory().removeItem(new Wood(25))){return false;}

    //send to server
    id = Client.getId();
    Client.addEntity(this);
    Protocol.send(8, this, Client.getConnection());
    return true;
  }

  /**
  * Checks if a Square foundation should be placed connected to the current Triangle foundation
  * @param p the position of the mouse cursor relative to the map
  * @return the transformation that should be applied to the Square. null if none should be applied
  */
  public AffineTransform checkPlacingHitBoxesSquare(Point2D p){
    //check all sides
    for (int i = 0; i < 3; ++i){
      //check  if the mouse is in a placing hitbox
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
        //if so set the translation to what it should be
        if (i == 0){
          out.translate(0,217);
        } else if (i == 1){
          out.rotate(Math.PI/3,125,125);
          out.translate(17,-187);
        } else {
          out.rotate(Math.PI/6,125,125);
          out.translate(-187,17);
        }
        return out;
      }
    }
    return null;
  }

  /**
  * Checks if a Triangle foundation should be placed connected to the current Triangle foundation
  * @param p the position of the mouse cursor relative to the map
  * @return the transformation that should be applied to the Triangle. null if none should be applied
  */
  public AffineTransform checkPlacingHitBoxesTriangle(Point2D p){
    //check all sides
    for (int i = 0; i < 3; ++i){
      //check  if the mouse is in a placing hitbox
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
        AffineTransform out2 = new AffineTransform();
        //if so set the translation to what it should be
        if (i == 0){
          out2.translate(0,144);
          out2.rotate(Math.PI,125,144.831216351);
        } else if (i == 1){
          out2.rotate(Math.PI/3,125,144.831216351);
          out2.translate(0,-144);
        } else {
          out2.rotate(5*Math.PI/3,125,144.831216351);
          out2.translate(0,-144);
        }
        out.concatenate(out2);
        return out;
      }
    }
    return null;
  }

  /**
  * Checks if a Wall foundation should be placed connected to the current Triangle foundation
  * @param p the position of the mouse cursor relative to the map
  * @return the transformation that should be applied to the Wall. null if none should be applied
  */
  public AffineTransform checkPlacingHitBoxesWall(Point2D p){
    //check all sides
    for (int i = 0; i < 3; ++i){
      //check if mouse is in the placing hit box
      if(placingHitBoxs[i].contains(p)){
        //set the translation to where it should be
        AffineTransform out = new AffineTransform(transform);
        AffineTransform out2 = new AffineTransform();
        if (i == 0){
          out2.translate(0,210);
          out2.rotate(Math.PI,125,5);
        } else if (i == 1){
          out2.rotate(Math.PI/3,125,5);
          out2.translate(120,0);
        } else {
          out2.rotate(5*Math.PI/3,125,5);
          out2.translate(-120,0);
        }
        out.concatenate(out2);
        return out;
      }
    }
    return null;
  }
}
