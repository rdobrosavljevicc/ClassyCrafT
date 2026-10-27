package raf.dsw.classycraft.app.gui.swing.tree.model;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter
public class ClassyTreeItem extends DefaultMutableTreeNode {
    private ClassyNode classyNode;

    public ClassyTreeItem(ClassyNode classyModel){
        this.classyNode = classyModel;
    }

    @Override
    public String toString() {
        return classyNode.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClassyTreeItem classyTreeItem = (ClassyTreeItem) obj;
        return getClassyNode().equals(classyTreeItem.getClassyNode());
    }
}
