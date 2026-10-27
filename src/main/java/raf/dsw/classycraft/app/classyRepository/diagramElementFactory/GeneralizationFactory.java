package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Generalization;

import java.awt.*;

public class GeneralizationFactory extends ConnectionFactory{
    @Override
    public ClassyNode createElement(String name, ClassyNodeComposite parent) {
        return new Generalization("Generalization" + parent.getChildren().size() + 1, parent, Color.BLACK, null);
    }
}
