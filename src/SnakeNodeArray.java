import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class SnakeNodeArray {
    private final List<SnakeNode> nodes = new ArrayList<>();

    // Adaugă un nou cap (la sfârșitul listei)
    public void addHead(Point2D newHeadPosition) {
        nodes.add(new SnakeNode(newHeadPosition));
    }

    // Elimină coada (primul element)
    public void removeTail() {
        if (!nodes.isEmpty()) {

            for(int i=0;i < nodes.size() - 1; i++){
                nodes.get(i).setPosition(nodes.get(i+1).getPosition());
            }

            nodes.remove(nodes.size() - 1);
        }
    }

    // Obține poziția capului
    public Point2D getHeadPosition() {
        if (nodes.isEmpty()) return null;
        return nodes.get(nodes.size() - 1).getPosition();
    }

    // Obține poziția cozii
    public Point2D getTailPosition() {
        if (nodes.isEmpty()) return null;
        return nodes.get(0).getPosition();
    }

    // Obține toate nodurile
    public List<SnakeNode> getNodes() {
        return nodes;
    }

    // Obține un nod după index
    public SnakeNode getNode(int index) {
        if (index < 0 || index >= nodes.size()) return null;
        return nodes.get(index);
    }

    // Obține dimensiunea lanțului
    public int size() {
        return nodes.size();
    }

}
