package raf.dsw.classycraft.app.classyRepository.diagramElementFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.composite.Connection;

public abstract class ConnectionFactory implements DiagramElementFactory{
    public Connection getElement(String name, ClassyNode parent) {
        return (Connection) createElement(name, (ClassyNodeComposite) parent);
    }
}
