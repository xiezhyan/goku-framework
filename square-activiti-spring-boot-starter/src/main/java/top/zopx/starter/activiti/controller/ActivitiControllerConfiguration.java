package top.zopx.starter.activiti.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zopx.starter.activiti.controller.main.StencilsetRestResource;
import top.zopx.starter.activiti.controller.model.ModelRestController;
import top.zopx.starter.activiti.controller.model.ModelSaveRestResource;
import top.zopx.starter.activiti.service.IActivitiService;
import top.zopx.starter.activiti.service.IBusinessFlowService;
import top.zopx.starter.activiti.service.impl.ActivitiServiceImpl;
import top.zopx.starter.activiti.service.impl.BusinessFlowServiceImpl;

/**
 * @author sanq.Yan
 * @date 2021/4/18
 */
@Component
public class ActivitiControllerConfiguration {

    @Bean
    public StencilsetRestResource stencilsetRestResource() {
        return new StencilsetRestResource();
    }

    @Bean
    public ModelSaveRestResource modelSaveRestResource() {
        return new ModelSaveRestResource();
    }

    @Bean
    public ModelRestController modelRestController() {
        return new ModelRestController();
    }

    @Bean
    public IActivitiService activitiService() {
        return new ActivitiServiceImpl();
    }

    @Bean
    public IBusinessFlowService businessFlowService() {
        return new BusinessFlowServiceImpl();
    }
}
