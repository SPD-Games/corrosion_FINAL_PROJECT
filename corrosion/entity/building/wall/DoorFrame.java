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
  private Path2D openHitBox;
  private Path2D closedHitBox;
  private static BufferedImage[][] sprites = new BufferedImage[2][4];

  /**
  * Loads all the sprites
  */
  public static void init(){
    try{
      sprites[0][0] = ImageIO.read(new File("sprites/doorframe/twigFrame.png"));
      sprites[0][1] = ImageIO.read(new File("sprites/doorframe/woodFrame.png"));
      sprites[0][2] = ImageIO.read(new File("sprites/doorframe/stoneFrame.png"));
      sprites[0][3] = ImageIO.read(new File("sprites/doorframe/metalFrame.png"));

      sprites[1][0] = ImageIO.read(new File("sprites/doorframe/twigOpen.png"));
      sprites[1][1] = ImageIO.read(new File("sprites/doorframe/woodOpen.png"));
      sprites[1][2] = ImageIO.read(new File("sprites/doorframe/stoneOpen.png"));
      sprites[1][3] = ImageIO.read(new File("sprites/doorframe/metalOpen.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading DoorFrame Sprite: " + e);
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
  * Blank constructor
  */
  public DoorFrame(){
    this(0, 0, 0);
  }

  /**
  * Draws the DoorFrame
  * @param g the graphics context to Use
  * @param t the time since last frame
  */
  public void draw(Graphics g, long t){
    ((Graphics2D)g).drawImage(sprite.getFrame(), transform, null);
  }

  public DoorFrame(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    sprite = new Sprite(null, new int[]{0,0}, sprites, new int[]{0});
  }

  public void open(){
    open = !open;
    if (open){
      hitBox = openHitBox;
      state[0] = 1;
    }else{
      hitBox = closedHitBox;
      state[0] = 0;
    }
    sprite.setState(state[0],state[1]);
    Protocol.send(8, this, Client.getConnection());
  }

  @Override
  public void upgrade(int level){
    state[1] = level;
    sprite.setState(state[0], state[1]);
    if (level == WOOD){
      hp = 10*25;
    } else if (level == STONE){
      hp = 60*25;
    } else if (level == METAL){
      hp = 120*25;
    }
    Protocol.send(8, this, Client.getConnection());
  }

  @Override
  public boolean place(){
    if (super.place()){
      closedHitBox = buildingHitBox;
      openHitBox = null;
    }
    return false;
  }

  @Override
  public Shape getBuildingHitBox(){
    return buildingHitBox;
  }
}
