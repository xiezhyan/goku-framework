package top.zopx.goku.framework.support.primary.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.tools.util.id.SnowFlake;
import top.zopx.goku.framework.support.primary.core.entity.BootstrapSnowflakeItem;
import top.zopx.goku.framework.support.primary.core.entity.Node;
import top.zopx.goku.framework.support.primary.core.service.IIDGetterService;
import top.zopx.goku.framework.support.primary.core.service.IRegisterNodeService;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Mr.Xie
 * @date 2022/1/22
 * @email xiezhyan@126.com
 */
public abstract class BaseIDSnowflakeGetterService implements IIDGetterService {
    private static final Logger LOG = LoggerFactory.getLogger(BaseIDSnowflakeGetterService.class);
    /**
     * 节点注册服务
     */
    private final IRegisterNodeService registerNodeService;
    /**
     * 雪花配置
     */
    private final BootstrapSnowflakeItem bootstrapSnowflakeItem;

    private SnowFlake snowFlake;

    protected BaseIDSnowflakeGetterService(IRegisterNodeService registerNodeService, BootstrapSnowflakeItem bootstrapSnowflakeItem) {
        this.registerNodeService = registerNodeService;
        this.bootstrapSnowflakeItem = bootstrapSnowflakeItem;
    }

    @Override
    public void init() {
        String ip = getIP("");
        final Node node = new Node();
        node.setIp(StringUtils.isNotBlank(ip) ? ip : bootstrapSnowflakeItem.getHost());
        node.setPort(bootstrapSnowflakeItem.getPort());

        int result = registerNodeService.register(node);
        LOG.info("节点注册seq={}", result);
        snowFlake = new SnowFlake(result, result);
    }

    @Override
    public long getID(String key) {
        return snowFlake.nextId();
    }

    /**
     * 获取当前节点IP
     *
     * @return IP
     */
    public String getIP(String instanceName) {
        String ip;
        instanceName = instanceName.trim();
        try {
            List<String> ipList = getHostAddress(instanceName);
            ip = (!ipList.isEmpty()) ? ipList.get(0) : "";
        } catch (Exception ex) {
            ip = "";
        }
        return ip;
    }

    private List<String> getHostAddress(String interfaceName) throws SocketException {
        List<String> ipList = new ArrayList<String>(5);
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            Enumeration<InetAddress> allAddress = ni.getInetAddresses();
            while (allAddress.hasMoreElements()) {
                InetAddress address = allAddress.nextElement();
                if (address.isLoopbackAddress()) {
                    // skip the loopback addr
                    continue;
                }
                if (address instanceof Inet6Address) {
                    // skip the IPv6 addr
                    continue;
                }
                String hostAddress = address.getHostAddress();
                if (null == interfaceName) {
                    ipList.add(hostAddress);
                } else if (interfaceName.equals(ni.getDisplayName())) {
                    ipList.add(hostAddress);
                }
            }
        }
        return ipList;
    }
}
