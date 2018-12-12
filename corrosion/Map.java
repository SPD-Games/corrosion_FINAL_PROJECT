/** Micheal Metzinger, Edward Pei
  * December 4 2018
  * draws the map the players will play on
  */
package corrosion;

import java.awt.Graphics;
import java.awt.Color;

public class Map{
  public int[] nodesX = new int[50];
  public int[] nodesY = new int[50];

  //TODO: random generation
  /**
  * Main constructor
  * Generates a circular map
  * @param height the height of the map
  * @param width the width of the map
  */
  public Map(int height, int width){
    for(int i = 0; i < nodesX.length; ++i){
      double f = (double)(2*Math.PI * i / nodesX.length);
      nodesX[i] = (int)(width * Math.sin(f));
      nodesY[i] = (int)(height * Math.cos(f));
    }
  }

  /**
  * Draws the Map
  * @param g the graphics context that is drawn on
  */
  public void draw(Graphics g){
    g.setColor(new Color(0, 100, 0));
    g.fillPolygon(nodesX, nodesY, 50);
  }
}
