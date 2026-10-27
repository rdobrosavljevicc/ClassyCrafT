package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Aggregation;

import java.awt.*;

public class AggregationFactory extends ConnectionFactory{

    @Override
    public ClassyNode createElement(String name, ClassyNodeComposite parent) {
        return new Aggregation("Aggregation" + parent.getChildren().size() + 1, parent, Color.BLACK, null);
    }
}
