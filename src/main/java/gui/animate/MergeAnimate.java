package gui.animate;

import entity.Tile;
import gui.singlePlay.GamePanel;

import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.1.
 */
public class MergeAnimate {


    private GamePanel gameView;
    private List<AnimateUnit> animateUnits;

    public MergeAnimate(GamePanel gameView, List<AnimateUnit> animateUnits) {
        this.gameView = gameView;
        this.animateUnits = animateUnits;
    }

    /**
     * 播放动画
     * @return 返回融合后的最大值
     */
    public int play() {
        int max = 0;
        for(AnimateUnit unit : animateUnits){
            Tile from = unit.getFrom();
            Tile to = unit.getTo();
            from.setValue(0);
            int v = to.getValue() << 1;
            to.setValue(v);
            gameView.updateValue(to);
            max = Integer.max(max,v);
        }
        return max;
    }
}
