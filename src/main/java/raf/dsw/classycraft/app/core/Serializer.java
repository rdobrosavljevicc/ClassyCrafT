package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

import java.io.File;

public interface Serializer {
    ClassyTreeItem loadProject(File file);
    void saveProject(Project node);

}
