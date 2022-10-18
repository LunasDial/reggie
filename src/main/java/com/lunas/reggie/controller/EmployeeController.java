package com.lunas.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lunas.reggie.commom.R;
import com.lunas.reggie.entity.Employee;
import com.lunas.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        /**
         * 1.将页面提交的密码进行MD5加密
         * 2.根据用户名查数据库
         * 3.查不到返回登录失败结果
         * 4.比对密码
         * 5.查看员工状态
         * 6.登录成功将员工的ID放入session中
         */

        //1.将页面提交的密码进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据用户名查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.查不到返回登录失败结果
        if(emp==null){
            return R.error("登录失败！无此用户");
        }
        //4.比对密码
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败！密码错误  ");
        }
        //5.查看员工状态
        if (emp.getStatus() == 0) {
            return R.error("登录失败！用户已被禁用");
        }
        // 6.登录成功将员工的ID放入session中
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);

    }

    //退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 去除session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工信息：{}", employee.toString());
         //设置默认密码为123456 并进行MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 设置创建时间
//        employee.setCreateTime(LocalDateTime.now());
//        // 设置更新时间
//        employee.setUpdateTime(LocalDateTime.now());
//        // 用户ID设置（session中取得）
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
//        // 调用存储方法
        employeeService.save(employee);
        return R.success("添加成功");
    }

    /**
     * 员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name) {
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> query = new LambdaQueryWrapper();
        //添加过滤条件
        query.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        query.orderByDesc(Employee::getUpdateTime);
        //执行查询

        employeeService.page(pageInfo, query);


        return R.success(pageInfo);
    }


    /**
     * 根据用户ID去修改用户状态
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){

        log.info(employee.toString());
        // 获取员工ID
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);

        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工对象
     * @param id
     * @return
     */

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工对象");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没查到对应员工信息");

    }






}
