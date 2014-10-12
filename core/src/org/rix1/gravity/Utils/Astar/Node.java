package org.rix1.gravity.Utils.Astar;

import org.rix1.gravity.Utils.Tile;

import java.util.ArrayList;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description:
 */

public class Node implements Comparable<Node> {

    private static Tile playerPos;
    private static Tile startPos;

    private int f = 0, g = 0, h = 0;
    private boolean status; // True = OPEN, false = CLOSED
    private ArrayList<Node> children;
    private Node parent;
    private Tile currentPos;

    public Tile getCurrentPos() {
        return currentPos;
    }

    /**
     * This should only be called when initializing the tree(?)
     * @param playerPos
     * @param startPos
     */



    public Node(Tile playerPos, Tile startPos){
        this.playerPos = playerPos;
        this.startPos = startPos;
        this.currentPos = startPos;
        g = 0;
        h = distanceToGoal(startPos);
        updateF();
        children = new ArrayList<Node>();
    }


    /**
     * Should be called when a new node is added.
     * @param parent
     * @param currentPos
     */

    public Node(Node parent, Tile currentPos){
        this.parent = parent;
        this.currentPos = currentPos;
        this.g = parent.getDistanceFromStart()+1;
        this.h = distanceToGoal(currentPos);
        updateF();
        children = new ArrayList<Node>();
        parent.addKid(this);
    }

    public boolean solutionFound(){
        return h==0;
    }

    public void addKid(Node kid){
        children.add(kid);
    }

    public boolean update(Tile newPosition, Node newParent){
        int tempF = (newParent.getDistanceFromStart() +1) + distanceToGoal(newPosition);
        if(tempF < f){
            this.parent = newParent;
            this.currentPos = newPosition;
            this.g = newParent.getDistanceFromStart()+1;
            this.h = distanceToGoal(newPosition);
            updateF();
            newParent.addKid(this);
            return true;
        }
        return false;
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

    public int distanceToGoal(Tile position){
        int dist = (Math.abs(playerPos.getX()-position.getX()) + Math.abs(playerPos.getY()-position.getY()));

        return dist;
    }

    @Override
    public String toString() {
        return "Node{" +
                "f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", status=" + status +
                ", parent=" + parent +
                ", currentPos=" + currentPos.toString() +
                '}';
    }

    @Override
    public int compareTo(Node otherNode) {
        return this.getHeuristic() - otherNode.getHeuristic();
    }
}
