package raf.dsw.classycraft.app.gui.swing.controller.myActions;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.serializer.JacksonSerializer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SaveProjectAction extends AbstractClassyAction {

    public SaveProjectAction() {
        putValue(SMALL_ICON, loadIcon("/images/save.png"));
        putValue(NAME, "Save Project/Diagram");
        putValue(SHORT_DESCRIPTION, "Save Project/Diagram");
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
                    project.setFilePath(file.getPath() + ".json");
                }
                else {
                    return;
                }

            }

            ApplicationFramework.getInstance().getSerializer().saveProject(project);

            project.setChanged(false);
        }
        else if (node instanceof Diagram){

            Diagram diagram = (Diagram) node;
            jfc.setCurrentDirectory(new File(".\\ClassyCrafT\\src\\main\\resources\\templates"));

            if (!diagram.isChanged()) {
                return;
            }

            if (diagram.getFilePath() == null || diagram.getFilePath().isEmpty()) {
                if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                    file = jfc.getSelectedFile();
                    diagram.setFilePath(file.getPath() + ".json");
                }
                else {
                    return;
                }
            }

            ((JacksonSerializer)ApplicationFramework.getInstance().getSerializer()).saveDiagram(diagram);

            diagram.setChanged(false);
        }
    }
}
