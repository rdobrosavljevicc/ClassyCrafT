package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ProjectToCodeAction extends AbstractClassyAction {
    public ProjectToCodeAction() {
        putValue(SMALL_ICON, loadIcon("/images/projectToCode.png"));
        putValue(NAME, "Project To Code Action");
        putValue(SHORT_DESCRIPTION, "Project To Code Action");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        ClassyNode node = MainFrame.getInstance().getClassyTree().getSelectedItem().getClassyNode();
        File file = null;

        if (node instanceof Project) {
            Project project = (Project) node;

            if (!project.isChanged()) {
                return;
            }

            if (project.getFilePath() == null || project.getFilePath().isEmpty()) {
                if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                    file = jfc.getSelectedFile();
                    file.mkdir();
                    ((ClassyTreeImplementation) MainFrame.getInstance().getClassyTree()).saveProjectAsCode(project, file.getPath() + "\\", project.getName());

                }
                else {
                    return;
                }
            }

            project.setChanged(false);
        }
    }
}
