import java.awt.*;

/**
 * Created by alexchoi1 on 7/22/14.
 * A 2d-tree is a generalization of a BST to two-dimensional keys.
 * The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as
 * keys in strictly alternating sequence.
 */
public class KdTree {

    private static class Node {
        // the point
        private Point2D point;
        // the axis-aligned rectangle corresponding to this node
        // for range search
        //  if the query rectangle does not intersect
        // the rectangle corresponding to a node,
        // there is no need to explore that node (or its subtrees).
        private RectHV rect;
        // the left/bottom subtree
        private Node leftBottom;
        // the right/top subtree
        private Node rightTop;

        private Node (Point2D p, RectHV r) {
            point = p;
            rect = r;
            leftBottom = null;
            rightTop = null;
        }
    }
    // root node
    private Node root;
    // size of tree
    private int size;
    // construct an empty KdTree
    public KdTree() {
        root = null;
        size = 0;
    }
    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // number of points in the set
    public int size() {
        return size;
    }

    // maybe? want to make rect splitting method that takes in left/right vs top/bottom
    // and coordinate and side
    // type def left right top bottom
    // takes in side and coordinate and rect
    private enum Side {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
    private RectHV splitRect(RectHV rect, Point2D point, Side side) {
        RectHV newRect;
        switch (side) {
            case LEFT:
                newRect = new RectHV(
                        rect.xmin(),
                        rect.ymin(),
                        point.x(),
                        rect.ymax());
                break;
            case RIGHT:
                newRect = new RectHV(
                        point.x(),
                        rect.ymin(),
                        rect.xmax(),
                        rect.ymax());
                break;
            case TOP:
                newRect = new RectHV(
                        rect.xmin(),
                        point.y(),
                        rect.xmax(),
                        rect.ymax());
                break;
            case BOTTOM:
                newRect = new RectHV(
                        rect.xmin(),
                        rect.ymin(),
                        rect.xmax(),
                        point.y());
                break;
            default:
                throw new IllegalArgumentException();
        }

        return newRect;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, new RectHV(0,0,1,1));
            size += 1;
        }
        // node we compare with point p. start at root
        Node node = root;
        // value we compare with, alternates between x an y
        boolean compareX = true;

        while (true) {
            // if we find point p, exit method
            if (node.point.equals(p)) return;

            if (compareX && (p.x() < node.point.x())) {
                // comparing X and point p is left of node point
                if (node.leftBottom != null) {
                    // has child: alternate compare, update node, continue loop
                    node = node.leftBottom;
                    compareX = !compareX;
                    continue;
                } else {
                   // no child, insert new child node here; exit method
                    RectHV newRect = splitRect(node.rect, node.point, Side.LEFT);
                    node.leftBottom = new Node(p, newRect);
                    size += 1;
                    return;
                }
            }
            else if (compareX && (p.x() >= node.point.x())) {
                // comparing X and point p is right or equal x of node point
                if (node.rightTop != null ) {
                    // has child: alternate compare, update node, continue loop
                    node = node.rightTop;
                    compareX = !compareX;
                    continue;
                } else {
                    // no child, insert new child node here; exit method
                    RectHV newRect = splitRect(node.rect, node.point, Side.RIGHT);
                    node.rightTop = new Node(p, newRect);
                    size += 1;
                    return;
                }
            }
         else if (!compareX && (p.y() < node.point.y())) {
                // comparing Y and point p is below node point
                if (node.leftBottom != null) {
                    // has child: alternate compare, update node, continue loop
                    node = node.leftBottom;
                    compareX = !compareX;
                    continue;
                } else {
                    // no child, insert new child node here; exit method
                    RectHV newRect = splitRect(node.rect, node.point, Side.BOTTOM);
                    node.leftBottom = new Node(p, newRect);
                    size += 1;
                    return;
                }
            } else {
                // comparing Y and point p is above or equal to node point
                if (node.rightTop != null ) {
                    // has child: alternate compare, update node, continue loop
                    node = node.rightTop;
                    compareX = !compareX;
                    continue;
                } else {
                    // no child, insert new child node here; exit method
                    RectHV newRect = splitRect(node.rect, node.point, Side.TOP);
                    node.rightTop = new Node(p, newRect);
                    size += 1;
                    return;
                }
            }
        }
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        // node we compare with point p. start at root
        Node node = root;
        // value we compare with, alternates between x an y
        boolean compareX = true;

        while (true) {
            if (node == null) return false;
            if (node.point.equals(p)) return true;

            if ((compareX && (p.x() < node.point.x()))
                || (!compareX && (p.y() < node.point.y()))) {
                    node = node.leftBottom;
            }
            else {
                    node = node.rightTop;
            }
            compareX = !compareX;
            continue;
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        draw(root, true);
    }
    private void draw(Node node, boolean compareX) {
        //  draw all of the points to standard draw in black
        // and the subdivisions in red (for vertical splits)
        // and blue (for horizontal splits)
        if (node != null) {
            StdDraw.setPenColor(Color.BLACK);
            node.point.draw();
            if (compareX) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(node.point.x(),node.rect.ymin(),node.point.x(),node.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(node.rect.xmin(),node.point.y(),node.rect.xmax(),node.point.y());
            }
            draw(node.leftBottom, !compareX);
            draw(node.rightTop, !compareX);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        if (root != null) {
            range(rect, root, stack);
        }
        return stack;
    }
    private Stack range(RectHV rect, Node node, Stack<Point2D> stack) {
        if (node == null) return stack;
        // add node point if in rect
        if (rect.contains(node.point)) {
            stack.push(node.point);
        }
        // if query rect intersects node rect, explore children
        if (rect.intersects(node.rect)) {
            range(rect, node.leftBottom, stack);
            range(rect, node.rightTop, stack);
        }
        return stack;
    }
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return nearest(p, root, root.point, true);
    }
    private Point2D nearest(Point2D queryPoint, Node node, Point2D champion, boolean compareX) {
        if (node == null) return champion;
        // if node point is closer than champion, it becomes the new champion
        if (queryPoint.distanceSquaredTo(node.point) < queryPoint.distanceSquaredTo(champion)) {
            champion = node.point;
        }
        // only explore node if it can contain a point closer than the champion
        if (node.rect.distanceSquaredTo(queryPoint) < queryPoint.distanceSquaredTo(champion)) {
            // query point is left or below node point
            if ((compareX && queryPoint.x() < node.point.x()) ||
                    (!compareX && queryPoint.y() < node.point.y())) {
                // explore left bottom first
                champion = nearest(queryPoint, node.leftBottom, champion, !compareX);
                champion = nearest(queryPoint, node.rightTop, champion, !compareX);
            } else {
                // query point is right, above, or equal to node point
                // explore right top first
                champion = nearest(queryPoint, node.rightTop, champion, !compareX);
                champion = nearest(queryPoint, node.leftBottom, champion, !compareX);
            }
        }
        return champion;
    }
    /*
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        String fileName = args[0];
        In in = new In(fileName);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D point = new Point2D(x,y);
            StdOut.println("contains before: " + kdTree.contains(point));
            kdTree.insert(point);
            StdOut.println("point:" + point);
            StdOut.println("contains after: " + kdTree.contains(point));
            StdOut.println("size: " + kdTree.size());
        }
        kdTree.draw();
    }
    */
}