import java.util.stream.IntStream;

class StickyHeader {
    int stickyHeader(int pageYOffset, int tableOffsetTop, int tableHeight, int[] breadCrumbs) {

        int totalBreadCrumbsHeight = IntStream.of(breadCrumbs).sum();
        pageYOffset += totalBreadCrumbsHeight;

        if (tableOffsetTop > pageYOffset || tableOffsetTop + tableHeight < pageYOffset) {
            return -1;
        }

        return totalBreadCrumbsHeight;
    }
}
