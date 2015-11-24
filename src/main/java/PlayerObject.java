import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.jump.BitBody;
import com.bitdecay.jump.BodyType;
import com.bitdecay.jump.JumperBody;
import com.bitdecay.jump.control.PlayerInputController;
import com.bitdecay.jump.gdx.input.GDXControls;
import com.bitdecay.jump.gdx.integration.BitTextureAtlas;
import com.bitdecay.jump.geom.BitRectangle;
import com.bitdecay.jump.level.DebugSpawnObject;
import com.bitdecay.jump.level.LevelObject;
import com.bitdecay.jump.leveleditor.example.game.GameObject;
import com.bitdecay.jump.render.JumperRenderState;
import com.bitdecay.jump.render.JumperRenderStateWatcher;
import com.bytebreak.animagic.Animation;
import com.bytebreak.animagic.Animator;
import com.bytebreak.animagic.FrameRate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jake on 11/11/2015.
 */
public class PlayerObject extends GameObject {

    TextureRegion playerTex = new TextureRegion();
    JumperBody playerBody = new JumperBody();

    Animator playerAnimator = new Animator("Player");

    @Override
    public List<BitBody> build(LevelObject template) {
        DebugSpawnObject spawnObject = (DebugSpawnObject) template;
        BitBody body = buildPlayerBody(spawnObject);
        buildAnimations();
        return Arrays.asList(body);
    }

    private void buildAnimations() {
        BitTextureAtlas characterAtlas = FoxGameLevel.assetManager.get("packed/character.atlas", BitTextureAtlas.class);
        FrameRate animationRate = FrameRate.perFrame(.05f);
        Animation rightStand = new Animation(JumperRenderState.RIGHT_STANDING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("kick").toArray(TextureRegion.class), new int[0]);
        Animation leftStand = new Animation(JumperRenderState.LEFT_STANDING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("kick").toArray(TextureRegion.class), new int[0]);
        Animation rightWalk = new Animation(JumperRenderState.RIGHT_RUNNING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("walk").toArray(TextureRegion.class), new int[0]);
        Animation leftWalk = new Animation(JumperRenderState.LEFT_RUNNING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("walk").toArray(TextureRegion.class), new int[0]);
        Animation rightJump = new Animation(JumperRenderState.RIGHT_JUMPING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("jump/2").toArray(TextureRegion.class), new int[0]);
        Animation leftJump = new Animation(JumperRenderState.LEFT_JUMPING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("jump/2").toArray(TextureRegion.class), new int[0]);
        Animation rightFall = new Animation(JumperRenderState.RIGHT_FALLING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("jump/4").toArray(TextureRegion.class), new int[0]);
        Animation leftFall = new Animation(JumperRenderState.LEFT_FALLING.toString(), Animation.AnimationPlayState.REPEAT, animationRate, characterAtlas.findRegions("jump/4").toArray(TextureRegion.class), new int[0]);

        com.badlogic.gdx.graphics.g2d.Animation thing = null;
        playerAnimator.addAnimation(rightStand);
        playerAnimator.addAnimation(leftStand);
        playerAnimator.addAnimation(rightWalk);
        playerAnimator.addAnimation(leftWalk);
        playerAnimator.addAnimation(rightJump);
        playerAnimator.addAnimation(leftJump);
        playerAnimator.addAnimation(rightFall);
        playerAnimator.addAnimation(leftFall);
    }

    private BitBody buildPlayerBody(DebugSpawnObject spawnObject) {
        playerTex = new TextureRegion(new Texture(Gdx.files.internal("gameAssets/PistonDick.png")));
        playerBody.props = spawnObject.props;
        playerBody.jumperProps = spawnObject.jumpProps;

        playerBody.bodyType = BodyType.DYNAMIC;
        playerBody.aabb = new BitRectangle(spawnObject.rect.xy.x,spawnObject.rect.xy.y,64,64);
        playerBody.renderStateWatcher = new JumperRenderStateWatcher();
        playerBody.controller = new PlayerInputController(GDXControls.defaultMapping);
        return playerBody;
    }

    @Override
    public void update(float delta) {
        String animation = playerBody.renderStateWatcher.getState().toString();
        if (playerAnimator.hasAnimation(animation)) {
            if (!animation.equals(playerAnimator.currentAnimationName())) {
                playerAnimator.switchToAnimation(animation);
            }
            playerAnimator.update(delta);
            playerTex = playerAnimator.getFrame();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if(playerBody.renderStateWatcher.getState().toString().toLowerCase().contains("right")) {
            batch.draw(playerTex, playerBody.aabb.xy.x, playerBody.aabb.xy.y, playerBody.aabb.width, playerBody.aabb.height);
        } else {
            batch.draw(playerTex, playerBody.aabb.xy.x+playerBody.aabb.width, playerBody.aabb.xy.y, -playerBody.aabb.width, playerBody.aabb.height);
        }
    }
}
