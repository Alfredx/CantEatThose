package com.tjerkw.slideexpandable.library;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tongji.sitp.canteatthose.ChooseActivity;
import com.tongji.sitp.canteatthose.Food;
import com.tongji.sitp.canteatthose.FoodType;
import com.tongji.sitp.canteatthose.R;

/**
 * A more specific expandable listview in which the expandable area
 * consist of some buttons which are context actions for the item itself.
 *
 * It handles event binding for those buttons and allow for adding
 * a listener that will be invoked if one of those buttons are pressed.
 *
 * @author tjerk
 * @date 6/26/12 7:01 PM
 */
public class ActionSlideExpandableListView extends SlideExpandableListView {
	private OnActionClickListener listener;
	private int[] buttonIds = null;
	


	public ActionSlideExpandableListView(Context context) {
		super(context);
	}

	public ActionSlideExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ActionSlideExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setItemActionListener(OnActionClickListener listener, int ... buttonIds) {
		this.listener = listener;
		this.buttonIds = buttonIds;
	}

	/**
	 * Interface for callback to be invoked whenever an action is clicked in
	 * the expandle area of the list item.
	 */
	public interface OnActionClickListener {
		/**
		 * Called when an action item is clicked.
		 *
		 * @param itemView the view of the list item
		 * @param clickedView the view clicked
		 * @param position the position in the listview
		 */
		public void onClick(View itemView, View clickedView, int position);
	}

	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(new WrapperListAdapterImpl(adapter) {

			@Override
			public View getView(final int position, View view, ViewGroup viewGroup) {
				final View listView = wrapped.getView(position, view, viewGroup);
				// add the action listeners
				if(buttonIds != null && listView!=null) {
					
					for(int id : buttonIds) {
						View buttonView = listView.findViewById(id);
						if(buttonView!=null) {
							buttonView.findViewById(id).setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									if(listener!=null) {
										listener.onClick(listView, view, position);
									}
								}
							});
						}
					}
				}
				return listView;
			}
		});
	}
	
	public void setAdapter(ListAdapter adapter,int toggle_object_id) {
		WrapperListAdapterImpl wlaimpl = new WrapperListAdapterImpl(adapter) {
			
			@Override
			public View getView(final int position, View view, ViewGroup viewGroup) {
				final View listView = wrapped.getView(position, view, viewGroup);

				// add the action listeners
				if(buttonIds != null && listView!=null) {
					final CharSequence listName = ((TextView)listView.findViewById(R.id.expandable_toggle_text)).getText();
					//new Task(listView,listName,position).execute();
					//linearlayout.removeAllViewsInLayout();
					if(!listName.equals("TextView")){
						final LinearLayout linearlayout = (LinearLayout)listView.findViewById(R.id.expandable);
						linearlayout.removeAllViews();
						FoodType type = ChooseActivity.type_set.get(listName);
						if((position+1) == type.id()){
							ArrayList<Food> foods = (ArrayList<Food>)ChooseActivity.manager.queryFoodByTypeId(type.id());
							for(final Food food :foods){
								Button textview = new Button(listView.getContext());
								textview.setId(food.id());
								try{
									textview.setCompoundDrawablesWithIntrinsicBounds(R.drawable.class.getField("f_"+food.id()).getInt(R.drawable.class), 0, 0, 0);												
								}catch(IllegalAccessException e){
									e.printStackTrace();
								}catch(NoSuchFieldException e){
									e.printStackTrace();
								}
								textview.setLongClickable(false);
								textview.setOnClickListener(new Button.OnClickListener(){
									@Override
									public void onClick(View v) {
										ChooseActivity.collectedData.put(v.getId(),v.getId());
										v.setBackgroundColor(0xffff0f);
										if(v.isClickable()){
											Toast.makeText(
													listView.getContext(),
													new StringBuilder(food.name()).append("已加入选择\n长按移出选择").toString(),
													Toast.LENGTH_SHORT
												).show();
										}
										v.setClickable(false);
										v.setLongClickable(true);
									}
								});
								textview.setOnLongClickListener(new Button.OnLongClickListener(){
									@Override
									public boolean onLongClick(View v) {
										ChooseActivity.collectedData.remove(v.getId());
										v.setBackgroundColor(0x0f0000);
										if(v.isLongClickable()){
											Toast.makeText(
													listView.getContext(),
													new StringBuilder(food.name()).append("已移出选择").toString(),
													Toast.LENGTH_SHORT
												).show(); 
										}
										v.setLongClickable(false);
										v.setClickable(true);
										return true;
									}
								});
								try{
									textview.setText(food.name());
								}catch(ArrayIndexOutOfBoundsException e){
									e.printStackTrace();
								}											
								linearlayout.addView(textview);
							}
						}
						
					}
					
					for(int id : buttonIds) {
						View buttonView = listView.findViewById(id);
						if(buttonView!=null) {
							buttonView.findViewById(id).setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View view) {
									if(listener!=null) {
										listener.onClick(listView, view, position);
									}
								}
							});
						}
					}
				}
				return listView;
			}
			
			
		};
		super.setAdapter(wlaimpl,toggle_object_id);
	}
	
	private class Task extends AsyncTask<Object, Void, Void>{
		private View listView;
		private CharSequence listName;
		private FoodType type;
		private ArrayList<Button> buttons;
		private ArrayList<Food> foods;
		private int position;
		public Task(View view, CharSequence listName, int position) {
			buttons = new ArrayList<Button>();
			this.listView = view;
			this.listName = listName;
			this.position = position;
			type = ChooseActivity.type_set.get(listName);
		}
		@Override
		protected Void doInBackground(Object... params) {
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
		}
		
	}
	
}
