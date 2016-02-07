package org.yakimovich.evolca.ui;

import java.awt.*;

/**
 * Colors to visualize cell state.
 */
public class Colors {
	public static Color getColor(int i){
		if(i == 0){
			return Color.BLACK;
		}
		
		if(i == 1){
			return Color.RED;
		}
		
		if(i == 2){
			return Color.GREEN;
		}
		
		if(i == 3){
			return Color.BLUE;
		}
		
		if(i == 4){
			return Color.CYAN;
		}
		
		if(i == 5){
			return Color.YELLOW;
		}
		
		if(i == 6){
			return Color.MAGENTA;
		}
		
		if(i == 7){
			return Color.ORANGE;
		}
		
		if(i == 8){
			return Color.DARK_GRAY;
		}
		
		if(i == 9){
			return Color.PINK;
		}
		
		//TODO: add color generation for other i
		
		return Color.WHITE;
	}
}
