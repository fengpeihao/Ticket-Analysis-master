package cn.xiaolong.lhck.ui;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.standards.library.listview.ListGroupPresenter;
import com.standards.library.listview.listview.RecycleListViewImpl;
import com.standards.library.listview.manager.BaseGroupListManager;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.adapter.TicketTypeAdapter;
import cn.xiaolong.lhck.base.BaseFuncFragment;
import cn.xiaolong.lhck.bean.TicketType;
import cn.xiaolong.lhck.bean.type.TicketTypeEnum;
import cn.xiaolong.lhck.group.LoadingPage;
import cn.xiaolong.lhck.group.Scene;
import cn.xiaolong.lhck.manager.TicketTypeManager;
import cn.xiaolong.lhck.ui.widget.RecycleViewDivider;
import cn.xiaolong.lhck.utils.LaunchUtil;


/**
 * @author xiaolong
 * @version v1.0
 * @function <发现页普通页面>
 * @date 2016/8/9-14:10
 */
public class TicketType2Fragment extends BaseFuncFragment {
    private TicketTypeAdapter ticketTypeAdapter;
    private ListGroupPresenter presenter;
    private BaseGroupListManager manager;
    private RecycleListViewImpl recycleListView;
    private TicketTypeEnum mTicketTypeEnum;

    public static TicketType2Fragment getNewInstance(TicketTypeEnum ticketTypeEnum) {
        TicketType2Fragment fragment = new TicketType2Fragment();
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
        return R.layout.fragment_ticket_types;
    }

    @Override
    public void init() {

//        Toolbar toolbar = (Toolbar) findView(R.id.toolbar);
//        BaseTitleBar mTitleBar = new BaseTitleBar(getContext(), toolbar, R.layout.titlebar_normalf);
//        TextView tvTitle = (TextView) mTitleBar.center;
//        View view = mTitleBar.left;
//        view.setVisibility(View.GONE);
//        tvTitle.setText("开奖");
//        tvTitle.setTextSize(18);
//        View viewn = (View) mTitleBar.viewn;
//        if(Build.VERSION.SDK_INT > 19) {
//            // TODO
//            viewn.setVisibility(View.GONE);
//        }else{
//            viewn.setVisibility(View.VISIBLE);
//        }


        recycleListView = new RecycleListViewImpl(true, false, false);
        LinearLayout rlContent = findView(R.id.rlContent);
        LoadingPage loadingPage = new LoadingPage(getActivity(), Scene.DEFAULT);
        ticketTypeAdapter = new TicketTypeAdapter(getActivity());
        manager = new TicketTypeManager(mTicketTypeEnum);
        presenter = ListGroupPresenter.create(getActivity(), recycleListView, manager, ticketTypeAdapter, loadingPage);
        recycleListView.getRecyclerView().addItemDecoration(new RecycleViewDivider(getActivity(),
                LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.main_divider_color)));
        rlContent.addView(presenter.getRootView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setListener() {
        ticketTypeAdapter.setOnItemClickListener(view ->
                LaunchUtil.launchActivity(getActivity(), OpenResultActivity.class,
                        OpenResultActivity.buildBundle((TicketType) view.getTag())));
    }

}
