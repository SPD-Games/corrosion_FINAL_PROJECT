package corrosion.entity.item;
import corrosion.entity.item.*;
import corrosion.Sprite;
import corrosion.entity.player.MainPlayer;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Metal extends Item{
  public static void init(){
    BufferedImage icon = null;
    try{
      //loads Barrel icon
      icon = ImageIO.read(new File("sprites/metal.png"));
    }catch(Exception e){
      //exits on error with message
      System.out.println("Reading Metal Sprite: " + e);
      System.exit(-1);
    }
    sprites = new Sprite(icon,null,null,null);
  }
  public static Sprite sprites;
  /**
  * Main Constructor
  * @param x the x position of the Item
  * @param y the y position of the Item
  * @param r the rotation applied to the Item
  */
  public Metal(double x, double y, double r, long id){
    super(x,y,r,id);
    sprite = sprites;
  }

  /**
  * Main Constructor
  */
  public Metal(){
    super();
    sprite = sprites;
  }

  /**
  * Main Constructor
  * @param stackSize the amount of Metal in the stack
  */
  public Metal(int stackSize){
    this();
    this.stackSize = stackSize;
  }
  public String getInfo(){
    return stackSize + "";
  }

  public void fromServer(){
    sprite = sprites;
  }
  public BufferedImage getIcon(){
    return sprite.getIcon();
  }
}
