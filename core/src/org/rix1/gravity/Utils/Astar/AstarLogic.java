package org.rix1.gravity.Utils.Astar;

import com.badlogic.gdx.utils.Array;
import org.rix1.gravity.Entites.EnemyEntity;
import org.rix1.gravity.Entites.Player;
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
    private Stack backTrackStack = new Stack();
    private Node currentNode;
    private EnemyEntity entity;
    private boolean solutionFound = false;


    public AstarLogic(Player player, EnemyEntity enemy){
        this.entity = enemy;
        initialNode = new Node(player.getTile(), enemy.getTile());
        System.out.println("Player pos: " + player.getTile().toString() + " enemy tile " + enemy.getTile().toString());
        pushToOpenQueue(initialNode);
        System.out.println("InitialNode: " +initialNode.toString());
        agendaLoop();
        System.out.println("================== ASTAR finished ===========================");
    }

    public void agendaLoop(){

        while(!openNodes.isEmpty()){
            System.out.println("Start searching! "  + openNodes.size());
            currentNode = openNodes.poll();

            if(currentNode.solutionFound()){
                solutionFound = true;
                System.out.println("SOLUTION FOUND: " + currentNode.toString());
               // TODO: Start backtracking and return list to Enemy
                break;
            }
            else findKids(currentNode);
        }

        if(openNodes.isEmpty() && !solutionFound){
            System.out.println("NO SOLUTION FOUND. LAST POS: " + currentNode.getCurrentPos().toString());
            // No solution found.
        }
    }

    public void findKids(Node parent){
        Tile currentTile = parent.getCurrentPos();

        int startX = currentTile.getX()-1;
        int startY = currentTile.getY()-1;

        for (int y = startY; y < startY+3; y++) {
            for (int x = startX; x < startX+3; x++) {
                if(entity.getGame().getLogicalMap()[y][x] == GameMap.EMPTY){ // If the tile is NOT occupied
                    Tile tempTile = new Tile(x, y);
                    generateKids(parent, tempTile);
                }
            }
        }
        pushToClosedQueue(parent);
    }

    public void generateKids(Node parent, Tile position){
        Node toBeUpdated = existsInOpenList(position);
        if(toBeUpdated != null) {
            toBeUpdated.update(position, parent);
            pushToOpenQueue(toBeUpdated);
        }
        else {
            // Create new node and add to open.
            pushToOpenQueue(new Node(parent, position));
        }
    }

    public Node existsInOpenList(Tile pos) {
        ArrayList<Node> tempArray = new ArrayList<Node>(openNodes);

        for (int i = 0; i < tempArray.size(); i++) {
            if (tempArray.get(i).getCurrentPos().compareTo(pos) == 0) { // this means the position is equal i guess
                return tempArray.get(i);
            }
        }
        return null;
    }

    public void pushToOpenQueue(Node node){
        node.setStatus(true);
        entity.getGame().getLogicalMap()[node.getCurrentPos().getY()][node.getCurrentPos().getX()] = GameMap.OPEN;
        openNodes.add(node);
    }


    public void pushToClosedQueue(Node processedNode){
        processedNode.setStatus(false);
        entity.getGame().getLogicalMap()[processedNode.getCurrentPos().getY()][processedNode.getCurrentPos().getX()] = GameMap.CLOSED;
        closedNodes.add(processedNode);
    }




}