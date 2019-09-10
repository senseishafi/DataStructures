

import java.util.LinkedList;

import java.util.LinkedList;
import java.util.Map;

public class UnbalancedMap<K extends Comparable<K>, V> implements IMap<K,V> {
    public class Node<K extends Comparable<K>, V> {
        K key;
        V value;
        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        Node left;
        Node right;
        Node parent;
    }


    protected Node<K,V> root;
    Node<K,V> parent;
    protected LinkedList<Node> nodelist = new LinkedList<>();

    public UnbalancedMap(){}
    public UnbalancedMap(IMap<K,V> iMap) {
        for(K key: iMap.keyset()) {
            add(key, iMap.getValue(key));
        }
    }
    @Override
    public boolean contains(K key) {
        return check(key, root);
    }
    private boolean check(K key, Node<K,V> node) {
        if(node == null) {return false;}
        if(((Comparable<K>) key).compareTo(node.key) < 0) {
            return check(key,node.left);
        }
        else if(((Comparable<K>) key).compareTo(node.key) > 0) {
            return check(key, node.right);
        }
        else{
            return true;
        }
    }

    @Override
    public boolean add(K key, V value) {
        if(contains(key)) {return false;}
        if(root == null) {
            root = new Node<K,V>(key, value);
            nodelist.add(root);
        }
        else{
            insert(key, value, root, null,false);
        }
        return true;
    }

    private void insert(K key, V value, Node<K,V> node, Node<K, V> parent, boolean lefty) {
        if(node == null) {
            if(lefty) {
                parent.left = new Node<K,V>(key, value);
                nodelist.add(parent.left);
            }
            else {
                parent.right = new Node<K,V>(key, value);
                nodelist.add(parent.right);
            }

        }
        else if(((Comparable<K>)key).compareTo((K)node.key) < 0) {
            insert(key, value, node.left, node, true);
        }
        else {
            insert(key, value, node.right, node, false);
        }
    }

    @Override
    public V delete(K key) {
        if(!(contains(key))) {return null;}

        Node<K, V> deleteNode = find(key, root, null);
        V deleteValue = deleteNode.value;

        if(deleteNode.left == null && deleteNode.right == null) {
            nodelist.remove(deleteNode);
            deleteNode = null;
            return deleteValue;
        }

        K keytemp;
        V valuetemp;

        if(deleteNode.right != null && deleteNode.left == null){

            if(parent == null){
                if(root.left == deleteNode) {
                    root.value = (V) root.left.value;
                    root.key = (K) root.left.key;
                    delete((K) root.left.key);
                }
                if(root.right == deleteNode) {
                    root.value = (V) root.right.value;
                    root.key = (K) root.right.key;
                    delete((K) root.right.key);









                }
                nodelist.remove(deleteNode);
                return deleteValue;
            }



            if(parent.left == deleteNode) {
                parent.left = deleteNode.right;
                nodelist.remove(deleteNode);
            }

            if(parent.right == deleteNode) {
                parent.right = deleteNode.right;
                nodelist.remove(deleteNode);
            }
            return deleteValue;
        }

        if(deleteNode.right == null && deleteNode.left != null){


            if(parent == null){
                if(root.left == deleteNode) {
                    root.value = (V) root.left.value;
                    root.key = (K) root.left.key;
                    delete((K) root.left.key);
                }

                if(root.right == deleteNode) {
                    root.value = (V) root.right.value;
                    root.key = (K) root.right.key;
                    delete((K) root.right.key);









                }

                nodelist.remove(deleteNode);
                return deleteValue;
            }




            if(parent.right == deleteNode) {
                parent.right = deleteNode.left;
                nodelist.remove(deleteNode);
            }
            if(parent.left == deleteNode) {
                parent.left = deleteNode.left;
                nodelist.remove(deleteNode);
            }
            return deleteValue;
        }

        Node<K,V> foundNode = findRight(deleteNode);


       V value1 = foundNode.value;
        K key1 = foundNode.key;

        delete(foundNode.key);

        deleteNode.value = value1;
        deleteNode.key = key1;







        return deleteValue;











    }

    public Node findRight(Node<K,V> node) {
        Node closest = node.right;
        while(closest.left != null) {
            closest = closest.left;
        }
        return closest;
    }

    @Override
    public V getValue(K key) {
        return (V) (find(key, root, null).value);
    }

    public Node find(K key, Node <K,V> node, Node<K,V> parent){

        if(node == null) {return null;}
        if(((Comparable<K>) key).compareTo(node.key) < 0) {
            return (Node) find(key, node.left, node);
        }
        else if(((Comparable<K>) key).compareTo(node.key) > 0) {
            return (Node) find(key, node.right, node);
        }
        else{
            this.parent = parent;
            return node;

        }
    }

    @Override
    public K getKey(V value) {
        for(Node node: nodelist) {
            if(((Comparable <V>) node.value).compareTo(value) == 0) {
                return (K) node.key;
            }
        }
        return null;
    }


    @Override
    public Iterable<K> getKeys(V value) {
        LinkedList<K> allkeys = new LinkedList<>();

        for(Node<K,V> node : nodelist){
            if(((Comparable <V>) node.value).compareTo(value) == 0){
                allkeys.add(node.key);
            }
        }
        return allkeys;
    }

    @Override
    public int size() {
        return nodelist.size();
    }

    @Override
    public boolean isEmpty() {
        if(root == null){return true;}
        return false;
    }

    @Override
    public void clear() {
        root = null;
        nodelist.clear();
    }

    @Override
    public Iterable<K> keyset() {
        LinkedList<K> keyList  = new LinkedList<>();
        for(Node node: nodelist) {
            keyList.add((K) node.key);
        }
        return keyList;
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> valueList = new LinkedList<>();

        for(Node node: nodelist) {
            valueList.add((V) node.value);
        }
        return valueList;
    }




}
