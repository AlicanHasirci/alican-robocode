package peakrobots.alican;

import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.util.*;

public class Acheron extends AdvancedRobot {

    private Tank me = new Tank();
    private Map<String, Enemy> enemies = new HashMap<>();
    int movementDirection = 1;

    public void run() {
        setColors(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
        addCustomEvent(new RadarTurnCompleteCondition(this));
        while (true) {
            turnRadarLeft(45);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        me.setPosition(getX(), getY());
        double distance = e.getDistance();
        System.out.println(distance);
        double globalBearing = (this.getHeadingRadians() + e.getBearingRadians());
        double x = distance * Math.sin(globalBearing) + me.position.x;
        double y = distance * Math.cos(globalBearing) + me.position.y;

        String name = e.getName();
        Enemy enemy;
        if (enemies.containsKey(name)) {
            enemy = enemies.get(e.getName());
        } else {
            enemy = new Enemy();
            addCustomEvent(new EnemyFiredCondition(enemy));
            enemies.put(name, enemy);
        }
        enemy.OnScanned(e,x,y);
    }

    public void onRobotDeath(RobotDeathEvent event) {
        enemies.remove(event.getName());
    }

    public void onCustomEvent(CustomEvent e) {
        Condition condition = e.getCondition();
        if (condition instanceof RadarTurnCompleteCondition) {
            fireToClosest();
        }
        if (condition instanceof EnemyFiredCondition) {
            dodge(((EnemyFiredCondition) condition).enemy);
        }
    }

    private void dodge(Enemy enemy) {
        ScannedRobotEvent e = enemy.event;
        setTurnRight(e.getBearing() + 90 * movementDirection);
        movementDirection = -movementDirection;
        setAhead((e.getDistance() / 4 + 25) * movementDirection);
    }

    private void fireToClosest() {
        Enemy e = findClosestEnemy();
        if (e != null) {
            if (e.event.getDistance() < 300)
                setTurnGunRightRadians(Utils.normalRelativeAngle(this.getHeadingRadians() + e.event.getBearingRadians() - this.getGunHeadingRadians()));
            if (e.event.getDistance() < 200)
                setFire((getEnergy() / 100) * 3);
        }
    }

    private Enemy findClosestEnemy () {
        String name = "";
        double closest = Double.MAX_VALUE;
        for (Map.Entry<String, Enemy> entry : enemies.entrySet()) {
            Enemy enemy = entry.getValue();
            double distance = enemy.event.getDistance();
            if (distance < closest) {
                closest = distance;
                name = enemy.event.getName();
            }
        }
        return enemies.get(name);
    }

    @Override
    public void onPaint(Graphics2D g) {
        super.onPaint(g);
        me.drawTank(g);
        for (Map.Entry<String, Enemy> entry : enemies.entrySet()) {
            entry.getValue().drawTank(g);
        }
    }
}
