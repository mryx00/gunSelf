package com.stylefeng.gunSelf.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.base.tips.CancelTip;
import com.stylefeng.gunSelf.core.base.tips.MyTip;
import com.stylefeng.gunSelf.core.base.tips.Tip;
import com.stylefeng.gunSelf.core.common.annotion.BussinessLog;
import com.stylefeng.gunSelf.core.common.annotion.Permission;
import com.stylefeng.gunSelf.core.common.constant.dictmap.DeptDict;
import com.stylefeng.gunSelf.core.common.constant.factory.ConstantFactory;
import com.stylefeng.gunSelf.core.common.exception.BizExceptionEnum;
import com.stylefeng.gunSelf.core.exception.FadpException;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import com.stylefeng.gunSelf.core.node.ZTreeNode;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.SX.service.ICustomerService;
import com.stylefeng.gunSelf.modular.system.model.Customer;
import com.stylefeng.gunSelf.modular.system.model.Dept;
import com.stylefeng.gunSelf.modular.system.model.User;
import com.stylefeng.gunSelf.modular.system.service.IDeptService;
import com.stylefeng.gunSelf.modular.system.service.IUserService;
import com.stylefeng.gunSelf.modular.system.warpper.DeptWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/system/dept/";

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICustomerService customerService;

    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @Permission
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        LogObjectHolder.me().set(dept);
        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 新增部门
     */
    @BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new FadpException(BizExceptionEnum.REQUEST_NULL);
        }
        //完善pids,根据pid拿到pid的pids
        deptSetPids(dept);
        return this.deptService.insert(dept);
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false)String condition,@RequestParam(required = false)String tips) {
        List<Map<String, Object>> list = this.deptService.list(condition,tips);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptService.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new FadpException(BizExceptionEnum.REQUEST_NULL);
        }
        deptSetPids(dept);
        deptService.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

    /**
     * 恢复部门
     */
    @Transactional(readOnly = false)
    @BussinessLog(value = "恢复部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/recovery")
    @ResponseBody
    public Object recovery(@RequestParam Integer deptId) {
        Dept dept = deptService.selectById(deptId);
        if (dept.getTips().equals("N")) {
            Tip tip = new MyTip(500,"该部门已经被恢复");
            return tip;
        }
        dept.setTips("N");
        deptService.updateById(dept);

        return SUCCESS_TIP;
    }

    /**
     * 撤销部门
     */
    @Transactional(readOnly = false)
    @BussinessLog(value = "撤销部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/cancel")
    @Permission
    @ResponseBody
    public Object cancel(@RequestParam Integer deptId) {

        Dept dept = deptService.selectById(deptId);
        dept.setTips("Y");
        List<User> users = userService.selectList(new EntityWrapper<User>().eq("deptid", deptId));
        for (User user:users) {
            user.setStatus(2);
            List<Customer> belongUsers = customerService.selectList(new EntityWrapper<Customer>().eq("belongUser", user.getId()));
            if (belongUsers.size()>0){
                return new CancelTip(user.getName());
            }
            userService.updateById(user);
        }

        deptService.updateById(dept);
        return SUCCESS_TIP;
    }



    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
            dept.setPid(0);
            dept.setPids("[0],");
        } else {
            int pid = dept.getPid();
            Dept temp = deptService.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
}
