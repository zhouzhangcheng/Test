package com.jock.tbshopcar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class DragView extends LinearLayout {

	// �������ƴ������
	private ViewDragHelper viewDragHelper;
	// ��һ��view
	private View contentView;
	// �ڶ���view
	private View actionView;

	private int dragDistance;
	private final double AUTO_OPEN_SPEED_LIMIT = 400.0;
	private int draggedX;

	/**
	 * ��������
	 */
	private onSlideListener onSlide;
	// ���µ�x
	private float downX;

	private float downY;

	public DragView(Context context) {
		this(context, null);
	}

	public DragView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// ����һ�����лص��ӿڵ�ViewDragHelper
		viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
	}

	// ��View�����е��ӿؼ� ����ӳ���xml�󴥷�
	@Override
	protected void onFinishInflate() {
		// �õ���һ��������ʾ��ͼ
		contentView = getChildAt(0);
		// �õ��ڶ���������ʾ��ͼ����ɾ����ͼ��
		actionView = getChildAt(1);
		// Ĭ�ϲ���ʾ
		actionView.setVisibility(GONE);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		dragDistance = actionView.getMeasuredWidth();
	}

	/**
	 * ��ԭ
	 */
	public void revert() {
		if (viewDragHelper != null) {
			viewDragHelper.smoothSlideViewTo(contentView, 0, 0);
			invalidate();
		}
	}

	/**
	 * ���ƴ���ļ���ʵ��
	 */
	private class DragHelperCallback extends ViewDragHelper.Callback {

		// tryCaptureView��η���ture���ʾ���Բ����view������Ը��ݴ���ĵ�һ��view����������Щ���Բ���
		@Override
		public boolean tryCaptureView(View view, int i) {
			return view == contentView || view == actionView;
		}

		// ��captureview��λ�÷����ı�ʱ�ص�
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			// ����ƶ��˶���
			draggedX = left;
			// ���ظ���ͼ�¼������ø���ͼ�¼�Ӱ��
			// getParent().requestDisallowInterceptTouchEvent(true);
			if (changedView == contentView) {
				actionView.offsetLeftAndRight(dx);
			} else {
				contentView.offsetLeftAndRight(dx);
			}
			if (actionView.getVisibility() == View.GONE) {
				actionView.setVisibility(View.VISIBLE);
			}
			// ˢ����ͼ
			invalidate();
		}

		/**
		 * clampViewPositionHorizontal,
		 * clampViewPositionVertical�����ڸ÷����ж�child�ƶ��ı߽���п��ƣ� left , top
		 * �ֱ�Ϊ�����ƶ�����λ�ã�������������£���ϣ��ֻ��ViewGroup���ڲ��ƶ���������С>=paddingleft��
		 * ���<=ViewGroup.getWidth()-paddingright-child.getWidth���Ϳ��԰������´����д��
		 */
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (child == contentView) {
				final int leftBound = getPaddingLeft();
				final int minLeftBound = -leftBound - dragDistance;
				final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
				return newLeft;
			} else {
				final int minLeftBound = getPaddingLeft()
						+ contentView.getMeasuredWidth() - dragDistance;
				final int maxLeftBound = getPaddingLeft()
						+ contentView.getMeasuredWidth() + getPaddingRight();
				final int newLeft = Math.min(Math.max(left, minLeftBound),
						maxLeftBound);
				return newLeft;
			}
		}

		/**
		 * ԭ����ʲô�أ���Ҫ����Ϊ�������View�������¼�����ô�������ƣ�DOWN-MOVE*-UP��
		 * ����ֱ�ӽ���onTouchEvent����onTouchEvent��DOWN��ʱ���ȷ����captureView��
		 * ��������¼�����ô�ͻ�����onInterceptTouchEvent�������ж��Ƿ���Բ��� �����жϵĹ����л�ȥ�ж����������ص��ķ�����
		 * getViewHorizontalDragRange��getViewVerticalDragRange��
		 * ֻ���������������ش���0��ֵ���������Ĳ������ԣ� �������Button���ԣ����߸�TextView�����clickable = true
		 * ����Ҫ�ǵ���д����������������
		 */
		@Override
		public int getViewHorizontalDragRange(View child) {
			return dragDistance;
		}

		// ��ָ�ͷŵ�ʱ��ص�
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			boolean settleToOpen = false;
			if (xvel > AUTO_OPEN_SPEED_LIMIT) {
				settleToOpen = false;
			} else if (xvel < -AUTO_OPEN_SPEED_LIMIT) {
				settleToOpen = true;
			} else if (draggedX <= -dragDistance / 2) {
				settleToOpen = true;
			} else if (draggedX > -dragDistance / 2) {
				settleToOpen = false;
			}

			final int settleDestX = settleToOpen ? -dragDistance : 0;
			if (onSlide != null) {
				if (settleDestX == 0) {
					onSlide.onSlided(false, DragView.this);
				} else {
					onSlide.onSlided(true, DragView.this);
				}
			}
			viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
			ViewCompat.postInvalidateOnAnimation(DragView.this);
		}
	}

	public void setOnSlide(onSlideListener onSlide) {
		this.onSlide = onSlide;
	}

	/**
	 * ����������ͼ������ViewDragHelper���ƴ��� ���Ե��²���������ͼ����¼������ã�������Ҫ�Լ��������¼�
	 */
	public interface onSlideListener {
		/**
		 * �໬����֮����� true�Ѿ��໬��false��δ�໬
		 */
		void onSlided(boolean isSlide, DragView dragView);

		/**
		 * δ�໬״̬�µ�Ĭ����ʾ����ĵ���¼�
		 */
		void onClick(DragView dragView);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// �տ�ʼ��������ͼ�¼�.��onTouchEvent�������ƶ����ǵ��
		// getParent().requestDisallowInterceptTouchEvent(false);
		if (viewDragHelper.shouldInterceptTouchEvent(event)) {
			return true;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ��¼���µ�����
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downX = event.getRawX();
			downY = event.getRawY();
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// x��y�ƶ��ľ���С��10�ͳ�������¼�
			if (Math.abs(downX - event.getRawX()) < Math.abs(downY
					- event.getRawY())) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// x��y�ƶ��ľ���С��10�ͳ�������¼�
			if (Math.abs(downX - event.getRawX()) < 10
					&& Math.abs(downY - event.getRawY()) < 10) {
				if (onSlide != null) {
					if (actionView.getVisibility() == View.VISIBLE) {
						if (inRangeOfView(actionView, event)) {
							onSlide.onClick(this);
							revert();
						} else {
							revert();
						}
					}
				}
			}
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			revert();
		}
		// �������ص����¼�������������ڷ���ǰ�ַ��¼�
		viewDragHelper.processTouchEvent(event);
		// ��ʾ�������¼������������´���
		return true;
	}

	/**
	 * �ж��Ƿ��ڵ��ɾ����ť��
	 * 
	 * @param view
	 * @param ev
	 * @return
	 */
	private boolean inRangeOfView(View view, MotionEvent ev) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		if (ev.getX() > x && ev.getX() < (x + view.getWidth())
				&& ev.getRawY() > y && ev.getRawY() < (y + view.getHeight())) {
			return true;
		}
		return false;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (viewDragHelper.continueSettling(true)) {
			/**
			 * ����ʧЧ�����ڽ������Ķ���ʱ�䲽,ͨ������ʾ֡�� ����������Դ��ⲿ�ĵ���UI�߳�ֻ�е����ֹ۵��Ǹ��ӵ�һ�����ڡ�
			 */
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
}
