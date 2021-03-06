package cn.xiaolong.lhck.ui;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.standards.library.listview.ListGroupPresenter;
import com.standards.library.listview.listview.RecycleListViewImpl;
import com.standards.library.listview.manager.BaseGroupListManager;
import com.standards.library.model.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.adapter.TicketRecentOpenAdapter;
import cn.xiaolong.lhck.base.BaseFuncFragment;
import cn.xiaolong.lhck.bean.TicketType;
import cn.xiaolong.lhck.bean.type.TicketTypeEnum;
import cn.xiaolong.lhck.group.LoadingPage;
import cn.xiaolong.lhck.group.Scene;
import cn.xiaolong.lhck.manager.TicketTypeManager;
import cn.xiaolong.lhck.ui.follow.FollowAddActivity;
import cn.xiaolong.lhck.ui.widget.RecycleViewDivider;
import cn.xiaolong.lhck.utils.LaunchUtil;


/**
 * @author xiaolong
 * @version v1.0
 * @function <我的关注>
 * @date 2017年9月12日 14:55:21
 */
public class MyFollowNewFragment extends BaseFuncFragment {
    private TicketRecentOpenAdapter ticketRecentOpenAdapter;
    private ListGroupPresenter presenter;
    private BaseGroupListManager manager;
    private RecycleListViewImpl recycleListView;


    private TicketTypeEnum mTicketTypeEnum;

    public static MyFollowNewFragment getNewInstance(TicketTypeEnum ticketTypeEnum) {
        MyFollowNewFragment fragment = new MyFollowNewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ticketListType", ticketTypeEnum);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void getExtra() {
        if (getArguments() != null) {
            mTicketTypeEnum = (TicketTypeEnum) getArguments().getSerializable("ticketListType");
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_follow;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        recycleListView = new RecycleListViewImpl(true, false, false);
        RelativeLayout rlContent = findView(R.id.rlContent);
        LoadingPage loadingPage = new LoadingPage(getActivity(), Scene.TICKET_FAVORITE);
        ticketRecentOpenAdapter = new TicketRecentOpenAdapter(getActivity());
        manager = new TicketTypeManager(mTicketTypeEnum);
        presenter = ListGroupPresenter.create(getActivity(), recycleListView, manager, ticketRecentOpenAdapter, loadingPage);
        recycleListView.getRecyclerView().addItemDecoration(new RecycleViewDivider(getActivity(),
                LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.main_divider_color)));
        rlContent.addView(presenter.getRootView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setListener() {
        ClickView(findView(R.id.tvAdd)).subscribe(o -> LaunchUtil.launchActivity(getActivity(), FollowAddActivity.class));
        ticketRecentOpenAdapter.setOnItemClickListener(view ->
                LaunchUtil.launchActivity(getActivity(), OpenResultActivity.class,
                        OpenResultActivity.buildBundle((TicketType) view.getTag())));

//        ClickView(findView(R.id.ivHint)).subscribe(o -> ToastUtil.showToast("由于数据来源问题，开奖数据会有2-6分钟的延迟！"));
//        ClickView(findView(R.id.ivLocation))
//                .subscribe(o -> LaunchUtil.launchDefaultWeb(getActivity(),
//                        "http://map.baidu.com/mobile/webapp/search/search/qt=s&wd=%E5%BD%A9%E7%A5%A8&newmap=1&ie=utf-8&c=194/vt=map", ""));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void refreshData(Event event) {
        ticketRecentOpenAdapter.setItems((List<TicketType>) event.data);
        presenter.onRefresh();
    }
}
