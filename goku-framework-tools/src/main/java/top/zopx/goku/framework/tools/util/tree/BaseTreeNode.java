package top.zopx.goku.framework.tools.util.tree;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/18 10:28
 */
public abstract class BaseTreeNode<T extends Serializable, E extends BaseTreeNode<T,E>> implements Serializable {

    private T id;

    private T parentId;

    private List<E> children;

    // ********************** //
    public boolean isParentNode() {
        return StringUtils.equals("0", parentId.toString());
    }

    public boolean isLeafNode(E root) {
        return StringUtils.equals(root.getId().toString(), this.parentId.toString());
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getParentId() {
        return parentId;
    }

    public void setParentId(T parentId) {
        this.parentId = parentId;
    }

    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }
}
