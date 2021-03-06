package cn.ihealthbaby.weitaixinpro.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.ihealthbaby.weitaixin.library.log.LogUtil;
import cn.ihealthbaby.weitaixin.library.util.Util;

/**
 * Created by liuhongjian on 15/9/2 10:16. 坐标转换的基类
 */
public class CoordinateView extends View {
	private final static String TAG = "CoordinateView";
	protected int cellWidth;
	protected int yMax = 300;
	protected int yMin = 0;
	protected int safeMax = 160;
	protected int safeMin = 110;
	protected int limitMax = 210;
	protected int limitMin = 60;
	/**
	 * 单位s
	 */
	protected int xMax = 20 * 60;
	protected int xMin = 0;
	protected int timeMinute = getTimeMinute();
	protected int pointsPerMin = 120;
	/**
	 * 每个格子代表的时间,单位s
	 */
	protected int gridX = 2;
	/**
	 * 每个格子代表的心跳值
	 */
	protected int gridY = 10;
	protected String limitMinString;
	protected String limitMaxString;
	protected String safeMaxString;
	protected String safeMinString;

	public CoordinateView(Context context) {
		this(context, null, 0);
	}

	public CoordinateView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CoordinateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		cellWidth = Util.dip2px(context, 10);
		limitMinString = "" + limitMin;
		limitMaxString = "" + limitMax;
		safeMaxString = "" + safeMax;
		safeMinString = "" + safeMin;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int measuredWidth = getMinWidth() + getPaddingLeft() + getPaddingRight();
		final int measuredHeight = getMinHeight() + getPaddingTop() + getPaddingBottom();
		LogUtil.d(TAG, "measuredWidth:[%s],measuredHeight:[%s]", measuredWidth, measuredHeight);
		setMeasuredDimension(measuredWidth, measuredHeight);
	}

	public int getMinHeight() {
		return (yMax - yMin) * cellWidth / gridY;
	}

	public int getMinWidth() {
		return (xMax - xMin) * 60 * cellWidth / gridX;
	}

	/**
	 * 图表坐标转换为canvas坐标
	 *
	 * @param x 时间,单位为s
	 * @return
	 */
	protected float convertX(float x) {
		return getPaddingLeft() + xMin + x * cellWidth / gridX;
	}

	/**
	 * canvas坐标转换为 图表坐标(单位s)
	 *
	 * @param x 像素,px
	 * @return 时间 单位s
	 */
	public float reconvertX(float x) {
		return (x - getPaddingLeft() - xMin) * gridX / cellWidth;
	}

	public float reconvertXDiff(float xDiff) {
		return xDiff * gridX / cellWidth;
	}

	/**
	 * 图表坐标转换为canvas坐标
	 *
	 * @param y 心率值,单位为1
	 * @return
	 */
	protected float convertY(float y) {
		return getPaddingTop() + (yMax - yMin - y) * cellWidth / gridY;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}

	public int getLimitMax() {
		return limitMax;
	}

	public void setLimitMax(int limitMax) {
		this.limitMax = limitMax;
		limitMaxString = "" + limitMax;
	}

	public int getLimitMin() {
		return limitMin;
	}

	public void setLimitMin(int limitMin) {
		this.limitMin = limitMin;
		limitMinString = "" + limitMin;
	}

	public int getPointsPerMin() {
		return pointsPerMin;
	}

	public void setPointsPerMin(int pointsPerMin) {
		this.pointsPerMin = pointsPerMin;
	}

	public int getSafeMax() {
		return safeMax;
	}

	public void setSafeMax(int safeMax) {
		this.safeMax = safeMax;
		safeMaxString = "" + safeMax;
	}

	public int getSafeMin() {
		return safeMin;
	}

	public void setSafeMin(int safeMin) {
		this.safeMin = safeMin;
		safeMinString = "" + safeMin;
	}

	public void resetTimeMinute() {
		timeMinute = getTimeMinute();
	}

	public int getTimeMinute() {
		return (xMax - xMin) / 60;
	}

	public int getxMax() {
		return xMax;
	}

	public void setxMax(int xMax) {
		this.xMax = xMax;
	}

	public int getxMin() {
		return xMin;
	}

	public void setxMin(int xMin) {
		this.xMin = xMin;
	}

	public int getyMax() {
		return yMax;
	}

	public void setyMax(int yMax) {
		this.yMax = yMax;
	}

	public int getyMin() {
		return yMin;
	}

	public void setyMin(int yMin) {
		this.yMin = yMin;
	}
}
