package com.itheima.quickindex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ListView;

import com.itheima.quickindex.adapter.MyAdapter;
import com.itheima.quickindex.domain.Person;
import com.itheima.quickindex.ui.QuickIndexBar;
import com.itheima.quickindex.ui.QuickIndexBar.OnLetterUpdateListener;

public class MainActivity extends Activity implements OnLetterUpdateListener {

    private List<Person> persons;
	private ListView lv;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        QuickIndexBar qib = (QuickIndexBar) findViewById(R.id.qib);
        qib.setOnLetterUpdateListener(this);
        
        lv = (ListView) findViewById(R.id.lv);
        
        // 初始化数据
        
    	persons = new ArrayList<Person>();
    	fillAndSortData(persons);
        
        // 设置数据适配器
        lv.setAdapter(new MyAdapter(persons));
    }

    /**
     * 填充并给给数据排序
     */
    private void fillAndSortData(List<Person> persons) {
    	// 填充
    	for (int i = 0; i < Cheeses.NAMES.length; i++) {
    		persons.add(new Person(Cheeses.NAMES[i]));
		}
    	
    	// 排序
    	Collections.sort(persons);
	}

	@Override
    public void onLetterUpdate(String letter){
    	System.out.println(letter);
    	// 找到集合中首次出现letter的位置, 跳过去
    	for (int i = 0; i < persons.size(); i++) {
    		String l = persons.get(i).getPinyin().charAt(0) + "";
    		if(TextUtils.equals(l, letter)){
    			lv.setSelection(i);
    			break;
    		}
		}
    }
    
    
    
}
