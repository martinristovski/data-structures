public class AVLTree<T extends Comparable<T>> implements Iterable<T> {
    public class Node {
        public int balanceFactor, height;
        public T value;
        public Node left, right;

        public Node(T value) { this.value = value; }
    }

    public Node root;
    
    private int nodeCount = 0;

    public int height() {
        if (root == null) return 0;
        return root.height;
    }

    public int size() { return nodeCount; }

    public boolean isEmpty() { return size() == 0; }

    public boolean contains(T value) { return contains(root, value); }

    private boolean contains(Node node, T value) {
        if (node == null) return false;

        if (value.compareTo(node.value) < 0) return contains(node.left, value);

        if (value.compareTo(node.value) > 0) return contains(node.right, value);

        return true;
    }

    public boolean insert(T value) {
        if (value == null) return false;

        if (!contains(root, value)) {
            root = insert(root, value);
            nodeCount++;
            return true;
        }

        return false;
    }

    private Node insert(Node node, T value) {
        if (node == null) return new Node(value);

        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }

        update(node);

        return balance(node);
    }

    private void update(Node node) {
        int leftSubtreeHeight = (node.left == null) ? -1 : node.left.height;
        int rightSubtreeHeight = (node.right == null) ? -1 : node.right.height;

        node.height = 1 + Math.max(leftSubtreeHeight, rightSubtreeHeight);

        node.balanceFactor = rightSubtreeHeight - leftSubtreeHeight;
    }

    private Node balance(Node node) {
        if (node.balanceFactor == -2) {
            if (node.left.balanceFactor <= 0) { return leftLeftCase(node); }
            else { return leftRightCase(node); }
        } else if (node.balanceFactor == 2) {
            if (node.right.balanceFactor >= 0) { return rightRightCase(node); }
            else { return rightLeftCase(node); }
        }

        return node;
    }

    private Node leftLeftCase(Node node) {
        return rotateRight(node);
    }

    private Node leftRightCase(Node node) {
        node.left = rotateLeft(node);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        return rotateLeft(node);
    }

    private Node rightLeftCase(Node node) {
        node.right = rotateRight(node.right);
        return rightRightCase(node);
    }

    private Node rotateLeft(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;

        update(node);
        update(newParent);

        return newParent;
    }

    private Node rotateRight(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;

        update(node);
        update(newParent);

        return newParent;
    }

    public boolean remove(T value) {
        if (value == null) return false;

        if (contains(root, value)) {
            root = remove(root, value);
            nodeCount--;
            return true;
        }

        return false;
    }

    private Node remove(Node node, T value) {
        if (node == null) return null;

        if (value.compareTo(node.value) < 0) {
            node.left = remove(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    T successor = findMin(node.right);
                    node.value = successor;

                    node.right = remove(node.right, successor);
                }
            }
        }

        update(node);

        return balance(node);
    }

    private T findMin(Node node) {
        while (node.left != null) node = node.left;
        return node.value;
    }

    private T findMax(Node node) {
        while (node.right != null) node = node.right;
        return node.value;
    }

    public java.util.Iterator<T> iterator() {

        final int expectedCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            Node traverser = root;

            @Override
            public boolean hasNext() {
                if (expectedCount != nodeCount) 
                    throw new java.util.ConcurrentModificationException();
                
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedCount != nodeCount) 
                    throw new java.util.ConcurrentModificationException();

                while (traverser != null && traverser != null) {
                    stack.push(traverser.left);
                    traverser = traverser.left;
                }

                Node node = stack.pop();

                if (node.right != null) {
                    stack.push(node.right);
                    traverser = node.right;
                }

                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}