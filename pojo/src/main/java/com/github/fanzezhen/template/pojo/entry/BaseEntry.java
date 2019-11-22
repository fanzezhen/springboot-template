package com.github.fanzezhen.template.pojo.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 公共Model,将每个表都有的公共字段抽取出来
 *
 * @ MappedSuperclass注解表示不是一个完整的实体类，将不会映射到数据库表，但是它的属性都将映射到其子类的数据库字段中
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "create_user_id")
    private Long createUserId;

    public void init(BaseEntry baseEntry){
        this.createTime = baseEntry.getCreateTime();
        this.createUserId = baseEntry.getCreateUserId();
    }
}
