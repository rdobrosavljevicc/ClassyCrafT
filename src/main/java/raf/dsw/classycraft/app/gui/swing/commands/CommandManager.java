package raf.dsw.classycraft.app.gui.swing.commands;

import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.view.DiagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CommandManager extends AbstractCommand{
    private List<AbstractCommand> commands = new ArrayList<>();
    private int currentCommand = 0;

    public void addCommand(AbstractCommand command){
        while(currentCommand < commands.size())
            commands.remove(currentCommand);
        commands.add(command);
        doCommand();
    }

    public void doCommand(){
        if (currentCommand < commands.size()){
            commands.get(currentCommand++).doCommand();
            SwingUtilities.updateComponentTreeUI(((ClassyTreeImplementation)MainFrame.getInstance().getClassyTree()).getTreeView());
            ((DiagramView)MainFrame.getInstance().getPackageView().getCurrPane()).enableUndoAction();
        }
        if (currentCommand == commands.size()){
            ((DiagramView)MainFrame.getInstance().getPackageView().getCurrPane()).disableRedoAction();
        }
        if (currentCommand > 0) {
            ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getDiagram().setChanged(true);
        }
    }

    public void undoCommand(){
        if (currentCommand > 0){
            ((DiagramView)MainFrame.getInstance().getPackageView().getCurrPane()).enableRedoAction();
            commands.get(--currentCommand).undoCommand();
            SwingUtilities.updateComponentTreeUI(((ClassyTreeImplementation)MainFrame.getInstance().getClassyTree()).getTreeView());
        }
        if (currentCommand == 0){
            ((DiagramView) MainFrame.getInstance().getPackageView().getCurrPane()).getDiagram().setChanged(false);
            ((DiagramView)MainFrame.getInstance().getPackageView().getCurrPane()).disableUndoAction();
        }
    }
}
