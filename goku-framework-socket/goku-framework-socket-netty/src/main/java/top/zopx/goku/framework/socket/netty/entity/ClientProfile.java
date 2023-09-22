package top.zopx.goku.framework.socket.netty.entity;

import top.zopx.goku.framework.socket.netty.runner.MultiServerRunner;

import java.util.Collections;
import java.util.Set;

/**
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2023/3/23
 */
public class ClientProfile {

    private Double loadCount;

    private MultiServerRunner runner;

    public Double getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(Double loadCount) {
        this.loadCount = loadCount;
    }

    public MultiServerRunner getRunner() {
        return runner;
    }

    public void setRunner(MultiServerRunner runner) {
        this.runner = runner;
    }

    public int getServerId() {
        return runner.getServerId();
    }

    public Set<String> getServerJobTypeSet() {
        if (null == runner) {
            return Collections.emptySet();
        } else {
            return runner.getServerJobTypeSet();
        }
    }
}
