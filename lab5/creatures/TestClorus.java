package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @authr FIXME
 */

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 31), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.01);
        p.move();
        assertEquals(1.94, p.energy(), 0.01);
        p.stay();
        assertEquals(1.93, p.energy(), 0.01);
        p.stay();
        assertEquals(1.92, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        Clorus other = c.replicate();
        assertTrue(c.energy() == other.energy());
        assertEquals(other.energy(),c.energy(), .02);
        assertFalse(c.equals(other));
    }

    @Test
    public void testChoose() {

        Clorus c = new Clorus(2.0);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);

        c.attack(new Clorus(2.0));
        assertEquals(c.energy(), 4.0, .02);

        c = new Clorus(2.0);
        HashMap<Direction, Occupant> nowhere = new HashMap<Direction, Occupant>();
        nowhere.put(Direction.TOP, new Plip());
        nowhere.put(Direction.BOTTOM, new Impassible());
        nowhere.put(Direction.LEFT, new Impassible());
        nowhere.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(nowhere);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        c = new Clorus(2.0);
        HashMap<Direction, Occupant> rep = new HashMap<Direction, Occupant>();
        rep.put(Direction.TOP, new Empty());
        rep.put(Direction.BOTTOM, new Impassible());
        rep.put(Direction.LEFT, new Impassible());
        rep.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(rep);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);
        c.replicate();
        assertEquals(c.energy(), 1.0, .02);

        c = new Clorus(.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Impassible());
        allEmpty.put(Direction.BOTTOM, new Impassible());
        allEmpty.put(Direction.LEFT, new Impassible());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.MOVE, Direction.RIGHT);

        assertEquals(unexpected, actual);
    }
}
