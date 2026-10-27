package raf.dsw.classycraft.app.classyRepository.composite;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Interclass extends DiagramElement{
    private String visibility;
    private Point position;
    private Dimension size;

    public Interclass(String name, ClassyNode parent, Paint paint, Stroke stroke, String visibility, Point position, Dimension size) {
        super(name, parent, paint, stroke);
        this.visibility = visibility;
        this.position = position;
        this.size = size;
    }

    public abstract void updateSize(FontMetrics metrics);
}
