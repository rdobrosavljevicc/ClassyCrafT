package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassContent;

@Getter
@Setter
public class Attribute extends ClassContent {
    private String type;

    public Attribute(String visibility, String type, String name) {
        super(visibility, name);
        this.type = type;
    }

    @Override
    public String toString() {
        return super.getVisibility() + " " + type + " " + super.getName();
    }
}
