package raf.dsw.classycraft.app.gui.swing.view.painter;


import raf.dsw.classycraft.app.classyRepository.composite.Connection;
import raf.dsw.classycraft.app.classyRepository.composite.DiagramElement;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class AggregationPainter extends ConnectionPainter {
    public AggregationPainter(DiagramElement element) {
        super(element);
        element.setStroke(new BasicStroke(1));
        Connection connection = (Connection) element;

        if (connection.getFromElement() == null || connection.getToElement() == null)
            return;

        this.setShape(new GeneralPath());
        if (connection.getFromElement() == connection.getToElement()){
            this.setDirection(new int[]{(int) (connection.getFromElement().getPosition().getX() + connection.getFromElement().getSize().getWidth()), ((int) (connection.getFromElement().getPosition().getY() + (connection.getFromElement().getSize().getHeight() / 2))), (int) (connection.getFromElement().getPosition().getX() + (connection.getFromElement().getSize().getWidth() / 2)), ((int) connection.getFromElement().getPosition().getY())});
            ((GeneralPath) this.getShape()).moveTo((float) direction[0], (float) direction[1]);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 8, (float) direction[1] + 5);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 16, (float) direction[1]);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 8, (float) direction[1] - 5);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1]);
            ((GeneralPath)this.getShape()).closePath();
        }
        else if (connection.getFromElement().getPosition().getX() > connection.getToElement().getPosition().getX() + connection.getToElement().getSize().getWidth()) {
            this.setDirection(new int[]{((int) connection.getFromElement().getPosition().getX()), ((int) (connection.getFromElement().getPosition().getY() + (connection.getFromElement().getSize().getHeight() / 2))), ((int) (connection.getToElement().getPosition().getX() + connection.getToElement().getSize().getWidth())), ((int) (connection.getToElement().getPosition().getY() + (connection.getToElement().getSize().getHeight()) / 2))});
            ((GeneralPath) this.getShape()).moveTo((float) direction[0], (float) direction[1]);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] - 8, (float) direction[1] + 5);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] - 16, (float) direction[1]);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] - 8, (float) direction[1] - 5);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1]);
            ((GeneralPath)this.getShape()).closePath();
        }
        else if (connection.getFromElement().getPosition().getX() + connection.getFromElement().getSize().getWidth() < connection.getToElement().getPosition().getX()) {
            this.setDirection(new int[]{(int) (connection.getFromElement().getPosition().getX() + connection.getFromElement().getSize().getWidth()), ((int) (connection.getFromElement().getPosition().getY() + (connection.getFromElement().getSize().getHeight() / 2))), ((int) connection.getToElement().getPosition().getX()), ((int) (connection.getToElement().getPosition().getY() + (connection.getToElement().getSize().getHeight()) / 2))});
            ((GeneralPath) this.getShape()).moveTo((float) direction[0], (float) direction[1]);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 8, (float) direction[1] + 5);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 16, (float) direction[1]);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 8, (float) direction[1] - 5);
            ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1]);
            ((GeneralPath)this.getShape()).closePath();
        }
        else{
            if (connection.getFromElement().getPosition().getY() > connection.getToElement().getPosition().getY()) {
                this.setDirection(new int[]{(int) (connection.getFromElement().getPosition().getX() + (connection.getFromElement().getSize().getWidth() / 2)), ((int) connection.getFromElement().getPosition().getY()), (int) ((connection.getToElement().getPosition().getX()) + (connection.getToElement().getSize().getWidth() / 2)), ((int) (connection.getToElement().getPosition().getY() + connection.getToElement().getSize().getHeight()))});
                ((GeneralPath) this.getShape()).moveTo((float) direction[0], (float) direction[1]);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 5, (float) direction[1] - 8);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1] - 16);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0] - 5, (float) direction[1] - 8);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1]);
                ((GeneralPath)this.getShape()).closePath();
            }
            else if (connection.getFromElement().getPosition().getY() < connection.getToElement().getPosition().getY()) {
                this.setDirection(new int[]{((int) (connection.getFromElement().getPosition().getX() + (connection.getFromElement().getSize().getWidth() / 2))), (int) (connection.getFromElement().getPosition().getY() + connection.getFromElement().getSize().getHeight()), (int) ((connection.getToElement().getPosition().getX()) + (connection.getToElement().getSize().getWidth() / 2)), (int) connection.getToElement().getPosition().getY()});
                ((GeneralPath) this.getShape()).moveTo((float) direction[0], (float) direction[1]);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0] + 5, (float) direction[1] + 8);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1] + 16);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0] - 5, (float) direction[1] + 8);
                ((GeneralPath)this.getShape()).lineTo((float) direction[0], (float) direction[1]);
                ((GeneralPath)this.getShape()).closePath();
            }
        }
    }
}
