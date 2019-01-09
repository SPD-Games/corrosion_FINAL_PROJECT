
package corrosion.entity.building;

import corrosion.entity.Entity;
import corrosion.entity.player.*;
import corrosion.network.*;
import corrosion.network.protocol.*;
import java.awt.Graphics;
import java.awt.Shape;

public abstract class Building extends Entity {
  protected static final int TWIG = 0;
  protected static final int WOOD = 1;
  protected static final int STONE = 2;
  protected static final int METAL = 3;
  protected double hp = 26;
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
  @Override
  public abstract Shape getHitBox();
  public abstract Shape getBuildingHitBox();
  public abstract void upgrade(int level);
  public abstract boolean place();

  @Override
  public void hit(int damage){
    hp -= damage;
    if (hp <= 0){
      //remove
      Client.removeEntity(this);
      Protocol.send(12,this,Client.getConnection());
      return;
    }
    Protocol.send(8, this, Client.getConnection());
  }
}
