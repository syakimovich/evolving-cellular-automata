package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ExportToGifDialog extends JDialog {
    private JTextField ageFromField = new JTextField("100");
    private JTextField ageToField = new JTextField("120");
    private JButton chooseFileButton = new JButton("Output file");
    private JButton exportButton = new JButton("Export");
    private JLabel pathLabel = new JLabel();
    private JTextField zoomField = new JTextField("2");
    private JTextField delayField = new JTextField("10");

    public ExportToGifDialog(Universe u){
        setTitle("Export to GIF");
        GridLayout layout = new GridLayout(0, 2);
        setLayout(layout);
        add(new JLabel("age from"));
        add(ageFromField);
        add(new JLabel("age to"));
        add(ageToField);

        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                int rVal = c.showSaveDialog(ExportToGifDialog.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    pathLabel.setText(c.getSelectedFile().getAbsolutePath());
                }
            }
        });

        add(chooseFileButton);
        add(pathLabel);

        add(new JLabel("zoom"));
        add(zoomField);
        add(new JLabel("delay (0.01 s)"));
        add(delayField);



        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int ageFrom = Integer.parseInt(ageFromField.getText());
                    int ageTo = Integer.parseInt(ageToField.getText());
                    int zoom = Integer.parseInt(zoomField.getText());
                    int delay = Integer.parseInt(delayField.getText());
                    GraphicsUtils.exportToGif(u, ageFrom, ageTo, pathLabel.getText(), zoom, delay, true);
                    ExportToGifDialog.this.dispose();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error: " + ex);
                }
            }
        });

        add(exportButton);
        setMinimumSize(new Dimension(600, 300));
    }
}
