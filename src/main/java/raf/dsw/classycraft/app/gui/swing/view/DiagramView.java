package raf.dsw.classycraft.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.Connection;
import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.Class;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;
import raf.dsw.classycraft.app.classyRepository.implementation.Interface;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.gui.swing.commands.CommandManager;
import raf.dsw.classycraft.app.gui.swing.view.painter.ElementPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

@Getter
@Setter
public class DiagramView extends JPanel {
    private Diagram diagram;
    private double scale;
    private Point translateCoordinates;
    private ArrayList<DiagramElement> selectedElements;
    private CommandManager commandManager;

    protected DiagramView(String name) {
        this.setBackground(Color.WHITE);
        this.setName(name);
        setDiagram(MainFrame.getInstance().getPackageView().getCurrPackage().getClassyNode());
        commandManager = new CommandManager();
        selectedElements = new ArrayList<>();
        scale = 1;
        translateCoordinates = new Point(0, 0);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        at.translate(translateCoordinates.getX(), translateCoordinates.getY());
        g2.transform(at);


        super.paintComponent(g);
        g2.setComposite(AlphaComposite.getInstance(3, 0.8F));
        for (ElementPainter painter : MainFrame.getInstance().getPackageView().getPainters()) {
            painter.paint(g2, painter.getDiagramElement());
        }
    }
    public void setDiagram(ClassyNode node){
        Package pack = (Package) node;
        for (ClassyNode child : pack.getChildren()){
            if (this.getName().equals(child.getName())) {
                this.diagram = (Diagram) child;
                return;
            }
        }
    }

    public Point getPosition(Point pos){
        return new Point((int) (pos.getX() / scale - translateCoordinates.getX()), (int) (pos.getY() / scale - translateCoordinates.getY()));
    }

    public void clearSelected(){
        for (DiagramElement selectedElement : selectedElements){
            if (selectedElement instanceof Class)
                selectedElement.setPaint(Color.ORANGE);
            else if (selectedElement instanceof Interface)
                selectedElement.setPaint(Color.PINK.darker());
            else if (selectedElement instanceof Enum)
                selectedElement.setPaint(Color.GREEN.darker());
            else if (selectedElement instanceof Connection)
                selectedElement.setPaint(Color.BLACK);
        }
        selectedElements.clear();
        repaint();
    }
    public void newCoordinates(Point pos){
        translateCoordinates = new Point((int) (translateCoordinates.getX() + pos.getX()), (int) (translateCoordinates.getY() + pos.getY()));
        repaint();
    }


    public CommandManager getCommandManager() {
        return commandManager;
    }


    public void disableUndoAction() {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
    }


    public void disableRedoAction() {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
    }

    public void enableRedoAction() {
        MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
    }

    public void enableUndoAction() {
        MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
    }
}
