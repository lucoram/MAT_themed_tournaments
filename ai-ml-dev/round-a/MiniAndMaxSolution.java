
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MiniAndMaxSolution {

    static List<TreeNode[]> nodeValues;
    static List<String> winResList;
    static List<String> drawResList;
    static StringBuilder resBuilder;

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        String leavesValues = scan.nextLine();
        int leavesLen = leavesValues.length();

        nodeValues = new ArrayList<>();

        TreeNode[] leavesScore = new TreeNode[leavesLen];
        nodeValues.add(leavesScore);

        for (int i = 0; i < leavesLen; i++) {
            char c = leavesValues.charAt(i);
            if (c == 'W') {
                leavesScore[i] = new TreeNode(1);
            }
            if (c == 'L') {
                leavesScore[i] = new TreeNode(-1);
            }
            if (c == 'D') {
                leavesScore[i] = new TreeNode(0);
            }
        }

        boolean maximizing = true;
        int levelCount = 1;

        for (int i = 2; i < leavesLen; i *= 2) {
            maximizing = !maximizing;
            levelCount++;
        }

        TreeNode[] lastLevel = leavesScore;

        for (int c = 0; c < levelCount; c++) {
            int newLevelLen = lastLevel.length / 2;
            TreeNode[] newLevelValues = new TreeNode[newLevelLen];

            for (int i = 0, j = 0; i < newLevelLen; i++, j += 2) {

                TreeNode newNode = new TreeNode(0);
                newLevelValues[i] = newNode;

                newNode.val = maximizing ? Math.max(lastLevel[j].val, lastLevel[j + 1].val)
                        : Math.min(lastLevel[j].val, lastLevel[j + 1].val);
                newNode.left = lastLevel[j];
                newNode.right = lastLevel[j + 1];
            }

            maximizing = !maximizing;
            nodeValues.add(newLevelValues);
            lastLevel = newLevelValues;
        }

        resBuilder = new StringBuilder("");
        winResList = new ArrayList<>();
        drawResList = new ArrayList<>();

        startDFS(nodeValues.get(levelCount)[0]);

        if (!winResList.isEmpty()) {
            Collections.sort(winResList);
            System.out.println("WINS");
            for (String res : winResList) {
                System.out.println(res);
            }
        } else if (!drawResList.isEmpty()) {
            Collections.sort(drawResList);
            System.out.println("DRAWS");
            for (String res : drawResList) {
                System.out.println(res);
            }
        } else {
            System.out.println("LOSES");
        }
    }

    private static void startDFS(TreeNode treeNode) {
        if (treeNode.left == null && treeNode.right == null) {
            if (treeNode.val == 1) {
                winResList.add(resBuilder.toString());
            } else if (treeNode.val == 0) {
                drawResList.add(resBuilder.toString());
            }

            return;
        }

        int leftVal = treeNode.left.val;
        int rightVal = treeNode.right.val;

        if (rightVal >= 0) {
            resBuilder.append("R");
            startDFS(treeNode.right);
            resBuilder.deleteCharAt(resBuilder.length() - 1);
        }
        if (leftVal >= 0) {
            resBuilder.append("L");
            startDFS(treeNode.left);
            resBuilder.deleteCharAt(resBuilder.length() - 1);
        }
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}
