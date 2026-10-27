package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;

public abstract class InterclassFactory implements DiagramElementFactory{
    public Interclass getElement(String name, String visibility, Point position, Dimension size, ClassyNodeComposite parent) {
        Interclass child = (Interclass) createElement(name, parent);
        child.setVisibility(visibility);
        child.setPosition(position);
        child.setSize(size);
        return child;
    }
}
