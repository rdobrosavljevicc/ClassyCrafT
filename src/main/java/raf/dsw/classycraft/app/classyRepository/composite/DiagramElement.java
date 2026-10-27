package raf.dsw.classycraft.app.classyRepository.composite;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class DiagramElement extends ClassyNode{
    private Paint paint;
    private Stroke stroke;

    public DiagramElement(String name, ClassyNode parent, Paint paint, Stroke stroke) {
        super(name, parent);
        this.paint = paint;
        this.stroke = stroke;
    }

}
