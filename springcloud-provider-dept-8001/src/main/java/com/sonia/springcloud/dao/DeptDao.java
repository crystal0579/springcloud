package com.sonia.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.sonia.springcloud.pojo.Dept;

import java.util.List;

@Mapper
@Repository
public interface DeptDao {
    public boolean addDept(Dept dept);
    public Dept queryById(Integer id);
    public List<Dept> queryAll();
}
