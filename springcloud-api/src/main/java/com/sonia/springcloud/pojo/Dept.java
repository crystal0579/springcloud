package com.sonia.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)//链式写法
@NoArgsConstructor
public class Dept implements Serializable {
    private Integer deptno;
    private String deptName;
    /**
     * 一个服务对应一个数据库，同一个信息可能存在于不同的数据库
     */
    private String dbsource;

    public Dept(String deptName){
        this.deptName = deptName;
    }

}
