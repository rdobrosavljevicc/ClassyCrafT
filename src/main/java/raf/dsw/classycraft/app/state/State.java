package raf.dsw.classycraft.app.state;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;

import java.awt.*;

public abstract class State {
    public abstract void mousePressed(ClassyNodeComposite parent, Point pos);
    public abstract void mouseDragged(ClassyNodeComposite parent, Point pos);
    public abstract void mouseReleased(ClassyNodeComposite parent, Point pos);
}
