package top.zopx.goku.framework.support.primary.core.event;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zopx.goku.framework.support.primary.core.buffer.Segment;
import top.zopx.goku.framework.support.primary.core.buffer.SegmentBuffer;
import top.zopx.goku.framework.support.primary.core.entity.Business;

/**
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public class FillingSegmentListener {

    private static final Logger LOG = LoggerFactory.getLogger(FillingSegmentListener.class);

    @Subscribe
    public void filling(FillingSegmentEvent event) {
        LOG.info("接收到事件");
        final SegmentBuffer buffer = event.getSegmentBuffer();
        Segment segment = null;
        Exception ex = null;
        try {
            final Business business = event.getBusinessService().getBusinessByKey(event.getTag(), event.getSegmentPullSize());
            segment = business2Segment(business);
        } catch (Exception e) {
            LOG.error("转换出现异常：{}", e.getMessage());
            ex = e;
        }
        buffer.setNextSegment(segment);
        buffer.complete(ex);
    }

    private Segment business2Segment(Business business) {
        final Segment segment = new Segment();
        final long current = business.getCurrentID();
        segment.setMinID(current);
        segment.setCurrentID(current);
        segment.setMaxID(business.getMaxID());
        return segment;
    }

}
