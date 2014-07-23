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

        public Node (Point2D p, RectHV r) {
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
    public enum Side {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
    private RectHV halveRect(RectHV rect, Point2D point, Side side) {
        RectHV newRect;
        switch (side) {
            case LEFT:
                newRect = new RectHV(
                        rect.xmin(),
                        rect.ymin(),
                        rect.xmin() + rect.width()/2,
                        rect.ymax());
                break;
            case RIGHT:
                newRect = new RectHV(
                        rect.xmin() + rect.width()/2,
                        rect.ymin(),
                        rect.xmax(),
                        rect.ymax());
                break;
            case TOP:
                newRect = new RectHV(
                        rect.xmin(),
                        rect.ymin() + rect.height()/2,
                        rect.xmax(),
                        rect.ymax());
                break;
            case BOTTOM:
                newRect = new RectHV(
                        rect.xmin(),
                        rect.ymin(),
                        rect.xmax(),
                        rect.ymin() + rect.height()/2);
                break;
            default:
                throw new IllegalArgumentException();
        }

        return newRect;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (root == null) {
            // make root node with point
            // root rect is whole box
            // we are told to assume all x and y will be between 0 and 1
            root = new Node(p, new RectHV(0,0,1,1));
        } else {
            // start with root node
            // compare p.x with root.point.x
            Node node = root;
            // either comparing X or comparing Y
            // (left/right partition or top/bottom partition)
            boolean compareX = true;

            // while loop
            // loop stops when new node inserted or existing is found
            boolean searching = true;
            while (searching) {
                // exit if we find p
                if (node.point.equals(p)) return;
                if (compareX) {
                    if (p.x() < node.point.x()) {
                        // p is left of node point
                        if (node.leftBottom != null) {
                            // has left child, keep searching
                            node = node.leftBottom;
                            compareX = !compareX;
                            continue;
                        } else {
                            // no left child, insert new node on left
                            RectHV newRect = halveRect(node.rect, node.point, Side.LEFT);
                            Node newNode = new Node(p, newRect);
                            node.leftBottom = newNode;
                            return;
                        }
                    } else {
                        // p is right or equal x of node point
                        if (node.rightTop != null ) {
                            // has right child, keep searching
                            node = node.rightTop;
                            compareX = !compareX;
                            continue;
                        } else {
                            // no right child, insert new node on right
                            RectHV newRect = halveRect(node.rect, node.point, Side.RIGHT);
                            Node newNode = new Node(p, newRect);
                            node.rightTop = newNode;
                            return;
                        }
                    }
                } else {
                    // !compareX means we are comparing y
                    if (p.y() < node.point.y()) {
                        // p is below node point
                        if (node.leftBottom != null) {
                            // has bottom child, keep searching
                            node = node.leftBottom;
                            compareX = !compareX;
                            continue;
                        } else {
                            // no bottom child, insert new node on bottom
                            RectHV newRect = halveRect(node.rect, node.point, Side.BOTTOM);
                            Node newNode = new Node(p, newRect);
                            node.leftBottom = newNode;
                            return;
                        }
                    } else {
                        // p is above or equal y of node point
                        if (node.rightTop != null ) {
                            // has top child, keep searching
                            node = node.rightTop;
                            compareX = !compareX;
                            continue;
                        } else {
                            // no top child, insert new node on top
                            RectHV newRect = halveRect(node.rect, node.point, Side.TOP);
                            Node newNode = new Node(p, newRect);
                            node.rightTop = newNode;
                            return;
                        }
                    }
                }
            }


            // compare appropriate dimension of node.point with p
            // check appropriate child of node
            // if null add there, increase size
            // if not null, switch bool, update node, continue comparison
            if (compareX && node.point.x() < p.x()) {
                //

            } else {

            }




            size += 1;
        }
    }
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    // draw all of the points to standard draw
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                stack.push(point);
            }
        }
        return stack;
    }
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) return null;
        Point2D nearestPoint = set.min();
        double nearestDistance = p.distanceTo(nearestPoint);
        for (Point2D point : set) {
            if (p.distanceTo(point) < nearestDistance) {
                nearestDistance = p.distanceTo(point);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }        // a nearest neighbor in the set to p; null if set is empty
}