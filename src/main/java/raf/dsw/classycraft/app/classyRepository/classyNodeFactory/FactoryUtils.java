package raf.dsw.classycraft.app.classyRepository.classyNodeFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;

public class FactoryUtils {
    static ProjectFactory projectFactory;
    static PackageFactory packageFactory;
    static DiagramFactory diagramFactory;
    public static ClassyNodeFactory getNodeFactory(ClassyNode parent, int childType){
        if (parent instanceof ProjectExplorer){
            return projectFactory;
        }
        else if (parent instanceof Project){
            return packageFactory;
        }
        else if (parent instanceof Package){
            if (childType == 0)
                return diagramFactory;
            else if (childType == 1)
                return packageFactory;
        }
        return null;
    }

    static{
        projectFactory = new ProjectFactory();
        packageFactory = new PackageFactory();
        diagramFactory = new DiagramFactory();
    }
}
