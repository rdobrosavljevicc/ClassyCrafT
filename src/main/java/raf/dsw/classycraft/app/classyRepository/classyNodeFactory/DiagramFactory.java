package raf.dsw.classycraft.app.classyRepository.classyNodeFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;

import java.util.UUID;

public class DiagramFactory extends ClassyNodeFactory{
    @Override
    public ClassyNode createNode() {
        return new Diagram("Diagram" + UUID.randomUUID().toString().substring(0, 8), null);
    }
}
