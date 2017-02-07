package util;

import entity.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public class ColorSet {
    public static final Color MENU_BACKGROUND = new Color(0xfaf8ef);
    public static final Color BACKGROUND = new Color(187,173,160);

    private static final Map<Integer,Color> textColorMap;
    private static final Map<Integer,Color> bgColorMap;

    static{
        bgColorMap = new HashMap<>();
        bgColorMap.put(0,new Color(205,193,180));
        bgColorMap.put(2,new Color(238,228,218));
        bgColorMap.put(4,new Color(236,224,200));
        bgColorMap.put(8,new Color(243,177,121));
        bgColorMap.put(16,new Color(245,149,99));
        bgColorMap.put(32,new Color(244,125,97));
        bgColorMap.put(64,new Color(244,94,60));
        bgColorMap.put(128,new Color(236,206,117));
        bgColorMap.put(256,new Color(237,203,97));
        bgColorMap.put(512,new Color(236,199,99));
        bgColorMap.put(1024,new Color(236,196,64));
        bgColorMap.put(2048,new Color(236,193,46));

        textColorMap = new HashMap<>();
        textColorMap.put(0,new Color(205,193,180));
        textColorMap.put(2,new Color(119,110,101));
        textColorMap.put(4,new Color(119,110,101));
    }

    public static Color getBGColor(int tileValue){
        return bgColorMap.get(tileValue);
    }

    public static Color getTextColor(int tileValue){
        if(tileValue > 4){
            return new Color(251,246,242);
        }else return textColorMap.get(tileValue);
    }
}
