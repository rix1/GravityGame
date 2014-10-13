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

    public Node(Tile startPos, Tile goalPosition){
        this.playerPos = goalPosition;
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

    public Node(Node parent, Tile currentPos, int weight){
        this.parent = parent;
        this.currentPos = currentPos;
        this.g = parent.getDistanceFromStart()+weight;
        this.h = distanceToGoal(currentPos);
        updateF();
        children = new ArrayList<Node>();
        parent.addKid(this);
    }

    public boolean update(Tile newPosition, Node newParent, int weight){
        int tempF = (newParent.getDistanceFromStart() + weight) + distanceToGoal(newPosition);

        System.out.println("comparing " + tempF + " to existing: " + f);

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

    public Node getParent() {
        return parent;
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

    public int distanceToGoal(Tile position){
        int dist = (Math.abs(playerPos.getIntX()-position.getIntX()) + Math.abs(playerPos.getIntY()-position.getIntY()));

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
