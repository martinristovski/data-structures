public class BinarySearchTree<T extends Comparable<T>> {
    private int nodeCount = 0;

    private Node root = null;

    private class Node {
        T data;
        Node left, right;

        public Node(Node left, Node right, T data) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public int size() {
        return nodeCount;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(T data) {
        if (contains(data)) {
            return false;
        } else {
            root = add(root, data);
            nodeCount++;
            return true;
        }
    }

    private Node add(Node node, T data) {
        if (node = null) {
            node = new Node(null, null, data);
        } else {
            if (data.compareTo(node.data) < 0) {
                node.left = add(node.left, data);
            } else {
                node.right = add(node.right, data);
            }
        }

        return node;
    }

    public boolean remove(T data) {
        if (contains(data)) {
            root = remove(root, data);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T data) {
        if (node == null) return null;

        int comp = data.compareTo(node.data);

        if (comp < 0) {
            node.left = remove(node.left, data);
        } else if (comp > 0) {
            node.right = remove(node.right, data);
        } else {
            if (node.left == null) {
                Node rightChild = node.right;

                node.data = null;
                node = null;

                return rightChild;
            } else if (node.right == null) {
                Node leftChild = node.left;

                node.data = null;
                node = null;

                return leftChild;
            } else {
                Node temp = findMin(node.right);
                node.data = temp.data;

                node.right = remove(node.right, temp.data);
            }
        }
        
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public boolean contains(T data) {
        return contains(root, data);
    }

    private boolean contains(Node node, T data) {
        if (node == null) return false;
        
        int comp = data.compareTo(node.data);

        if (comp < 0) return contains(node.left, data);
        else if (comp > 0) return contains(node.right, data);
        else return true;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }
}