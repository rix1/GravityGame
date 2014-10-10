package org.rix1.gravity.Utils.Astar;

import com.badlogic.gdx.utils.Array;
import org.rix1.gravity.Utils.Position;

import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description:
 */

public class AstarLogic {

    private Node intialNode; // This is the starting postion
    private Array<Node> closedNodes = new Array<Node>();
    private PriorityQueue<Node> openNodes = new PriorityQueue<Node>();
    private Stack backTrackStack = new Stack();


    public AstarLogic(Position playerPos, Position enemyPos){
    // TODO: Initialize OpenQueue
        intialNode = new Node(playerPos, enemyPos);
        // TODO: Add state(?) Figure out what add state does
        pushToOpenQueue(intialNode);
        agendaLoop();
    }

    public void agendaLoop(){
        while(!openNodes.isEmpty()){

            if(openNodes.peek().solutionFound()){
               // TODO: Start backtracking
                break;
            }
            findkids(openNodes.poll());

            if(openNodes.isEmpty()) {
               // This means no solution is found. Do something special.
                break;
            }
        }
    }

    public void findkids(Node parent){
        //TODO: Do collision detection and figure out what surrounding pieces are walls etc.
        for (int i = 0; i < 8; i++) {
            // If tile is walkable
        }

    }

    public void pushToClosedQueue(Node processedNode){
        processedNode.setStatus(false);
        closedNodes.add(processedNode);
    }

    public void pushToOpenQueue(Node node){
        node.setStatus(true);
        openNodes.add(node);
    }


}
