package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Interface;

import java.awt.*;

public class InterfaceFactory extends InterclassFactory{
    @Override
    public ClassyNode createElement(String name, ClassyNodeComposite parent) {
        return new Interface(name, parent, Color.PINK.darker(), new BasicStroke(2), null, null, null);
    }
}
