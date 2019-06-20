package entity;

import java.io.Serializable;
import java.util.List;

//分页结果类,用于封装查询分页的 总记录数和当前页的记录
public class PageResult implements Serializable {

    //总记录数
    private long total;
    //当前页记录
    private List rows;

    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
