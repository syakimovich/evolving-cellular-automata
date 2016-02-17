package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class UniversePanelWithInfo extends JPanel{
	private JLabel label;
	private UniversePanel universePanel;
    private List<Measure> measures = new ArrayList<Measure>();
    private NumberFormat doubleFormatter = new DecimalFormat("######.00");
	
	public UniversePanelWithInfo(Universe universe, int zoom){
        universePanel = new UniversePanel(universe, zoom);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.universePanel);
        label = new JLabel("initial text");
        this.add(label);
        updateSize();
	}

    private void updateSize(){
        Dimension size = new Dimension(universePanel.getWidth(),
                universePanel.getHeight() + 100);
        setSize(size);
        setMinimumSize(size);
    }

    public void setUniverse(Universe universe){
        universePanel.setUniverse(universe);
        updateSize();
    }

    public void setZoom(int zoom){
        universePanel.setZoom(zoom);
        updateSize();
    }
	
	public void setLabelText(String text){
		label.setText(text);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		label.setText(getInfoHTML());
	}

    public Universe getUniverse(){
        return universePanel.getUniverse();
    }

    public int getZoom(){
        return universePanel.getZoom();
    }

    public void addMeasure(Measure m){
        measures.add(m);
    }

    public void setMeasures(List<Measure> newMeasures){
        measures = newMeasures;
    }

    private String getInfoBody() {
        StringBuffer result = new StringBuffer();
        result.append("age: ");
        result.append(universePanel.getUniverse().getAge());
        for(Measure m: measures){
            result.append("<br/>");
            result.append(m.getName());
            result.append(": ");
            result.append(doubleFormatter.format(m.getValue(universePanel.getUniverse())));
        }
        return result.toString();
    }

    public String getInfoHTML() {return "<html><body>" + getInfoBody() + "<body><html>"; }
}
