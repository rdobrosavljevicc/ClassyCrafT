package raf.dsw.classycraft.app.gui.swing.view;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.classyRepository.implementation.*;
import raf.dsw.classycraft.app.classyRepository.implementation.Class;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.painter.*;
import raf.dsw.classycraft.app.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class PackageView extends JPanel {
    private ClassyTreeItem currPackage;
    private JLabel projectName;
    private JLabel authorName;
    private JTabbedPane tabbedPane;
    private StateManager stateManager;
    private ArrayList<ElementPainter> painters;
    private int childType;
    private StateToolBar toolbar;
    private JPanel diagramViewAndToolbar;


    private Component currPane;
    private MouseListener tabbedPaneMouseListener;
    private MouseMotionListener tabbedPaneMouseMotionListener;
    private MouseWheelListener tabbedPaneMouseWheelListener;


    public PackageView() {
        this.setLayout((new BoxLayout(this, BoxLayout.Y_AXIS)));
        projectName = new JLabel();
        authorName = new JLabel();
        stateManager = new StateManager();
        tabbedPane = new JTabbedPane();
        painters = new ArrayList<>();

        toolbar = new StateToolBar();
        diagramViewAndToolbar = new JPanel();
        diagramViewAndToolbar.setLayout(new BoxLayout(diagramViewAndToolbar, BoxLayout.X_AXIS));
        diagramViewAndToolbar.add(tabbedPane);
        diagramViewAndToolbar.add(toolbar);
        toolbar.setVisible(false);

        this.add(projectName);
        this.add(authorName);
        this.add(diagramViewAndToolbar);
        projectName.setAlignmentX(LEFT_ALIGNMENT);
        authorName.setAlignmentX(LEFT_ALIGNMENT);
        diagramViewAndToolbar.setAlignmentX(LEFT_ALIGNMENT);
        childType = 0;
        tabbedPaneMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                getStateManager().getCurrent().mousePressed(((DiagramView) tabbedPane.getSelectedComponent()).getDiagram(), ((DiagramView) currPane).getPosition(e.getPoint()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                getStateManager().getCurrent().mouseReleased(((DiagramView) tabbedPane.getSelectedComponent()).getDiagram(), ((DiagramView) currPane).getPosition(e.getPoint()));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        tabbedPaneMouseMotionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                getStateManager().getCurrent().mouseDragged(((DiagramView) tabbedPane.getSelectedComponent()).getDiagram(), ((DiagramView) currPane).getPosition(e.getPoint()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
        tabbedPaneMouseWheelListener = e -> {
            if (e.getWheelRotation() < 0) {
                ((DiagramView) currPane).setScale(((DiagramView) currPane).getScale() * 1.1);
            } else if (e.getWheelRotation() > 0) {
                ((DiagramView) currPane).setScale(((DiagramView) currPane).getScale() / 1.1);
            }
            repaint();
        };

        toolbar.getComponent(0).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] possibilities = {"Class", "Interface", "Enum"};
                childType = JOptionPane.showOptionDialog(null,
                        "Select The Element Type",
                        "Type Selection",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        null);
                if (childType == -1)
                    childType = 0;
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        toolbar.getComponent(1).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] connection = {"Generalization", "Dependency", "Composition", "Aggregation"};
                childType = JOptionPane.showOptionDialog(null,
                        "Select The Connection Type",
                        "Type Selection",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        connection,
                        null);
                if (childType == -1)
                    childType = 0;
            }


            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        toolbar.getComponent(4).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!((DiagramView) currPane).getSelectedElements().isEmpty())
                    ((DiagramView) currPane).clearSelected();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        tabbedPane.addChangeListener(e -> {
            if (currPane != null) {
                currPane.removeMouseListener(tabbedPaneMouseListener);
                currPane.removeMouseMotionListener(tabbedPaneMouseMotionListener);
                currPane.removeMouseWheelListener(tabbedPaneMouseWheelListener);
            }
            currPane = tabbedPane.getSelectedComponent();

            if (tabbedPane.getSelectedComponent() == null)
                return;

            changePainters();

            currPane.addMouseWheelListener(tabbedPaneMouseWheelListener);
            currPane.addMouseListener(tabbedPaneMouseListener);
            currPane.addMouseMotionListener(tabbedPaneMouseMotionListener);
        });
    }

    public void updatePackageView(Object notification) {
        if (currPackage == null)
            return;
        Project project = findProject();
        projectName.setText(project.getName());
        authorName.setText(project.getAuthor());
        changeTabbedPane(notification);
        changePainters();
    }

    private Project findProject() {
        ClassyNode curr = currPackage.getClassyNode();
        while (!(curr instanceof Project)) {
            curr = curr.getParent();
        }
        return (Project) curr;
    }

    public void setCurrPackage(ClassyTreeItem currPackage) {
        if (!toolbar.isVisible())
            toolbar.setVisible(true);
        this.currPackage = currPackage;
        tabbedPane.removeAll();
        if (currPackage == null)
            return;
        for (ClassyNode child : ((ClassyNodeComposite) currPackage.getClassyNode()).getChildren()) {
            if (child instanceof Diagram) {
                DiagramView diagramView = new DiagramView(child.getName());
                tabbedPane.addTab(child.getName(), diagramView);
            }
        }
        getStateManager().setAddInterclass();
        updatePackageView(this);
    }

    public void changeTabbedPane(Object notification) {
        if (notification instanceof Diagram && ((Diagram) notification).getParent().equals(currPackage.getClassyNode())) {
            for (Component component : tabbedPane.getComponents()) {
                if (((Diagram) notification).getName().equals(component.getName())) {
                    tabbedPane.remove(component);
                    return;
                }
            }
            DiagramView diagramView = new DiagramView(((Diagram) notification).getName());
            tabbedPane.addTab(((Diagram) notification).getName(), diagramView);
        }
    }

    public void changePainters() {
        painters.clear();
        if (tabbedPane.getSelectedComponent() == null)
            return;
        for (ClassyNode child : ((DiagramView) tabbedPane.getSelectedComponent()).getDiagram().getChildren()) {
            painters.add(makePainter((DiagramElement) child));
        }
        repaint();
    }

    public ElementPainter makePainter(DiagramElement diagramElement) {
        if (diagramElement instanceof Class)
            return new ClassPainter(diagramElement);
        else if (diagramElement instanceof Interface)
            return new InterfacePainter(diagramElement);
        else if (diagramElement instanceof Enum)
            return new EnumPainter(diagramElement);
        else if (diagramElement instanceof Generalization)
            return new GeneralizationPainter(diagramElement);
        else if (diagramElement instanceof Dependency)
            return new DependencyPainter(diagramElement);
        else if (diagramElement instanceof Composition)
            return new CompositionPainter(diagramElement);
        else if (diagramElement instanceof Aggregation)
            return new AggregationPainter(diagramElement);
        return null;
    }

    public String elementVisibilityUpdate(DiagramElement diagramElement) {

        if (diagramElement instanceof Interclass) {
            if (((Interclass) diagramElement).getVisibility() == null) {
                String[] visibilityList = {"+", "-", "#", "~"};
                String[] visibilityWList = {"Public", "Private", "Protected", "Package"};
                int visibilityIndex = JOptionPane.showOptionDialog(null,
                        "Select the visibility",
                        "Selection",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        visibilityWList,
                        null);


                if (visibilityIndex != -1)
                    return visibilityList[visibilityIndex];
            }
        }
        return null;
    }


    public void connectionContent(Connection connection){
        if (!(connection instanceof Composition || connection instanceof Aggregation))
            return;

        String variablename;
        if (connection instanceof Composition)
            variablename = ((Composition) connection).getVariableName();
        else
            variablename = ((Aggregation) connection).getVariableName();

        JTextField textField = new JTextField(variablename);
        textField.setPreferredSize(new Dimension(60, 20));
        Object[] options = {textField, "Apply new name"};
        int classOptions2 = JOptionPane.showOptionDialog(null,
                "Change Variable Name",
                "Change",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null);

        if (classOptions2 == -1 || options[0].equals(""))
            return;

        variablename = ((JTextField) options[0]).getText();

        if (connection instanceof Composition)
            ((Composition) connection).setVariableName(variablename);
        else
            ((Aggregation) connection).setVariableName(variablename);

        updatePackageView(null);
    }

    public ArrayList<Object> interclassContent(Interclass interclass) {
        ArrayList<Object> returnValue = new ArrayList<>();
        if (interclass == null)
            return null;
        if (!(interclass instanceof Enum)) {
            Object[] options = {"Change name", "Add method/attribute", "Change existing method/attribute"};
            int classOptions = JOptionPane.showOptionDialog(null,
                    "Select What you want to change",
                    "Selection",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    null);

            if (classOptions == 0) {
                JTextField textField = new JTextField(interclass.getName());
                Object[] options2 = {textField, "Apply new name"};
                int classOptions2 = JOptionPane.showOptionDialog(null,
                        "Change Interclass Name",
                        "Change",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options2,
                        null);

                if (classOptions2 == -1 || (((JTextField) options2[0]).getText()).equals(""))
                    return null;
                for (ClassyNode child : ((DiagramView)tabbedPane.getSelectedComponent()).getDiagram().getChildren()){
                    if (child.getName().equals(((JTextField) options2[0]).getText()))
                        return null;
                }
                returnValue.add(((JTextField) options2[0]).getText());
                returnValue.add(interclass);
                return returnValue;
            }
            else if (classOptions == 1) {
                String[] visibilityList = {"+", "-", "#", "~"};
                JComboBox<String> visibilityCB = new JComboBox<>(new String[]{"Public", "Private", "Protected", "Package"});
                visibilityCB.setSelectedIndex(0);
                JTextField name = new JTextField("");
                name.setPreferredSize(new Dimension(60, 20));
                JTextField type = new JTextField("");
                type.setPreferredSize(new Dimension(60, 20));

                Object[] options2 = {visibilityCB, name, type, "Add Attribute/Method"};
                int classOptions2 = JOptionPane.showOptionDialog(null,
                        "Creates a METHOD if name contains '(' and ')' , ATTRIBUTE if it doesn't",
                        "Add Attribute/Method",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options2,
                        null);

                String sName = ((JTextField) options2[1]).getText();
                String sVisibility = visibilityList[((JComboBox<?>) options2[0]).getSelectedIndex()];
                String sType = ((JTextField) options2[2]).getText();

                if (classOptions2 == -1 || sName.equals(""))
                    return null;

                Boolean contains1 = ((JTextField) options2[1]).getText().contains("(");
                Boolean contains2 = ((JTextField) options2[1]).getText().contains(")");

                if (contains1 && contains2){
                    returnValue.add(null);
                    returnValue.add(new Method(sVisibility, sName, sType));
                }
                else {
                    if (interclass instanceof Interface)
                        return null;
                    returnValue.add(null);
                    returnValue.add(new Attribute(sVisibility, sType, sName));
                }
                return returnValue;
            }
            else if (classOptions == 2) {
                JComboBox<Object> objectOfChange;
                if (interclass instanceof Class) {
                    objectOfChange = new JComboBox<>(((Class) interclass).getContent().toArray());
                    if (((Class) interclass).getContent().isEmpty())
                        return null;
                }
                else {
                    objectOfChange = new JComboBox<>(((Interface) interclass).getContent().toArray());
                    if (((Interface) interclass).getContent().isEmpty())
                        return null;
                }
                objectOfChange.setSelectedIndex(0);
                String[] visibilityList = {"+", "-", "#", "~"};
                JComboBox<String> visibilityCB = new JComboBox<>(new String[]{"Public", "Private", "Protected", "Package"});
                visibilityCB.setSelectedIndex(0);
                JTextField name = new JTextField("");
                name.setPreferredSize(new Dimension(60, 20));
                JTextField type = new JTextField("");
                type.setPreferredSize(new Dimension(60, 20));

                Object[] options2 = {objectOfChange, visibilityCB, name, type, "Change Attribute/Method"};
                int classOptions2 = JOptionPane.showOptionDialog(null,
                        "Changes into a METHOD if name contains '(' and ')' , ATTRIBUTE if it doesn't",
                        "Change Attribute/Method",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options2,
                        null);

                String sName = ((JTextField)options2[2]).getText();
                String sVisibility = visibilityList[((JComboBox<?>)options2[1]).getSelectedIndex()];
                String sType = ((JTextField)options2[3]).getText();


                if (classOptions2 == -1 || sName.equals("") || sType.equals(""))
                    return null;

                Boolean contains1 = ((JTextField) options2[2]).getText().contains("(");
                Boolean contains2 = ((JTextField) options2[2]).getText().contains(")");

                if (contains1 && contains2){
                    returnValue.add(objectOfChange.getSelectedItem());
                    returnValue.add(new Method(sVisibility, sName, sType));
                }
                else{
                    if (interclass instanceof Interface)
                        return null;

                    returnValue.add(objectOfChange.getSelectedItem());
                    returnValue.add(new Attribute(sVisibility, sType, sName));
                }
                return returnValue;
            }
        }
        else{
            Object[] options = {"Change name", "Change content"};
            int classOptions = JOptionPane.showOptionDialog(null,
                    "Select What you want to change",
                    "Selection",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    null);

            if (classOptions == 0) {
                JTextField textField = new JTextField(interclass.getName());
                Object[] options2 = {textField, "Apply new name"};
                int classOptions2 = JOptionPane.showOptionDialog(null,
                        "Change Interclass Name",
                        "Change",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options2,
                        null);

                if (classOptions2 == -1 || (((JTextField) options2[0]).getText()).equals(""))
                    return null;
                for (ClassyNode child : ((DiagramView) tabbedPane.getSelectedComponent()).getDiagram().getChildren()) {
                    if (child.getName().equals(((JTextField) options2[0]).getText()))
                        return null;
                }
                returnValue.add(((JTextField) options2[0]).getText());
                returnValue.add(interclass);
                return returnValue;
            }
            if (classOptions == 1) {
                StringBuilder stringBuilder = new StringBuilder();
                boolean first = true;
                for (String value : ((Enum) interclass).getContent()) {
                    if (first) {
                        stringBuilder.append(value);
                        first = false;
                        continue;
                    }
                    stringBuilder.append(", ");
                    stringBuilder.append(value);
                }
                String objectOfChange = stringBuilder.toString();
                JTextField objectOfChangeTF = new JTextField(objectOfChange);
                objectOfChangeTF.setPreferredSize(new Dimension(60, 20));

                Object[] options2 = {objectOfChangeTF, "Change values"};
                int classOptions2 = JOptionPane.showOptionDialog(null,
                        "Delete or Add values",
                        "Change Values",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options2,
                        null);
                if (classOptions2 == -1)
                    return null;

                objectOfChange = objectOfChangeTF.getText().replaceAll(" ", "");

                returnValue.addAll(Arrays.asList(objectOfChange.split(",")));
                return returnValue;
            }
        }
        return null;
    }
}
