/**
* Michael Metzinger
* Jan 13 2019
* A square foundation that can have walls/doors/other foundations connected to it
*/

package corrosion.entity.building;

import corrosion.entity.building.Building;
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

public class Square extends Building {
  private static BufferedImage[][] sprites = new BufferedImage[1][4];
  private transient Sprite sprite;
  private Path2D[] placingHitBoxs = new Path2D[4];
  private Path2D hitBox;
  private int[] state;

  /**
  * Sets up all the sprites
  */
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/square/twigSquare.png"));
      sprites[0][1] = ImageIO.read(new File("sprites/square/woodSquare.png"));
      sprites[0][2] = ImageIO.read(new File("sprites/square/stoneSquare.png"));
      sprites[0][3] = ImageIO.read(new File("sprites/square/metalSquare.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading square Sprite: " + e);
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
  public Square(){
    this(0, 0, 0);
  }

  /**
  * Main Constructor
  * @param xPos the x position of the Square
  * @param yPos the y position of the Square
  * @param rotation the rotation of the Square
  */
  public Square(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    state = new int[]{0,0};
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  /**
  * Draws the Square
  * @param g the graphics context to Use
  * @param t the time since last frame
  */
  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }


  public void drawPreview(Graphics g, Player p){
    Point2D pointOnMap = Mouse.getPointOnMap();
    Point mousePos = Mouse.getPosition();
    ArrayList<Entity> entities = Client.getEntities();

    transform.setToTranslation(pointOnMap.getX()-125, pointOnMap.getY()-125);
    rotation = Math.atan2(mousePos.getX(), mousePos.getY());
    transform.rotate(rotation, 125, 125);

    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        AffineTransform newTransform = ((Square)entities.get(i)).checkPlacingHitBoxesSquare(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          break;
        }
      }else if (entities.get(i) instanceof Triangle){
        AffineTransform newTransform = ((Triangle)entities.get(i)).checkPlacingHitBoxesSquare(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          break;
        }
      }
    }

    Point2D a = new Point2D.Double(0,0);
    a = transform.transform(a,null);
    Point2D b = new Point2D.Double(0,249);
    b = transform.transform(b,null);
    Point2D c = new Point2D.Double(249,249);
    c = transform.transform(c,null);
    Point2D d = new Point2D.Double(249,0);
    d = transform.transform(d,null);
    hitBox = new Path2D.Double();
    hitBox.moveTo(a.getX(), a.getY());
    hitBox.lineTo(b.getX(), b.getY());
    hitBox.lineTo(c.getX(), c.getY());
    hitBox.lineTo(d.getX(), d.getY());

    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        Shape otherHitBox = ((Square)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          return;
        }
      } else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          return;
        }
      }
    }
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public Shape getBuildingHitBox(){
    return hitBox;
  }

  @Override
  public Shape getHitBox(){return null;}

  public void upgrade(int level){
    state = new int[]{0,level};
    Inventory i = MainPlayer.getMainPlayer().getInvetory();
    Item uses;
    if (level == WOOD){
      uses = new Wood(100);
      if (!i.removeItem(uses)){return;}
      hp = 30*25;
    } else if (level == STONE){
      uses = new Stone(100);
      if (!i.removeItem(uses)){return;}
      hp = 120*25;
    } else if (level == METAL){
      uses = new Metal(100);
      if (!i.removeItem(uses)){return;}
      hp = 240*25;
    }
    sprite.setState(0, level);
    Protocol.send(8, this, Client.getConnection());
  }
  public AffineTransform checkPlacingHitBoxesSquare(Point2D p){
    for (int i = 0; i < 4; ++i){
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
        if (i == 0){
          out.translate(-250,0);
        } else if (i == 1){
          out.translate(0,250);
        } else if(i == 2){
          out.translate(250,0);
        } else {
          out.translate(0,-250);
        }
        return out;
      }
    }
    return null;
  }
  public AffineTransform checkPlacingHitBoxesTriangle(Point2D p){
    for (int i = 0; i < 4; ++i){
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
        if (i == 0){
          out.translate(-197,-20);
          out.rotate(Math.PI/6, 125, 144.831216351);
        } else if (i == 1){
          out.translate(0,177);
          out.rotate(Math.PI, 125, 144.831216351);
        } else if(i == 2){
          out.translate(197,-20);
          out.rotate(-Math.PI/6, 125, 144.831216351);
        } else {
          out.translate(0,-217);
        }
        return out;
      }
    }
    return null;
  }

  public AffineTransform checkPlacingHitBoxesWall(Point2D p){
    for (int i = 0; i < 4; ++i){
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
        if (i == 0){
          out.rotate(Math.PI/2,-125,-125);
          out.translate(0,-255);
        } else if (i == 1){
          out.translate(0,245);
        } else if(i == 2){
          out.rotate(Math.PI/2,-125,-125);
          out.translate(0,-505);
        } else {
          out.translate(0,-5);
        }
        return out;
      }
    }
    return null;
  }

  public boolean place(){
    //gets all vertecies
    Point2D a = new Point2D.Double(1,1);
    a = transform.transform(a,null);
    Point2D b = new Point2D.Double(1,249);
    b = transform.transform(b,null);
    Point2D c = new Point2D.Double(249,249);
    c = transform.transform(c,null);
    Point2D d = new Point2D.Double(249,1);
    d = transform.transform(d,null);
    Point2D e = new Point2D.Double(125,125);
    e = transform.transform(e,null);

    xPos = e.getX();
    yPos = e.getY();

    hitBox = new Path2D.Double();
    hitBox.moveTo(a.getX(), a.getY());
    hitBox.lineTo(b.getX(), b.getY());
    hitBox.lineTo(c.getX(), c.getY());
    hitBox.lineTo(d.getX(), d.getY());

    ArrayList<Entity> entities = Client.getEntities();
    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        Shape otherHitBox = ((Square)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          //TODO DRAW CANNOT PLACE
          return false;
        }
      } else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          //TODO DRAW CANNOT PLACE
          return false;
        }
      }
    }
    //create all hitboxes
    placingHitBoxs[0] = new Path2D.Double();
    placingHitBoxs[0].moveTo(a.getX(), a.getY());
    placingHitBoxs[0].lineTo(b.getX(), b.getY());
    placingHitBoxs[0].lineTo(e.getX(), e.getY());
    placingHitBoxs[1] = new Path2D.Double();
    placingHitBoxs[1].moveTo(c.getX(), c.getY());
    placingHitBoxs[1].lineTo(b.getX(), b.getY());
    placingHitBoxs[1].lineTo(e.getX(), e.getY());
    placingHitBoxs[2] = new Path2D.Double();
    placingHitBoxs[2].moveTo(c.getX(), c.getY());
    placingHitBoxs[2].lineTo(d.getX(), d.getY());
    placingHitBoxs[2].lineTo(e.getX(), e.getY());
    placingHitBoxs[3] = new Path2D.Double();
    placingHitBoxs[3].moveTo(a.getX(), a.getY());
    placingHitBoxs[3].lineTo(d.getX(), d.getY());
    placingHitBoxs[3].lineTo(e.getX(), e.getY());

    if(!MainPlayer.getMainPlayer().getInvetory().removeItem(new Wood(25))){return false;}

    id = Client.getId();
    Client.addEntity(this);
    Protocol.send(8, this, Client.getConnection());
    return true;
  }
}
