package org.rix1.gravity.Utils.Astar;

import com.badlogic.gdx.utils.Array;
import org.rix1.gravity.Entites.EnemyEntity;
import org.rix1.gravity.Entites.Player;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.GameMap;
import org.rix1.gravity.Utils.Tile;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description:
 */

public class AstarLogic {

    private Node initialNode; // This is the starting postion
    private Array<Node> closedNodes = new Array<Node>();
    private PriorityQueue<Node> openNodes = new PriorityQueue<Node>();
    private Stack<Node> backTrackStack = new Stack<Node>();
    private Node currentNode;
    private boolean solutionFound = false;
    private GameMap tempMap;
    private boolean cornerNearBy;
    private GameClass game;
    private EnemyEntity enemy;
    private boolean cornerFound;
    private ArrayList<Node> tempArray;

    public AstarLogic(EnemyEntity enemyEntity, Player goal){
        this.enemy = enemyEntity;
        game = goal.getGame();
        initialNode = new Node(enemyEntity.getTile(), goal.getTile());
        pushToOpenQueue(initialNode);
        agendaLoop();
    }

    public boolean agendaLoop(){

        while(!openNodes.isEmpty()){
            currentNode = openNodes.poll();

            if(currentNode.solutionFound()){
//                System.out.println("Solution found at " + currentNode.getCurrentPos().toString()     +  " , starting backtrack");
                solutionFound = true;
                backtrack(currentNode);
                return true;
            }
            else findKids(currentNode);
        }

        if(openNodes.isEmpty() && !solutionFound){
            System.out.println("NO SOLUTION FOUND. LAST POS: " + currentNode.getCurrentPos().toString());
        }
        return false;
    }

    private void backtrack(Node node){
        backTrackStack.clear();

        backTrackStack.push(node);
        while(node.getParent() != null) {
            node = node.getParent();
            backTrackStack.push(node);
        }
    }

    public Stack<Node> getBackTrack(){
        return backTrackStack;
    }


    public void findKids(Node parent){
        Tile currentTile = parent.getCurrentPos();
        cornerFound = false;

        int startX = currentTile.getIntX()-1;
        int startY = currentTile.getIntY()-1;

        for (int y = startY; y < startY+3; y++) {
            for (int x = startX; x < startX+3; x++) {
                if(game.getLogicalMap()[y][x] == GameMap.EMPTY){ // If the tile is NOT occupied
                    Tile tempTile = new Tile(x, y);
                    if(cornerSearch(x, y))
                        cornerFound = true;
                    if(cornerFound) {
                        generateKids(parent, tempTile, GameMap.WEIGHT_DEFAULT);
                    }else generateKids(parent, tempTile, GameMap.WEIGHT_DEFAULT);
                }
            }
        }
        pushToClosedQueue(parent);
    }

    public boolean cornerSearch(int x, int y){
        tempMap = game.getMap();
        cornerNearBy = false;

        // Test for corner SOUTH-WEST
        if(tempMap.isWall(x-1,y-1) && !tempMap.isWall(x,y-1) && !tempMap.isWall(x-1,y)){
            cornerNearBy = true;
//            System.out.println("FOUND CORNER SOUTH-WEST: " + y + "," + x);
        }
        // Test for corner SOUTH-EAST
        if(tempMap.isWall(x+1,y-1) && !tempMap.isWall(x,y-1) && !tempMap.isWall(x+1,y)){
            cornerNearBy = true;
//            System.out.println("FOUND CORNER SOUTH-EAST: " + y + "," + x);
        }
        // Test for corner NORTH-WEST
        if(tempMap.isWall(x-1,y+1) && !tempMap.isWall(x,y+1) && !tempMap.isWall(x-1,y)){
            cornerNearBy = true;
//            System.out.println("FOUND CORNER NORTH-WEST: " + y + "," + x);
        }
        // Test for corner NORTH-EAST
        if(tempMap.isWall(x+1,y+1) && !tempMap.isWall(x,y+1) && !tempMap.isWall(x+1,y)){
            cornerNearBy = true;
//            System.out.println("FOUND CORNER NORTH-EAST: " + y + "," + x);
        }
        return cornerNearBy;
    }

    public void generateKids(Node parent, Tile position, int weight){
        Node toBeUpdated = existsInOpenList2(position);
        if(toBeUpdated != null) {
            System.out.println("UPDATING EXISTING NODE");
            toBeUpdated.update(position, parent, weight);
            pushToOpenQueue(toBeUpdated);
        }
        else {
            // Create new node and add to open.
            pushToOpenQueue(new Node(parent, position, weight));
        }
    }

    // Somehting wrong here...
    public Node existsInOpenList2(Tile pos){
//        System.out.println("Looking for match");
        tempArray = new ArrayList<Node>(openNodes);

        for (int i = 0; i < tempArray.size(); i++) {
            if(tempArray.get(i).getCurrentPos().getIntX() == pos.getIntX() && tempArray.get(i).getCurrentPos().getIntY() == pos.getIntY()){
//                System.out.println("Matching positions!");
            }
        }
        return null;
    }

    public Node existsInOpenList(Tile pos) {
        tempArray = new ArrayList<Node>(openNodes);
        System.out.println("CHECKING IN OPEN LIST");
        for (int i = 0; i < tempArray.size(); i++) {
            System.out.println("comparing: " + pos.toString() + " to:" + tempArray.get(i).getCurrentPos().toString());
            if(pos.toString().equals(tempArray.get(i).getCurrentPos().toString())){
                System.out.println("STRINGMATCH");
            }
//            if (tempArray.get(i).getCurrentPos().compareTo(pos) == 0) { // this means the position is equal i guess
//                System.out.println("Comparing nodePOs: " + tempArray.get(i).getCurrentPos().toString() + " to other pos " + pos.toString());
//                return tempArray.get(i);
            }
//        }
        return null;
    }

    public void pushToOpenQueue(Node node){
        node.setStatus(true);
        game.getLogicalMap()[node.getCurrentPos().getIntY()][node.getCurrentPos().getIntX()] = GameMap.OPEN;
        openNodes.add(node);
    }


    public void pushToClosedQueue(Node processedNode){
        processedNode.setStatus(false);
        game.getLogicalMap()[processedNode.getCurrentPos().getIntY()][processedNode.getCurrentPos().getIntX()] = GameMap.CLOSED;
        closedNodes.add(processedNode);
    }




}