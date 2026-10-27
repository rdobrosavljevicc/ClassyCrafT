package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassContent;

@Getter
@Setter
public class Method extends ClassContent {
    private String returnType;

    public Method(String visibility, String nameAndValues, String returnType) {
        super(visibility, nameAndValues);
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return super.getVisibility() + " " + super.getName() + " : " + returnType;
    }
}
