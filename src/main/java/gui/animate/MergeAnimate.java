package gui.animate;

import entity.Tile;
import gui.GamePanel;
import gui.TilePanel;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.1.
 */
public class MergeAnimate {

    private GamePanel gameView;
    private List<AnimateUnit> animateUnits;
    private TilePanel[][] tilePanels;

    public MergeAnimate(GamePanel gameView, List<AnimateUnit> animateUnits) {
        this.gameView = gameView;
        this.animateUnits = animateUnits;
        this.tilePanels = gameView.getTilePanels();
    }

    public void play() {
        for(AnimateUnit unit : animateUnits){
            Tile from = unit.getFrom();
            Tile to = unit.getTo();
            from.setValue(0);
            to.setValue(to.getValue() << 1);
            gameView.updateValue(to);
        }
    }
}
