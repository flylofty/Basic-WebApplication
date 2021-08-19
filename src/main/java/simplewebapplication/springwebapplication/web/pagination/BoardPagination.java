package simplewebapplication.springwebapplication.web.pagination;

import java.util.ArrayList;

public class BoardPagination {

    private ArrayList<Long> pageList;
    private Long currentPage;
    private Long lastPageNumber;
    private Integer currentBoardNumber; // 현재 페이지에서 보여줄 게시물 개수

    public BoardPagination() {
    }

    public BoardPagination(long totalBoardNumber, long currentPage, int currentBoardNumber) {
        this.currentPage = currentPage;
        this.currentBoardNumber = currentBoardNumber;

        this.lastPageNumber = totalBoardNumber / this.currentBoardNumber;
        if (totalBoardNumber % this.currentBoardNumber != 0)
            this.lastPageNumber++;

        initPageList(currentPage);
    }

    private void initPageList(long currentPage) {
        pageList = new ArrayList<>();

        if (currentPage == 1) {
            for(long page = 1; page <= 5; ++page) {
                if (page <= this.lastPageNumber) {
                    pageList.add(page);
                }
            }
        }
        else if (currentPage == this.lastPageNumber) {
            for(long page = currentPage - 4; page <= currentPage; ++page)
                if (page > 0) {
                    pageList.add(page);
                }
        }
        else if (currentPage == 2) {
            for(long page = 1; page <= currentPage + 3; ++page) {
                if (page <= this.lastPageNumber)
                    pageList.add(page);
            }
        }
        else if (currentPage == this.lastPageNumber - 1) {
            for(long page = currentPage - 3; page <= this.lastPageNumber; ++page)
                if (page > 0) {
                    pageList.add(page);
                }
        }
        else {
            for(long page = this.currentPage - 2; page <= this.currentPage + 2; ++page) {
                if (0 < page && page <= this.lastPageNumber) {
                    pageList.add(page);
                }
            }
        }
    }

    public ArrayList<Long> getPageList() {
        return pageList;
    }

    public void setPageList(ArrayList<Long> pageList) {
        this.pageList = pageList;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(Long lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public Integer getCurrentBoardNumber() {
        return currentBoardNumber;
    }

    public void setCurrentBoardNumber(Integer currentBoardNumber) {
        this.currentBoardNumber = currentBoardNumber;
    }
}