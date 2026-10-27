package raf.dsw.classycraft.app.gui.swing.tree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.classyNodeFactory.ClassyNodeFactory;
import raf.dsw.classycraft.app.classyRepository.classyNodeFactory.FactoryUtils;
import raf.dsw.classycraft.app.classyRepository.composite.*;
import raf.dsw.classycraft.app.classyRepository.implementation.*;
import raf.dsw.classycraft.app.classyRepository.implementation.Class;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.ClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Getter
@Setter
public class ClassyTreeImplementation implements ClassyTree {
    private ClassyTreeView treeView;
    private DefaultTreeModel treeModel;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new ClassyTreeView(treeModel);
        if (treeView.isSelectionEmpty())
            treeView.setSelectionRow(0);
        return treeView;
    }

    private void addClassContent(Interclass interclass, JsonNode node){
        node = node.get("content");
        int size = node.size();
        if (interclass instanceof Class) {
            for (int i = 0; i < size; i++) {
                JsonNode content = node.get(i);
                if (content.has("type"))
                    ((Class) interclass).getContent().add(new Attribute(content.get("visibility").asText(), content.get("type").asText(), content.get("name").asText()));
                else
                    ((Class) interclass).getContent().add(new Method(content.get("visibility").asText(), content.get("name").asText(), content.get("returnType").asText()));
            }
        }
        else if (interclass instanceof Interface) {
            for (int i = 0; i < size; i++) {
                JsonNode content = node.get(i);
                ((Interface) interclass).getContent().add(new Method(content.get("visibility").asText(), content.get("name").asText(), content.get("returnType").asText()));
            }
        }
        else if (interclass instanceof Enum){
            for (int i = 0; i < size; i++) {
                JsonNode content = node.get(i);
                ((Enum) interclass).getContent().add(content.asText());
            }
        }
    }

    public ClassyTreeItem loadProject(JsonNode node){
        ArrayList<ClassyTreeItem> children = new ArrayList<>();
        if (!node.has("children")){
            DiagramElement diagramElement;
            Color color = new Color(node.get("paint").get("red").asInt(), node.get("paint").get("green").asInt(), node.get("paint").get("blue").asInt(), node.get("paint").get("alpha").asInt());
            Stroke stroke = new BasicStroke((float) node.get("stroke").get("lineWidth").asDouble());
            Point position = null;
            Dimension size = null;
            if (node.has("content")){
                position = new Point(node.get("position").get("x").asInt(), node.get("position").get("y").asInt());
                size = new Dimension(node.get("size").get("width").asInt(), node.get("size").get("height").asInt());
            }
            if (color.equals(Color.ORANGE)){
                diagramElement = new Class(node.get("name").asText(), null, color, stroke, node.get("visibility").asText(), position, size);
            }
            else if (color.equals(Color.PINK.darker())){
                diagramElement = new Interface(node.get("name").asText(), null, color, stroke, node.get("visibility").asText(), position, size);
            }
            else if (color.equals(Color.GREEN.darker())){
                diagramElement = new Enum(node.get("name").asText(), null, color, stroke, node.get("visibility").asText(), position, size);
            }
            else if (node.get("name").asText().contains("Dependency")){
                diagramElement = new Dependency(node.get("name").asText(), null, color, stroke);
            }
            else if (node.get("name").asText().contains("Generalization")){
                diagramElement = new Generalization(node.get("name").asText(), null, color, stroke);
            }
            else if (node.get("name").asText().contains("Aggregation")){
                diagramElement = new Aggregation(node.get("name").asText(), null, color, stroke);
            }
            else if (node.get("name").asText().contains("Composition")){
                diagramElement = new Composition(node.get("name").asText(), null, color, stroke);
            }
            else
                return null;

            if (diagramElement instanceof Interclass)
                addClassContent((Interclass) diagramElement, node);

            return new ClassyTreeItem(diagramElement);
        }

        if (!(node instanceof ArrayNode)) {
            for (int i = 0;  i < node.get("children").size() ; i++){
                ClassyTreeItem newChild = loadProject(node.get("children").get(i));
                if (newChild.getClassyNode() instanceof Connection){
                    for (ClassyTreeItem child : children){
                        if (node.get("children").get(i).get("fromElement").get("name").asText().equals(child.getClassyNode().getName()))
                            ((Connection) newChild.getClassyNode()).setFromElement((Interclass) child.getClassyNode());
                        if (node.get("children").get(i).get("toElement").get("name").asText().equals(child.getClassyNode().getName()))
                            ((Connection) newChild.getClassyNode()).setToElement((Interclass) child.getClassyNode());
                    }
                }
                children.add(newChild);
            }
        }

        ClassyNodeComposite parent;

        if (node.has("author")) {
            parent = new Project(node.get("name").asText(), null, node.get("author").asText());
            ((Project) parent).setFilePath(node.get("filePath").asText());
            ((Project) parent).setChanged(false);
        }
        else if (node.has("filePath") || children.get(0).getClassyNode() instanceof DiagramElement){
            parent = new Diagram(node.get("name").asText(), null);
            ((Diagram) parent).setFilePath(node.get("filePath").asText());
            ((Diagram) parent).setChanged(false);
        }
        else
            parent = new Package(node.get("name").asText(), null);

        ClassyTreeItem classyTreeItemParent = new ClassyTreeItem(parent);

        for (ClassyTreeItem child : children){
            if (!(child.getClassyNode() instanceof Connection))
                classyTreeItemParent.add(child);
            ApplicationFramework.getInstance().getClassyRepository().addChild(parent, child.getClassyNode());
        }

        return classyTreeItemParent;
    }

    private String visibilityToString(String v){
        String visibility = "";
        switch (v) {
            case "+":
                visibility = "public ";
                break;
            case "-":
                visibility = "private ";
                break;
            case "#":
                visibility = "protected ";
                break;
        }
        return visibility;
    }

    private void implementVariables(FileWriter fw, ArrayList<Connection> connections) throws IOException {
        for (Connection connection : connections) {
            String variableVisibility = visibilityToString(connection.getToElement().getVisibility());
            if (connection instanceof Composition) {
                String variableName = ((Composition) connection).getVariableName();
                if (variableName == null || variableName.equals(""))
                    variableName = connection.getToElement().getName().substring(0, 1).toLowerCase() + connection.getToElement().getName().substring(1);

                fw.write(variableVisibility + connection.getToElement().getName() + " " + variableName);
            }
            else {
                String variableName = ((Aggregation) connection).getVariableName();
                if (variableName == null || variableName.equals(""))
                    variableName = connection.getToElement().getName().substring(0, 1).toLowerCase() + connection.getToElement().getName().substring(1);

                fw.write(variableVisibility + "ArrayList<" + connection.getToElement().getName() + ">" + " " + variableName + "s");
            }
            fw.write("\n");
        }
    }

    private void implementMethods(FileWriter fw, ArrayList<Connection> connections, ClassyNode node) throws IOException {
        for (Connection connection : connections) {
            ArrayList<ClassContent> classContent = new ArrayList<>();
            if (connection.getToElement() instanceof Class)
                classContent.addAll(((Class) connection.getToElement()).getContent());
            else if (connection.getToElement() instanceof Interface)
                classContent.addAll(((Interface) connection.getToElement()).getContent());

            for (ClassContent content : classContent) {
                if (!(content instanceof Method))
                    continue;

                String contentVisibility = visibilityToString(content.getVisibility());

                if (node instanceof Class){
                    fw.write(contentVisibility + ((Method) content).getReturnType() + " " + content.getName());
                    fw.write("{" + '\n' + '\n' + "}");
                }

                else if (node instanceof Interface) {
                    fw.write(contentVisibility + ((Method) content).getReturnType() + " " + content.getName() + ";");
                }
                fw.write('\n');
            }
        }
    }

    private void makeInterclassTXT(FileWriter fw, String path, Interclass node) throws IOException {
        ArrayList<Connection> inheritance = new ArrayList<>();
        ArrayList<Connection> association = new ArrayList<>();
        for (ClassyNode child : ((ClassyNodeComposite) node.getParent()).getChildren()){
            if (child instanceof Connection && ((Connection) child).getFromElement().equals(node)) {
                if (child instanceof Generalization || child instanceof Dependency)
                    inheritance.add((Connection) child);
                else if (child instanceof Aggregation || child instanceof Composition)
                    association.add((Connection) child);
            }
        }
        String visibility = visibilityToString(node.getVisibility());
        fw.write("package" + " " + path.replace(('\\' + node.getName()), "").replace(("\\"), ".") + '\n');
        fw.write(visibility + node.getClass().toString().substring(node.getClass().toString().lastIndexOf(".") + 1).toLowerCase() + " " + node.getName() + " ");
        for (ClassyNode connection : inheritance){
            if (connection instanceof Generalization)
                fw.write("extends " + ((Generalization) connection).getToElement() + " ");
            else
                fw.write("implements " + ((Dependency) connection).getToElement() + " ");
        }
        fw.write("{" + "\n\n");
        if (node instanceof Class){
            implementVariables(fw, association);
            for (ClassContent content : ((Class) node).getContent()){
                String contentVisibility = visibilityToString(content.getVisibility());
                if (content instanceof Attribute)
                    fw.write(content.toString().replace(content.getVisibility() + " ", contentVisibility));
                if (content instanceof Method) {
                    fw.write(contentVisibility + ((Method) content).getReturnType() + " " + content.getName());
                    fw.write("{" + '\n' + '\n' + "}");
                }
                fw.write('\n');
            }
        }
        else if (node instanceof Interface){
            for (Method content : ((Interface) node).getContent()){
                String contentVisibility = visibilityToString(content.getVisibility());
                fw.write(contentVisibility + content.getReturnType() + " " + content.getName() + ";");
                fw.write('\n');
            }
        }

        else{
            int i = 0;
            for (String content : ((Enum) node).getContent()){
                if (i == 0){
                    fw.write(content);
                    i++;
                    continue;
                }
                fw.write(",");
                fw.write(content);
            }
        }
        implementMethods(fw, inheritance, node);
        fw.write("}");
    }

    public void saveProjectAsCode(ClassyNode node, String dir, String path){
        if (node instanceof Connection)
            return;
        if (node instanceof Interclass){
            try {
                FileWriter fw = new FileWriter(dir + path + ".txt");
                makeInterclassTXT(fw, path, (Interclass) node);
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        if (node instanceof Project || node instanceof Package){
            File file = new File(dir + "\\" + path);
            file.mkdir();
        }
        for (ClassyNode child : ((ClassyNodeComposite) node).getChildren()){
            if (node instanceof Diagram)
                saveProjectAsCode(child, dir,path.replace(node.getName(), "") + child.getName());
            else
                saveProjectAsCode(child, dir, path + '\\' + child.getName());
        }
    }

    @Override
    public void addChild(ClassyTreeItem parent) {
        if (!(parent.getClassyNode() instanceof ClassyNodeComposite))
            return;

        int childType = 0;

        if (parent.getClassyNode() instanceof Package){
            Object[] possibilities = {"Diagram", "Package", "Cancel"};
            childType = JOptionPane.showOptionDialog(null,
                    "Select the node type",
                    "Type Selection",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    null);
            if (childType == 2 || childType == -1)
                return;
        }

        ClassyNodeFactory nodeFactory = FactoryUtils.getNodeFactory(parent.getClassyNode(), childType);
        if (nodeFactory == null)
            return;

        ClassyNode child = nodeFactory.getNode(parent.getClassyNode());

        if (((ClassyNodeComposite) parent.getClassyNode()).getChildren().contains(child))
            return;

        ApplicationFramework.getInstance().getClassyRepository().addChild(((ClassyNodeComposite) parent.getClassyNode()), child);
        ClassyTreeItem newChild = new ClassyTreeItem(child);
        parent.add(newChild);

        if (child instanceof Package) {
            MainFrame.getInstance().getPackageView().setCurrPackage(newChild);
        }

        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void deleteChild(ClassyTreeItem parent, ClassyTreeItem node) {
        if (node == null || node.getClassyNode() instanceof ProjectExplorer) {
            return;
        }

        ClassyTreeItem currItem = MainFrame.getInstance().getPackageView().getCurrPackage();
        if (currItem != null) {
            while (!(currItem.getClassyNode() instanceof ProjectExplorer)) {
                if (currItem.getClassyNode().equals(node.getClassyNode())) {
                    MainFrame.getInstance().getPackageView().setCurrPackage(null);
                    break;
                }
                currItem = (ClassyTreeItem) currItem.getParent();
            }
        }


        ApplicationFramework.getInstance().getClassyRepository().deleteNode(node.getClassyNode());
        parent.remove(node);
        treeView.setSelectionPath(treeView.getSelectionPath().getParentPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void editAuthor(ClassyTreeItem node) {
        if (node == null || !(node.getClassyNode() instanceof Project))
            return;

        String newAuthor = JOptionPane.showInputDialog("The new author name:");

        while(newAuthor != null && newAuthor.equals("")) {
            newAuthor = JOptionPane.showInputDialog("The name cannot be empty\nThe new author name:");
        }

        if (newAuthor == null)
            return;

        ApplicationFramework.getInstance().getClassyRepository().editAuthor(node.getClassyNode(), newAuthor);
    }

    @Override
    public ClassyTreeItem getSelectedItem() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }
}