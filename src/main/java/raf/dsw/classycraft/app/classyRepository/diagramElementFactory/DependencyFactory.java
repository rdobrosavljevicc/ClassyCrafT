package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Dependency;

import java.awt.*;

public class DependencyFactory extends ConnectionFactory{
    @Override
    public ClassyNode createElement(String name, ClassyNodeComposite parent) {
        return new Dependency("Dependency" + parent.getChildren().size() + 1, parent, Color.BLACK, null);
    }
}
