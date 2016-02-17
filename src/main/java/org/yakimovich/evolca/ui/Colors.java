package org.yakimovich.evolca.ui;

import java.awt.*;

/**
 * Colors to visualize cell state.
 */
public class Colors {

	public static Color getColor(int i){
        if(i == 0){
            return new Color(0x000000);
        }
        if(i == 1){
            return new Color(0xFF0000);
        }
        if(i == 2){
            return new Color(0x800000);
        }
        if(i == 3){
            return new Color(0xFFFF00);
        }
        if(i == 4){
            return new Color(0x808000);
        }
        if(i == 5){
            return new Color(0x00FF00);
        }
        if(i == 6){
            return new Color(0x008000);
        }
        if(i == 7){
            return new Color(0x00FFFF);
        }
        if(i == 8){
            return new Color(0x008080);
        }
        if(i == 9){
            return new Color(0x0000FF);
        }
        if(i == 10){
            return new Color(0x000080);
        }
        if(i == 11){
            return new Color(0xFF00FF);
        }
        if(i == 12){
            return new Color(0x800080);
        }
        if(i == 13){
            return new Color(0x808080);
        }
        if(i == 14){
            return new Color(0xC0C0C0);
        }
        if(i == 15){
            return new Color(0xFFFFFF);
        }

        int a = i / 4;
        int b = i % 4;
        int c = b / 2;
        int d = b % 2;
        float h = ((float) a) / 8.0f + 0.15f;
        float s = ((float) c + 0.8f) / 2.0f;
        float v = ((float) d + 0.8f) / 2.0f;
        return Color.getHSBColor(h, s, v);
	}
}
