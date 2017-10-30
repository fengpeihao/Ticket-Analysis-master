package cn.xiaolong.lhck.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.lhck.R;
import cn.xiaolong.lhck.base.BaseTitleBar;
import cn.xiaolong.lhck.base.BaseTitleBarActivity;
import cn.xiaolong.lhck.bean.TicketOpenData;
import cn.xiaolong.lhck.bean.TicketRegular;
import cn.xiaolong.lhck.presenter.OpenResultPresenter;
import cn.xiaolong.lhck.presenter.view.IOpenResultView;

/**
 * Created by junbo on 15/11/2016.
 */

public class RuleActivity extends BaseTitleBarActivity<OpenResultPresenter> implements IOpenResultView {
    private TextView tvTitle;
    ListView listView;
    RuleAdapter adapter;
    List<Rule> ruleList = new ArrayList<>();
    List<Rule> data = new ArrayList<>();

    @Override
    public void initTitleBar(BaseTitleBar titleBar) {
        tvTitle = (TextView) titleBar.center;
        tvTitle.setText("开奖规则");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rule;
    }

    @Override
    protected void init() {
        listView = (ListView) findViewById(R.id.listView);
        adapter = new RuleAdapter(data, this);
        listView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Rule> rules = read();
                Message msg=new Message();
                msg.what=0;
                msg.obj=rules;
                handler.sendMessage(msg);
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show(data.get(position));
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    data.clear();
                    data.addAll((List<Rule>) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void setListener() {

    }

    @Override
    public void getSingleOpenResultSuccess(TicketOpenData ticketOpenData) {

    }

    @Override
    public void getRegularSuccess(TicketRegular ticketRegular) {

    }

    void show(Rule rule) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(rule.getName()).setMessage(rule.getContent())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();
    }


    public List<Rule> read() {
        if (ruleList.size() > 0)
            return ruleList;

        TypedArray ta = RuleActivity.this.getResources().obtainTypedArray(R.array.rule_name);
        if (ta != null) {
            for (int i = 0; i < ta.length(); i++) {
                String name = RuleActivity.this.getResources().getString(ta.getResourceId(i, 0));
                Rule rule = new Rule();
                rule.setName(name);
                ruleList.add(i, rule);

            }
        }
        TypedArray ruleContent = RuleActivity.this.getResources().obtainTypedArray(R.array.rule);
        if (ruleContent != null) {
            for (int j = 0; j < ruleContent.length(); j++) {
                InputStream in = RuleActivity.this.getResources().openRawResource(
                        (ruleContent.getResourceId(j, 0)));
                BufferedReader br;
                try {
                    br = new BufferedReader(new InputStreamReader(in, "utf-8"));

                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line.trim() + "\n");
                    }
                    Rule rule = ruleList.get(j);
                    rule.setContent(sb.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ruleList;
    }
}
