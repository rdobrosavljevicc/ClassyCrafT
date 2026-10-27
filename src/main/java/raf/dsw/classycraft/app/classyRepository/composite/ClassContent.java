package raf.dsw.classycraft.app.classyRepository.composite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ClassContent {
    private String name;
    private String visibility;

    public ClassContent(String visibility, String name) {
        this.name = name;
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
