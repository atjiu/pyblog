package cn.tomoya.model;

import java.util.List;

/**
 * Created by tomoya on 2017/7/10.
 */
public class Page {

  private int pageNo;
  private int PageSize;
  private List<Blog> list;
  private boolean firstPage;
  private boolean lastPage;
  private int totalPage;
  private int totalCount;

  public Page(int pageNo, int pageSize, int totalCount, List<Blog> list) {
    this.pageNo = pageNo;
    PageSize = pageSize;
    this.list = list;
    this.totalCount = totalCount;

    if(totalCount % pageSize == 0) {
      this.totalPage = totalCount / pageSize;
    } else {
      this.totalPage = totalCount / pageSize + 1;
    }

    if(pageNo == 1) this.firstPage = true;

    if(this.totalPage == pageNo) this.lastPage = true;
  }

  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public int getPageSize() {
    return PageSize;
  }

  public void setPageSize(int pageSize) {
    PageSize = pageSize;
  }

  public List<Blog> getList() {
    return list;
  }

  public void setList(List<Blog> list) {
    this.list = list;
  }

  public boolean isFirstPage() {
    return firstPage;
  }

  public void setFirstPage(boolean firstPage) {
    this.firstPage = firstPage;
  }

  public boolean isLastPage() {
    return lastPage;
  }

  public void setLastPage(boolean lastPage) {
    this.lastPage = lastPage;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
