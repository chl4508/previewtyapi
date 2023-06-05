package com.morpheus.previewtyapi.vo.v2;

import lombok.Data;

@Data
public class CommonVO {

    private int pageStart;
    private int pageEnd;
    private int pageNo;
    private int pageNum;

    public CommonVO(){}
    public void paging(int pageNo, int pageNum){
        CommonVO commonVO = new CommonVO();
        int pageStart = pageNum * (pageNo - 1) + 1;
        int pageEnd = pageNum * pageNo;
        this.setPageStart(pageStart);
        this.setPageEnd(pageEnd);
        this.setPageNo(pageNo);
        this.setPageNum(pageNum);
    }

}
