package org.rix1.gravity.Utils.Astar;

import org.rix1.gravity.Utils.Position;
import org.rix1.gravity.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description:
 */

public class Node implements Comparable<Node> {

    private static Position playerPos;
    private static Position startPos;

    private int f = 0, g = 0, h = 0;
    private boolean status; // True = OPEN, false = CLOSED
    private ArrayList<Node> children;
    private Node parent;
    private Position currentPos;


    /**
     * This should only be called when initializing the tree(?)
     * @param playerPos
     * @param startPos
     */

    public Node(Position playerPos, Position startPos){
        this.playerPos = Utils.getTile(playerPos);
        this.startPos = Utils.getTile(startPos);
        g = -1;
        h = getDistToGoal();
    }


    /**
     * Should be called when a new node is added.
     * @param parent
     * @param currentPos
     */

    private Node(Node parent, Position currentPos){
        this.parent = parent;
        this.currentPos = currentPos;
        this.g = parent.getDistanceFromStart()+1;
        this.h = distanceToGoal();
        parent.addKid(this);
    }

    public boolean solutionFound(){
        return h==0;
    }

    public void addKid(Node kid){
        children.add(kid);
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void updateF(){
        f = g+h;
    }

    public int getDistToGoal(){
        return h;
    }

    public int getDistanceFromStart(){
        return g;
    }

    public int getHeuristic(){
        return f;
    }

    public int distanceToGoal(){
        int dist = (Math.abs(playerPos.getIntX()-currentPos.getIntX()) + Math.abs(playerPos.getIntY()-currentPos.getIntY()));
        return dist;
    }

    @Override
    public int compareTo(Node otherNode) {
        return this.getHeuristic() - otherNode.getHeuristic();
    }
}
