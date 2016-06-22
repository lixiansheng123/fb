package com.yuedong.football_mad.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.ViewUtils;

/**
 * 数据酷
 */
public class DataKuActivity extends BaseActivity {
    private DataKuListFragment dataKuCompetitionFragment;
    private DataKuListFragment dataKuTeamFragment;
    private DataKuListFragment dataKuAthleteFragment;
    private DataKuListFragment dataKuCountryFragment;
    private DataKuListFragment dataKuOtherFragment;
    private DataKuSearchFragment dataKuSearchFragment;
    @ViewInject(R.id.sel1)
    private View sel1;
    @ViewInject(R.id.sel2)
    private View sel2;
    @ViewInject(R.id.sel3)
    private View sel3;
    @ViewInject(R.id.sel4)
    private View sel4;
    @ViewInject(R.id.sel5)
    private View sel5;
    @ViewInject(R.id.sel6)
    private View sel6;
    private View[] sels = new View[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "数据酷", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_data_ku);
        initFragment();
        if (savedInstanceState == null) {
            addFragment(dataKuCompetitionFragment, R.id.fragment_container, false);
        }
    }

    private void initFragment() {
        Bundle competitionBundle = new Bundle();
        competitionBundle.putInt(Constant.KEY_ACTION, DataKuListFragment.ACTION_COMPETITION);
        dataKuCompetitionFragment = new DataKuListFragment();
        dataKuCompetitionFragment.setArguments(competitionBundle);
        dataKuTeamFragment = new DataKuListFragment();
        Bundle teamBundel = new Bundle();
        teamBundel.putInt(Constant.KEY_ACTION, DataKuListFragment.ACTION_TEAM);
        dataKuTeamFragment.setArguments(teamBundel);
        Bundle athleteBundle = new Bundle();
        athleteBundle.putInt(Constant.KEY_ACTION,DataKuListFragment.ACTION_ATHLETE);
        dataKuAthleteFragment = new DataKuListFragment();
        dataKuAthleteFragment.setArguments(athleteBundle);
        Bundle countryBundle = new Bundle();
        countryBundle.putInt(Constant.KEY_ACTION,DataKuListFragment.ACTION_COUNTRY);
        dataKuCountryFragment = new DataKuListFragment();
        dataKuCountryFragment.setArguments(countryBundle);
        Bundle otherBundle = new Bundle();
        otherBundle.putInt(Constant.KEY_ACTION,DataKuListFragment.ACTION_OTHER);
        dataKuOtherFragment = new DataKuListFragment();
        dataKuOtherFragment.setArguments(otherBundle);
        dataKuSearchFragment = new DataKuSearchFragment();
    }

    @Override
    protected void ui() {
        sels[0] = sel1;
        sels[1] = sel2;
        sels[2] = sel3;
        sels[3] = sel4;
        sels[4] = sel5;
        sels[5] = sel6;
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @Override
    protected Fragment getDefaultFrag() {
        return dataKuCompetitionFragment;
    }
    @OnClick({R.id.rl_search,R.id.rl_competition,R.id.rl_team,R.id.rl_player,R.id.rl_team,R.id.rl_country,R.id.rl_other})
    protected void clickEvent(View view) {
        hideAllSel();
        switch (view.getId()) {
            case R.id.rl_search:
                ViewUtils.showLayout(sel1);
                switchContent(mDisplayContext,dataKuSearchFragment,R.id.fragment_container);
                break;
            case R.id.rl_competition:
                ViewUtils.showLayout(sel2);
                switchContent(mDisplayContext, dataKuCompetitionFragment, R.id.fragment_container);
                break;
            case R.id.rl_team:
                ViewUtils.showLayout(sel3);
                switchContent(mDisplayContext, dataKuTeamFragment, R.id.fragment_container);
                break;
            case R.id.rl_player:
                ViewUtils.showLayout(sel4);
                switchContent(mDisplayContext,dataKuAthleteFragment,R.id.fragment_container);
                break;
            case R.id.rl_country:
                ViewUtils.showLayout(sel5);
                switchContent(mDisplayContext,dataKuCountryFragment,R.id.fragment_container);
                break;
            case R.id.rl_other:
                ViewUtils.showLayout(sel6);
                switchContent(mDisplayContext,dataKuOtherFragment,R.id.fragment_container);
                break;
        }
    }

    private void hideAllSel(){
        for (View sel : sels)
            ViewUtils.hideLayout(sel);
    }
}
