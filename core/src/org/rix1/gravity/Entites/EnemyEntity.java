package org.rix1.gravity.Entites;

import org.rix1.gravity.GameClass;
import org.rix1.gravity.GameMap;
import org.rix1.gravity.Utils.Astar.AstarLogic;
import org.rix1.gravity.Utils.Astar.Node;
import org.rix1.gravity.Utils.Direction;

import java.util.Stack;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */
public class EnemyEntity extends MovableEntity{

    public static boolean astarRun = true;
    private AstarLogic aStar;
    private Stack<Node> directions;
    private Node nextDirection;
    private Player player;


    public EnemyEntity(GameClass game, float x, float y, int width, int height, float speed) {
        super(game, x, y, width, height, speed);
        this.texture = GameMap.TEX_ENEMY;
        directions = new Stack<Node>();
    }


    @Override
    public void update(float delta) {
        player = game.getPlayer();

        dx = 0;
        dy = 0;

        if(astarRun) {
            if(player.isPlayerBig()){
                aStar = new AstarLogic(this, game.getGoal());

            }else aStar = new AstarLogic(this, player);
            directions.clear();
            directions = aStar.getBackTrack();
//            System.out.println("Player position: " + player.getTile().toString() + " stack " + directions.toString());
            astarRun = false;
        }

        if(!directions.isEmpty()){
            nextDirection = directions.peek();

            if(getTile().compareTo(nextDirection.getCurrentPos()) == 0){
                directions.pop();
                if(!directions.isEmpty()){
                    nextDirection = directions.peek();
                }
            }
        }else {
//            System.out.println("EMPTY QUEUE - SNo more directions!");
        }

        if(nextDirection!= null) {

            if (nextDirection.getCurrentPos().getIntY() > getTile().getIntY()) {
                dy = speed * delta;
                currentDir = Direction.U;
            }
            if (nextDirection.getCurrentPos().getIntY() < getTile().getIntY()) {
                dy = -speed * delta;
                currentDir = Direction.D;
            }
            if (nextDirection.getCurrentPos().getIntX() < getTile().getIntX()) {
                dx = -speed * delta;
                currentDir = Direction.L;
            }
            if (nextDirection.getCurrentPos().getIntX() > getTile().getIntX()) {
                dx = speed * delta;
                currentDir = Direction.R;
            }
        }
    }
}
