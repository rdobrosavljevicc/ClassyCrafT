package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Class;

import java.awt.*;

public class ClassFactory extends InterclassFactory{

    @Override
    public ClassyNode createElement(String name, ClassyNodeComposite parent) {
        return new Class(name, parent, Color.ORANGE, new BasicStroke(2), null, null, null);
    }
}
