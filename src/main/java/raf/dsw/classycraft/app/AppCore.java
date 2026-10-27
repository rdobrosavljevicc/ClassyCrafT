package raf.dsw.classycraft.app;

import raf.dsw.classycraft.app.classyRepository.ClassyRepositoryImplementation;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.Gui;
import raf.dsw.classycraft.app.core.Serializer;
import raf.dsw.classycraft.app.gui.swing.SwingGui;
import raf.dsw.classycraft.app.serializer.JacksonSerializer;

public class AppCore {
    public static void main(String[] args) {
        ApplicationFramework appCore = ApplicationFramework.getInstance();
        ClassyRepositoryImplementation classyRepositoryImplementation = new ClassyRepositoryImplementation();
        Serializer serializer = new JacksonSerializer();
        Gui gui = new SwingGui();
        appCore.initialize(gui, classyRepositoryImplementation, serializer);
        appCore.run();
    }
}