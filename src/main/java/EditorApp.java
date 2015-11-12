import com.badlogic.gdx.Game;
import com.bitdecay.jump.leveleditor.render.LevelEditor;

public class EditorApp extends Game {

    @Override
    public void create() {
        FoxGameLevel level = new FoxGameLevel();
        setScreen(new LevelEditor(level));
    }

}
