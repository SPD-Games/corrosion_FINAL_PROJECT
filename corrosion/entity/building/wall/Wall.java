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
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/wall/twigWall.png"));
      sprites[0][1] = ImageIO.read(new File("sprites/wall/woodWall.png"));
      sprites[0][2] = ImageIO.read(new File("sprites/wall/stoneWall.png"));
      sprites[0][3] = ImageIO.read(new File("sprites/wall/metalWall.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading wall Sprite: " + e);
      System.exit(-1);
    }
  }

  public void fromServer(){
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  public Wall(){
    this(0, 0, 0);
  }

  public Wall(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    setZIndex(2);
    state = new int[]{0,0};
    sprite = new Sprite(null, state, sprites, new int[]{0});
  }

  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public void drawPreview(Graphics g, Player p){
    placeable = false;
    Point2D pointOnMap = Mouse.getPointOnMap();
    Point mousePos = Mouse.getPosition();
    ArrayList<Entity> entities = Client.getEntities();

    transform.setToTranslation(pointOnMap.getX()-125, pointOnMap.getY()-2.5);
    rotation = Math.atan2(mousePos.getX(), mousePos.getY());
    transform.rotate(rotation, 125, 2.5);

    boolean onFoundation = false;
    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        AffineTransform newTransform = ((Square)entities.get(i)).checkPlacingHitBoxesWall(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          onFoundation = true;
          break;
        }
      }else if (entities.get(i) instanceof Triangle){
        AffineTransform newTransform = ((Triangle)entities.get(i)).checkPlacingHitBoxesWall(pointOnMap);
        if (newTransform != null){
          transform = newTransform;
          onFoundation = true;
          break;
        }
      }
    }

    if(!onFoundation){return;}
    buildingHitBox = new Path2D.Double();
    buildingHitBox.moveTo(15, 0);
    buildingHitBox.lineTo(235, 0);
    buildingHitBox.lineTo(235, 10);
    buildingHitBox.lineTo(15, 10);
    buildingHitBox.transform(transform);
    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Wall){
        Shape otherHitBox = ((Wall)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          return;
        }
      }
    }
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
    placeable = true;
  }

  public Shape getBuildingHitBox(){
    return buildingHitBox;
  }

  @Override
  public Shape getHitBox(){
    return hitBox;
  }

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

  public boolean place(){
    if(!placeable){return false;}
    ArrayList<Entity> entities = Client.getEntities();
    buildingHitBox = new Path2D.Double();
    buildingHitBox.moveTo(15, 0);
    buildingHitBox.lineTo(235, 0);
    buildingHitBox.lineTo(235, 10);
    buildingHitBox.lineTo(15, 10);
    buildingHitBox.transform(transform);

    hitBox = new Path2D.Double();
    hitBox.moveTo(-5, 0);
    hitBox.lineTo(255, 0);
    hitBox.lineTo(255, 10);
    hitBox.lineTo(-5, 10);
    hitBox.transform(transform);
    xPos = buildingHitBox.getCurrentPoint().getX();
    yPos = buildingHitBox.getCurrentPoint().getY();
    boolean onFoundation = false;
    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Square){
        Shape otherHitBox  = ((Square)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          onFoundation = true;
          break;
        }
      }else if (entities.get(i) instanceof Triangle){
        Shape otherHitBox = ((Triangle)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          onFoundation = true;
          break;
        }
      }
    }

    if(!onFoundation){return false;}

    for (int i = 0; i < entities.size(); ++i){
      if (entities.get(i) instanceof Wall){
        Shape otherHitBox = ((Wall)entities.get(i)).getBuildingHitBox();
        if (HitDetection.hit(otherHitBox,buildingHitBox)){
          return false;
        }
      }
    }
    if(!MainPlayer.getMainPlayer().getInvetory().removeItem(new Wood(25))){return false;}

    id = Client.getId();
    Client.addEntity(this);
    Protocol.send(8, this, Client.getConnection());
    return true;
  }
}
