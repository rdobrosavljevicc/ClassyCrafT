package raf.dsw.classycraft.app.gui.swing.commands.implementation;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

public class AddElementCommand extends AbstractCommand {
    private ClassyTreeItem parent;
    private ClassyTreeItem child;

    public AddElementCommand(ClassyTreeItem parent, ClassyTreeItem child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void doCommand() {
        if (child == null || parent == null)
            return;
        if (child.getClassyNode() instanceof Interclass)
            parent.add(child);
        ApplicationFramework.getInstance().getClassyRepository().addChild((ClassyNodeComposite) parent.getClassyNode(), child.getClassyNode());
    }

    @Override
    public void undoCommand() {
        if (child == null || parent == null)
            return;
        if (child.getClassyNode() instanceof Interclass)
            child.removeFromParent();
        ApplicationFramework.getInstance().getClassyRepository().deleteNode(child.getClassyNode());
    }


}
