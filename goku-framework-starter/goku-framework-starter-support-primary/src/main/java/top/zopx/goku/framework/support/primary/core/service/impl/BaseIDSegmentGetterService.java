package top.zopx.goku.framework.support.primary.core.service.impl;

import com.google.common.collect.Maps;
import top.zopx.goku.framework.support.primary.core.buffer.SegmentBuffer;
import top.zopx.goku.framework.support.primary.core.entity.IDGetter;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;
import top.zopx.goku.framework.support.primary.core.service.IIDGetterService;
import top.zopx.goku.framework.support.primary.core.service.IUpdatePlot;
import top.zopx.goku.framework.tools.exception.BusException;
import top.zopx.goku.framework.tools.exception.IBus;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Xie
 * @date 2022/1/22
 * @email xiezhyan@126.com
 */
public abstract class BaseIDSegmentGetterService implements IIDGetterService {

    private final IBusinessService businessService;
    private final IUpdatePlot updatePlot;

    private static final Map<String, SegmentBuffer> BUFFER_MAP = Maps.newConcurrentMap();

    public BaseIDSegmentGetterService(IBusinessService businessService, IUpdatePlot updatePlot) {
        this.businessService = businessService;
        this.updatePlot = updatePlot;
    }

    @Override
    public long getID(String key) {
        return getID2Segment(key);
    }

    /**
     * 基于Segment获取ID
     *
     * @param key 业务ID
     * @return ID
     */
    private long getID2Segment(String key) {
        SegmentBuffer buffer = BUFFER_MAP.get(key);
        if (Objects.isNull(buffer)) {
            synchronized (this) {
                if (Objects.isNull(buffer = BUFFER_MAP.get(key))) {
                    buffer = new SegmentBuffer(key, this.updatePlot, this.businessService);
                    BUFFER_MAP.put(key, buffer);
                }
            }
        }

        try {
            final IDGetter wrapper = buffer.next(1);
            return wrapper.getCurrentID();
        } catch (Exception e) {
            BUFFER_MAP.remove(key);
            throw new BusException(e.getMessage(), IBus.ERROR_CODE);
        }
    }
}
