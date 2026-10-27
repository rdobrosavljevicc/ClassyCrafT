package raf.dsw.classycraft.app.gui.swing.controller.stateActions;

import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageAction extends AbstractClassyAction {
    public SaveImageAction() {
        putValue(SMALL_ICON, loadIcon("/images/saveImage.png"));
        putValue(NAME, "Save Image");
        putValue(SHORT_DESCRIPTION, "Save Diagram As Image");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainFrame.getInstance().getPackageView().getCurrPane() == null)
            return;

        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File(".\\ClassyCrafT\\src\\main\\resources\\screenshots"));
        File file = null;

        if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            file = new File(jfc.getSelectedFile().getPath() + ".png");
        }
        else {
            return;
        }

        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(MainFrame.getInstance().getPackageView().getCurrPane().getBounds());
        } catch (AWTException e1) {
            e1.printStackTrace();
            return;
        }

        Graphics2D graphics2D = image.createGraphics();
        MainFrame.getInstance().getPackageView().getCurrPane().print(graphics2D);
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
