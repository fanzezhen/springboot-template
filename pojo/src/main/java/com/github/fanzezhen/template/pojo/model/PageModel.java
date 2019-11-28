package com.github.fanzezhen.template.pojo.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.github.fanzezhen.util.DateUtil;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageModel<T> extends Page<T> {
    private T param;
    private String startDate;
    private String endDate;

    public void setStartDate(String startDate) {
        this.startDate = DateUtil.addTimeToDateString(startDate, "00:00:00");
    }

    public void setEndDate(String endDate) {
        this.endDate = DateUtil.addTimeToDateString(endDate, "23:59:59");
    }

}
