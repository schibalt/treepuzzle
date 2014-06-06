package treepuzzle;

/**
 *
 * @author Andrew Tilisky
 */
public class Node
{
    private Node left;
    private Node right;
    private int height;
    private String data;
//        private Node parent;

    public Node(String data, Node parent)
    {
        this.data = data;
//            this.parent = parent;
    }

    /**
     * @return the left
     */
    public Node getLeft()
    {
        return left;
    }

    /**
     * @return the right
     */
    public Node getRight()
    {
        return right;
    }

    /**
     * @return the data
     */
    public String getData()
    {
        return data;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(Node left)
    {
        this.left = left;
    }

    /**
     * @param right the right to set
     */
    public void setRight(Node right)
    {
        this.right = right;
    }

    public boolean hasRight()
    {
        if (right != null)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public boolean hasLeft()
    {
        if (left != null)
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
}
