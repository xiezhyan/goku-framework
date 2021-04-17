package top.zopx.square.service.impl;

import org.springframework.stereotype.Service;
import top.zopx.starter.distribution.annotation.Distribution;
import top.zopx.starter.distribution.properties.SquareDistributionProperties;

import javax.annotation.Resource;

/**
 * @author sanq.Yan
 * @date 2021/4/16
 */
@Service
public class DistributationServiceImpl {

    @Resource
    private SquareDistributionProperties squareDistributionProperties;

    @Distribution
    public void lock1() {
        System.out.println("lock");
    }


    @Distribution
    public void lock2(String name) {
    }


}
