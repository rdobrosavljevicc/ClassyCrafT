package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;

@Getter
@Setter
public class Project extends ClassyNodeComposite {
    private String author;
    private String filePath;
    private boolean changed;

    public Project(String name, ClassyNode parent, String author) {
        super(name, parent);
        this.author = author;
        this.changed = true;
    }

}
