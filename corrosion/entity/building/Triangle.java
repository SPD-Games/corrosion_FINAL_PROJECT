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

public class Triangle extends Building {
  private static BufferedImage[][] sprites = new BufferedImage[1][4];
  public transient Sprite sprite;
  private Path2D[] placingHitBoxs = new Path2D[3];
  private Path2D hitBox;
  private int[] state;

  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/triangle/twigTriangle.png"));
      sprites[0][1] = ImageIO.read(new File("sprites/triangle/woodTriangle.png"));
      sprites[0][2] = ImageIO.read(new File("sprites/triangle/stoneTriangle.png"));
      sprites[0][3] = ImageIO.read(new File("sprites/triangle/metalTriangle.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading triangle Sprite: " + e);
      System.exit(-1);
    }
  }

  public void fromServer(){
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  public Triangle(){
    this(0, 0, 0);
  }

  public Triangle(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    state = new int[]{0,0};
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public void drawPreview(Graphics g, Player p){
    Point2D pointOnMap = Mouse.getPointOnMap();
    Point mousePos = Mouse.getPosition();
    transform.setToTranslation(pointOnMap.getX()-125, pointOnMap.getY()-144.831216351);
    rotation = Math.atan2(mousePos.getX(), mousePos.getY());
    transform.rotate(rotation, 125, 144.831216351);

    ArrayList<Entity> entities = Client.getEntities();

    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        AffineTransform newTransform = ((Square)entities.get(i)).checkPlacingHitBoxesTriangle(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          break;
        }
      } else if (entities.get(i) instanceof Triangle){
        AffineTransform newTransform = ((Triangle)entities.get(i)).checkPlacingHitBoxesTriangle(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          break;
        }
      }
    }

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
    for (int i = 0; i < entities.size(); ++i){
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

    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public Shape getBuildingHitBox(){
    return hitBox;
  }
  @Override
  public Shape getHitBox(){return null;}
  public void upgrade(int level){
    state = new int[]{0,level};
    sprite.setState(0, level);
    if (level == WOOD){
      hp = 30*25;
    } else if (level == STONE){
      hp = 120*25;
    } else if (level == METAL){
      hp = 240*25;
    }
    Protocol.send(8, this, Client.getConnection());
  }
  public boolean place(){
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
    ArrayList<Entity> entities = Client.getEntities();
    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        Shape otherHitBox = ((Square)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          return false;
        }
      } else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,hitBox)){
          return false;
        }
      }
    }

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

    id = Client.getId();
    Client.addEntity(this);
    Protocol.send(8, this, Client.getConnection());
    return true;
  }
  double j = 0;
  public AffineTransform checkPlacingHitBoxesSquare(Point2D p){
    for (int i = 0; i < 3; ++i){
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
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
  public AffineTransform checkPlacingHitBoxesTriangle(Point2D p){
    for (int i = 0; i < 3; ++i){
      if(placingHitBoxs[i].contains(p)){
        AffineTransform out = new AffineTransform(transform);
        AffineTransform out2 = new AffineTransform();
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

  public AffineTransform checkPlacingHitBoxesWall(Point2D p){
    for (int i = 0; i < 3; ++i){
      if(placingHitBoxs[i].contains(p)){
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
