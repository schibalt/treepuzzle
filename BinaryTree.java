package treepuzzle;

import java.util.ArrayList;
import java.util.List;

/**
COPYRIGHT (C) 2012 Andrew Tilisky. All Rights Reserved.
 * binary search tree class,
 * similar add and search functions, publically accessible functions to
 * reference them, creation method and inorder traversal for debugging.
 * edit ap. 3 2012: avl verstion
@author Andrew Tilisky
 */
public class BinaryTree
{
    private Node root;
    private int heightcount;
    private int[][] heighttable;
    private int dictsize;

    /**
     * @return the heightcount
     */
    public int getHeightcount()
    {
        return heightcount;
    }

    //words are added iteratively and the root's checked and rebalanced as needed
    public void createTree(List<String> dict, boolean debug)
    {
        for (String word : dict)
        {
            if (root != null)
            {
                add(root, word.toUpperCase(), debug);
                root = rebalance(root);
            } else
            {
                root = new Node(word.toUpperCase(), null);
            }
        }
    }

    public void inorder()
    {
        inorder(root);
    }

    //used for debugging purposes.  does an inorder traversal to show all dictionary
    // nodes are actually in the tree or depending on configuration which nodes 
    // aren't balanced
    private void inorder(Node node)
    {
        if (node == null)
        {
            node = root;
        }

        if (node.hasLeft())
        {
            inorder(node.getLeft());
        }
//        System.out.println(node.getData());

        int heightdiff = getHeightDifference(node, false);

        if (heightdiff > 1 || heightdiff < -1)
        {
            System.out.println(node.getData() + " isn't balanced");
        }
        if (node.hasRight())
        {
            inorder(node.getRight());
        }
    }

//    public int height(boolean debug)
    public int height()
    {
        return height(root);
//        return height(root, debug);
    }

//    private int height(Node node, boolean debug)
    private int height(Node node)
    {
        heightcount++;
        int leftheight = 0;
        int rightheight = 0;

        if (node.hasLeft())
        {
            leftheight = height(node.getLeft());
//            leftheight = height(node.getLeft(), debug);
        }
        if (node.hasRight())
        {
            rightheight = height(node.getRight());
//            rightheight = height(node.getRight(), debug);
        }
//        if (debug)
//        {
//            System.out.println(node.getData() + " lheight "
//                    + leftheight + " rheight " + rightheight);
//        }
        return 1 + Math.max(leftheight, rightheight);
    }

    public boolean search(String word)
    {
        return search(root, word);
    }

    private boolean search(Node node, String word)
    {
        boolean found = false;

        if (node.getData() != null)
        {
            if (word.equals(node.getData()))
            {
                found = true;
            }
            if (!found)
            {
                if (word.compareTo(node.getData()) < 0)
                {
                    if (node.getLeft() != null)
                    {
                        found = search(node.getLeft(), word);
                    }
                } else
                {
                    if (node.getRight() != null)
                    {
                        found = search(node.getRight(), word);
                    }
                }
            }
        }

        return found;
    }

    private void add(Node toAddTo, String word, boolean debug)
    {
        if (word.compareTo((toAddTo.getData())) > 0)
        {
            // either set the node's right node to one with the word
            // as its key
            if (toAddTo.getRight() == null)
            {
                toAddTo.setRight(new Node(word, toAddTo));
                // or pass the word to the right node for addition
            } else
            {
                add(toAddTo.getRight(), word, debug);
                toAddTo.setRight(rebalance(toAddTo.getRight()));
            }
//                toAddTo.setRight(rebalance(toAddTo.getRight(), debug));
        } else
        {
            if (toAddTo.getLeft() == null)
            {
                toAddTo.setLeft(new Node(word, toAddTo));
            } else
            {
                add(toAddTo.getLeft(), word, debug);
                toAddTo.setLeft(rebalance(toAddTo.getLeft()));
            }
//                toAddTo.setLeft(rebalance(toAddTo.getLeft(), debug));
        }
    }

    // this is for insting the word->height hashtable
    public void setDict(ArrayList<String> dict)
    {
        dictsize = dict.size();
        // inst dict to matrix length == dictsize w/2 columns (1st for hash
//2nd for height of tree where rootdata == word hashed into col #1)
        //this is hashtable that stores the heights of subtrees
        //readit's faster than programmatically looking up the height.
        this.heighttable = new int[dictsize][2];
    }

    private Node rotateRight(Node oldRoot)
    {
        Node result = oldRoot.getLeft();

        oldRoot.setLeft(result.getRight());
        result.setRight(oldRoot);
        return result;
    }

    private Node rotateLeftRight(Node node)
    {
        Node child = node.getLeft();
        node.setLeft(rotateLeft(child));
        return rotateRight(node);
    }

    private Node rotateLeft(Node oldRoot)
    {
        Node result = oldRoot.getRight();

        oldRoot.setRight(result.getLeft());
        result.setLeft(oldRoot);
        return result;
    }

    private Node rotateRightLeft(Node oldRoot)
    {
        Node result = oldRoot.getRight();
        oldRoot.setRight(rotateRight(result));
        return rotateLeft(oldRoot);
    }

//    private int getHeightDifference(Node node, boolean debug, boolean useTable)
    private int getHeightDifference(Node node, boolean useTable)
    {
        int leftheight = 0;
        int rightheight = 0;

        //this logic is for checking the hastabel instead of calling height.
        //the point is to avoid all the recursive invokations but ultimately
        //wasn't utilized
//        if (useTable)
//        {
//            if (node.hasLeft())
//            {
//                leftheight = heighttable[Math.abs(node.getLeft().getData().hashCode() % dictsize)][1];
//            }
//            if (node.hasRight())
//            {
//                rightheight = heighttable[Math.abs(node.getRight().getData().hashCode() % dictsize)][1];
//            }
//        } else
//        {
        if (node.hasLeft())
        {
            leftheight = height(node.getLeft());
//                leftheight = height(node.getLeft(), debug);
            heighttable[Math.abs(node.getLeft().getData().hashCode() % dictsize)][1] = leftheight;
        }
        if (node.hasRight())
        {
            rightheight = height(node.getRight());
//                rightheight = height(node.getRight(), debug);
            heighttable[Math.abs(node.getRight().getData().hashCode() % dictsize)][1] = rightheight;
        }
//        }
        return leftheight - rightheight;
    }

//    private Node rebalance(Node toBalance, boolean debug)
    private Node rebalance(Node toBalance)
    {
        int heightDiff = getHeightDifference(toBalance, false);
//        int heightDiff = getHeightDifference(toBalance, debug, false);
        // if the left's more than one level deeper
        if (heightDiff > 1)
        {
            // and that's because its left node's deeper than its right
//            if (getHeightDifference(toBalance.getLeft(), debug, true) > 0)
            if (getHeightDifference(toBalance.getLeft(), false) > 0)
            {
                toBalance = rotateRight(toBalance);
                //and that's because its right node's deeper
            } else
            {
                toBalance = rotateLeftRight(toBalance);
            }
        } else // the right's deeper
        if (heightDiff < -1)
        {
//            if (getHeightDifference(toBalance.getRight(), debug, true) < 0)
            if (getHeightDifference(toBalance.getRight(), false) < 0)
            {
                toBalance = rotateLeft(toBalance);
            } else
            {
                toBalance = rotateRightLeft(toBalance);
            }
        }
        return toBalance;
    }
}
