package corrosion.entity.building.wall;

import corrosion.entity.building.wall.*;
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

public class DoorFrame extends Wall {
  private boolean open = false;
  private Path2D hitBoxTmp;
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/wall/twigWall.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading square Sprite: " + e);
      System.exit(-1);
    }
  }

  public void fromServer(){
    sprite = new Sprite(null, new int[]{0,0}, sprites, new int[]{0});
  }

  public DoorFrame(){
    this(0, 0, 0);
  }

  public DoorFrame(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    sprite = new Sprite(null, new int[]{0,0}, sprites, new int[]{0});
  }

  public void open(){
    open = !open;
    if (open){
      hitBoxTmp = hitBox;
      hitBox = null;
    }else{
      hitBox = hitBoxTmp;
    }
  }

  public Shape getBuildingHitBox(){
    if (hitBoxTmp == null){
      return hitBox;
    }
    return hitBoxTmp;
  }
}
