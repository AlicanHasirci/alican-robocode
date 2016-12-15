package peakrobots.alican;

import robocode.Condition;

public class EnemyFiredCondition extends Condition {

    public Enemy enemy;

    public EnemyFiredCondition (Enemy enemy) {
        name = "EnemyFiredCondition";
        this.enemy = enemy;
    }

    @Override
    public boolean test() {
        if (enemy.event != null) {
            double changeInEnergy = enemy.energy - enemy.event.getEnergy();
            enemy.energy = enemy.event.getEnergy();
            return changeInEnergy > 0 && changeInEnergy<=3;
        }
        return false;
    }
}