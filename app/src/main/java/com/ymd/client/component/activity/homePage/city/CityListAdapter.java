package com.ymd.client.component.activity.homePage.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ymd.client.R;
import com.ymd.client.component.adapter.MySimpleAdapter;
import com.ymd.client.component.widget.other.PinnedSectionListView;
import com.ymd.client.component.widget.recyclerView.MyGridView;
import com.ymd.client.model.bean.city.CityChooseObject;
import com.ymd.client.model.bean.city.CityEntity;
import com.ymd.client.model.info.LocationInfo;
import com.ymd.client.utils.ToolUtil;

/**
 * 城市选择的适配器
 * @author 荣维鹤
 *
 */
public class CityListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
	private List<CityChooseObject> list;
	private Activity context;
	private OnItemClick myItemClick;
	public List<CityChooseObject> getList() {
		return list;
	}
	public void setList(List<CityChooseObject> list) {
		if(list!=null){
			this.list = list;
		}else{
			list=new ArrayList<CityChooseObject>();
		}
	}
	public CityListAdapter(Context context,List<CityChooseObject> list){
		this.setList(list);
		this.context= (Activity)context;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CityChooseObject getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setOnItemClick(OnItemClick click) {
		this.myItemClick = click;
	}

	@Override
	public View getView(int position, View converView, ViewGroup viewGrop) {
		ViewHolder vh = null;
	//	if(converView == null){
			vh=new ViewHolder();
			converView=LayoutInflater.from(context).inflate(R.layout.city_choose_list_item, null);
			vh.textView=(TextView)converView.findViewById(R.id.textItem);
			vh.imageView=(ImageView)converView.findViewById(R.id.itemImage);
			vh.gridView = (MyGridView) converView.findViewById(R.id.gridView);
			converView.setTag(vh);
	/*	} else {
			vh=(ViewHolder) converView.getTag();
		}*/
		final CityChooseObject bean = getItem(position);

		/*if (bean.iconUrl != null && bean.iconUrl.trim().length() > 0)
			vh.imageView.setImageURI(Uri.parse(bean.iconUrl));*/
		if (bean.getType() == CityChooseObject.SECTION) {
			vh.textView.setText(bean.getCityName());
			vh.textView.setBackgroundResource(R.color.bg_color);
			vh.imageView.setVisibility(View.GONE);
		} else if (bean.getType() == CityChooseObject.ITEM){
			vh.textView.setBackgroundResource(R.drawable.list_view_item_selector);
			vh.imageView.setVisibility(View.VISIBLE);
			vh.textView.setText(bean.getCityName());
			vh.imageView.setVisibility(View.GONE);
		} else {
			vh.textView.setVisibility(View.GONE);
			vh.imageView.setVisibility(View.GONE);
			vh.gridView.setVisibility(View.VISIBLE);
			vh.gridView.setCacheColorHint(Color.TRANSPARENT);
			List<Map<String,Object>> citys = new ArrayList<>();
			for (CityEntity item : bean.getCitys()) {
				Map<String,Object> map = new HashMap<>();
				map.put("CITY", item.getCityName());
				citys.add(map);
			}
			MySimpleAdapter adapter = new MySimpleAdapter(context, citys, R.layout.city_text_item,
					new String[]{"CITY"}, new int[]{R.id.textItem});
			vh.gridView.setAdapter(adapter);
			vh.gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,	int p, long arg3) {
					//	Intent intent = new Intent(activity,)
					if (myItemClick != null) {
						myItemClick.onItemClick(bean.getCitys().get(p));
					}
				}
			});
		}
		return converView;
	}

	public class ViewHolder {
		public TextView textView;
		public ImageView imageView;
		public MyGridView gridView;
	}


	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == CityChooseObject.SECTION;//0是标题，1是内容
	}
/*
	@Override
	public int getViewTypeCount() {
		return 3;//3种view的类型 baseAdapter中得方法
	}*/
	@Override
	public int getItemViewType(int position) {
		return ((CityChooseObject)getItem(position)).getType();
	}
	public void refresh(ArrayList<CityChooseObject> arr){
		setList(arr);
		notifyDataSetChanged();
	}

	public interface OnItemClick {
		public void onItemClick(CityEntity bean);
	}

}