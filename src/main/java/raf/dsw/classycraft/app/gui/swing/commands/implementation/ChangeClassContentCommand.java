package raf.dsw.classycraft.app.gui.swing.commands.implementation;

import raf.dsw.classycraft.app.classyRepository.ClassyRepositoryImplementation;
import raf.dsw.classycraft.app.classyRepository.composite.ClassContent;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;
import raf.dsw.classycraft.app.classyRepository.implementation.Class;
import raf.dsw.classycraft.app.classyRepository.implementation.Enum;
import raf.dsw.classycraft.app.classyRepository.implementation.Interface;
import raf.dsw.classycraft.app.classyRepository.implementation.Method;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.commands.AbstractCommand;

import java.awt.*;
import java.util.ArrayList;

public class ChangeClassContentCommand extends AbstractCommand {
    private Interclass interclass;
    private ArrayList<Object> newContent;
    private ArrayList<Object> initialContent;
    private String initialName;
    private FontMetrics metrics;

    public ChangeClassContentCommand(Interclass interclass, ArrayList<Object> newContent) {
        this.interclass = interclass;
        this.newContent = newContent;
        initialContent = new ArrayList<>();
        initialName = String.valueOf(interclass.getName());

        if (interclass instanceof Class)
            initialContent = new ArrayList<>(((Class) interclass).getContent());

        else if (interclass instanceof Interface)
            initialContent = new ArrayList<>(((Interface) interclass).getContent());

        else if (interclass instanceof Enum)
            initialContent = new ArrayList<>(((Enum) interclass).getContent());

        Font font = new Font("Helvetica",Font.PLAIN,12);
        Canvas canvas = new Canvas();
        metrics = canvas.getFontMetrics(font);
    }

    @Override
    public void doCommand() {
        if (interclass == null || newContent == null)
            return;

        if (newContent.size() >= 2 && newContent.get(0) != null && newContent.get(1) != null && newContent.get(1).equals(interclass))
            interclass.setName((String) newContent.get(0));

        else if (interclass instanceof Class) {
            if (newContent.get(0) != null)
                ((Class) interclass).getContent().remove((ClassContent) newContent.get(0));
            ((Class) interclass).getContent().add((ClassContent) newContent.get(1));
            ((Class) interclass).rearrangeContent();
        }
        else if (interclass instanceof Interface){
            if (newContent.get(0) != null)
                ((Interface) interclass).getContent().remove((Method) newContent.get(0));
            ((Interface) interclass).getContent().add((Method) newContent.get(1));
        }
        else {
            ((Enum) interclass).getContent().clear();
            for (Object value : newContent){
                ((Enum) interclass).getContent().add((String) value);
            }
        }
        interclass.updateSize(metrics);
        ((ClassyRepositoryImplementation) ApplicationFramework.getInstance().getClassyRepository()).notifySubscribers(this);

    }

    @Override
    public void undoCommand() {
        if (interclass == null || newContent == null)
            return;

        if (newContent.size() >= 2 && newContent.get(0) != null && newContent.get(1) != null && newContent.get(1).equals(interclass))
            interclass.setName(initialName);

        else if (interclass instanceof Class) {
            if (newContent.get(0) != null)
                ((Class) interclass).getContent().add((ClassContent) newContent.get(0));
            ((Class) interclass).getContent().remove((ClassContent) newContent.get(1));
            ((Class) interclass).rearrangeContent();
        }
        else if (interclass instanceof Interface) {
            if (newContent.get(0) != null)
                ((Interface) interclass).getContent().add((Method) newContent.get(0));
            ((Interface) interclass).getContent().remove((Method) newContent.get(1));
        }
        else {
            ((Enum) interclass).getContent().clear();
            for (Object value : initialContent){
                ((Enum) interclass).getContent().add((String) value);
            }
        }
        interclass.updateSize(metrics);
        ((ClassyRepositoryImplementation) ApplicationFramework.getInstance().getClassyRepository()).notifySubscribers(this);
    }
}
