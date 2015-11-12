import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.jump.BitBody;
import com.bitdecay.jump.BodyType;
import com.bitdecay.jump.JumperBody;
import com.bitdecay.jump.control.PlayerInputController;
import com.bitdecay.jump.gdx.input.GDXControls;
import com.bitdecay.jump.geom.BitRectangle;
import com.bitdecay.jump.level.builder.DebugSpawnObject;
import com.bitdecay.jump.level.builder.LevelObject;
import com.bitdecay.jump.leveleditor.example.game.GameObject;
import com.bitdecay.jump.render.JumperRenderStateWatcher;

/**
 * Created by jake on 11/11/2015.
 */
public class PlayerObject extends GameObject {

    TextureRegion playerTex = new TextureRegion();
    JumperBody playerBody = new JumperBody();

    @Override
    public BitBody build(LevelObject template) {
        DebugSpawnObject spawnObject = (DebugSpawnObject) template;
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
