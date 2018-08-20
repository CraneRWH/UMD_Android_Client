package com.ymd.client.component.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ymd.client.utils.ToolUtil;


/**
 * 自定义Adapter适配器
 * @author 荣维鹤
 */
public class MySimpleAdapter extends SimpleAdapter {

    private MyViewListener viewListener;

    private int[] mTo; // 指向布局里面控件的id 比如：R.id.btn
    private String[] mFrom; // 数据来源，来自Map里面的key
    private ViewBinder mViewBinder;// 接口类型，里面有个setViewValue方法，用于出现特殊类型控件比如：drawable的时候在外部初始化接口，实现具体方法

    private List<? extends Map<String, ?>> mData;// 用List打包的Map数据源

    private int mResource;// 布局
    private int mDropDownResource;// 不知道干嘛用的，但是估计也是留给外部调用的
    private LayoutInflater mInflater;// 这个大家都知道，LayoutInflater用来载入界面

    private ArrayList<Map<String, ?>> mUnfilteredData;

    private Map<Integer,FormatCallBack> formats = new HashMap<Integer,FormatCallBack>();

    // SimpleAdapter初始化，将传进了的参数都赋给本地的对应变量
    public MySimpleAdapter(Context context,List<? extends Map<String, ?>> data, int resource,String[] from, int[] to) {
        super(context, data, resource, from, to);
        setDefaultData(context, data, resource, from, to);
    }

    public MySimpleAdapter(Context context,List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,
                           MyViewListener viewListener) {
        super(context, data, resource, from, to);
        setDefaultData(context, data, resource, from, to);
        this.setViewListener(viewListener);
    }

    private void setDefaultData(Context context,List<? extends Map<String, ?>> data, int resource,String[] from, int[] to) {
        mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View view = createViewFromResource(position, convertView, parent, mResource);;
        if (viewListener != null) {
            viewListener.callBackViewListener((Map<String,Object>)getItem(position), view, parent, position);
        }
        return view;
    }

    public MyViewListener getMyViewListener() {
        return viewListener;
    }

    public void setViewListener(MyViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public interface MyViewListener{
        void callBackViewListener(Map<String, Object> data,View view, ViewGroup parent,int position);
    }


    /*
     * ListView 针对每个item，要求 adapter “返回一个视图” (getView)，
     * 也就是说ListView在开始绘制的时候，系统首先调用getCount（）函数，根据他的返回值得到ListView的长度，
     * 然后根据这个长度，调用getView（）一行一行的绘制ListView的每一项。如果你的getCount（）返回值是0的话，
     * 列表一行都不会显示，如果返回1，就只显示一行。返回几则显示几行。如果我们有几千几万甚至更多的item要显示怎么办？
     * 为每个Item创建一个新的View？不可能！！！实际上Android早已经缓存了这些视图，大家可以看下下面这个截图来理解下，
     * 这个图是解释ListView工作原理的最经典的图了大家可以收藏下，不懂的时候拿来看看，加深理解，
     * 其实Android中有个叫做Recycler的构件
     */
    public int getCount() {
        return mData.size();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /*
     * ListView的机制在这里再说一下，当第一个数据来getView的时候convertView肯定是null，
     * 那么就用mInflater.inflate(resource, parent,
     * false)给它初始化一个View，但是当一屏滑到底了，第一个item，滑出了屏幕，那么它将
     * 从底部出来，那时候convertView就不为null，这个方法的好处就是当convertView不为null
     * 的时候不用加载布局，直接使用convertView， 节省了一步，这也是所说的优化ListView的第一个步骤。
     */
    private View createViewFromResource(int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        // 这个方法是核心
        try {
            bindView(position, v);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public void setDropDownViewResource(int resource) {
        this.mDropDownResource = resource;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent,
                mDropDownResource);
    }

    // 这个方法的主要功能就是按照 to数组里面控件的顺序挨个赋值，比如new int[]{R.id.tv_name,R.id.tv_pwd}。
    private void bindView(int position, View view) throws Exception {
        final Map dataSet = mData.get(position);// 找到对应的position位置的map数据
        // 如果没找到跳出
        if (dataSet == null) {
            return;
        }
        /*
         * 将外部实现的ViewBinder，赋值给本地，SimpleAdapter能够实现：
         * checkBox,CheckedTextView,TextView
         * ,ImageView,数据类型也就是Boolean，Integer，Stirng， 所以特殊数据类型的时候才用到ViewBinder
         * ,如果没用到就不需要外部实现ViewBinder接口和里面的方法
         */
        final ViewBinder binder = mViewBinder;
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;
        //view.findViewById(to[i])，循环找控件
        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            //如果v不为空的话，找到对应的数据，Object类型的data转换为String，因为boolean在map里面纯的也是true或者false
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (formats.containsKey(to[i])) {
                    text = formats.get(to[i]).Format(dataSet,data);
                }
                if (text == null) {
                    text = "";
                }
                //标志变量bound，判断外部有没有实现ViewBinder，如果实现了，就执行binder.setViewValue(v, data, text)，如果符合特殊条件，就返回true
                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }
                //如果满足if (!bound)那么bound就还是false,说明是普通数据
                if (!bound) {
                    //查看v是不是Checkable的实例化类型，满足的情况可能是CheckBox，CheckedTextView
                    if (v instanceof Checkable) {
                        //如果数据类型是boolean，那么就是CheckBox
                        ((Checkable) v).setChecked(ToolUtil.changeBoolean(data));
                        /*if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                            //如果不是CheckBox，那么判断是不是继承TextView的CheckedTextView，是的话赋值，不是就抛出异常
                        } else if (v instanceof TextView) {
                            setViewText((TextView) v, text);
                        } else {
                            throw new IllegalStateException(v.getClass()
                                    .getName()
                                    + " should be bound to a Boolean, not a "
                                    + (data == null ? "<unknown type>"
                                            : data.getClass()));
                        }
*/                        //如果不是Checkable的实例化类型，判断是不是TextView的实例化类型
                    } else if (v instanceof TextView) {
                        setViewText((TextView) v, text);
                        //都不是以上情况，就判断一下是不是ImageView的实例化类型
                    }
                    else if (v instanceof ImageView) {
                        //这里只满足数据类型为int也就是R.drawable.ic，和String类型的url，如果想实现直接用drawbale，就要实现ViewBinder
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    }
                    else {
                        throw new IllegalStateException(
                                v.getClass().getName()
                                        + " 不是一个View,不能被MySimpleAdapter赋值 ");
                    }
                }
            }
        }
    }

    public void setFormat(int viewId,FormatCallBack back) {
        formats.put(viewId, back);
    }

    /*public void setFormat(int viewId, ,FormatCallBack back) {
    	formats.put(viewId, back);
    }*/

    public interface FormatCallBack{
        //  	public String Format(Object result);
        public String Format(Map<String,Object> data, Object result);
    }


    public ViewBinder getViewBinder() {
        return mViewBinder;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            v.setImageURI(Uri.parse(value));
        }
    }

    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

}
