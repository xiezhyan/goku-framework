package top.zopx.goku.example.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.util.copy.IStructMapping;
import top.zopx.goku.framework.http.util.login.UserLoginHelper;
import top.zopx.goku.framework.http.constant.ErrorEnum;
import org.mapstruct.Mapper;

import top.zopx.goku.example.mapper.RoleMapper;
import top.zopx.goku.example.entity.Role;
import top.zopx.goku.example.entity.vo.RoleVO;
import top.zopx.goku.example.entity.dto.RoleDTO;
import top.zopx.goku.example.service.RoleService;
import top.zopx.goku.framework.tools.util.id.SnowFlake;


/**
 * (Role)表服务实现类
 *
 * @author Mr.Xie
 * @date 2023-05-18 09:43:50
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleStruct roleStruct;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private SnowFlake snowFlake;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(RoleDTO body) {
        Role entity = roleStruct.copyToT(body);
        buildToSave(entity);
        roleMapper.save(entity);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(Collection<RoleDTO> data) {
        if (CollectionUtils.isEmpty(data)) {
            throw new BusException(ErrorEnum.ERROR_CREATE);
        }

        List<Role> entityList = data.stream()
                .map(item -> {
                    Role entity = roleStruct.copyToT(item);
                    buildToSave(entity);
                    return entity;
                })
                .toList();
        roleMapper.saveBatch(entityList);
        return Boolean.TRUE;
    }

    /**
     * 修改信息
     *
     * @param body 参数信息
     * @param id   主键
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(RoleDTO body, Long id) {
        Role entity = getById(id);
        roleStruct.copyIgnoreNull(body, entity);
        entity.setId(id);
        entity.setUpdater(UserLoginHelper.getUserId());
        entity.setUpdateTime(LocalDateTime.now());
        roleMapper.update(entity, id);
        return Boolean.TRUE;
    }

    /**
     * 删除信息
     *
     * @param id 主键
     * @return 是否删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        roleMapper.updateIsDeleteToYes(id, UserLoginHelper.getUserId());
        return Boolean.TRUE;
    }

    /**
     * 删除信息
     *
     * @param data 批量删除
     * @return 是否删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Collection<Long> data) {
        if (CollectionUtils.isEmpty(data)) {
            throw new BusException(ErrorEnum.ERROR_DELETE);
        }

        roleMapper.updateIsDeleteToYesBatch(data, UserLoginHelper.getUserId());
        return Boolean.TRUE;
    }

    /**
     * 查看详情
     *
     * @param id 主键
     * @return 详细信息
     */
    @Override
    public RoleVO get(Long id) {
        return roleStruct.copyToV(getById(id));
    }

    /**
     * 查看全部
     *
     * @param body 查询参数
     * @return 列表
     */
    @Override
    public List<RoleVO> list(RoleDTO body) {
        List<Role> entityList = roleMapper.getList(body, null, null);
        return entityList.stream()
                .map(item -> roleStruct.copyToV(item))
                .toList();
    }

    /**
     * 分页
     *
     * @param body       查询参数
     * @param pagination 分页信息
     * @return 列表
     */
    @Override
    public List<RoleVO> list(RoleDTO body, Pagination pagination) {
        int currentIndex = Optional.ofNullable(pagination.getCurrentIndex()).orElse(1);
        int pageSize = Optional.ofNullable(pagination.getPageSize()).orElse(1000);
        PageInfo<Role> pageInfo =
                PageMethod.startPage(currentIndex, pageSize)
                        .doSelectPageInfo(() -> roleMapper.getList(body, pagination.getStartRow(), pagination.getEndRow()));
        return buildPage(pageInfo, pagination, currentIndex, pageSize);
    }

    private List<RoleVO> buildPage(PageInfo<Role> pageInfo, Pagination pagination, int currentIndex, int pageSize) {
        pagination.setTotalCount(pageInfo.getTotal());
        pagination.setCurrentIndex(currentIndex);
        pagination.setPageSize(pageSize);
        return pageInfo.getList()
                .stream()
                .map(item -> roleStruct.copyToV(item))
                .toList();
    }

    protected Role getById(Long id) {
        return Optional.ofNullable(roleMapper.getById(id)).orElseThrow(() -> new BusException(ErrorEnum.NOT_ENTITY));
    }

    private void buildToSave(Role entity) {
        entity.setId(snowFlake.nextId());
        entity.setCreater(UserLoginHelper.getUserId());
        entity.setCreateTime(LocalDateTime.now());
        entity.setIsDelete(0);
    }


    @Mapper(componentModel = "spring")
    public interface RoleStruct extends IStructMapping<RoleDTO, RoleVO, Role> {
    }
}

