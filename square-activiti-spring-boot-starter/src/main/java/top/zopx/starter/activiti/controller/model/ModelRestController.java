package top.zopx.starter.activiti.controller.model;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zopx.starter.tools.basic.Page;
import top.zopx.starter.tools.basic.Pagination;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@RestController
@RequestMapping("/activiti")
public class ModelRestController {

    @Resource
    private RepositoryService repositoryService;

    /**
     * 分页获取流程数据
     * @param pagination 分页条件
     * @return
     */
    @GetMapping("/list")
    public Page<Model> getList(Pagination pagination) {
        int firstResult = (pagination.getCurrentIndex() - 1) * pagination.getPageSize();
        final ModelQuery query = repositoryService.createModelQuery();
        pagination.setTotalCount(query.count());

        return new Page<>(pagination, query.orderByCreateTime().desc().listPage(firstResult, pagination.getPageSize()));
    }
}
