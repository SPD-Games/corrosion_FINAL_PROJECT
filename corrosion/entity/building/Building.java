
package corrosion.entity.building;

import corrosion.entity.Entity;
import corrosion.entity.player.*;

import java.awt.Graphics;
import java.awt.Shape;

public abstract class Building extends Entity {
  protected static final int TWIG = 0;
  protected static final int WOOD = 1;
  protected static final int STONE = 2;
  protected static final int METAL = 3;
  protected double hp;
  protected double maxHp;
  protected boolean placed;
  protected int state = 0;

  public Building(){
    super();
  }

  public Building(double xPos, double yPos, double rotation){
    super(xPos, yPos, rotation, -1);
  }

  public abstract void draw(Graphics g, long t);
  public abstract void drawPreview(Graphics g, Player p);
  public abstract Shape getHitBox();
  public abstract Shape getBuildingHitBox();
  public abstract void upgrade(int level);
  public abstract boolean place();
  
  public void repair(double hp){
    hp += this.hp;
    this.hp = Math.min(maxHp, hp);
  }

  public void hit(double damage){
    hp -= damage;
    if (hp <= 0){
      //remove
    }
  }
}
