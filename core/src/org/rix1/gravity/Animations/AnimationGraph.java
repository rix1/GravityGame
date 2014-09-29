package org.rix1.gravity.Animations;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.LinkedList;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */

public class AnimationGraph{

    private LinkedList<Animation> quedAnimations;
    private Sprite currentSprite;

    public boolean contains(Animation b){
        return quedAnimations.contains(b);
    }

    public AnimationGraph(){
        quedAnimations = new LinkedList<Animation>();
        quedAnimations.add(new IdleAnimation(0, 0, "playerWalk.txt"));
    }

    public void add(Animation animation){
        if(quedAnimations.contains(animation))
            quedAnimations.remove(animation);
        quedAnimations.addFirst(animation);
    }

    @Override
    public String toString() {
        return "AnimationGraph{" +
                "quedAnimations=" + quedAnimations.toString() +
                ", currentSprite=" + currentSprite +
                '}';
    }

    public Sprite getNext(){
        currentSprite = quedAnimations.getFirst().animate();

        if(currentSprite == null){
            quedAnimations.removeFirst();
            getNext();
        }
        return currentSprite;
    }

    public void remove(){
        if(quedAnimations.size() >= 2){
            quedAnimations.removeFirst();
        }
    }

    public Sprite getCurrentSprite(){
        return currentSprite;
    }
}
