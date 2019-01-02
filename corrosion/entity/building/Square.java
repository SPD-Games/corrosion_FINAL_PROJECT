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

public class Square extends Building {
  private static BufferedImage[][] sprites = new BufferedImage[1][4];
  public transient Sprite sprite;

  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/square/twigSquare.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading square Sprite: " + e);
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

    //gets all vertecies
    Point2D a = new Point2D.Double(0,0);
    a = transform.transform(a,null);
    Point2D b = new Point2D.Double(0,250);
    b = transform.transform(b,null);
    Point2D c = new Point2D.Double(250,250);
    c = transform.transform(c,null);
    Point2D d = new Point2D.Double(250,0);
    d = transform.transform(d,null);
    Point2D e = new Point2D.Double(125,125);
    e = transform.transform(e,null);

    //draws all hitboxes for triangles
    g.setColor(Color.GREEN);
    g.fillOval((int)a.getX()-5, (int)a.getY()-5, 10, 10);
    g.fillOval((int)b.getX()-5, (int)b.getY()-5, 10, 10);
    g.fillOval((int)c.getX()-5, (int)c.getY()-5, 10, 10);
    g.fillOval((int)d.getX()-5, (int)d.getY()-5, 10, 10);
    g.fillOval((int)e.getX()-5, (int)e.getY()-5, 10, 10);

    Path2D t1 = new Path2D.Double();
    t1.moveTo(a.getX(), a.getY());
    t1.lineTo(b.getX(), b.getY());
    t1.lineTo(e.getX(), e.getY());
    ((Graphics2D)g).fill(t1);

    g.setColor(Color.BLUE);
    Path2D t2 = new Path2D.Double();
    t2.moveTo(c.getX(), c.getY());
    t2.lineTo(b.getX(), b.getY());
    t2.lineTo(e.getX(), e.getY());

    ((Graphics2D)g).fill(t2);

    g.setColor(Color.RED);
    Path2D t3 = new Path2D.Double();
    t3.moveTo(c.getX(), c.getY());
    t3.lineTo(d.getX(), d.getY());
    t3.lineTo(e.getX(), e.getY());

    ((Graphics2D)g).fill(t3);

    g.setColor(Color.PINK);
    Path2D t4 = new Path2D.Double();
    t4.moveTo(a.getX(), a.getY());
    t4.lineTo(d.getX(), d.getY());
    t4.lineTo(e.getX(), e.getY());

    ((Graphics2D)g).fill(t4);
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
