package dgsw.hs.kr.ahnt.Fragment;

import android.support.v4.app.Fragment;

/**
 * Created by neutral on 30/04/2018.
 */

public abstract class BaseFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getTitle());
    }

    protected abstract String getTitle();
}
