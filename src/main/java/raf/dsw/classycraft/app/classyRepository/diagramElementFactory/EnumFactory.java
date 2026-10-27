package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;

import java.awt.*;

public class EnumFactory extends InterclassFactory{
    @Override
    public ClassyNode createElement(String name, ClassyNodeComposite parent) {
        return new Enum(name, parent, Color.GREEN.darker(), new BasicStroke(2), null, null, null);
    }
}
