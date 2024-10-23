package structures;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    // Insertar un elemento en el AVL
    public void AVLInsert(T element) {
        root = insertRecursive(root, element); // Se llama al caso recursivo
    }

    private Node<T> insertRecursive(Node<T> node, T element) {

        // Caso: Árbol vacío, se crea el nuevo nodo
        if (node == null) {
            return new Node<>(element);
        }

        // Si el elemento es menor a la raíz, se inserta a la izquierda
        if (element.compareTo(node.getData()) < 0) {
            node.setLeft(insertRecursive(node.getLeft(), element));
        }else{
            node.setRight(insertRecursive(node.getRight(), element));
        }
        // Actualizar la altura del nodo actual
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) +1);

        // Obtener el factor de balance para ver si el nodo se desbalancea
        int balance = getBalanceFactor(node);

        if(balance > 1) {
            if(getBalanceFactor(node.getLeft()) < 0){
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }

        if(balance < -1) {
            if(getBalanceFactor(node.getLeft()) > 0){
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }
        return node;
    }

    // Metodo para buscar un elemento en el arbol AVl
    public Node<T> AVLSearch(T element) {
        return searchRecursive(root, element);
    }

    // Metodo recursivo de busqueda
    private Node<T> searchRecursive(Node<T> current, T element) {
        // Caso: Arbol vacio
        if (current == null) {
            return null; // El elemento no se encuentra en el árbol
        }

        // Si es menor al Padre busca a la izquierda
        if (element.compareTo(current.getData()) < 0) {
            return searchRecursive(current.getLeft(), element);

            // Si es mayor al padre busca a la derecha
        } else if (element.compareTo(current.getData()) > 0) {
            return searchRecursive(current.getRight(), element);

        } else {
            return current; // Elemento encontrado
        }
    }

    // Metodo para eliminar un elemento del arbol AVL
    public void AVLDelete(T element) {
        root = deleteRecursive(root, element);
    }

    // Metodo recursivo para eliminar un elemento
    private Node<T> deleteRecursive(Node<T> node, T element) {
        // 1. Realiza la eliminación normal en un árbol binario de búsqueda
        if (node == null) {
            return null; // El elemento no se encuentra
        }

        if (element.compareTo(node.getData()) < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), element));
        } else if (element.compareTo(node.getData()) > 0) {
            node.setRight(deleteRecursive(node.getRight(), element));
        } else {
            // Nodo con un solo hijo o sin hijo
            if (node.getLeft() == null) {
                return node.getRight(); // Retorna el hijo derecho o null
            } else if (node.getRight() == null) {
                return node.getLeft(); // Retorna el hijo izquierdo o null
            }

            // Nodo con dos hijos: obtener el sucesor inorden (el más pequeño en el subárbol derecho)
            node.setData(getMinValueNode(node.getRight()).getData()); // Copia el valor del sucesor inorden
            node.setRight(deleteRecursive(node.getRight(), node.getData())); // Elimina el sucesor inorden
        }

        // Si el árbol tenía un solo nodo, retorna
        if (node == null) {
            return null;
        }

        // 2. Actualiza la altura del nodo actual
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);

        // 3. Obtiene el factor de balance
        int balance = getBalanceFactor(node);

        // 4. Si el nodo se desbalancea, hay 4 casos

        // Caso Izquierda Izquierda
        if (balance > 1 && getBalanceFactor(node.getLeft()) >= 0) {
            return rotateRight(node);
        }

        // Caso Izquierda Derecha
        if (balance > 1 && getBalanceFactor(node.getLeft()) < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // Caso Derecha Derecha
        if (balance < -1 && getBalanceFactor(node.getRight()) <= 0) {
            return rotateLeft(node);
        }

        // Caso Derecha Izquierda
        if (balance < -1 && getBalanceFactor(node.getRight()) > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    // Metodo para obtener el nodo con el valor minimo en un sub arbol
    private Node<T> getMinValueNode(Node<T> node) {
        Node<T> current = node;

        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current;
    }

    // Obtener la altura del nodo
    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    // Obtener el factor de balance
    private int getBalanceFactor(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getRight()) - getHeight(node.getLeft());
    }

    // Rotar a la derecha
    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.getLeft();
        Node<T> T2 = x.getRight();

        // Rotar
        x.setRight(y);
        y.setLeft(T2);

        // Actualizar alturas
        y.setHeight(Math.max(getHeight(y.getLeft()), getHeight(y.getRight())) + 1);
        x.setHeight(Math.max(getHeight(x.getLeft()), getHeight(x.getRight())) + 1);

        return x;
    }

    // Rotar a la izquierda
    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.getRight();
        Node<T> T2 = y.getLeft();

        // Rotar
        y.setLeft(x);
        x.setRight(T2);

        // Actualizar alturas
        x.setHeight(Math.max(getHeight(x.getLeft()), getHeight(x.getRight())) + 1);
        y.setHeight(Math.max(getHeight(y.getLeft()), getHeight(y.getRight())) + 1);

        return x;
    }


    public String preOrder(){

        if(root == null){
            return  "AVL is empty";
        } else {
            return preOrderRecursive(root);
        }
    }

    private String preOrderRecursive(Node<T> current) {
        if (current == null) {
            return ""; // Caso base: Si current nulo, no se agrega nada.
        }

        // Pedimos primero la raiz y primero vamos por el nodo izquierdo y luego por el derecho
        // Imprimimos el valor y el factor de balanceo
        return "(" + current.getData() + "," + getBalanceFactor(current) + ")" +
                preOrderRecursive(current.getLeft()) + preOrderRecursive(current.getRight());
    }
}
