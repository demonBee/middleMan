package com.chinaunicom.sirbee.utils;

/**
 * @author hjf
 * @date 2019/2/25 15:29
 * @Description
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 递归封装树形菜单
 */
public class Tree {
//    List<Menu> nodes = new ArrayList<Menu>();
//
//    public Tree(List<Menu> nodes) {
//        super();
//        this.nodes = nodes;
//    }
//
//    /**
//     * 构建树形结构
//     *
//     * @return
//     */
//    public List<Menu> buildTree() {
//        List<Menu> treeNodes = new ArrayList<Menu>();
//        List<Menu> rootNodes = getRootNodes();
//        for (Menu rootNode : rootNodes) {
//            buildChildNodes(rootNode);
//            treeNodes.add(rootNode);
//        }
//        return treeNodes;
//    }
//
//    /**
//     * 递归子节点
//     *
//     * @param node
//     */
//    public void buildChildNodes(Menu node) {
//        List<Menu> children = getChildNodes(node);
//        if (!children.isEmpty()) {
//            for (Menu child : children) {
//                buildChildNodes(child);
//            }
//            node.setChildren(children);
//        }
//    }
//
//    /**
//     * 获取父节点下所有的子节点
//     *
//     * @param
//     * @param pnode
//     * @return
//     */
//    public List<Menu> getChildNodes(Menu pnode) {
//        List<Menu> childNodes = new ArrayList<Menu>();
//        for (Menu n : nodes) {
//            if (pnode.getMenuId().equals(n.getParentId())) {
//                childNodes.add(n);
//            }
//        }
//        return childNodes;
//    }
//
//    /**
//     * 判断是否为根节点
//     *
//     * @param
//     * @param
//     * @return
//     */
//    public boolean rootNode(Menu node) {
//        boolean isRootNode = true;
//        for (Menu n : nodes) {
//            if (node.getParentId().equals(n.getMenuId())) {
//                isRootNode = false;
//                break;
//            }
//        }
//        return isRootNode;
//    }
//
//    /**
//     * 获取集合中所有的根节点
//     *
//     * @param
//     * @return
//     */
//    public List<Menu> getRootNodes() {
//        List<Menu> rootNodes = new ArrayList<Menu>();
//        for (Menu n : nodes) {
//            if (rootNode(n)) {
//                rootNodes.add(n);
//            }
//        }
//        return rootNodes;
//    }
}
