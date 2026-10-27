package raf.dsw.classycraft.app.classyRepository.classyNodeFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;

public abstract class ClassyNodeFactory{
    public ClassyNode getNode(ClassyNode parent) {
        ClassyNode child = createNode();
        child.setParent(parent);
        return child;
    }

    public abstract ClassyNode createNode();
}
