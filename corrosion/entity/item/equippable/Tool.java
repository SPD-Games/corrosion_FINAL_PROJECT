package corrosion.entity.item.equippable;

import corrosion.entity.item.equippable.Equippable;
import java.awt.image.BufferedImage;
import corrosion.entity.Entity;
import java.io.Serializable;
import corrosion.Sprite;
import corrosion.network.Client;
import corrosion.network.protocol.Protocol;
import java.awt.*;
import java.awt.geom.*;
import corrosion.entity.player.*;
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
import java.util.ArrayList;
import corrosion.HitDetection;
import corrosion.entity.HitMarker;

public class Tool extends Equippable{
  private static BufferedImage icon;
  private static BufferedImage[][] sprites = new BufferedImage[1][];;
  private final int[] USE_READY = {0,5};
  private static Path2D[] hitBoxs = new Path2D[6];
  private boolean hitAnything = false;
  private int damage = 70;
  public static void init(){
    try{
      //loads icon
      icon = ImageIO.read(Tool.class.getResourceAsStream("/sprites/club/icon.png"));
      //loads relaod animations
      sprites[0] = new BufferedImage[6];
      for (int i = 1; i <= 5; ++i){
        sprites[0][i-1] = ImageIO.read(Tool.class.getResourceAsStream("/sprites/club/animation/frame" + i + ".png"));
      }
      sprites[0][5] = sprites[0][0];

      hitBoxs[0] = new Path2D.Double();
      hitBoxs[0].moveTo(450,192);
      hitBoxs[0].lineTo(95,527);
      hitBoxs[0].lineTo(545,340);
      hitBoxs[1] = new Path2D.Double();
      hitBoxs[1].moveTo(385,145);
      hitBoxs[1].lineTo(516,265);
      hitBoxs[1].lineTo(116,547);
      hitBoxs[2] = new Path2D.Double();
      hitBoxs[2].moveTo(315,109);
      hitBoxs[2].lineTo(465,205);
      hitBoxs[2].lineTo(138,560);
      hitBoxs[3] = new Path2D.Double();
      hitBoxs[3].moveTo(245,90);
      hitBoxs[3].lineTo(405,155);
      hitBoxs[3].lineTo(160,575);
      hitBoxs[4] = new Path2D.Double();
      hitBoxs[4].moveTo(163,92);
      hitBoxs[4].lineTo(330,116);
      hitBoxs[4].lineTo(185,578);
      hitBoxs[5] = hitBoxs[0];
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Tool Sprite: " + e);
      System.exit(-1);
    }
  }
  /**
   * Main Constructor
  */
  public Tool(){
    this(new int[]{0,5});
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Tool(int[] state){
    super(new Sprite(icon, state, sprites, new int[]{40}));
    stackable = false;
  }

  /**
   * Main Constructor
   * @param p the player that has the crossbow equipped
  */
  public Tool(double xPos, double yPos, double rotation, long id){
    super(xPos,yPos,rotation, id);
    this.sprite = new Sprite(icon, new int[]{0,5}, sprites, new int[]{40});
  }

  public void reload(){

  }

  public void attack2(Point p, Player player){

  }

  public void attack(Point p, Player player){
    //checks if tool is ready for use
    if (sprite.isState(USE_READY, false)){
      hitAnything = false;
      //starts attack animation
      sprite.startAnimation(0);
    }
  }

  public void attackOff(Player player){

  }

  public void drawEquipped(Graphics g, Player player){
    transform = player.getTransform();
    transform.translate(0, -130);
    transform.scale(0.25,0.25);
    ((Graphics2D)(g)).drawImage(sprite.getFrame(), transform, null);
    update(player);
  }

  public void update(Player player){
    if (player != MainPlayer.getMainPlayer() || sprite.isState(USE_READY, false)){return;}
    ArrayList<Entity> entities = Client.getEntities();
    for (int i = 0; i < entities.size(); ++i){

      Entity e = entities.get(i);
      if (e == this){continue;}
      if (HitDetection.hit(e.getHitBox(), getSwingHitBox())){
        e.hit(damage);
        Protocol.send(8 ,new HitMarker(getXPos(),getYPos(), "-"+damage), Client.getConnection());
        hit();
        return;
      }
    }

    ArrayList<Player> players = Client.getPlayers();
    for (int i = 0; i < players.size(); ++i){
      if (players.get(i).equals(player)){continue;}
      if (HitDetection.hit(players.get(i).getHitBox(), getSwingHitBox())){
        ArrayList out = new ArrayList();
        out.add(players.get(i).getId());
        out.add(damage);
        Protocol.send(8 ,new HitMarker(players.get(i).getXPos()+50,players.get(i).getYPos()+50, "-"+damage), Client.getConnection());
        Protocol.send(10, out, Client.getConnection());
        hit();
        return;
      }
    }
  }

  public double getXPos(){
    Point2D p = transform.transform(new Point2D.Double(),null);
    return p.getX();
  }
  public double getYPos(){
    Point2D p = transform.transform(new Point2D.Double(),null);
    return p.getY();
  }
  public String getInfo(){
    return "";
  }

  public BufferedImage getIcon(){
    return icon;
  }

  public void fromServer(){
    this.sprite = new Sprite(icon, new int[]{0,5}, sprites, new int[]{40});
  }

  public Shape getSwingHitBox(){
    if (hitAnything){return null;}
    Path2D out = (Path2D)hitBoxs[sprite.getState()[1]].clone();
    out.transform(transform);
    return out;
  }

  private void hit(){
    hitAnything = true;
  }
}
