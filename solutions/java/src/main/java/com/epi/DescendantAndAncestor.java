package com.epi;

import com.epi.BinarySearchTreePrototypeTemplate.BSTNode;

public class DescendantAndAncestor {
  // @include
  public static boolean isRSDescendantAncestorPairForM(
      BSTNode<Integer> ancDes0, BSTNode<Integer> ancDes1, BSTNode<Integer> middle) {
    BSTNode<Integer> curAncDes0 = ancDes0, curAncDes1 = ancDes1;

    // Perform interleaved searching from ancDes0 and ancDes1 for middle.
    while (curAncDes0 != null && curAncDes0 != ancDes1 && curAncDes0 != middle
           && curAncDes1 != null && curAncDes1 != ancDes0 
           && curAncDes1 != middle) {
      curAncDes0 = curAncDes0.getData() > middle.getData() 
                   ? curAncDes0.getLeft() : curAncDes0.getRight();
      curAncDes1 = curAncDes1.getData() > middle.getData() ? 
                   curAncDes1.getLeft() : curAncDes1.getRight();
    }

    // If both searches were unsuccessful, or we got from ancDes0 to ancDes1 
    // without seeing middle, or from ancDes1 to ancDes0 without seeing middle,
    // middle cannot lie between ancDes0 and ancDes1.
    if (curAncDes0 == ancDes1 || curAncDes1 == ancDes0 
        || (curAncDes0 != middle && curAncDes1 != middle)) {
      return false;
    }

    // If we get here, we already know one of ancDes0 or ancDes1 has a path to 
    // middle. Check if middle has a path to ancDes1 or to ancDes0.
    return curAncDes0 == middle
           ? searchTarget(middle, ancDes1) : searchTarget(middle, ancDes0);
  }

  private static boolean searchTarget(
      BSTNode<Integer> from, BSTNode<Integer> target) {
    while (from != null && from != target) {
      from = from.getData() > target.getData() 
               ? from.getLeft() : from.getRight();
    }
    return from == target;
  }
  // @exclude

  private static void smallTest() {
    BSTNode<Integer> root = new BSTNode<>(5);
    root.setLeft(new BSTNode<>(2));
    root.getLeft().setRight(new BSTNode<>(4));
    assert (!isRSDescendantAncestorPairForM(root, root.getLeft(),
                                            root.getLeft().getRight()));

    root = new BSTNode<>(4);
    root.setLeft(new BSTNode<>(2));
    root.getLeft().setLeft(new BSTNode<>(1));
    root.getLeft().setRight(new BSTNode<>(3));
    root.setRight(new BSTNode<>(6));
    root.getRight().setLeft(new BSTNode<>(5));
    root.getRight().setRight(new BSTNode<>(7));
    assert (!isRSDescendantAncestorPairForM(root.getRight(), root.getLeft(),
                                            root.getRight().getLeft()));
  }

  public static void main(String[] args) {
    smallTest();
    // 3
    // 2 5
    // 1 4 6
    BSTNode<Integer> root = new BSTNode<>(3);
    root.setLeft(new BSTNode<>(2));
    root.getLeft().setLeft(new BSTNode<>(1));
    root.setRight(new BSTNode<>(5));
    root.getRight().setLeft(new BSTNode<>(4));
    root.getRight().setRight(new BSTNode<>(6));
    assert (isRSDescendantAncestorPairForM(root, root.getRight().getRight(),
                                           root.getRight()));
    assert (isRSDescendantAncestorPairForM(root.getRight().getRight(), root,
                                           root.getRight()));
    assert (!isRSDescendantAncestorPairForM(root, root.getRight(),
                                            root.getRight().getRight()));
    assert (!isRSDescendantAncestorPairForM(root.getRight(), root,
                                            root.getRight().getRight()));
    assert (!isRSDescendantAncestorPairForM(root.getRight().getLeft(),
                                            root.getRight().getRight(),
                                            root.getRight()));
    assert (!isRSDescendantAncestorPairForM(root.getRight().getLeft(),
                                            root.getLeft().getLeft(),
                                            root.getRight()));
  }
}
