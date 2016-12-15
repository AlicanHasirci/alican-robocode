package peakrobots.alican;

import robocode.ScannedRobotEvent;

import java.awt.*;

public class Tank {
    private static final int SIZE = 50;
    private static final Color DEBUG = new Color (0, 1, 1, .5f);

    public Position position = new Position();

    public void setPosition (double x, double y) {
        position.set(x, y);
    }

    public void drawTank(Graphics g) {
        g.setColor(getColor());
        g.fillRect((int)position.x - SIZE / 2, (int)position.y - SIZE / 2, SIZE, SIZE);
    }

    protected Color getColor () {
        return DEBUG;
    }
}