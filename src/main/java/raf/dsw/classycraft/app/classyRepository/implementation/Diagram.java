package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;

@Getter
@Setter
public class Diagram extends ClassyNodeComposite {
    private String filePath;
    private boolean changed;
    public Diagram(String name, ClassyNode parent) {
        super(name, parent);
        changed = true;
    }
}
