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

public class Square extends Building {
  private static BufferedImage[][] sprites = new BufferedImage[1][4];
  public transient Sprite sprite;

  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/square/twigSquare.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Pistol Sprite: " + e);
      System.exit(-1);
    }
  }

  public Square(){
    this(0, 0, 0);
  }

  public Square(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    sprite = new Sprite(null, new int[]{0,0}, sprites, new int[]{0});
  }

  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public void drawPreview(Graphics g, Player p){
    Point pointOnMap = Mouse.getPointOnMap();
    Point mousePos = Mouse.getPosition();
    pointOnMap.x -= 125;
    pointOnMap.y -= 125;
    transform.setToTranslation(pointOnMap.x, pointOnMap.y);
    rotation = Math.atan2(mousePos.getX(), mousePos.getY());
    transform.rotate(rotation, 125, 125);
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public Shape getHitBox(){return null;}
  public void upgrade(int level){}

  public void place(){
      Client.addEntity(this);
      id = Client.getId();
      Protocol.send(8, this, Client.getConnection());
  }
}
