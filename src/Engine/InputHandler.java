package Engine;

//import Utility.Point;
import Engine.Engine;
import Nodes.*;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.*;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    private Point mouseClickPos;
    private Point mousePos;
    private Node selectedNode;
    private int index;

    private Engine engine;
    private Key up = new Key();
    private Key down = new Key();
    private Key left = new Key();
    private Key right = new Key();
    private Key space = new Key();

    public InputHandler(Engine e) {
        engine = e;
        engine.addKeyListener(this);
        engine.addMouseListener(this);
        engine.addMouseMotionListener(this);

        this.mouseClickPos = new Point();
    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        //toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        //engine.player.isWalking = false;
        //toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent arg0) {
    }



    public void mouseClicked(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());
        //System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());
    }

//    public Engine getGame() {
//        return engine;
//    }

    //public void setGame(Engine game) {
//        this.engine = game;
//    }
    public void mousePressed(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());
        System.out.println("Mouse Clicked at: " + e.getX() + ", " + e.getY());

        for (Node node : engine.nodeList) {
            if (node.getHitBox().contains(mouseClickPos) && !node.getPhysicsBody().isStatic) {
                this.selectedNode = node;
                System.out.println("selected node " + selectedNode.getName());
            }
            if (!node.getPhysicsBody().isStatic) {
                node.setDragging(true);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (Node node : engine.nodeList) {
            node.setDragging(false);
            if (node.getName() == "spring" && this.selectedNode == node) {
                Point adjustedPoint = new Point();
                adjustedPoint.x = engine.spring.initialPosition.x - (engine.spring.sprite.getWidth() / 2);
                adjustedPoint.y = engine.spring.initialPosition.y - (engine.spring.sprite.getHeight() / 2);
                engine.spring.setNodePosition(adjustedPoint);
                if (node instanceof SpringNode) {
                    //double x = ((SpringNode) node).calcDistanceFromEquilibrium();
                    double velMagnitude = node.getPhysicsBody().vFromPEsToKEPEg(node, engine.groundNode);
                    System.out.println("velMag: " + velMagnitude);
                    ((SpringNode) node).launchProjectile(velMagnitude);

                }
            }
        }
        this.selectedNode = null;
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClickPos = new Point(e.getX(), e.getY());

        for (Node node : engine.nodeList) {
            if ((node.getDragging() || node.containsPoint(mouseClickPos)) && node.equals(selectedNode) ) {
                node.setDragging(true);
                Point adjustedPoint = new Point();
                adjustedPoint.x = mouseClickPos.x - (node.getHitBox().width / 2);
                adjustedPoint.y = mouseClickPos.y - (node.getHitBox().height / 2);

                if (node instanceof SpringNode) {
                    if (((SpringNode) node).calcDistanceFromEquilibrium() <= 120) {
                        node.setNodePosition(adjustedPoint);
                    }
                } else {
                    node.setNodePosition(adjustedPoint);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mousePos = new Point(e.getX(), e.getY());
    }

    public Point getMousePos() { return mousePos; }

    public void setMousePos(Point mousePos) { this.mousePos = mousePos; }

    public Node getSelectedNode() { return selectedNode; }

    public void setSelectedNode(Node selectedNode) {this.selectedNode = selectedNode; }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Point getMouseClickPos() { return mouseClickPos; }

    public void setMouseClickPos(Point mouseClickPos) {
        this.mouseClickPos = mouseClickPos;
    }
}


