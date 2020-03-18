package com.sonia.springcloud.service;

import com.sonia.springcloud.pojo.Dept;

import java.util.List;

public interface DeptService {
    public boolean addDept(Dept dept);
    public Dept queryById(Integer id);
    public List<Dept> queryAll();
}
