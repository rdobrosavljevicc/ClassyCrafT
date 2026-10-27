package raf.dsw.classycraft.app.classyRepository.implementation;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.Connection;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;

@Getter
@Setter
public class Aggregation extends Connection {
    String variableName;
    public Aggregation(String name, ClassyNode parent, Paint paint, Stroke stroke) {
        super(name, parent, paint, stroke);
        variableName = "";
    }

    public Aggregation(String name, ClassyNode parent, Paint paint, Stroke stroke, Interclass fromElement, Interclass toElement) {
        super(name, parent, paint, stroke, fromElement, toElement);
    }
}
