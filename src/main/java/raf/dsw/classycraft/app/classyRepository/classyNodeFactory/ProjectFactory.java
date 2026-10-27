package raf.dsw.classycraft.app.classyRepository.classyNodeFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;

import java.util.UUID;

public class ProjectFactory extends ClassyNodeFactory{
    @Override
    public ClassyNode createNode() {
        return new Project("Project" + UUID.randomUUID().toString().substring(0, 8), null, "Author" + UUID.randomUUID().toString().substring(0, 8));
    }
}
