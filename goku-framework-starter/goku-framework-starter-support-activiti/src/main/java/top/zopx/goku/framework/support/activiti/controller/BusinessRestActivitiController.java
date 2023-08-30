/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.zopx.goku.framework.support.activiti.controller;

import org.springframework.web.bind.annotation.*;
import top.zopx.goku.framework.support.activiti.entity.dto.ModelDTO;
import top.zopx.goku.framework.support.activiti.entity.vo.ModelVO;
import top.zopx.goku.framework.support.activiti.service.IBusinessActivitiService;
import top.zopx.goku.framework.tools.entity.vo.Page;
import top.zopx.goku.framework.tools.entity.vo.Pagination;
import top.zopx.goku.framework.tools.entity.wrapper.R;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author Mr.Xie
 * @date 2022/06/15
 */
@RestController
@RequestMapping("/rest")
public class BusinessRestActivitiController {

    @Resource
    private IBusinessActivitiService businessActivitiService;

    /**
     * 跳转到可视化界面
     *
     * @param modelId  流程编号
     * @param tenantId 租户
     * @param category 分类
     * @page /modeler.html?modelId=
     */
    @GetMapping("/to")
    public R<String> createModel(
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "category", required = false) String category
    ) {
        final String redirectUrl = businessActivitiService.saveOrUpdate(modelId, tenantId, category);
        return R.result(redirectUrl);
    }

    /**
     * 发布流程
     *
     * @param modelId 流程ID
     * @return Boolean true：成功  false：失败
     */
    @PostMapping("/deploy/{modelId}")
    public R<Boolean> deploy(@PathVariable("modelId") String modelId) {
        return R.status(businessActivitiService.deploy(modelId));
    }

    /**
     * 删除流程
     *
     * @param modelId 流程ID
     * @return Boolean true：删除成功    false：删除失败
     */
    @DeleteMapping("/{modelId}")
    public R<Boolean> deleteByModelId(@PathVariable("modelId") String modelId) {
        return R.status(businessActivitiService.deleteByModelId(modelId));
    }

    /**
     * 分页获取流程数据
     *
     * @param name       流程名称
     * @param key        唯一标识
     * @param category   分类
     * @param tenantId   租户
     * @param pagination 分页
     * @return Page<ModelResponse>
     */
    @GetMapping("/page")
    public R<Page<ModelVO>> getList(
            Pagination pagination,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tenantId", required = false) String tenantId) {

        ModelDTO request = new ModelDTO();
        request.setName(name);
        request.setKey(key);
        request.setCategory(category);
        request.setTenantId(tenantId);

        final List<ModelVO> list = businessActivitiService.getList(request, pagination);
        return R.result(new Page<>(pagination, list));
    }
}
