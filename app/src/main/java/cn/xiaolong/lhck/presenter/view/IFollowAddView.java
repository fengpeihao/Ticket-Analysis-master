package cn.xiaolong.lhck.presenter.view;

import java.util.List;

import cn.xiaolong.lhck.base.ILoadingView;
import cn.xiaolong.lhck.bean.TicketType;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/11 11:17
 */

public interface IFollowAddView extends ILoadingView {
    void onGetTicketListSuccess(List<TicketType> ticketTypeList);
}
