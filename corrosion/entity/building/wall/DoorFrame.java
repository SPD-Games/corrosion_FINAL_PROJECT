/**
* Michael Metzinger
* Jan 13 2019
* A door that can be opened and closed
*/

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
import corrosion.Inventory;
import corrosion.entity.item.*;

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
      sprites[0][0] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/metalFrame.png"));
      sprites[0][1] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/woodFrame.png"));
      sprites[0][2] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/stoneFrame.png"));
      sprites[0][3] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/metalFrame.png"));
      sprites[1][0] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/twigOpen.png"));
      sprites[1][1] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/woodOpen.png"));
      sprites[1][2] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/stoneOpen.png"));
      sprites[1][3] = ImageIO.read(DoorFrame.class.getResourceAsStream("/sprites/doorframe/metalOpen.png"));
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

  /**
  * Main Constructor
  * @param xPos the x position of the DoorFrame
  * @param yPos the y position of the DoorFrame
  * @param rotation the rotation of the DoorFrame
  */
  public DoorFrame(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation);
    sprite = new Sprite(null, new int[]{0,0}, sprites, new int[]{0});
  }

  /**
  * Toggles the state of the door. Open to closed. Closed to open.
  */
  public void open(){
    //toggles the state
    open = !open;
    //checks if the door is open
    if (open){
      //change the drawing and hitboxs
      hitBox = openHitBox;
      state[0] = 1;
    }else{
      //change the drawing and hitboxs
      hitBox = closedHitBox;
      state[0] = 0;
    }
    //sends the door to the server
    sprite.setState(state[0],state[1]);
    Protocol.send(8, this, Client.getConnection());
  }

  /**
  * Upgrades the door
  * @param level the level of upgrade to apply to the door
  */
  @Override
  public void upgrade(int level){
    Inventory i = MainPlayer.getMainPlayer().getInvetory();
    Item uses;
    //checks which type to upgrade the door to
    if (level == WOOD){
      //checks if the player has the available resourses
      uses = new Wood(100);
      if (!i.removeItem(uses)){return;}
      //upgrade
      hp = 30*25;
    } else if (level == STONE){
      //checks if the player has the available resourses
      uses = new Stone(100);
      if (!i.removeItem(uses)){return;}
      //upgrade
      hp = 120*25;
    } else if (level == METAL){
      //checks if the player has the available resourses
      uses = new Metal(100);
      if (!i.removeItem(uses)){return;}
      //upgrade
      hp = 240*25;
    }

    //sends Door to server
    state = new int[]{0,level};
    sprite.setState(0, level);
    Protocol.send(8, this, Client.getConnection());
  }

  /**
  * Places the DoorFrame
  * @return if the placement was succsesful
  */
  @Override
  public boolean place(){
    //checks if the door can be placed
    if (super.place()){
      //set the hitboxs
      closedHitBox = hitBox;
      openHitBox = null;
    }
    //door cant be placed
    return false;
  }

  /**
  * Gets the building hitBox
  * @return the building hitBox
  */
  @Override
  public Shape getBuildingHitBox(){
    return buildingHitBox;
  }
}
