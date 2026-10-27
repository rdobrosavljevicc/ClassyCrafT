package raf.dsw.classycraft.app.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.core.Serializer;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.io.*;

public class JacksonSerializer implements Serializer {
    ObjectMapper mapper;

    public JacksonSerializer() {
        mapper = new ObjectMapper();
    }

    @Override
    public ClassyTreeItem loadProject(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            JsonNode rootNode = mapper.readTree(fileReader);
            return ((ClassyTreeImplementation) MainFrame.getInstance().getClassyTree()).loadProject(rootNode);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
    public void saveProject(Project project) {
        try (FileWriter writer = new FileWriter(project.getFilePath())) {
            mapper.writeValue(writer, project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveDiagram(Diagram diagram) {
        try (FileWriter writer = new FileWriter(diagram.getFilePath())) {
            mapper.writeValue(writer, diagram);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
