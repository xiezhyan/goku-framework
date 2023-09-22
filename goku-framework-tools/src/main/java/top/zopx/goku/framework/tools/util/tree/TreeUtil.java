package top.zopx.goku.framework.tools.util.tree;

import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 通用生成Tree结构 平均比反射性能提高一倍左右
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/6/18 10:32
 */
public final class TreeUtil {

    private TreeUtil() {
    }

    public static <T extends Serializable, E extends BaseTreeNode<T, E>> void toTree(
            Collection<E> data, Collection<E> resultData
    ) {
        if (CollectionUtils.isEmpty(data)) {
            return;
        }

        for (Iterator<E> iterator = data.iterator(); iterator.hasNext(); ) {
            E next = iterator.next();
            if (next.isParentNode()) {
                resultData.add(next);
                iterator.remove();
            }
        }

        resultData.forEach(item -> item.setChildren(buildChildren(item, data)));
    }

    private static <T extends Serializable, E extends BaseTreeNode<T, E>> List<E> buildChildren(
            E root, Collection<E> data
    ) {
        List<E> children = new ArrayList<>();

        if (CollectionUtils.isEmpty(data)) {
            return children;
        }

        for (Iterator<E> iterator = data.iterator(); iterator.hasNext(); ) {
            E next = iterator.next();
            if (next.isLeafNode(root)) {
                children.add(next);
                iterator.remove();
            }
        }

        children.forEach(item -> item.setChildren(buildChildren(item, data)));
        return children;
    }

    public static void main(String[] args) {
        TreeNodeVO treeNodeVO = new TreeNodeVO();
        toTree(Collections.singleton(treeNodeVO), new ArrayList<>());
    }

    private static class TreeNodeVO extends BaseTreeNode<Long, TreeNodeVO> {

    }
}
