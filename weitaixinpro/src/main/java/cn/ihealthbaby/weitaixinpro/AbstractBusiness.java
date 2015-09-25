package cn.ihealthbaby.weitaixinpro;

import android.content.Context;
import android.content.Intent;

import java.util.Map;

import cn.ihealthbaby.client.Result;
import cn.ihealthbaby.weitaixin.library.data.net.Business;
import cn.ihealthbaby.weitaixin.library.util.SPUtil;
import cn.ihealthbaby.weitaixinpro.ui.login.BindActivity;

/**
 * Created by liuhongjian on 15/7/28 17:55.
 */
public abstract class AbstractBusiness<T> implements Business<T> {
	@Override
	public void handleValidator(Context context) {
	}

	@Override
	public void handleResult(Result<T> result) {
	}

	@Override
	public void handleAccountError(Context context, Map<String, Object> msgMap) {
		SPUtil.clearUser(context);
		Intent intent = new Intent(context, BindActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void handleError(Map<String, Object> msgMap) {
	}

	@Override
	public void handleException(Exception e) {
	}

	@Override
	public void handleClientError(Exception e) {
	}
}
