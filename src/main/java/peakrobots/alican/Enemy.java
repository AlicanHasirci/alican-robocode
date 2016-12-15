package peakrobots.alican;

import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.ScannedRobotEvent;

import java.awt.*;

public class Enemy extends Tank {

    private static Color DEBUG = new Color(1, 0, 0, .5f);
    public double energy = 100;
    public ScannedRobotEvent event = null;

    protected Color getColor () {
        return DEBUG;
    }

    public void OnScanned (ScannedRobotEvent event, double x, double y) {
        this.event = event;
        this.position.set(x, y);
    }
}