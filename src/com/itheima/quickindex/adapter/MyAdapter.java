package com.itheima.quickindex.adapter;

import java.util.List;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itheima.quickindex.R;
import com.itheima.quickindex.domain.Person;

public class MyAdapter extends BaseAdapter {

	
	private final List<Person> persons;

	public MyAdapter(List<Person> persons) {
		this.persons = persons;
	}

	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view;
		if(convertView == null){
			view = View.inflate(parent.getContext(), R.layout.item_person_list, null);
		}else {
			view = convertView;
		}
	
		TextView tv_index = (TextView) view.findViewById(R.id.tv_index);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		
		Person person = persons.get(position);
		// 当前首字母
		String currentLetter = person.getPinyin().charAt(0) + "";
		
		// 判断/分组
		String letter = null;
		if(position == 0){
			// 索引为0
			letter = currentLetter;
		}else {
			// 当前 首字母 和 上一个 不同
			String preLetter = persons.get(position - 1).getPinyin().charAt(0) + "";
			if(!TextUtils.equals(preLetter, currentLetter)){
				// 不同, 需要显示当前首字母
				letter = currentLetter;
			}
		}
		
		tv_index.setText(letter);
		tv_index.setVisibility(letter == null ? View.GONE : View.VISIBLE);
		
		tv_name.setText(person.getName());
		
		return view;
	}
	
	
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


}
