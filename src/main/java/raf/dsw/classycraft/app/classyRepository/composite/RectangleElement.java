package raf.dsw.classycraft.app.classyRepository.composite;

import java.awt.*;

public class RectangleElement extends Interclass{
    public RectangleElement(String name, ClassyNode parent, Paint paint, Stroke stroke, String visibility, Point position, Dimension size) {
        super(name, parent, paint, stroke, visibility, position, size);
    }

    @Override
    public void updateSize(FontMetrics metrics) {

    }
}
