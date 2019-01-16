//icon
//sprites - animation frames - 2d image array
package corrosion;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

public class Sprite{
  //timer object used to call animate every set amount of time
  private Timer animationTimer = new Timer(0, new AbstractAction() {
      @Override
      /**
      * Calls animate every delay timer
      * @param ae null
      */
      public void actionPerformed(ActionEvent ae) {
        animate();
      }
  });

  private final BufferedImage icon;
  private int[] state;//formated {animation, frame}
  private final BufferedImage[][] sprites;
  private int[] delay;//delay of each animation {delay a0, delay a1...}

  /**
  * Main Constructor
  * @param icon the icon of the Sprite
  * @param state the initial state of the Sprite
  * @param sprites array of animations(arrays of buffered images)
  * @param delay the delay time in ms for each animation
  */
  public Sprite(BufferedImage icon, int[] state, BufferedImage[][] sprites, int[] delay){
    this.icon = icon;
    this.state = state;
    this.sprites = sprites;
    this.delay = delay;
  }

  public int[] getDelay(){
    return delay;
  }

  /**
  * Gets the current frame of the sprites
  * @return the current frame of the sprites
  */
  public BufferedImage getFrame(){
    return sprites[state[0]][state[1]];
  }

  /**
  * Gets the current state of the sprites
  * @return the current state of the sprites
  */
  public int[] getState(){
    return state;
  }

  /**
  * Checks if a state matches the current state
  * @param animation the animation Number
  * @param frame the frame number
  * @return if they do match
  */
  public boolean isState(int animation, int frame){
    return (animation == state[0]) && (frame == state[1]);
  }

  /**
  * Checks if a state matches the current state
  * @param animation the animation number
  * @param frame the frame number
  * @param  animationState true - the animation is running. false - the animation has ended
  * @return if they do match
  */
  public boolean isState(int animation, int frame, boolean animationState){
    return isState(animation, frame) && (animationState == animationTimer.isRunning());
  }

  /**
  * Checks if a state matches the current state
  * @param state the animation state to check
  * @param  animationState true - the animation is running. false - the animation has ended
  * @return if they do match
  */
  public boolean isState(int[] state, boolean animationState){
    return isState(state[0], state[1], animationState);
  }

  /**
  * Checks if a state matches the current state
  * @param state the animation state to check
  * @param  animationState true - the animation is running. false - the animation has ended
  * @return if they do match
  */
  public boolean isState(int[] state){
    return isState(state[0], state[1]);
  }

  /**
  * Sets the state of the animation
  * @param animation the animation number
  * @param frame the frame number
  */
  public void setState(int animation, int frame){
    synchronized (state){
      state[0] = animation;
      state[1] = frame;
    }
  }

  /**
  * Gets the icon of the sprites
  * @return the icon of the sprite
  */
  public BufferedImage getIcon(){
    return icon;
  }

  /**
  * Starts the selected endAnimation
  * @param i the index of the animation
  */
  public void startAnimation(int i){
    //locks state
    synchronized (state){
      //sets the animation frame
      state = new int[]{i,0};
    }

    //checks if animation is length 1
    if (sprites[i].length == 1){
      return;
    }

    //sets the timer delay to the set delay for the animation
    animationTimer.setDelay(delay[i]);

    //starts the timer
    animationTimer.start();
  }

  /**
  * Sets the frame to the next frame
  */
  private void animate(){
    //locks state
    synchronized (state){
    //checks if the next frame is the last frame
    if (state[1] + 2 == sprites[state[0]].length){
      endAnimation();
    }
      //next frame
      ++state[1];
    }
  }

  /**
  * go to next frame
  */
  public void nextFrame(){
    synchronized (state){
      if (state[1] + 1 < sprites[state[0]].length){
        ++state[1];
      }
    }
  }

  /**
  * Stops the current animation
  */
  public void endAnimation(){
    //stops the timer
    animationTimer.stop();
  }

}
