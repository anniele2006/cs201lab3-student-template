import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     public static String verify(String traversal1, String traversal2, String traversal3){
          int[][] traversals = {
               Arrays.stream(traversal1.split("-")).mapToInt(Integer::parseInt).toArray(),
               Arrays.stream(traversal2.split("-")).mapToInt(Integer::parseInt).toArray(),
               Arrays.stream(traversal3.split("-")).mapToInt(Integer::parseInt).toArray()
          };
          int n = traversals[0].length;

          // traversal lengths dont match
          if (traversals[1].length != n || traversals[2].length != n) {
               return "Invalid traversals";
          }

          // try each input as the preorder traversal, if its valid cross check the other 2
          for (int p = 0; p < 3; p++) {
               int[] preorder = traversals[p];
               int[] inorder = new int[n];
               int[] postorder = new int[n];

               // positions for reading preorder writing inorder postorder.
               int[] indices = {0, 0, 0};
               generateTraversals(preorder, inorder, postorder, indices, Long.MIN_VALUE, Long.MAX_VALUE);

               // valid BST preorder has every value
               if (indices[0] != n) {
                    continue;
               }

               // indices of remaining two inputs
               int a = (p + 1) % 3;
               int b = (p + 2) % 3;

               // a is inorder, b is postorder.
               if (Arrays.equals(traversals[a], inorder) && Arrays.equals(traversals[b], postorder)) {
                    return formatResult(p, a, b);
               }

               // b is inorder, a is postorder.
               if (Arrays.equals(traversals[b], inorder) && Arrays.equals(traversals[a], postorder)) {
                    return formatResult(p, b, a);
               }
          }

          return "Invalid traversals";
     }

     private static void generateTraversals(int[] preorder, int[] inorder, int[] postorder, int[] indices, long lower, long upper) {
          if (indices[0] == preorder.length) {
               return;
          }
          int value = preorder[indices[0]];
          // leave this value for a subtree whose bounds allow it
          if (value <= lower || value >= upper) {
               return;
          }
          indices[0]++; // add subtrees root

          // left subtree has values between lower and value
          generateTraversals(preorder, inorder, postorder, indices, lower, value);

          // L P R
          inorder[indices[1]++] = value;

          // right subtree values between upper and value
          generateTraversals(preorder, inorder, postorder, indices, value, upper);

          // L R P
          postorder[indices[2]++] = value;
     }

     private static String formatResult(int preorderIndex, int inorderIndex, int postorderIndex) {
          String[] orders = new String[3];
          orders[preorderIndex] = "Preorder";
          orders[inorderIndex] = "Inorder";
          orders[postorderIndex] = "Postorder";
          return "Traversal 1 - " + orders[0] + ", Traversal 2 - " + orders[1] + ", Traversal 3 - " + orders[2];
     }

}