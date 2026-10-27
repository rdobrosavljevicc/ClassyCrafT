package raf.dsw.classycraft.app.gui.swing.view.painter;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.composite.Interclass;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
public class InterclassPainter extends ElementPainter{
    private ArrayList<Object> classContent;

    public InterclassPainter(DiagramElement element) {
        super(element);
        classContent = new ArrayList<>();
    }

    @Override
    public void paint(Graphics2D graphics, DiagramElement element) {
        graphics.setPaint(Color.BLACK);
        graphics.setStroke(element.getStroke());
        graphics.draw(this.getShape());
        graphics.setPaint(element.getPaint());
        graphics.fill(this.getShape());
        Interclass ic = (Interclass) element;
        graphics.setPaint(Color.BLACK);
        String visibilityAndName = ic.getVisibility() + " " + ic.getName();

        int x = (int) (ic.getPosition().getX() + 1);
        int y = (int) (ic.getPosition().getY() + 10);
        graphics.drawString(visibilityAndName, x, y);
        graphics.drawLine((int) ic.getPosition().getX(), (int) (ic.getPosition().getY() + 12), (int) (ic.getPosition().getX() + ic.getSize().getWidth()), (int) (ic.getPosition().getY() + 12));
        y += 12;
        for (Object content : classContent){
            graphics.drawString(content.toString(), x , y);
            y += 10;
        }
    }

    @Override
    public boolean elementAt(DiagramElement element, Point point) {
        return false;
    }
}
