package game.interfaces.view;

import entity.Tile;
import game.interfaces.game.IGameEngine;
import gui.animate.AnimateUnit;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public interface IGameView extends Remote{
    /**
     * 初始化界面
     */
    void init(Tile[][] tiles)throws RemoteException;

    /**
     * 播放移动动画
     * @param animateUnits
     */
    void doMoveAnimate(List<AnimateUnit> animateUnits,int order)throws RemoteException;

    /**
     * 合并
     */
    int doMergeAnimate(List<AnimateUnit> animateUnits,int order)throws RemoteException;

    /**
     * 更新一个瓦片的值
     */
    void updateValue(Tile tile,int order)throws RemoteException;

    /**
     * 死了
     */
    void gameOver()throws RemoteException;

    /**
     * 赢了
     */
    void win()throws RemoteException;

    /**
     * 注入游戏引擎
     */
    void setEngine(IGameEngine engine)throws RemoteException;

}
