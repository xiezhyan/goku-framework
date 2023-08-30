package top.zopx.goku.example.controller;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import top.zopx.goku.framework.http.annotation.AnnoLog;
import top.zopx.goku.framework.http.entity.dto.CollectionEntityDTO;
import top.zopx.goku.framework.http.util.validate.constant.ValidGroup;
import top.zopx.goku.framework.tools.entity.vo.Page;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.wrapper.R;


import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.example.service.RoleService;

import java.util.List;


/**
 * (Role)表控制层
 *
 * @author Mr.Xie
 * @date 2023-05-17 15:02:52
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Resource
    private RoleService roleService;

    @GetMapping
    public R<Page<RoleVO>> getPage(Pagination pagination, RoleDTO body) {
        return R.result(
                new Page<>(pagination, roleService.list(body, pagination))
        );
    }

    @GetMapping("/list")
    public R<List<RoleVO>> getList(RoleDTO body) {
        return R.result(
                roleService.list(body)
        );
    }

    @GetMapping("/{id}")
    @AnnoLog(value = "'获取' + #id + '的详细信息'")
    public R<RoleVO> getByKey(@PathVariable(value = "id") Long id) {
        return R.result(roleService.get(id));
    }

    @PostMapping
    @AnnoLog(value = "保存")
    public R<Boolean> save(@Validated(value = {ValidGroup.Create.class}) @RequestBody RoleDTO body) {
        return R.status(roleService.save(body));
    }

    @PutMapping("/{id}")
    @AnnoLog(value = "'修改' + #id + '的信息'")
    public R<Boolean> updateByKey(@PathVariable("id") Long id, @Validated(value = {ValidGroup.Update.class}) @RequestBody RoleDTO body) {
        return R.status(roleService.update(body, id));
    }

    @DeleteMapping("/{id}")
    @AnnoLog(value = "'删除'+ #id +'数据'")
    public R<Boolean> deleteByKey(@PathVariable("id") Long id) {
        return R.status(roleService.delete(id));
    }


    @DeleteMapping("/batch")
    @AnnoLog(value = "批量删除")
    public R<Boolean> deleteBatch(@RequestBody @Valid CollectionEntityDTO<Long> body) {
        return R.status(roleService.delete(body.getData()));
    }

    @PostMapping("/batch")
    @AnnoLog(value = "批量保存")
    public R<Boolean> save(@Validated(value = {ValidGroup.Create.class}) @RequestBody CollectionEntityDTO<RoleDTO> body) {
        return R.status(roleService.save(body.getData()));
    }

}

