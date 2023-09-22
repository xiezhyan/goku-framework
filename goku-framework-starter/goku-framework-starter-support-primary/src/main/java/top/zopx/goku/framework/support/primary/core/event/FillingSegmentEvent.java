package top.zopx.goku.framework.support.primary.core.event;


import top.zopx.goku.framework.support.primary.core.buffer.SegmentBuffer;
import top.zopx.goku.framework.support.primary.core.service.IBusinessService;

/**
 * @author Mr.Xie
 * @date 2022/1/17
 * @email xiezhyan@126.com
 */
public class FillingSegmentEvent {

    private SegmentBuffer segmentBuffer;

    private String tag;

    private IBusinessService businessService;

    private int segmentPullSize;

    public FillingSegmentEvent(SegmentBuffer segmentBuffer, IBusinessService businessService, String tag, int segmentPullSize) {
        this.segmentBuffer = segmentBuffer;
        this.businessService = businessService;
        this.tag = tag;
        this.segmentPullSize = segmentPullSize;
    }

    public SegmentBuffer getSegmentBuffer() {
        return segmentBuffer;
    }

    public void setSegmentBuffer(SegmentBuffer segmentBuffer) {
        this.segmentBuffer = segmentBuffer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public IBusinessService getBusinessService() {
        return businessService;
    }

    public void setBusinessService(IBusinessService businessService) {
        this.businessService = businessService;
    }

    public int getSegmentPullSize() {
        return segmentPullSize;
    }

    public void setSegmentPullSize(int segmentPullSize) {
        this.segmentPullSize = segmentPullSize;
    }
}
