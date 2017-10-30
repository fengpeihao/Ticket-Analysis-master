package cn.xiaolong.lhck.presenter.view;

import cn.xiaolong.lhck.base.ILoadingView;
import cn.xiaolong.lhck.bean.TicketOpenData;
import cn.xiaolong.lhck.bean.TicketRegular;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/8 15:02
 */

public interface IOpenResultView extends ILoadingView {
    void getSingleOpenResultSuccess(TicketOpenData ticketOpenData);

    void getRegularSuccess(TicketRegular ticketRegular);
}
