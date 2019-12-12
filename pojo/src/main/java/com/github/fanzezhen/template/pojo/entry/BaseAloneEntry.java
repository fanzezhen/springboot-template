package com.github.fanzezhen.template.pojo.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 公共Model,将每个表都有的公共字段抽取出来
 *
 * @ MappedSuperclass注解表示不是一个完整的实体类，将不会映射到数据库表，但是它的属性都将映射到其子类的数据库字段中
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAloneEntry extends BaseEntry implements Serializable {
    private Integer status; //0--正常；1--禁用
    @Column(name = "del_flag")
    private Integer delFlag; //1-已删除；0-未删除
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "update_user_id")
    private String updateUserId;

    public void init(BaseAloneEntry baseAlonePo){
        this.status = baseAlonePo.getStatus();
        this.delFlag = baseAlonePo.getDelFlag();
        this.updateTime = baseAlonePo.getUpdateTime();
        this.updateUserId = baseAlonePo.getUpdateUserId();
        super.init(new BaseEntry(baseAlonePo.getId(), baseAlonePo.getCreateTime(), baseAlonePo.updateUserId));
    }
}
