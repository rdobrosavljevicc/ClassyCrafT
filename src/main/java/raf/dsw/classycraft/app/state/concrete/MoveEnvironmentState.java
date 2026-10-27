package raf.dsw.classycraft.app.state.concrete;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.state.State;

import java.awt.*;

public class MoveEnvironmentState extends State {
    Point firstPos;
    @Override
    public void mousePressed(ClassyNodeComposite parent, Point pos) {
        firstPos = pos;
    }

    @Override
    public void mouseDragged(ClassyNodeComposite parent, Point pos) {
        ((DiagramView)MainFrame.getInstance().getPackageView().getCurrPane()).newCoordinates(new Point((int) (pos.getX() - firstPos.getX()), (int) (pos.getY() - firstPos.getY())));
    }

    @Override
    public void mouseReleased(ClassyNodeComposite parent, Point pos) {

    }
}
