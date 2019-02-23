package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    private int red;
    private int green;
    private int blue;

    public Clorus(double e) {
        super("clorus");
        red = 34;
        green = 0;
        blue = 31;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(red, green, blue);
    }

    public void attack(Creature c) {
        this.energy += c.energy();
    }

    public void move() {
        this.energy -= 0.03;
        if (this.energy < 0){
            this.energy = 0;
        }
    }

    public void stay() {
        this.energy -= 0.01;
        if (this.energy < 0){
            this.energy = 0;
        }
    }

    public Clorus replicate() {
        this.energy = this.energy / 2;
        double e = this.energy;
        return new Clorus(e);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")){
                emptyNeighbors.addLast(d);
            }
        }

        Deque<Direction> plips = new ArrayDeque<>();
        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("plip")) {
                plips.addLast(d);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plips.size() != 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plips));
        } else if (this.energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
