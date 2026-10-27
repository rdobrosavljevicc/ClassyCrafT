package raf.dsw.classycraft.app.classyRepository.implementation;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.Connection;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;

public class Generalization extends Connection {
    public Generalization(String name, ClassyNode parent, Paint paint, Stroke stroke) {
        super(name, parent, paint, stroke);
    }

    public Generalization(String name, ClassyNode parent, Paint paint, Stroke stroke, Interclass fromElement, Interclass toElement) {
        super(name, parent, paint, stroke, fromElement, toElement);
    }
}
