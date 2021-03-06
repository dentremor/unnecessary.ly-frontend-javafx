package ly.unnecessary.frontend.components;

import static com.almasb.fxgl.dsl.FXGL.byType;
import static com.almasb.fxgl.dsl.FXGL.play;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;

import ly.unnecessary.frontend.EntityType;
import ly.unnecessary.frontend.PowerupType;

public class BrickComponent extends Component {
    private boolean blockIsInfected = false;

    public BrickComponent() {

    }

    public BrickComponent(int lives) {

    }

    public void hitByBall() {
        var hp = entity.getComponent(HealthIntComponent.class);
        hp.damage(1);

        if (hp.getValue() == 0) {
            if (FXGLMath.randomBoolean(0.1f)) {
                play("alpha/power_up.wav");
                spawn("actionBrick", entity.getPosition());
                blockIsInfected = true;
            }
            entity.removeFromWorld();
        } else
            System.out.println("Lives: " + hp.getValue());

        if (FXGLMath.randomBoolean(1f) && !blockIsInfected) {
            if (byType(EntityType.POWERUPDROP).isEmpty()) {
                PowerupType powerUp = PowerupType.pickPowerUp();
                if (powerUp != null) {
                    spawn("powerupdrop", new SpawnData(entity.getX() + entity.getWidth() / 2, entity.getY())
                            .put("type", powerUp.getType()).put("texture", powerUp.getTextureString()));
                }
            }
        }
    }

    public int getLives() {
        return 0;
    }
}
