package raf.dsw.classycraft.app.classyRepository.classyNodeFactory;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;

import java.util.UUID;

public class PackageFactory extends ClassyNodeFactory{
    @Override
    public ClassyNode createNode() {
        return new Package("Package" + UUID.randomUUID().toString().substring(0, 8), null);
    }
}
